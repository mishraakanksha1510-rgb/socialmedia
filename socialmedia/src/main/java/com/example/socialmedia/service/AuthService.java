package com.example.socialmedia.service;

import com.example.socialmedia.dto.RegisterRequest;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.socialmedia.dto.Loginreq;
import com.example.socialmedia.service.JwtService;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

   public AuthService(UserRepository userRepository,
                   PasswordEncoder passwordEncoder,
                   JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
}

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already registered";
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            return "Phone number already registered";
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAge(request.getAge());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);

        return "User registered successfully";

    }

    public String login(Loginreq request) {

        User user = userRepository.findByEmail(request.getEmail())
            .orElse(null);

         if (user == null) {
             return "Invalid email or password";
    }

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        return "Invalid email or password";
    }

    return jwtService.generateToken(user.getEmail());
    }

    
}