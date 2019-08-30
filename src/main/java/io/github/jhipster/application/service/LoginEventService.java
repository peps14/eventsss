package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.LoginEventDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.jhipster.application.domain.LoginEvent}.
 */
public interface LoginEventService {

    /**
     * Save a loginEvent.
     *
     * @param loginEventDTO the entity to save.
     * @return the persisted entity.
     */
    LoginEventDTO save(LoginEventDTO loginEventDTO);

    /**
     * Get all the loginEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LoginEventDTO> findAll(Pageable pageable);


    /**
     * Get the "id" loginEvent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LoginEventDTO> findOne(Long id);

    /**
     * Delete the "id" loginEvent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
