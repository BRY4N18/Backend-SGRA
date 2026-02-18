package com.CLMTZ.Backend.repository.security.impl;

import com.CLMTZ.Backend.dto.security.ServerCredentialDTO;
import com.CLMTZ.Backend.repository.security.IServerCredentialRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ServerCredentialRepositoryImpl implements IServerCredentialRepository {

    private static final Logger log = LoggerFactory.getLogger(ServerCredentialRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ServerCredentialDTO> getServerCredential(Integer userId, String masterKey) {
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("seguridad.fn_get_server_credential");
            query.registerStoredProcedureParameter(1, Integer.class, jakarta.persistence.ParameterMode.IN);
            query.registerStoredProcedureParameter(2, String.class, jakarta.persistence.ParameterMode.IN);
            query.setParameter(1, userId);
            query.setParameter(2, masterKey);

            query.execute();

            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();

            if (results.isEmpty()) {
                log.warn("No se encontraron credenciales de servidor para userId: {}", userId);
                return Optional.empty();
            }

            Object[] row = results.getFirst();
            ServerCredentialDTO credential = new ServerCredentialDTO();
            credential.setDbUser((String) row[0]);
            credential.setDbPassword((String) row[1]);

            log.info("Credenciales de servidor obtenidas correctamente para userId: {}", userId);
            return Optional.of(credential);

        } catch (Exception e) {
            log.error("Error al obtener credenciales de servidor para userId: {}", userId, e);
            return Optional.empty();
        }
    }
}

