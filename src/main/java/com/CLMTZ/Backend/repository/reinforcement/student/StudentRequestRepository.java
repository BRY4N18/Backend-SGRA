package com.CLMTZ.Backend.repository.reinforcement.student;

import com.CLMTZ.Backend.dto.reinforcement.student.StudentRequestCreateRequestDTO;
import com.CLMTZ.Backend.dto.reinforcement.student.StudentRequestPreviewRequestDTO;
import com.CLMTZ.Backend.dto.reinforcement.student.StudentRequestPreviewResponseDTO;

import java.util.List;

public interface StudentRequestRepository {
    StudentRequestPreviewResponseDTO previewRequest(StudentRequestPreviewRequestDTO req);
    Integer createRequest(Integer userId, StudentRequestCreateRequestDTO req);

    /**
     * Inserta participantes para una solicitud de sesión grupal.
     *
     * @param requestId ID de la solicitud creada
     * @param studentIds Lista de IDs de estudiantes participantes
     */
    void addParticipants(Integer requestId, List<Integer> studentIds);
}