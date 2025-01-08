package org.lsh.everyemr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_resets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordReset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // 요청 ID

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 요청한 사용자 (FK)

    @Column(name = "reset_token", nullable = false, unique = true)
    private String resetToken;  // 비밀번호 재설정 토큰

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;  // 요청 생성일

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;  // 토큰 만료 시간
}
