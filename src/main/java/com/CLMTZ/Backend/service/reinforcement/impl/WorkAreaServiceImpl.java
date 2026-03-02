package com.CLMTZ.Backend.service.reinforcement.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.CLMTZ.Backend.dto.reinforcement.WorkAreaDTO;
import com.CLMTZ.Backend.model.reinforcement.WorkArea;
import com.CLMTZ.Backend.repository.reinforcement.IWorkAreaRepository;
import com.CLMTZ.Backend.repository.reinforcement.IWorkAreaTypesRepository;
import com.CLMTZ.Backend.service.reinforcement.IWorkAreaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkAreaServiceImpl implements IWorkAreaService {

    private final IWorkAreaRepository repository;
    private final IWorkAreaTypesRepository workAreaTypesRepository;

    @Override
    public List<WorkAreaDTO> findAll() { return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList()); }

    @Override
    public WorkAreaDTO findById(Integer id) { return repository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("WorkArea not found with id: " + id)); }

    @Override
    public WorkAreaDTO save(WorkAreaDTO dto) {
        WorkArea e = new WorkArea();
        e.setWorkArea(dto.getWorkArea()); e.setCapacity(dto.getCapacity()); e.setAvailability(dto.getAvailability());
        if (dto.getWorkAreaTypeId() != null) e.setWorkAreaTypeId(workAreaTypesRepository.findById(dto.getWorkAreaTypeId()).orElseThrow(() -> new RuntimeException("WorkAreaTypes not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public WorkAreaDTO update(Integer id, WorkAreaDTO dto) {
        WorkArea e = repository.findById(id).orElseThrow(() -> new RuntimeException("WorkArea not found with id: " + id));
        e.setWorkArea(dto.getWorkArea()); e.setCapacity(dto.getCapacity()); e.setAvailability(dto.getAvailability());
        if (dto.getWorkAreaTypeId() != null) e.setWorkAreaTypeId(workAreaTypesRepository.findById(dto.getWorkAreaTypeId()).orElseThrow(() -> new RuntimeException("WorkAreaTypes not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public void deleteById(Integer id) { repository.deleteById(id); }

    private WorkAreaDTO toDTO(WorkArea e) {
        WorkAreaDTO d = new WorkAreaDTO();
        d.setWorkAreaId(e.getWorkAreaId()); d.setWorkArea(e.getWorkArea()); d.setCapacity(e.getCapacity()); d.setAvailability(e.getAvailability());
        d.setWorkAreaTypeId(e.getWorkAreaTypeId() != null ? e.getWorkAreaTypeId().getWorkAreaTypeId() : null);
        return d;
    }
}
