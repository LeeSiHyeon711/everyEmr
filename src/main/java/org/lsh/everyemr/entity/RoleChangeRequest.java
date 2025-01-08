package org.lsh.everyemr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.lsh.everyemr.model.RoleChangeStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "role_change_requests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleChangeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // 요청 ID

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 요청한 사용자 (FK)

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RoleChangeStatus status = RoleChangeStatus.PENDING;  // 요청 상태

    @Column(name = "reason")
    private String reason;  // 요청 사유

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy;  // 검토한 관리자 (FK)

    @Column(name = "review_comment")
    private String reviewComment;  // 검토 의견

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;  // 생성일
}
