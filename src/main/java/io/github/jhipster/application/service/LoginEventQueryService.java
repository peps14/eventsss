package io.github.jhipster.application.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.github.jhipster.application.domain.LoginEvent;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.LoginEventRepository;
import io.github.jhipster.application.service.dto.LoginEventCriteria;
import io.github.jhipster.application.service.dto.LoginEventDTO;
import io.github.jhipster.application.service.mapper.LoginEventMapper;

/**
 * Service for executing complex queries for {@link LoginEvent} entities in the database.
 * The main input is a {@link LoginEventCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LoginEventDTO} or a {@link Page} of {@link LoginEventDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LoginEventQueryService extends QueryService<LoginEvent> {

    private final Logger log = LoggerFactory.getLogger(LoginEventQueryService.class);

    private final LoginEventRepository loginEventRepository;

    private final LoginEventMapper loginEventMapper;

    public LoginEventQueryService(LoginEventRepository loginEventRepository, LoginEventMapper loginEventMapper) {
        this.loginEventRepository = loginEventRepository;
        this.loginEventMapper = loginEventMapper;
    }

    /**
     * Return a {@link List} of {@link LoginEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LoginEventDTO> findByCriteria(LoginEventCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LoginEvent> specification = createSpecification(criteria);
        return loginEventMapper.toDto(loginEventRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LoginEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LoginEventDTO> findByCriteria(LoginEventCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LoginEvent> specification = createSpecification(criteria);
        return loginEventRepository.findAll(specification, page)
            .map(loginEventMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LoginEventCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LoginEvent> specification = createSpecification(criteria);
        return loginEventRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<LoginEvent> createSpecification(LoginEventCriteria criteria) {
        Specification<LoginEvent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), LoginEvent_.id));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), LoginEvent_.username));
            }
            if (criteria.getApplication() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApplication(), LoginEvent_.application));
            }
            if (criteria.getLoginDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoginDate(), LoginEvent_.loginDate));
            }
            if (criteria.getIpAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIpAddress(), LoginEvent_.ipAddress));
            }
            if (criteria.getDelegationId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDelegationId(), LoginEvent_.delegationId));
            }
            if (criteria.getDelegator() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDelegator(), LoginEvent_.delegator));
            }
        }
        return specification;
    }
}
