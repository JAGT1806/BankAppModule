package com.jagt.client.domain.model;

import com.jagt.client.domain.model.enums.IdentificationType;
import com.jagt.client.domain.model.value.UserNameValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private Long id;
    private IdentificationType identificationType;
    private UserNameValue name;
    private String email;
    private LocalDate birthDate;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
