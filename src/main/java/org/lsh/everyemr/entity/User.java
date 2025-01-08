package org.lsh.everyemr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.lsh.everyemr.model.AccountStatus;
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
    private int id;  // 고유 ID

    @Column(name = "username", nullable = false, unique = true)
    private String username;  // 사용자 닉네임 (유니크 추가)

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;  // 사용자 이메일 (유니크 추가)

    @Enumerated(EnumType.STRING)  // Enum을 String 형태로 저장
    @Column(name = "role", nullable = false)
    private Role role = Role.USER;  // 기본 역할 (ROLE_USER, ROLE_ADMIN, ROLE_MASTER)

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;  // 계정 상태 (ACTIVE/WITHDRAWN)

    @Column(name = "is_loading", nullable = false)
    private Boolean isLoading = false;  // 권한 부여 대기 상태 여부

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;  // 계정 생성일

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;  // 계정 수정일

    @Transient
    private String token;  // 인증 토큰 (DB 저장 안 함)
}
