package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EventsssApp;
import io.github.jhipster.application.config.TestSecurityConfiguration;
import io.github.jhipster.application.domain.LoginEvent;
import io.github.jhipster.application.repository.LoginEventRepository;
import io.github.jhipster.application.service.LoginEventService;
import io.github.jhipster.application.service.dto.LoginEventDTO;
import io.github.jhipster.application.service.mapper.LoginEventMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.LoginEventCriteria;
import io.github.jhipster.application.service.LoginEventQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LoginEventResource} REST controller.
 */
@SpringBootTest(classes = {EventsssApp.class, TestSecurityConfiguration.class})
public class LoginEventResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_APPLICATION = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_LOGIN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOGIN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_LOGIN_DATE = Instant.ofEpochMilli(-1L);

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_DELEGATION_ID = 1L;
    private static final Long UPDATED_DELEGATION_ID = 2L;
    private static final Long SMALLER_DELEGATION_ID = 1L - 1L;

    private static final String DEFAULT_DELEGATOR = "AAAAAAAAAA";
    private static final String UPDATED_DELEGATOR = "BBBBBBBBBB";

    @Autowired
    private LoginEventRepository loginEventRepository;

    @Autowired
    private LoginEventMapper loginEventMapper;

    @Autowired
    private LoginEventService loginEventService;

    @Autowired
    private LoginEventQueryService loginEventQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restLoginEventMockMvc;

    private LoginEvent loginEvent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoginEventResource loginEventResource = new LoginEventResource(loginEventService, loginEventQueryService);
        this.restLoginEventMockMvc = MockMvcBuilders.standaloneSetup(loginEventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoginEvent createEntity(EntityManager em) {
        LoginEvent loginEvent = new LoginEvent()
            .username(DEFAULT_USERNAME)
            .application(DEFAULT_APPLICATION)
            .loginDate(DEFAULT_LOGIN_DATE)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .delegationId(DEFAULT_DELEGATION_ID)
            .delegator(DEFAULT_DELEGATOR);
        return loginEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoginEvent createUpdatedEntity(EntityManager em) {
        LoginEvent loginEvent = new LoginEvent()
            .username(UPDATED_USERNAME)
            .application(UPDATED_APPLICATION)
            .loginDate(UPDATED_LOGIN_DATE)
            .ipAddress(UPDATED_IP_ADDRESS)
            .delegationId(UPDATED_DELEGATION_ID)
            .delegator(UPDATED_DELEGATOR);
        return loginEvent;
    }

    @BeforeEach
    public void initTest() {
        loginEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoginEvent() throws Exception {
        int databaseSizeBeforeCreate = loginEventRepository.findAll().size();

        // Create the LoginEvent
        LoginEventDTO loginEventDTO = loginEventMapper.toDto(loginEvent);
        restLoginEventMockMvc.perform(post("/api/login-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginEventDTO)))
            .andExpect(status().isCreated());

        // Validate the LoginEvent in the database
        List<LoginEvent> loginEventList = loginEventRepository.findAll();
        assertThat(loginEventList).hasSize(databaseSizeBeforeCreate + 1);
        LoginEvent testLoginEvent = loginEventList.get(loginEventList.size() - 1);
        assertThat(testLoginEvent.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testLoginEvent.getApplication()).isEqualTo(DEFAULT_APPLICATION);
        assertThat(testLoginEvent.getLoginDate()).isEqualTo(DEFAULT_LOGIN_DATE);
        assertThat(testLoginEvent.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testLoginEvent.getDelegationId()).isEqualTo(DEFAULT_DELEGATION_ID);
        assertThat(testLoginEvent.getDelegator()).isEqualTo(DEFAULT_DELEGATOR);
    }

    @Test
    @Transactional
    public void createLoginEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loginEventRepository.findAll().size();

        // Create the LoginEvent with an existing ID
        loginEvent.setId(1L);
        LoginEventDTO loginEventDTO = loginEventMapper.toDto(loginEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoginEventMockMvc.perform(post("/api/login-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LoginEvent in the database
        List<LoginEvent> loginEventList = loginEventRepository.findAll();
        assertThat(loginEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = loginEventRepository.findAll().size();
        // set the field null
        loginEvent.setUsername(null);

        // Create the LoginEvent, which fails.
        LoginEventDTO loginEventDTO = loginEventMapper.toDto(loginEvent);

        restLoginEventMockMvc.perform(post("/api/login-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginEventDTO)))
            .andExpect(status().isBadRequest());

        List<LoginEvent> loginEventList = loginEventRepository.findAll();
        assertThat(loginEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApplicationIsRequired() throws Exception {
        int databaseSizeBeforeTest = loginEventRepository.findAll().size();
        // set the field null
        loginEvent.setApplication(null);

        // Create the LoginEvent, which fails.
        LoginEventDTO loginEventDTO = loginEventMapper.toDto(loginEvent);

        restLoginEventMockMvc.perform(post("/api/login-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginEventDTO)))
            .andExpect(status().isBadRequest());

        List<LoginEvent> loginEventList = loginEventRepository.findAll();
        assertThat(loginEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoginDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = loginEventRepository.findAll().size();
        // set the field null
        loginEvent.setLoginDate(null);

        // Create the LoginEvent, which fails.
        LoginEventDTO loginEventDTO = loginEventMapper.toDto(loginEvent);

        restLoginEventMockMvc.perform(post("/api/login-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginEventDTO)))
            .andExpect(status().isBadRequest());

        List<LoginEvent> loginEventList = loginEventRepository.findAll();
        assertThat(loginEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIpAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = loginEventRepository.findAll().size();
        // set the field null
        loginEvent.setIpAddress(null);

        // Create the LoginEvent, which fails.
        LoginEventDTO loginEventDTO = loginEventMapper.toDto(loginEvent);

        restLoginEventMockMvc.perform(post("/api/login-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginEventDTO)))
            .andExpect(status().isBadRequest());

        List<LoginEvent> loginEventList = loginEventRepository.findAll();
        assertThat(loginEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLoginEvents() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList
        restLoginEventMockMvc.perform(get("/api/login-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loginEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].application").value(hasItem(DEFAULT_APPLICATION.toString())))
            .andExpect(jsonPath("$.[*].loginDate").value(hasItem(DEFAULT_LOGIN_DATE.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].delegationId").value(hasItem(DEFAULT_DELEGATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].delegator").value(hasItem(DEFAULT_DELEGATOR.toString())));
    }
    
    @Test
    @Transactional
    public void getLoginEvent() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get the loginEvent
        restLoginEventMockMvc.perform(get("/api/login-events/{id}", loginEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loginEvent.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.application").value(DEFAULT_APPLICATION.toString()))
            .andExpect(jsonPath("$.loginDate").value(DEFAULT_LOGIN_DATE.toString()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS.toString()))
            .andExpect(jsonPath("$.delegationId").value(DEFAULT_DELEGATION_ID.intValue()))
            .andExpect(jsonPath("$.delegator").value(DEFAULT_DELEGATOR.toString()));
    }

    @Test
    @Transactional
    public void getAllLoginEventsByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where username equals to DEFAULT_USERNAME
        defaultLoginEventShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the loginEventList where username equals to UPDATED_USERNAME
        defaultLoginEventShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllLoginEventsByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultLoginEventShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the loginEventList where username equals to UPDATED_USERNAME
        defaultLoginEventShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllLoginEventsByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where username is not null
        defaultLoginEventShouldBeFound("username.specified=true");

        // Get all the loginEventList where username is null
        defaultLoginEventShouldNotBeFound("username.specified=false");
    }

    @Test
    @Transactional
    public void getAllLoginEventsByApplicationIsEqualToSomething() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where application equals to DEFAULT_APPLICATION
        defaultLoginEventShouldBeFound("application.equals=" + DEFAULT_APPLICATION);

        // Get all the loginEventList where application equals to UPDATED_APPLICATION
        defaultLoginEventShouldNotBeFound("application.equals=" + UPDATED_APPLICATION);
    }

    @Test
    @Transactional
    public void getAllLoginEventsByApplicationIsInShouldWork() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where application in DEFAULT_APPLICATION or UPDATED_APPLICATION
        defaultLoginEventShouldBeFound("application.in=" + DEFAULT_APPLICATION + "," + UPDATED_APPLICATION);

        // Get all the loginEventList where application equals to UPDATED_APPLICATION
        defaultLoginEventShouldNotBeFound("application.in=" + UPDATED_APPLICATION);
    }

    @Test
    @Transactional
    public void getAllLoginEventsByApplicationIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where application is not null
        defaultLoginEventShouldBeFound("application.specified=true");

        // Get all the loginEventList where application is null
        defaultLoginEventShouldNotBeFound("application.specified=false");
    }

    @Test
    @Transactional
    public void getAllLoginEventsByLoginDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where loginDate equals to DEFAULT_LOGIN_DATE
        defaultLoginEventShouldBeFound("loginDate.equals=" + DEFAULT_LOGIN_DATE);

        // Get all the loginEventList where loginDate equals to UPDATED_LOGIN_DATE
        defaultLoginEventShouldNotBeFound("loginDate.equals=" + UPDATED_LOGIN_DATE);
    }

    @Test
    @Transactional
    public void getAllLoginEventsByLoginDateIsInShouldWork() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where loginDate in DEFAULT_LOGIN_DATE or UPDATED_LOGIN_DATE
        defaultLoginEventShouldBeFound("loginDate.in=" + DEFAULT_LOGIN_DATE + "," + UPDATED_LOGIN_DATE);

        // Get all the loginEventList where loginDate equals to UPDATED_LOGIN_DATE
        defaultLoginEventShouldNotBeFound("loginDate.in=" + UPDATED_LOGIN_DATE);
    }

    @Test
    @Transactional
    public void getAllLoginEventsByLoginDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where loginDate is not null
        defaultLoginEventShouldBeFound("loginDate.specified=true");

        // Get all the loginEventList where loginDate is null
        defaultLoginEventShouldNotBeFound("loginDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllLoginEventsByIpAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where ipAddress equals to DEFAULT_IP_ADDRESS
        defaultLoginEventShouldBeFound("ipAddress.equals=" + DEFAULT_IP_ADDRESS);

        // Get all the loginEventList where ipAddress equals to UPDATED_IP_ADDRESS
        defaultLoginEventShouldNotBeFound("ipAddress.equals=" + UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllLoginEventsByIpAddressIsInShouldWork() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where ipAddress in DEFAULT_IP_ADDRESS or UPDATED_IP_ADDRESS
        defaultLoginEventShouldBeFound("ipAddress.in=" + DEFAULT_IP_ADDRESS + "," + UPDATED_IP_ADDRESS);

        // Get all the loginEventList where ipAddress equals to UPDATED_IP_ADDRESS
        defaultLoginEventShouldNotBeFound("ipAddress.in=" + UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllLoginEventsByIpAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where ipAddress is not null
        defaultLoginEventShouldBeFound("ipAddress.specified=true");

        // Get all the loginEventList where ipAddress is null
        defaultLoginEventShouldNotBeFound("ipAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllLoginEventsByDelegationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where delegationId equals to DEFAULT_DELEGATION_ID
        defaultLoginEventShouldBeFound("delegationId.equals=" + DEFAULT_DELEGATION_ID);

        // Get all the loginEventList where delegationId equals to UPDATED_DELEGATION_ID
        defaultLoginEventShouldNotBeFound("delegationId.equals=" + UPDATED_DELEGATION_ID);
    }

    @Test
    @Transactional
    public void getAllLoginEventsByDelegationIdIsInShouldWork() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where delegationId in DEFAULT_DELEGATION_ID or UPDATED_DELEGATION_ID
        defaultLoginEventShouldBeFound("delegationId.in=" + DEFAULT_DELEGATION_ID + "," + UPDATED_DELEGATION_ID);

        // Get all the loginEventList where delegationId equals to UPDATED_DELEGATION_ID
        defaultLoginEventShouldNotBeFound("delegationId.in=" + UPDATED_DELEGATION_ID);
    }

    @Test
    @Transactional
    public void getAllLoginEventsByDelegationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where delegationId is not null
        defaultLoginEventShouldBeFound("delegationId.specified=true");

        // Get all the loginEventList where delegationId is null
        defaultLoginEventShouldNotBeFound("delegationId.specified=false");
    }

    @Test
    @Transactional
    public void getAllLoginEventsByDelegationIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where delegationId is greater than or equal to DEFAULT_DELEGATION_ID
        defaultLoginEventShouldBeFound("delegationId.greaterThanOrEqual=" + DEFAULT_DELEGATION_ID);

        // Get all the loginEventList where delegationId is greater than or equal to UPDATED_DELEGATION_ID
        defaultLoginEventShouldNotBeFound("delegationId.greaterThanOrEqual=" + UPDATED_DELEGATION_ID);
    }

    @Test
    @Transactional
    public void getAllLoginEventsByDelegationIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where delegationId is less than or equal to DEFAULT_DELEGATION_ID
        defaultLoginEventShouldBeFound("delegationId.lessThanOrEqual=" + DEFAULT_DELEGATION_ID);

        // Get all the loginEventList where delegationId is less than or equal to SMALLER_DELEGATION_ID
        defaultLoginEventShouldNotBeFound("delegationId.lessThanOrEqual=" + SMALLER_DELEGATION_ID);
    }

    @Test
    @Transactional
    public void getAllLoginEventsByDelegationIdIsLessThanSomething() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where delegationId is less than DEFAULT_DELEGATION_ID
        defaultLoginEventShouldNotBeFound("delegationId.lessThan=" + DEFAULT_DELEGATION_ID);

        // Get all the loginEventList where delegationId is less than UPDATED_DELEGATION_ID
        defaultLoginEventShouldBeFound("delegationId.lessThan=" + UPDATED_DELEGATION_ID);
    }

    @Test
    @Transactional
    public void getAllLoginEventsByDelegationIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where delegationId is greater than DEFAULT_DELEGATION_ID
        defaultLoginEventShouldNotBeFound("delegationId.greaterThan=" + DEFAULT_DELEGATION_ID);

        // Get all the loginEventList where delegationId is greater than SMALLER_DELEGATION_ID
        defaultLoginEventShouldBeFound("delegationId.greaterThan=" + SMALLER_DELEGATION_ID);
    }


    @Test
    @Transactional
    public void getAllLoginEventsByDelegatorIsEqualToSomething() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where delegator equals to DEFAULT_DELEGATOR
        defaultLoginEventShouldBeFound("delegator.equals=" + DEFAULT_DELEGATOR);

        // Get all the loginEventList where delegator equals to UPDATED_DELEGATOR
        defaultLoginEventShouldNotBeFound("delegator.equals=" + UPDATED_DELEGATOR);
    }

    @Test
    @Transactional
    public void getAllLoginEventsByDelegatorIsInShouldWork() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where delegator in DEFAULT_DELEGATOR or UPDATED_DELEGATOR
        defaultLoginEventShouldBeFound("delegator.in=" + DEFAULT_DELEGATOR + "," + UPDATED_DELEGATOR);

        // Get all the loginEventList where delegator equals to UPDATED_DELEGATOR
        defaultLoginEventShouldNotBeFound("delegator.in=" + UPDATED_DELEGATOR);
    }

    @Test
    @Transactional
    public void getAllLoginEventsByDelegatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        // Get all the loginEventList where delegator is not null
        defaultLoginEventShouldBeFound("delegator.specified=true");

        // Get all the loginEventList where delegator is null
        defaultLoginEventShouldNotBeFound("delegator.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLoginEventShouldBeFound(String filter) throws Exception {
        restLoginEventMockMvc.perform(get("/api/login-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loginEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].application").value(hasItem(DEFAULT_APPLICATION)))
            .andExpect(jsonPath("$.[*].loginDate").value(hasItem(DEFAULT_LOGIN_DATE.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].delegationId").value(hasItem(DEFAULT_DELEGATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].delegator").value(hasItem(DEFAULT_DELEGATOR)));

        // Check, that the count call also returns 1
        restLoginEventMockMvc.perform(get("/api/login-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLoginEventShouldNotBeFound(String filter) throws Exception {
        restLoginEventMockMvc.perform(get("/api/login-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLoginEventMockMvc.perform(get("/api/login-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLoginEvent() throws Exception {
        // Get the loginEvent
        restLoginEventMockMvc.perform(get("/api/login-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoginEvent() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        int databaseSizeBeforeUpdate = loginEventRepository.findAll().size();

        // Update the loginEvent
        LoginEvent updatedLoginEvent = loginEventRepository.findById(loginEvent.getId()).get();
        // Disconnect from session so that the updates on updatedLoginEvent are not directly saved in db
        em.detach(updatedLoginEvent);
        updatedLoginEvent
            .username(UPDATED_USERNAME)
            .application(UPDATED_APPLICATION)
            .loginDate(UPDATED_LOGIN_DATE)
            .ipAddress(UPDATED_IP_ADDRESS)
            .delegationId(UPDATED_DELEGATION_ID)
            .delegator(UPDATED_DELEGATOR);
        LoginEventDTO loginEventDTO = loginEventMapper.toDto(updatedLoginEvent);

        restLoginEventMockMvc.perform(put("/api/login-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginEventDTO)))
            .andExpect(status().isOk());

        // Validate the LoginEvent in the database
        List<LoginEvent> loginEventList = loginEventRepository.findAll();
        assertThat(loginEventList).hasSize(databaseSizeBeforeUpdate);
        LoginEvent testLoginEvent = loginEventList.get(loginEventList.size() - 1);
        assertThat(testLoginEvent.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testLoginEvent.getApplication()).isEqualTo(UPDATED_APPLICATION);
        assertThat(testLoginEvent.getLoginDate()).isEqualTo(UPDATED_LOGIN_DATE);
        assertThat(testLoginEvent.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testLoginEvent.getDelegationId()).isEqualTo(UPDATED_DELEGATION_ID);
        assertThat(testLoginEvent.getDelegator()).isEqualTo(UPDATED_DELEGATOR);
    }

    @Test
    @Transactional
    public void updateNonExistingLoginEvent() throws Exception {
        int databaseSizeBeforeUpdate = loginEventRepository.findAll().size();

        // Create the LoginEvent
        LoginEventDTO loginEventDTO = loginEventMapper.toDto(loginEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoginEventMockMvc.perform(put("/api/login-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LoginEvent in the database
        List<LoginEvent> loginEventList = loginEventRepository.findAll();
        assertThat(loginEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLoginEvent() throws Exception {
        // Initialize the database
        loginEventRepository.saveAndFlush(loginEvent);

        int databaseSizeBeforeDelete = loginEventRepository.findAll().size();

        // Delete the loginEvent
        restLoginEventMockMvc.perform(delete("/api/login-events/{id}", loginEvent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoginEvent> loginEventList = loginEventRepository.findAll();
        assertThat(loginEventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoginEvent.class);
        LoginEvent loginEvent1 = new LoginEvent();
        loginEvent1.setId(1L);
        LoginEvent loginEvent2 = new LoginEvent();
        loginEvent2.setId(loginEvent1.getId());
        assertThat(loginEvent1).isEqualTo(loginEvent2);
        loginEvent2.setId(2L);
        assertThat(loginEvent1).isNotEqualTo(loginEvent2);
        loginEvent1.setId(null);
        assertThat(loginEvent1).isNotEqualTo(loginEvent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoginEventDTO.class);
        LoginEventDTO loginEventDTO1 = new LoginEventDTO();
        loginEventDTO1.setId(1L);
        LoginEventDTO loginEventDTO2 = new LoginEventDTO();
        assertThat(loginEventDTO1).isNotEqualTo(loginEventDTO2);
        loginEventDTO2.setId(loginEventDTO1.getId());
        assertThat(loginEventDTO1).isEqualTo(loginEventDTO2);
        loginEventDTO2.setId(2L);
        assertThat(loginEventDTO1).isNotEqualTo(loginEventDTO2);
        loginEventDTO1.setId(null);
        assertThat(loginEventDTO1).isNotEqualTo(loginEventDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(loginEventMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(loginEventMapper.fromId(null)).isNull();
    }
}
