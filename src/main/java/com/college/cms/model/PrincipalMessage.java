package com.college.cms.model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping("/api/principal-message")
public class PrincipalMessage {

    @Id
    private Long id = 1L; // Always 1 (only one record)

    @Column(length = 5000)
    private String message;
}