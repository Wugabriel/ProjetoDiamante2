package com.diamante.user.service;

import com.diamante.user.entity.User;
import com.diamante.user.entity.UserPreferences;
import com.diamante.user.repository.UserPreferencesRepository;
import com.diamante.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserPreferencesRepository preferencesRepository;

    public UserService(UserRepository userRepository, UserPreferencesRepository preferencesRepository) {
        this.userRepository = userRepository;
        this.preferencesRepository = preferencesRepository;
    }

    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    public UserPreferences savePreferences(Long userId, UserPreferences preferences) {
        User user = findById(userId);
        preferencesRepository.findByUser_Id(userId).ifPresent(preferencesRepository::delete);
        preferences.setUser(user);
        return preferencesRepository.save(preferences);
    }

    public UserPreferences getPreferences(Long userId) {
        return preferencesRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Preferences not found for user: " + userId));
    }
}
