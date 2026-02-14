package com.CLMTZ.Backend.service.reinforcement;

import java.util.List;
import com.CLMTZ.Backend.dto.reinforcement.ReinforcementRequestStatusDTO;

public interface IReinforcementRequestStatusService {
    List<ReinforcementRequestStatusDTO> findAll();
    ReinforcementRequestStatusDTO findById(Integer id);
    ReinforcementRequestStatusDTO save(ReinforcementRequestStatusDTO dto);
    ReinforcementRequestStatusDTO update(Integer id, ReinforcementRequestStatusDTO dto);
    void deleteById(Integer id);
}
