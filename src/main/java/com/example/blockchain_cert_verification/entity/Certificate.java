package com.example.blockchain_cert_verification.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @Column(unique = true, nullable = false)
    private String hashValue;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime uploadedAt;

    @ManyToOne
    private User user;
}
