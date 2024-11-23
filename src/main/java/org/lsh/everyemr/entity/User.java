package org.lsh.everyemr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.lsh.everyemr.model.Role;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 고유 ID

    @Column(name = "k_id", nullable = false, unique = true)
    private Long kakaoId;  // 카카오 사용자 고유 ID

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;  // 사용자 이메일

    @Column(name = "username", nullable = false)
    private String username;  // 사용자 닉네임

    @Enumerated(EnumType.STRING)  // Enum을 String 형태로 저장
    @Column(name = "role", nullable = false)
    private Role role = Role.USER;  // 기본 역할 (ROLE_USER, ROLE_ADMIN, ROLE_MASTER)

    @Column(name = "is_loading", nullable = false)
    private Boolean isLoading = false;  // 권한 부여 대기 상태 여부

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;  // 계정 생성일

    @Transient
    private String token;
}
