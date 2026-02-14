package com.CLMTZ.Backend.service.academic;

import java.util.List;

import com.CLMTZ.Backend.dto.academic.TeachingDTO;

public interface ITeachingService {
    List<TeachingDTO> findAll();
    TeachingDTO findById(Integer id);
    TeachingDTO save(TeachingDTO dto);
    TeachingDTO update(Integer id, TeachingDTO dto);
    void deleteById(Integer id);
}
