package ru.adamrain.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.adamrain.main.exception.AlreadyExistException;
import ru.adamrain.main.repository.UserRepository;
import ru.adamrain.main.security.SecurityService;
import ru.adamrain.main.web.model.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    private final SecurityService securityService;

    @GetMapping("/get")
    public ResponseEntity<String> get(){
        System.out.println("sdasdasdas");
        return ResponseEntity.ok("asdasd");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(securityService.authenticateUser(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<SimpleResponse> registerUser(@RequestBody CreateUserRequest loginRequest) {

        if(userRepository.existsByTel(loginRequest.getTel())){
            throw new AlreadyExistException("Phone number exists!");
        }

        securityService.register(loginRequest);


        return ResponseEntity.ok(new SimpleResponse("user created!"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(securityService.refreshTokenResponse(request));
    }

    public ResponseEntity<SimpleResponse> logoutUser(@AuthenticationPrincipal UserDetails userDetails){
        securityService.logout();

        return ResponseEntity.ok(new SimpleResponse("User logout. PhoneNumber is: " + userDetails.getUsername()));
    }
}
