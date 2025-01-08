package org.lsh.everyemr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // 로그 ID

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 수정한 사용자 (FK)

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;  // 수정된 병원 (FK)

    @Column(name = "action", nullable = false)
    private String action;  // 수정 내용

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;  // 수정 시간
}
