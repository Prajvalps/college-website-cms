package com.college.cms.repository;

import com.college.cms.model.PrincipalMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrincipalMessageRepository extends JpaRepository<PrincipalMessage, Long> {
}