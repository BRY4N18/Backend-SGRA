package com.CLMTZ.Backend.dto.academic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeachingDTO {
    private Integer teachingId;
    private Boolean state;
    private Integer userId;
    private Integer modalityId;
}
