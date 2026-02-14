package com.CLMTZ.Backend.service.academic.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.CLMTZ.Backend.dto.academic.TeachingDTO;
import com.CLMTZ.Backend.model.academic.Teaching;
import com.CLMTZ.Backend.repository.academic.IModalityRepository;
import com.CLMTZ.Backend.repository.academic.ITeachingRepository;
import com.CLMTZ.Backend.repository.general.IUserRepository;
import com.CLMTZ.Backend.service.academic.ITeachingService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeachingServiceImpl implements ITeachingService {

    private final ITeachingRepository repository;
    private final IUserRepository userRepository;
    private final IModalityRepository modalityRepository;

    @Override
    public List<TeachingDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public TeachingDTO findById(Integer id) {
        return repository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Teaching not found with id: " + id));
    }

    @Override
    public TeachingDTO save(TeachingDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
    }

    @Override
    public TeachingDTO update(Integer id, TeachingDTO dto) {
        Teaching entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Teaching not found with id: " + id));
        entity.setState(dto.getState());
        if (dto.getUserId() != null) entity.setUserId(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        if (dto.getModalityId() != null) entity.setModalityId(modalityRepository.findById(dto.getModalityId()).orElseThrow(() -> new RuntimeException("Modality not found")));
        return toDTO(repository.save(entity));
    }

    @Override
    public void deleteById(Integer id) { repository.deleteById(id); }

    private TeachingDTO toDTO(Teaching e) {
        TeachingDTO dto = new TeachingDTO();
        dto.setTeachingId(e.getTeachingId());
        dto.setState(e.getState());
        dto.setUserId(e.getUserId() != null ? e.getUserId().getUserId() : null);
        dto.setModalityId(e.getModalityId() != null ? e.getModalityId().getIdModality() : null);
        return dto;
    }

    private Teaching toEntity(TeachingDTO dto) {
        Teaching entity = new Teaching();
        entity.setState(dto.getState() != null ? dto.getState() : true);
        if (dto.getUserId() != null) entity.setUserId(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        if (dto.getModalityId() != null) entity.setModalityId(modalityRepository.findById(dto.getModalityId()).orElseThrow(() -> new RuntimeException("Modality not found")));
        return entity;
    }
}
