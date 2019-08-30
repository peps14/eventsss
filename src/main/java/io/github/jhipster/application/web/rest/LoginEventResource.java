package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.service.LoginEventService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.service.dto.LoginEventDTO;
import io.github.jhipster.application.service.dto.LoginEventCriteria;
import io.github.jhipster.application.service.LoginEventQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.LoginEvent}.
 */
@RestController
@RequestMapping("/api")
public class LoginEventResource {

    private final Logger log = LoggerFactory.getLogger(LoginEventResource.class);

    private static final String ENTITY_NAME = "loginEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoginEventService loginEventService;

    private final LoginEventQueryService loginEventQueryService;

    public LoginEventResource(LoginEventService loginEventService, LoginEventQueryService loginEventQueryService) {
        this.loginEventService = loginEventService;
        this.loginEventQueryService = loginEventQueryService;
    }

    /**
     * {@code POST  /login-events} : Create a new loginEvent.
     *
     * @param loginEventDTO the loginEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loginEventDTO, or with status {@code 400 (Bad Request)} if the loginEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/login-events")
    public ResponseEntity<LoginEventDTO> createLoginEvent(@Valid @RequestBody LoginEventDTO loginEventDTO) throws URISyntaxException {
        log.debug("REST request to save LoginEvent : {}", loginEventDTO);
        if (loginEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new loginEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoginEventDTO result = loginEventService.save(loginEventDTO);
        return ResponseEntity.created(new URI("/api/login-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /login-events} : Updates an existing loginEvent.
     *
     * @param loginEventDTO the loginEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loginEventDTO,
     * or with status {@code 400 (Bad Request)} if the loginEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loginEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/login-events")
    public ResponseEntity<LoginEventDTO> updateLoginEvent(@Valid @RequestBody LoginEventDTO loginEventDTO) throws URISyntaxException {
        log.debug("REST request to update LoginEvent : {}", loginEventDTO);
        if (loginEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LoginEventDTO result = loginEventService.save(loginEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, loginEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /login-events} : get all the loginEvents.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loginEvents in body.
     */
    @GetMapping("/login-events")
    public ResponseEntity<List<LoginEventDTO>> getAllLoginEvents(LoginEventCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LoginEvents by criteria: {}", criteria);
        Page<LoginEventDTO> page = loginEventQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /login-events/count} : count all the loginEvents.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/login-events/count")
    public ResponseEntity<Long> countLoginEvents(LoginEventCriteria criteria) {
        log.debug("REST request to count LoginEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(loginEventQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /login-events/:id} : get the "id" loginEvent.
     *
     * @param id the id of the loginEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loginEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/login-events/{id}")
    public ResponseEntity<LoginEventDTO> getLoginEvent(@PathVariable Long id) {
        log.debug("REST request to get LoginEvent : {}", id);
        Optional<LoginEventDTO> loginEventDTO = loginEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loginEventDTO);
    }

    /**
     * {@code DELETE  /login-events/:id} : delete the "id" loginEvent.
     *
     * @param id the id of the loginEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/login-events/{id}")
    public ResponseEntity<Void> deleteLoginEvent(@PathVariable Long id) {
        log.debug("REST request to delete LoginEvent : {}", id);
        loginEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
