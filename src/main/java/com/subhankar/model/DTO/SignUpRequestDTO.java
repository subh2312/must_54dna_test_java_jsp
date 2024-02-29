package com.subhankar.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDTO {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String nickName;
    private String userId;
}
