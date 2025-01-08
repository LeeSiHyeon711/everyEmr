package org.lsh.everyemr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "sync_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // 동기화 기록 고유 식별자

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;  // 동기화된 병원 (FK)

    @Column(name = "action", nullable = false)
    private String action;  // 동기화된 내용

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;  // 동기화된 시간
}
