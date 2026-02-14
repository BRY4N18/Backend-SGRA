package com.CLMTZ.Backend.service.reinforcement;

import java.util.List;
import com.CLMTZ.Backend.dto.reinforcement.AttendanceReinforcementDTO;

public interface IAttendanceReinforcementService {
    List<AttendanceReinforcementDTO> findAll();
    AttendanceReinforcementDTO findById(Integer id);
    AttendanceReinforcementDTO save(AttendanceReinforcementDTO dto);
    AttendanceReinforcementDTO update(Integer id, AttendanceReinforcementDTO dto);
    void deleteById(Integer id);
}
