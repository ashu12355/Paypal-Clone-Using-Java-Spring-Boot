package com.paypal.userService.controller;

import com.paypal.userService.dto.JwtResponse;
import com.paypal.userService.dto.LoginRequest;
import com.paypal.userService.dto.SignupRequest;
import com.paypal.userService.entity.User;
import com.paypal.userService.repository.UserRepository;
import com.paypal.userService.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;


    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                          JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest){
        Optional<User> existingUser = userRepository.findByEmail(signupRequest.getEmail());

        if(existingUser.isPresent()){
            return ResponseEntity.badRequest().body("User Already exist");
        }

        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userRepository.save(user);

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok("User Registered Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());

        if(userOpt.isEmpty()){
            return ResponseEntity.status(401).body("User not Found");
        }
        User user = userOpt.get();
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            return ResponseEntity.status(401).body("Invalid Credentials");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(),user.getRole());

        return ResponseEntity.ok(new JwtResponse(token));
    }


}
