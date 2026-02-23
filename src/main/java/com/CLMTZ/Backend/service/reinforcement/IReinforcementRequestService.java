package com.CLMTZ.Backend.service.reinforcement;

import java.util.List;
import com.CLMTZ.Backend.dto.reinforcement.ReinforcementRequestDTO;

public interface IReinforcementRequestService {
    List<ReinforcementRequestDTO> findAll();

    ReinforcementRequestDTO findById(Integer id);

    ReinforcementRequestDTO save(ReinforcementRequestDTO dto);

    ReinforcementRequestDTO update(Integer id, ReinforcementRequestDTO dto);

    void deleteById(Integer id);

    List<ReinforcementRequestDTO> findByStatusId(Integer statusId);

    ReinforcementRequestDTO updateStatus(Integer id, Integer statusId);
}
