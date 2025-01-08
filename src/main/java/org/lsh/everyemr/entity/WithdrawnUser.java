package org.lsh.everyemr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "withdrawn_users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawnUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // 탈퇴 기록 고유 식별자

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 탈퇴한 사용자 (FK)

    @Column(name = "reason")
    private String reason;  // 탈퇴 사유

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;  // 탈퇴일
}
