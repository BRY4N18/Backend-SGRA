package com.CLMTZ.Backend.service.academic.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.CLMTZ.Backend.dto.academic.CareerDTO;
import com.CLMTZ.Backend.dto.academic.CareerLoadDTO;
import com.CLMTZ.Backend.model.academic.Career;
import com.CLMTZ.Backend.repository.academic.IAcademicAreaRepository;
import com.CLMTZ.Backend.repository.academic.ICareerRepository;
import com.CLMTZ.Backend.repository.academic.IModalityRepository;
import com.CLMTZ.Backend.service.academic.ICareerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CareerServiceImpl implements ICareerService {

    private final ICareerRepository repository;
    private final IAcademicAreaRepository academicAreaRepository;
    private final IModalityRepository modalityRepository;

    @Override
    public List<CareerDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public CareerDTO findById(Integer id) {
        return repository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Career not found with id: " + id));
    }

    @Override
    public CareerDTO save(CareerDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
    }

    @Override
    public CareerDTO update(Integer id, CareerDTO dto) {
        Career entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Career not found with id: " + id));
        entity.setNameCareer(dto.getNameCareer());
        entity.setSemester(dto.getSemester());
        entity.setState(dto.getState());
        if (dto.getAcademicAreaId() != null) {
            entity.setAcademicAreaId(academicAreaRepository.findById(dto.getAcademicAreaId())
                    .orElseThrow(() -> new RuntimeException("AcademicArea not found")));
        }
        if (dto.getModalityId() != null) {
            entity.setModalityId(modalityRepository.findById(dto.getModalityId())
                    .orElseThrow(() -> new RuntimeException("Modality not found")));
        }
        return toDTO(repository.save(entity));
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    private CareerDTO toDTO(Career entity) {
        CareerDTO dto = new CareerDTO();
        dto.setCareerId(entity.getCareerId());
        dto.setNameCareer(entity.getNameCareer());
        dto.setSemester(entity.getSemester());
        dto.setState(entity.getState());
        dto.setAcademicAreaId(entity.getAcademicAreaId() != null ? entity.getAcademicAreaId().getAreaAcademicId() : null);
        dto.setModalityId(entity.getModalityId() != null ? entity.getModalityId().getIdModality() : null);
        return dto;
    }

    private Career toEntity(CareerDTO dto) {
        Career entity = new Career();
        entity.setNameCareer(dto.getNameCareer());
        entity.setSemester(dto.getSemester());
        entity.setState(dto.getState() != null ? dto.getState() : true);
        if (dto.getAcademicAreaId() != null) {
            entity.setAcademicAreaId(academicAreaRepository.findById(dto.getAcademicAreaId())
                    .orElseThrow(() -> new RuntimeException("AcademicArea not found")));
        }
        if (dto.getModalityId() != null) {
            entity.setModalityId(modalityRepository.findById(dto.getModalityId())
                    .orElseThrow(() -> new RuntimeException("Modality not found")));
        }
        return entity;
    }

    @Override
    public List<String> uploadCareers(List<CareerLoadDTO> dtos) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'uploadCareers'");
    }
}
