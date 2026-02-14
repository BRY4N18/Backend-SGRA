package com.CLMTZ.Backend.service.academic.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.CLMTZ.Backend.dto.academic.CoordinationDTO;
import com.CLMTZ.Backend.model.academic.Coordination;
import com.CLMTZ.Backend.repository.academic.ICareerRepository;
import com.CLMTZ.Backend.repository.academic.ICoordinationRepository;
import com.CLMTZ.Backend.repository.general.IUserRepository;
import com.CLMTZ.Backend.service.academic.ICoordinationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoordinationServiceImpl implements ICoordinationService {

    private final ICoordinationRepository repository;
    private final IUserRepository userRepository;
    private final ICareerRepository careerRepository;

    @Override
    public List<CoordinationDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public CoordinationDTO findById(Integer id) {
        return repository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Coordination not found with id: " + id));
    }

    @Override
    public CoordinationDTO save(CoordinationDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
    }

    @Override
    public CoordinationDTO update(Integer id, CoordinationDTO dto) {
        Coordination entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordination not found with id: " + id));
        if (dto.getUserId() != null) entity.setUserId(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        if (dto.getCareerId() != null) entity.setCareerId(careerRepository.findById(dto.getCareerId()).orElseThrow(() -> new RuntimeException("Career not found")));
        return toDTO(repository.save(entity));
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    private CoordinationDTO toDTO(Coordination entity) {
        CoordinationDTO dto = new CoordinationDTO();
        dto.setCoordinationId(entity.getCoordinationId());
        dto.setUserId(entity.getUserId() != null ? entity.getUserId().getUserId() : null);
        dto.setCareerId(entity.getCareerId() != null ? entity.getCareerId().getCareerId() : null);
        return dto;
    }

    private Coordination toEntity(CoordinationDTO dto) {
        Coordination entity = new Coordination();
        if (dto.getUserId() != null) entity.setUserId(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        if (dto.getCareerId() != null) entity.setCareerId(careerRepository.findById(dto.getCareerId()).orElseThrow(() -> new RuntimeException("Career not found")));
        return entity;
    }
}
