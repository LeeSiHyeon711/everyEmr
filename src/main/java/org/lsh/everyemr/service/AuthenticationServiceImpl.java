package org.lsh.everyemr.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.lsh.everyemr.dto.SignInRequest;
import org.lsh.everyemr.entity.User;
import org.lsh.everyemr.security.UserPrinciple;
import org.lsh.everyemr.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String signInAndReturnJWT(SignInRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(userPrinciple);

        User signInUser = userService.findByUsername(request.getUsername());

        if (signInUser == null || !passwordEncoder.matches(request.getPassword(), signInUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        log.info("응급실 알리미 로그인 처리 닉네임 : " + request.getUsername());
        log.info("로그인 한 유저의 모든 정보 : " + request);
        signInUser.setToken(jwt);

        log.info("signInUser : " + signInUser);

        return generateToken(signInUser); // signInUser는 User 객체
    }

    @Override
    public String generateToken(User user) {
        // HMAC용 Secret Key 생성
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        log.info("generateToken 메서드에서 생성됀 토큰 : "+ key);

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId())) // 사용자 ID를 subject로 설정
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS)) // 만료 시간
                .claim("role", user.getRole()) // 클레임 추가
                .claim("username", user.getUsername()) // Username을 추가 클레임으로 설정
                .claim("userId", user.getId()) // userId 클레임 추가
                .signWith(key, SignatureAlgorithm.HS256) // 서명 추가
                .compact(); // 토큰 생성
    }

}
