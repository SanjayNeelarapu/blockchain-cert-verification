package com.example.blockchain_cert_verification.service;

import com.example.blockchain_cert_verification.dto.uploadResult;
import com.example.blockchain_cert_verification.entity.BlockchainRecord;
import com.example.blockchain_cert_verification.entity.Certificate;
import com.example.blockchain_cert_verification.repository.BlockchainRepository;
import com.example.blockchain_cert_verification.repository.CertificateRepository;
import com.example.blockchain_cert_verification.util.HashUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final BlockchainRepository blockchainRepository;

    public CertificateService(CertificateRepository certificateRepository,
                              BlockchainRepository blockchainRepository) {
        this.certificateRepository = certificateRepository;
        this.blockchainRepository = blockchainRepository;
    }

    // Save new contract
    public uploadResult uploadCertificate(MultipartFile file) throws IOException {
        // 1. Hash the uploaded file
        String hash = HashUtil.generateHash(file.getBytes());
        Optional<Certificate> existing = certificateRepository.findByHashValue(hash);

        if(existing.isPresent()){
            return new uploadResult(true, existing.get());
        }

        // 2. Save certificate
        Certificate cert = new Certificate();
        cert.setFileName(file.getOriginalFilename());
        cert.setHashValue(hash);
        certificateRepository.saveAndFlush(cert);

        // 3. Save blockchain record
        BlockchainRecord record = new BlockchainRecord();
        record.setHashValue(hash);
        record.setTimestamp(LocalDateTime.now());
        blockchainRepository.saveAndFlush(record);

        return new uploadResult(false, cert);
    }

    // Verify contract
    public boolean verifyCertificate(MultipartFile file) throws IOException {
        String hash = HashUtil.generateHash(file.getBytes());
        return blockchainRepository.findByHashValue(hash).isPresent();
    }

    // list all certificates
    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }

    // get certificate by id
    public Optional<Certificate> getCertificateById(Long Id) {
        return certificateRepository.findById(Id);
    }

    // delete certificate
    public boolean deleteCertificate(Long id) {
        return certificateRepository.findById(id).map(cert -> {
            // delete blockchain record using hash
            blockchainRepository.deleteByHashValue(cert.getHashValue());
            // delete certificate itself
            certificateRepository.deleteById(id);
            return true; // successfully deleted
        }).orElse(false); // certificate not found
    }

}