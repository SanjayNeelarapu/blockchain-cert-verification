package com.example.blockchain_cert_verification.controller;

import com.example.blockchain_cert_verification.dto.ApiResponse;
import com.example.blockchain_cert_verification.dto.uploadResult;
import com.example.blockchain_cert_verification.entity.Certificate;
import com.example.blockchain_cert_verification.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;


    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadCertificate(@RequestParam("file") MultipartFile file) throws IOException {
        uploadResult result = certificateService.uploadCertificate(file);
        if(result.isAlreadyExists()){
            return ResponseEntity.ok(new ApiResponse("already exists", "Certificate already exists with hash : " + result.getCertificate().getHashValue(), result.getCertificate()));
        }
        return ResponseEntity.ok(new ApiResponse("success", "Certificate uploaded successfully with hash : " + result.getCertificate().getHashValue()));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse> verifyCertificate(@RequestParam("file") MultipartFile file) throws IOException {
        boolean isValid = certificateService.verifyCertificate(file);
        if (isValid) {
            return ResponseEntity.ok(new ApiResponse("genuine", "Genuine Contract: Verified successfully!"));
        } else {
            return ResponseEntity.ok(new ApiResponse("fake", " Fake Contract: Verification failed!"));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Certificate>> getAllCertificates(){
        List<Certificate> certificates = certificateService.getAllCertificates();
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/byId/{Id}")
    public ResponseEntity<ApiResponse> getCertificateById(@PathVariable Long Id){
        return certificateService.getCertificateById(Id)
                .map(cert -> ResponseEntity.ok(new ApiResponse("success", "Certificate found", cert)))
                .orElse(ResponseEntity.status(404)
                        .body(new ApiResponse("error", "Certificate not found")));
    }


    @Transactional
    @DeleteMapping("/byId/{Id}")
    public ResponseEntity<ApiResponse> deleteCertificate(@PathVariable Long Id) {
        try {
            boolean status = certificateService.deleteCertificate(Id);
            if (status) {
                return ResponseEntity.ok(
                        new ApiResponse("deleted", "Certificate deleted successfully!", null)
                );
            } else {
                return ResponseEntity.status(404).body(
                        new ApiResponse("not_found", "Certificate not found with id " + Id, null)
                );
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new ApiResponse("error_deleting", "An error occurred while deleting certificate", e.getMessage())
            );
        }
    }

}
