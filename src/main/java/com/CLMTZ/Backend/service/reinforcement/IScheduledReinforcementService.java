package com.CLMTZ.Backend.service.reinforcement;

import java.util.List;
import com.CLMTZ.Backend.dto.reinforcement.ScheduledReinforcementDTO;

public interface IScheduledReinforcementService {
    List<ScheduledReinforcementDTO> findAll();
    ScheduledReinforcementDTO findById(Integer id);
    ScheduledReinforcementDTO save(ScheduledReinforcementDTO dto);
    ScheduledReinforcementDTO update(Integer id, ScheduledReinforcementDTO dto);
    void deleteById(Integer id);
}
