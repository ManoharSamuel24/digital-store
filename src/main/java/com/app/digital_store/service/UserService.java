package com.app.digital_store.service;

import com.app.digital_store.entity.User;
import com.app.digital_store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void increaseDownloads(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(user -> {
            int current = user.getDownloads();
            user.setDownloads(current + 1);
            userRepository.save(user);
        });
    }

    public String getUsernameById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(User::getUsername).orElse("Unknown User");
    }

    public Optional<User> findById(int id){
        return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
