package org.lsh.everyemr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.lsh.everyemr.model.CongestionLevel;
import org.lsh.everyemr.model.SyncStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "hospitals")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // 병원 고유 ID

    @Column(name = "name", nullable = false)
    private String name;  // 병원 이름

    @Column(name = "address", nullable = false)
    private String address;  // 병원 주소

    @Column(name = "phone_number")
    private String phoneNumber;  // 병원 전화번호

    @Column(name = "region")
    private String region;  // 병원 지역

    @Enumerated(EnumType.STRING)
    @Column(name = "congestion_level", nullable = false)
    private CongestionLevel congestionLevel = CongestionLevel.LOW;  // 혼잡도

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;  // 생성일

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;  // 수정일

    @Enumerated(EnumType.STRING)
    @Column(name = "sync_status", nullable = false)
    private SyncStatus syncStatus = SyncStatus.SYNCED;  // 동기화 상태
}
