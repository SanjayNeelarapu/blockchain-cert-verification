package com.example.blockchain_cert_verification.repository;

import com.example.blockchain_cert_verification.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Optional<Certificate> findByHashValue(String hashValue);
}
