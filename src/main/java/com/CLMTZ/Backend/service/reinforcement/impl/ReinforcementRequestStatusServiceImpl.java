package com.CLMTZ.Backend.service.reinforcement.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.CLMTZ.Backend.dto.reinforcement.ReinforcementRequestStatusDTO;
import com.CLMTZ.Backend.model.reinforcement.ReinforcementRequestStatus;
import com.CLMTZ.Backend.repository.reinforcement.IReinforcementRequestStatusRepository;
import com.CLMTZ.Backend.service.reinforcement.IReinforcementRequestStatusService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReinforcementRequestStatusServiceImpl implements IReinforcementRequestStatusService {

    private final IReinforcementRequestStatusRepository repository;

    @Override
    public List<ReinforcementRequestStatusDTO> findAll() { return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList()); }

    @Override
    public ReinforcementRequestStatusDTO findById(Integer id) { return repository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("ReinforcementRequestStatus not found with id: " + id)); }

    @Override
    public ReinforcementRequestStatusDTO save(ReinforcementRequestStatusDTO dto) {
        ReinforcementRequestStatus e = new ReinforcementRequestStatus();
        e.setNameState(dto.getNameState()); e.setState(dto.getState() != null ? dto.getState() : true);
        return toDTO(repository.save(e));
    }

    @Override
    public ReinforcementRequestStatusDTO update(Integer id, ReinforcementRequestStatusDTO dto) {
        ReinforcementRequestStatus e = repository.findById(id).orElseThrow(() -> new RuntimeException("ReinforcementRequestStatus not found with id: " + id));
        e.setNameState(dto.getNameState()); e.setState(dto.getState());
        return toDTO(repository.save(e));
    }

    @Override
    public void deleteById(Integer id) { repository.deleteById(id); }

    private ReinforcementRequestStatusDTO toDTO(ReinforcementRequestStatus e) {
        ReinforcementRequestStatusDTO d = new ReinforcementRequestStatusDTO();
        d.setIdReinforcementRequestStatus(e.getIdReinforcementRequestStatus()); d.setNameState(e.getNameState()); d.setState(e.getState());
        return d;
    }
}
