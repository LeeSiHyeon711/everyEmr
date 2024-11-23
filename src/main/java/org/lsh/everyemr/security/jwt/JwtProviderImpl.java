package org.lsh.everyemr.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.lsh.everyemr.security.UserPrinciple;
import org.lsh.everyemr.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;



import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtProviderImpl implements JwtProvider {

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;


    @Override
    public String generateToken(UserPrinciple auth) { // 토큰 생성 메서드

        // 권한 정보를 문자열로 변환
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // HMAC 키 생성
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        log.info("JWT Secret Key: {}", JWT_SECRET);
        log.info("JWT Key Length: {}", JWT_SECRET.getBytes(StandardCharsets.UTF_8).length);
        log.info("Signing Algorithm: {}", SignatureAlgorithm.HS256);

        // JWT 생성
        String token = Jwts.builder()
                .setSubject(String.valueOf(auth.getId())) // userId를 항상 저장
                .claim("roles", authorities) // 권한 정보 추가
                .claim("userId", auth.getId()) // 사용자 ID 추가
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS)) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘 설정
                .compact();

        log.info("Generated JWT: {}", token); // 토큰 디버깅 로그
        return Jwts.builder()
                .setSubject(String.valueOf(auth.getId())) // sub에 userId 저장
                .claim("userId", auth.getId()) // userId 클레임 추가
                .claim("username", auth.getUsername()) // username을 별도 claim으로 추가
                .claim("roles", authorities) // roles 추가
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }



    public Authentication getAuthentication(HttpServletRequest request) {
        Claims claims = extractClaims(request);
        if (claims == null) return null;

        String username = claims.get("username", String.class); // username 클레임 참조
        Long userId = claims.get("userId", Long.class); // userId 클레임 참조
        String role = claims.get("role", String.class);


        List<GrantedAuthority> authorities = Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(
                new UserPrinciple(userId, username, authorities), null, authorities);
    }



    @Override
    public boolean isTokenValid(HttpServletRequest request) { // 토큰의 유효성 검사

        Claims claims = extractClaims(request);

        if (claims == null) {
            log.info("claims null error");
            return false;
        }

        if (claims.getExpiration().before(new Date())) return false;

        return true;
    }

    private Claims extractClaims(HttpServletRequest request) {
        String token = SecurityUtils.extractAuthTokenFromRequest(request);
        if (token == null) {
            log.error("Token is null or not found in the request");
            return null;
        }

        log.info("Extracted Token: {}", token);

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.info("Extracted Claims: {}", claims);
            return claims;
        } catch (Exception e) {
            log.error("Failed to extract claims from token. Error: {}", e.getMessage());
            return null;
        }
    }

}
