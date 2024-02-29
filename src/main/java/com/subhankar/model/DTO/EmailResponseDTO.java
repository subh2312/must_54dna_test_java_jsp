package com.subhankar.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailResponseDTO {
    private String from;
    private String to;
    private String subject;
    private String body;
    private LocalDateTime sentAt;
}
