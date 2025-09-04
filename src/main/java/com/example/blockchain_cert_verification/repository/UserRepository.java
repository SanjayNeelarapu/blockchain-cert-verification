package com.example.blockchain_cert_verification.repository;

import com.example.blockchain_cert_verification.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> { }
