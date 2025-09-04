package com.example.blockchain_cert_verification.repository;

import com.example.blockchain_cert_verification.entity.BlockchainRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockchainRepository extends JpaRepository<BlockchainRecord, Long> {
    Optional<BlockchainRecord> findByHashValue(String hashValue);

    void deleteByHashValue(String hash);
}
