package com.CLMTZ.Backend.service.academic.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.CLMTZ.Backend.dto.academic.ClassScheduleDTO;
import com.CLMTZ.Backend.model.academic.ClassSchedule;
import com.CLMTZ.Backend.repository.academic.*;
import com.CLMTZ.Backend.service.academic.IClassScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassScheduleServiceImpl implements IClassScheduleService {

    private final IClassScheduleRepository repository;
    private final ITimeSlotRepository timeSlotRepository;
    private final IClassRepository classRepository;
    private final IPeriodRepository periodRepository;

    @Override
    public List<ClassScheduleDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ClassScheduleDTO findById(Integer id) {
        return repository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("ClassSchedule not found with id: " + id));
    }

    @Override
    public ClassScheduleDTO save(ClassScheduleDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
    }

    @Override
    public ClassScheduleDTO update(Integer id, ClassScheduleDTO dto) {
        ClassSchedule entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClassSchedule not found with id: " + id));
        entity.setDay(dto.getDay());
        entity.setActive(dto.getActive());
        if (dto.getTimeSlotId() != null) entity.setTimeSlotId(timeSlotRepository.findById(dto.getTimeSlotId()).orElseThrow(() -> new RuntimeException("TimeSlot not found")));
        if (dto.getAssignedClassId() != null) entity.setAssignedClassId(classRepository.findById(dto.getAssignedClassId()).orElseThrow(() -> new RuntimeException("Class not found")));
        if (dto.getPeriodId() != null) entity.setPeriodId(periodRepository.findById(dto.getPeriodId()).orElseThrow(() -> new RuntimeException("Period not found")));
        return toDTO(repository.save(entity));
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    private ClassScheduleDTO toDTO(ClassSchedule entity) {
        ClassScheduleDTO dto = new ClassScheduleDTO();
        dto.setIdClassSchedule(entity.getIdClassSchedule());
        dto.setDay(entity.getDay());
        dto.setActive(entity.getActive());
        dto.setTimeSlotId(entity.getTimeSlotId() != null ? entity.getTimeSlotId().getTimeSlotId() : null);
        dto.setAssignedClassId(entity.getAssignedClassId() != null ? entity.getAssignedClassId().getIdClass() : null);
        dto.setPeriodId(entity.getPeriodId() != null ? entity.getPeriodId().getPeriodId() : null);
        return dto;
    }

    private ClassSchedule toEntity(ClassScheduleDTO dto) {
        ClassSchedule entity = new ClassSchedule();
        entity.setDay(dto.getDay());
        entity.setActive(dto.getActive() != null ? dto.getActive() : true);
        if (dto.getTimeSlotId() != null) entity.setTimeSlotId(timeSlotRepository.findById(dto.getTimeSlotId()).orElseThrow(() -> new RuntimeException("TimeSlot not found")));
        if (dto.getAssignedClassId() != null) entity.setAssignedClassId(classRepository.findById(dto.getAssignedClassId()).orElseThrow(() -> new RuntimeException("Class not found")));
        if (dto.getPeriodId() != null) entity.setPeriodId(periodRepository.findById(dto.getPeriodId()).orElseThrow(() -> new RuntimeException("Period not found")));
        return entity;
    }
}
