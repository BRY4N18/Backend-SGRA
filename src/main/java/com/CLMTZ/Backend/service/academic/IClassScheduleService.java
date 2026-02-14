package com.CLMTZ.Backend.service.academic;

import java.util.List;

import com.CLMTZ.Backend.dto.academic.ClassScheduleDTO;

public interface IClassScheduleService {
    List<ClassScheduleDTO> findAll();
    ClassScheduleDTO findById(Integer id);
    ClassScheduleDTO save(ClassScheduleDTO dto);
    ClassScheduleDTO update(Integer id, ClassScheduleDTO dto);
    void deleteById(Integer id);
}
