package com.CLMTZ.Backend.service.reinforcement.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.CLMTZ.Backend.dto.reinforcement.OnSiteReinforcementDTO;
import com.CLMTZ.Backend.model.reinforcement.OnSiteReinforcement;
import com.CLMTZ.Backend.repository.reinforcement.IOnSiteReinforcementRepository;
import com.CLMTZ.Backend.repository.reinforcement.IScheduledReinforcementRepository;
import com.CLMTZ.Backend.repository.reinforcement.IWorkAreaRepository;
import com.CLMTZ.Backend.service.reinforcement.IOnSiteReinforcementService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OnSiteReinforcementServiceImpl implements IOnSiteReinforcementService {

    private final IOnSiteReinforcementRepository repository;
    private final IWorkAreaRepository workAreaRepository;
    private final IScheduledReinforcementRepository scheduledReinforcementRepository;

    @Override
    public List<OnSiteReinforcementDTO> findAll() { return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList()); }

    @Override
    public OnSiteReinforcementDTO findById(Integer id) { return repository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("OnSiteReinforcement not found with id: " + id)); }

    @Override
    public OnSiteReinforcementDTO save(OnSiteReinforcementDTO dto) {
        OnSiteReinforcement e = new OnSiteReinforcement();
        e.setState(dto.getState());
        if (dto.getWorkAreaId() != null) e.setWorkAreaId(workAreaRepository.findById(dto.getWorkAreaId()).orElseThrow(() -> new RuntimeException("WorkArea not found")));
        if (dto.getScheduledReinforcementId() != null) e.setScheduledReinforcementId(scheduledReinforcementRepository.findById(dto.getScheduledReinforcementId()).orElseThrow(() -> new RuntimeException("ScheduledReinforcement not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public OnSiteReinforcementDTO update(Integer id, OnSiteReinforcementDTO dto) {
        OnSiteReinforcement e = repository.findById(id).orElseThrow(() -> new RuntimeException("OnSiteReinforcement not found with id: " + id));
        e.setState(dto.getState());
        if (dto.getWorkAreaId() != null) e.setWorkAreaId(workAreaRepository.findById(dto.getWorkAreaId()).orElseThrow(() -> new RuntimeException("WorkArea not found")));
        if (dto.getScheduledReinforcementId() != null) e.setScheduledReinforcementId(scheduledReinforcementRepository.findById(dto.getScheduledReinforcementId()).orElseThrow(() -> new RuntimeException("ScheduledReinforcement not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public void deleteById(Integer id) { repository.deleteById(id); }

    private OnSiteReinforcementDTO toDTO(OnSiteReinforcement e) {
        OnSiteReinforcementDTO d = new OnSiteReinforcementDTO();
        d.setOnSiteReinforcementId(e.getOnSiteReinforcementId()); d.setState(e.getState());
        d.setWorkAreaId(e.getWorkAreaId() != null ? e.getWorkAreaId().getWorkAreaId() : null);
        d.setScheduledReinforcementId(e.getScheduledReinforcementId() != null ? e.getScheduledReinforcementId().getScheduledReinforcementId() : null);
        return d;
    }
}
