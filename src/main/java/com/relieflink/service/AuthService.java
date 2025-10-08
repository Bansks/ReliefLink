package com.relieflink.service;

import com.relieflink.model.User;
import com.relieflink.repository.DataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private DataStore dataStore;

    public Optional<User> login(String username, String password) {
        return dataStore.findUserByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }

    public User register(User user) {
        return dataStore.saveUser(user);
    }

    public boolean usernameExists(String username) {
        return dataStore.findUserByUsername(username).isPresent();
    }
}
