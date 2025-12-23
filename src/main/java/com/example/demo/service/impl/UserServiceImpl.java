// package com.example.demo.service.impl;

// import com.example.demo.exception.ApiException;
// import com.example.demo.model.User;
// import com.example.demo.repository.UserRepository;
// import com.example.demo.service.UserService;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// @Service   
// public class UserServiceImpl implements UserService {

//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;

//     public UserServiceImpl(UserRepository userRepository,
//                            PasswordEncoder passwordEncoder) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Override
//     public User register(User user) {

//         // check duplicate email
//         userRepository.findByEmail(user.getEmail())
//                 .ifPresent(u -> {
//                     throw new ApiException("Email already exists");
//                 });

//         // default role
//         if (user.getRole() == null) {
//             user.setRole("STAFF");
//         }

//         // hash password
//         user.setPassword(passwordEncoder.encode(user.getPassword()));

//         return userRepository.save(user);
//     }

//     @Override
//     public User findByEmail(String email) {
//         return userRepository.findByEmail(email)
//                 .orElseThrow(() -> new ApiException("User not found"));
//     }
// }
