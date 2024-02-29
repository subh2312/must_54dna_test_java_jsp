package com.subhankar.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private String id;
    private String email;
    private String userId;
    private String token;

}
