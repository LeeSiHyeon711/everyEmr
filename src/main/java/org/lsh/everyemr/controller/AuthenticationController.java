package org.lsh.everyemr.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.lsh.everyemr.dto.AuthResponse;
import org.lsh.everyemr.dto.SignInRequest;
import org.lsh.everyemr.entity.User;
import org.lsh.everyemr.service.AuthenticationService;
import org.lsh.everyemr.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("api/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;


    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        // 사용자 저장
        User savedUser = userService.saveUser(user);
        // JWT 생성
        String token = authenticationService.generateToken(savedUser);
        // AuthResponse 반환
        return new ResponseEntity<>(new AuthResponse(savedUser, token), HttpStatus.CREATED);
    }


    @PostMapping("/sign-in")
    public ResponseEntity<Object> signIn(@RequestBody SignInRequest request) {
        try {
            // 로그인 성공 시 JWT 생성
            String token = authenticationService.signInAndReturnJWT(request);
            // 사용자 정보 가져오기
            User user = userService.findByUsername(request.getUsername());
            // 응답으로 토큰과 최소한의 사용자 정보 반환
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "user", Map.of(
                            "username", user.getUsername(),
                            "role", user.getRole()
                    )
            ));
        } catch (Exception e) {
            // 인증 실패 시 401 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}
