package com.CLMTZ.Backend.service.academic;

import java.util.List;

import com.CLMTZ.Backend.dto.academic.SubjectDTO;

public interface ISubjectService {
    List<SubjectDTO> findAll();
    SubjectDTO findById(Integer id);
    SubjectDTO save(SubjectDTO dto);
    SubjectDTO update(Integer id, SubjectDTO dto);
    void deleteById(Integer id);
}
