package com.CLMTZ.Backend.service.academic;

import java.util.List;

import com.CLMTZ.Backend.dto.academic.CoordinationDTO;

public interface ICoordinationService {
    List<CoordinationDTO> findAll();
    CoordinationDTO findById(Integer id);
    CoordinationDTO save(CoordinationDTO dto);
    CoordinationDTO update(Integer id, CoordinationDTO dto);
    void deleteById(Integer id);
}
