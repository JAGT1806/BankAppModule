package com.jagt.client.domain.model.value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNameValue {
    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;
}
