package com.college.cms.service;

import com.college.cms.model.PrincipalMessage;
import com.college.cms.repository.PrincipalMessageRepository;
import org.springframework.stereotype.Service;

@Service
public class PrincipalMessageService {

    private final PrincipalMessageRepository repository;

    public PrincipalMessageService(PrincipalMessageRepository repository) {
        this.repository = repository;
    }

    public PrincipalMessage save(PrincipalMessage message) {
        message.setId(1L); // Always overwrite same record
        return repository.save(message);
    }

    public PrincipalMessage get() {
        return repository.findById(1L).orElse(null);
    }
}