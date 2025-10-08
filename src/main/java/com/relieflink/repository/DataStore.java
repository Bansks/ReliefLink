package com.relieflink.repository;

import com.relieflink.model.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class DataStore {
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<Long, Donation> donations = new ConcurrentHashMap<>();
    private final Map<Long, Request> requests = new ConcurrentHashMap<>();
    private final Map<Long, Match> matches = new ConcurrentHashMap<>();
    
    private final AtomicLong userIdCounter = new AtomicLong(1);
    private final AtomicLong donationIdCounter = new AtomicLong(1);
    private final AtomicLong requestIdCounter = new AtomicLong(1);
    private final AtomicLong matchIdCounter = new AtomicLong(1);

    public DataStore() {
        User admin = new User();
        admin.setId(userIdCounter.getAndIncrement());
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setEmail("admin@relieflink.com");
        admin.setFullName("System Administrator");
        admin.setLocation("Central Command");
        admin.setRole(UserRole.ADMIN);
        users.put(admin.getId(), admin);
    }

    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setId(userIdCounter.getAndIncrement());
        }
        users.put(user.getId(), user);
        return user;
    }

    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<User> findUserByUsername(String username) {
        return users.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void deleteUser(Long id) {
        users.remove(id);
    }

    public Donation saveDonation(Donation donation) {
        if (donation.getId() == null) {
            donation.setId(donationIdCounter.getAndIncrement());
        }
        donations.put(donation.getId(), donation);
        return donation;
    }

    public Optional<Donation> findDonationById(Long id) {
        return Optional.ofNullable(donations.get(id));
    }

    public List<Donation> findAllDonations() {
        return new ArrayList<>(donations.values());
    }

    public List<Donation> findUnmatchedDonations() {
        return donations.values().stream()
                .filter(d -> !d.isMatched())
                .collect(Collectors.toList());
    }

    public Request saveRequest(Request request) {
        if (request.getId() == null) {
            request.setId(requestIdCounter.getAndIncrement());
        }
        requests.put(request.getId(), request);
        return request;
    }

    public Optional<Request> findRequestById(Long id) {
        return Optional.ofNullable(requests.get(id));
    }

    public List<Request> findAllRequests() {
        return new ArrayList<>(requests.values());
    }

    public List<Request> findUnmatchedRequests() {
        return requests.values().stream()
                .filter(r -> !r.isMatched())
                .collect(Collectors.toList());
    }

    public Match saveMatch(Match match) {
        if (match.getId() == null) {
            match.setId(matchIdCounter.getAndIncrement());
        }
        matches.put(match.getId(), match);
        return match;
    }

    public List<Match> findAllMatches() {
        return new ArrayList<>(matches.values());
    }

    public void resetAll() {
        donations.clear();
        requests.clear();
        matches.clear();
        users.clear();
        
        User admin = new User();
        admin.setId(userIdCounter.getAndIncrement());
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setEmail("admin@relieflink.com");
        admin.setFullName("System Administrator");
        admin.setLocation("Central Command");
        admin.setRole(UserRole.ADMIN);
        users.put(admin.getId(), admin);
    }

    public Map<String, Object> exportData() {
        Map<String, Object> data = new HashMap<>();
        data.put("users", new ArrayList<>(users.values()));
        data.put("donations", new ArrayList<>(donations.values()));
        data.put("requests", new ArrayList<>(requests.values()));
        data.put("matches", new ArrayList<>(matches.values()));
        return data;
    }
}
