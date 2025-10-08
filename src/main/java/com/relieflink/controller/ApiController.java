package com.relieflink.controller;

import com.relieflink.model.*;
import com.relieflink.repository.DataStore;
import com.relieflink.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {
    
    @Autowired
    private DonationService donationService;
    
    @Autowired
    private RequestService requestService;
    
    @Autowired
    private MatchingService matchingService;
    
    @Autowired
    private DataStore dataStore;

    @PostMapping("/donations")
    public Donation createDonation(@RequestBody Donation donation, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        String username = (String) session.getAttribute("username");
        donation.setDonorId(userId);
        donation.setDonorName(username);
        return donationService.createDonation(donation);
    }

    @GetMapping("/donations")
    public List<Donation> getAllDonations() {
        return donationService.getAllDonations();
    }

    @PostMapping("/requests")
    public Request createRequest(@RequestBody Request request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        String username = (String) session.getAttribute("username");
        request.setRequesterId(userId);
        request.setRequesterName(username);
        return requestService.createRequest(request);
    }

    @GetMapping("/requests")
    public List<Request> getAllRequests() {
        return requestService.getAllRequests();
    }

    @PostMapping("/matches/find")
    public List<Match> findMatches() {
        return matchingService.findMatches();
    }

    @GetMapping("/matches")
    public List<Match> getAllMatches() {
        return matchingService.getAllMatches();
    }

    @PostMapping("/admin/reset")
    public String resetSystem() {
        dataStore.resetAll();
        return "{\"success\": true, \"message\": \"System reset successfully\"}";
    }

    @GetMapping("/admin/backup")
    public Map<String, Object> backupData() {
        return dataStore.exportData();
    }

    @GetMapping("/admin/users")
    public List<User> getAllUsers() {
        return dataStore.findAllUsers();
    }
}
