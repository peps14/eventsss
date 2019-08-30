package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.LoginEventService;
import io.github.jhipster.application.domain.LoginEvent;
import io.github.jhipster.application.repository.LoginEventRepository;
import io.github.jhipster.application.service.dto.LoginEventDTO;
import io.github.jhipster.application.service.mapper.LoginEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LoginEvent}.
 */
@Service
@Transactional
public class LoginEventServiceImpl implements LoginEventService {

    private final Logger log = LoggerFactory.getLogger(LoginEventServiceImpl.class);

    private final LoginEventRepository loginEventRepository;

    private final LoginEventMapper loginEventMapper;

    public LoginEventServiceImpl(LoginEventRepository loginEventRepository, LoginEventMapper loginEventMapper) {
        this.loginEventRepository = loginEventRepository;
        this.loginEventMapper = loginEventMapper;
    }

    /**
     * Save a loginEvent.
     *
     * @param loginEventDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LoginEventDTO save(LoginEventDTO loginEventDTO) {
        log.debug("Request to save LoginEvent : {}", loginEventDTO);
        LoginEvent loginEvent = loginEventMapper.toEntity(loginEventDTO);
        loginEvent = loginEventRepository.save(loginEvent);
        return loginEventMapper.toDto(loginEvent);
    }

    /**
     * Get all the loginEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LoginEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoginEvents");
        return loginEventRepository.findAll(pageable)
            .map(loginEventMapper::toDto);
    }


    /**
     * Get one loginEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LoginEventDTO> findOne(Long id) {
        log.debug("Request to get LoginEvent : {}", id);
        return loginEventRepository.findById(id)
            .map(loginEventMapper::toDto);
    }

    /**
     * Delete the loginEvent by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LoginEvent : {}", id);
        loginEventRepository.deleteById(id);
    }
}
