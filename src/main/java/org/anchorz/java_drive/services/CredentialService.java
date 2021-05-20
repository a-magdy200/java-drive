package org.anchorz.java_drive.services;

import org.anchorz.java_drive.mappers.CredentialMapper;
import org.anchorz.java_drive.models.Credential;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class CredentialService {
    private List<Credential> credentials;
    private final CredentialMapper credentialMapper;
    public List<Credential> getAllCredentials(int userId) {
        this.credentials = credentialMapper.getAllCredentials(userId);
        return credentials;
    }
    public void addCredential(Credential credential) {
        credential.setCredentialId(this.credentialMapper.createCredential(credential));
    }
    public Credential getCredential(int credentialId) {
        return this.credentialMapper.getCredentials(credentialId);
    }
    public void updateCredential(Credential credential) {
        this.credentialMapper.updateCredential(credential);
    }
    public void deleteCredential(int credentialId) {
        this.credentialMapper.deleteCredential(credentialId);
    }
    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }
    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating Credentials Service bean");
        this.credentials = new ArrayList<>();
    }
}
