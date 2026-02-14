package com.CLMTZ.Backend.service.reinforcement.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.CLMTZ.Backend.dto.reinforcement.AttendanceReinforcementDTO;
import com.CLMTZ.Backend.model.reinforcement.AttendanceReinforcement;
import com.CLMTZ.Backend.repository.reinforcement.IAttendanceReinforcementRepository;
import com.CLMTZ.Backend.repository.reinforcement.IReinforcementPerformedRepository;
import com.CLMTZ.Backend.repository.academic.IStudentsRepository;
import com.CLMTZ.Backend.service.reinforcement.IAttendanceReinforcementService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceReinforcementServiceImpl implements IAttendanceReinforcementService {

    private final IAttendanceReinforcementRepository repository;
    private final IReinforcementPerformedRepository reinforcementPerformedRepository;
    private final IStudentsRepository studentsRepository;

    @Override
    public List<AttendanceReinforcementDTO> findAll() { return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList()); }

    @Override
    public AttendanceReinforcementDTO findById(Integer id) { return repository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("AttendanceReinforcement not found with id: " + id)); }

    @Override
    public AttendanceReinforcementDTO save(AttendanceReinforcementDTO dto) {
        AttendanceReinforcement e = new AttendanceReinforcement();
        e.setAttendance(dto.getAttendance());
        if (dto.getReinforcementPerformedId() != null) e.setReinforcementPerformedId(reinforcementPerformedRepository.findById(dto.getReinforcementPerformedId()).orElseThrow(() -> new RuntimeException("ReinforcementPerformed not found")));
        if (dto.getStudentId() != null) e.setStudentId(studentsRepository.findById(dto.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public AttendanceReinforcementDTO update(Integer id, AttendanceReinforcementDTO dto) {
        AttendanceReinforcement e = repository.findById(id).orElseThrow(() -> new RuntimeException("AttendanceReinforcement not found with id: " + id));
        e.setAttendance(dto.getAttendance());
        if (dto.getReinforcementPerformedId() != null) e.setReinforcementPerformedId(reinforcementPerformedRepository.findById(dto.getReinforcementPerformedId()).orElseThrow(() -> new RuntimeException("ReinforcementPerformed not found")));
        if (dto.getStudentId() != null) e.setStudentId(studentsRepository.findById(dto.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public void deleteById(Integer id) { repository.deleteById(id); }

    private AttendanceReinforcementDTO toDTO(AttendanceReinforcement e) {
        AttendanceReinforcementDTO d = new AttendanceReinforcementDTO();
        d.setAttendanceId(e.getAttendanceId()); d.setAttendance(e.getAttendance());
        d.setReinforcementPerformedId(e.getReinforcementPerformedId() != null ? e.getReinforcementPerformedId().getReinforcementPerformedId() : null);
        d.setStudentId(e.getStudentId() != null ? e.getStudentId().getStudentId() : null);
        return d;
    }
}
