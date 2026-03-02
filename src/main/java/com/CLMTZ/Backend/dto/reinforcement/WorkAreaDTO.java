package com.CLMTZ.Backend.dto.reinforcement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkAreaDTO {
    private Integer workAreaId;
    private String workArea;
    private Integer capacity;
    private Character availability;
    private Integer workAreaTypeId;
}
