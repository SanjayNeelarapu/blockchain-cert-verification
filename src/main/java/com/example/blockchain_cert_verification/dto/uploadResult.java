package com.example.blockchain_cert_verification.dto;

import com.example.blockchain_cert_verification.entity.Certificate;
import lombok.Getter;

@Getter
public class uploadResult {
    private boolean alreadyExists;
    private Certificate certificate;

    public uploadResult(boolean alreadyExists, Certificate certificate) {
        this.alreadyExists = alreadyExists;
        this.certificate = certificate;
    }

}
