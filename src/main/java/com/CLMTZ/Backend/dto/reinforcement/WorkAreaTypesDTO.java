package com.CLMTZ.Backend.dto.reinforcement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkAreaTypesDTO {
    private Integer workAreaTypeId;
    private Integer workAreaType;
    private Boolean state;
}
