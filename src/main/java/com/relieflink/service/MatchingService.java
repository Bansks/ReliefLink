package com.relieflink.service;

import com.relieflink.model.*;
import com.relieflink.repository.DataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchingService {
    
    @Autowired
    private DataStore dataStore;

    public List<Match> findMatches() {
        List<Match> newMatches = new ArrayList<>();
        List<Donation> unmatchedDonations = dataStore.findUnmatchedDonations();
        List<Request> unmatchedRequests = dataStore.findUnmatchedRequests();

        for (Donation donation : unmatchedDonations) {
            for (Request request : unmatchedRequests) {
                if (isCompatible(donation, request)) {
                    Match match = new Match(donation, request);
                    dataStore.saveMatch(match);
                    
                    donation.setMatched(true);
                    request.setMatched(true);
                    dataStore.saveDonation(donation);
                    dataStore.saveRequest(request);
                    
                    newMatches.add(match);
                    break;
                }
            }
        }
        return newMatches;
    }

    private boolean isCompatible(Donation donation, Request request) {
        if (donation.getCategory() != request.getCategory()) {
            return false;
        }
        
        if (!donation.getLocation().equalsIgnoreCase(request.getLocation())) {
            return false;
        }
        
        if (donation.getQuantity() < request.getQuantity()) {
            return false;
        }
        
        return true;
    }

    public List<Match> getAllMatches() {
        return dataStore.findAllMatches();
    }
}
