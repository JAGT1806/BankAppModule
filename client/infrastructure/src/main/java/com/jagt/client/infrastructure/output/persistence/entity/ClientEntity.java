package com.jagt.client.infrastructure.output.persistence.entity;

import com.jagt.client.domain.model.enums.IdentificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IdentificationType identificationType;
    @Column(nullable = false, unique = true, length = 20)
    private String identificationNumber;
    @Column(nullable = false)
    private String firstName;
    private String secondName;
    @Column(nullable = false)
    private String firstLastName;
    @Column(nullable = false)
    private String secondLastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
