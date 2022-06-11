package com.cevheri.blog.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cevheri.blog.IntegrationTest;
import com.cevheri.blog.domain.IntegrationLog;
import com.cevheri.blog.domain.enumeration.ExitCodeType;
import com.cevheri.blog.domain.enumeration.ThirdPartyAppName;
import com.cevheri.blog.repository.IntegrationLogRepository;
import com.cevheri.blog.service.dto.IntegrationLogDTO;
import com.cevheri.blog.service.mapper.IntegrationLogMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link IntegrationLogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IntegrationLogResourceIT {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ThirdPartyAppName DEFAULT_INTEGRATION_NAME = ThirdPartyAppName.MEDIUM;
    private static final ThirdPartyAppName UPDATED_INTEGRATION_NAME = ThirdPartyAppName.MEDIUM;

    private static final String DEFAULT_API_URL = "AAAAAAAAAA";
    private static final String UPDATED_API_URL = "BBBBBBBBBB";

    private static final ExitCodeType DEFAULT_EXIT_CODE = ExitCodeType.SUCCESS;
    private static final ExitCodeType UPDATED_EXIT_CODE = ExitCodeType.ERROR;

    private static final String DEFAULT_REQUEST_DATA = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_DATA = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/integration-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IntegrationLogRepository integrationLogRepository;

    @Autowired
    private IntegrationLogMapper integrationLogMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntegrationLogMockMvc;

    private IntegrationLog integrationLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntegrationLog createEntity(EntityManager em) {
        IntegrationLog integrationLog = new IntegrationLog()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .integrationName(DEFAULT_INTEGRATION_NAME)
            .apiUrl(DEFAULT_API_URL)
            .exitCode(DEFAULT_EXIT_CODE)
            .requestData(DEFAULT_REQUEST_DATA)
            .responseData(DEFAULT_RESPONSE_DATA)
            .errorCode(DEFAULT_ERROR_CODE)
            .errorMessage(DEFAULT_ERROR_MESSAGE);
        return integrationLog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntegrationLog createUpdatedEntity(EntityManager em) {
        IntegrationLog integrationLog = new IntegrationLog()
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .integrationName(UPDATED_INTEGRATION_NAME)
            .apiUrl(UPDATED_API_URL)
            .exitCode(UPDATED_EXIT_CODE)
            .requestData(UPDATED_REQUEST_DATA)
            .responseData(UPDATED_RESPONSE_DATA)
            .errorCode(UPDATED_ERROR_CODE)
            .errorMessage(UPDATED_ERROR_MESSAGE);
        return integrationLog;
    }

    @BeforeEach
    public void initTest() {
        integrationLog = createEntity(em);
    }

    @Test
    @Transactional
    void createIntegrationLog() throws Exception {
        int databaseSizeBeforeCreate = integrationLogRepository.findAll().size();
        // Create the IntegrationLog
        IntegrationLogDTO integrationLogDTO = integrationLogMapper.toDto(integrationLog);
        restIntegrationLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(integrationLogDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IntegrationLog in the database
        List<IntegrationLog> integrationLogList = integrationLogRepository.findAll();
        assertThat(integrationLogList).hasSize(databaseSizeBeforeCreate + 1);
        IntegrationLog testIntegrationLog = integrationLogList.get(integrationLogList.size() - 1);
        assertThat(testIntegrationLog.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testIntegrationLog.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testIntegrationLog.getIntegrationName()).isEqualTo(DEFAULT_INTEGRATION_NAME);
        assertThat(testIntegrationLog.getApiUrl()).isEqualTo(DEFAULT_API_URL);
        assertThat(testIntegrationLog.getExitCode()).isEqualTo(DEFAULT_EXIT_CODE);
        assertThat(testIntegrationLog.getRequestData()).isEqualTo(DEFAULT_REQUEST_DATA);
        assertThat(testIntegrationLog.getResponseData()).isEqualTo(DEFAULT_RESPONSE_DATA);
        assertThat(testIntegrationLog.getErrorCode()).isEqualTo(DEFAULT_ERROR_CODE);
        assertThat(testIntegrationLog.getErrorMessage()).isEqualTo(DEFAULT_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void createIntegrationLogWithExistingId() throws Exception {
        // Create the IntegrationLog with an existing ID
        integrationLog.setId(1L);
        IntegrationLogDTO integrationLogDTO = integrationLogMapper.toDto(integrationLog);

        int databaseSizeBeforeCreate = integrationLogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntegrationLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(integrationLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntegrationLog in the database
        List<IntegrationLog> integrationLogList = integrationLogRepository.findAll();
        assertThat(integrationLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIntegrationLogs() throws Exception {
        // Initialize the database
        integrationLogRepository.saveAndFlush(integrationLog);

        // Get all the integrationLogList
        restIntegrationLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(integrationLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].integrationName").value(hasItem(DEFAULT_INTEGRATION_NAME.toString())))
            .andExpect(jsonPath("$.[*].apiUrl").value(hasItem(DEFAULT_API_URL)))
            .andExpect(jsonPath("$.[*].exitCode").value(hasItem(DEFAULT_EXIT_CODE.toString())))
            .andExpect(jsonPath("$.[*].requestData").value(hasItem(DEFAULT_REQUEST_DATA.toString())))
            .andExpect(jsonPath("$.[*].responseData").value(hasItem(DEFAULT_RESPONSE_DATA.toString())))
            .andExpect(jsonPath("$.[*].errorCode").value(hasItem(DEFAULT_ERROR_CODE)))
            .andExpect(jsonPath("$.[*].errorMessage").value(hasItem(DEFAULT_ERROR_MESSAGE)));
    }

    @Test
    @Transactional
    void getIntegrationLog() throws Exception {
        // Initialize the database
        integrationLogRepository.saveAndFlush(integrationLog);

        // Get the integrationLog
        restIntegrationLogMockMvc
            .perform(get(ENTITY_API_URL_ID, integrationLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(integrationLog.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.integrationName").value(DEFAULT_INTEGRATION_NAME.toString()))
            .andExpect(jsonPath("$.apiUrl").value(DEFAULT_API_URL))
            .andExpect(jsonPath("$.exitCode").value(DEFAULT_EXIT_CODE.toString()))
            .andExpect(jsonPath("$.requestData").value(DEFAULT_REQUEST_DATA.toString()))
            .andExpect(jsonPath("$.responseData").value(DEFAULT_RESPONSE_DATA.toString()))
            .andExpect(jsonPath("$.errorCode").value(DEFAULT_ERROR_CODE))
            .andExpect(jsonPath("$.errorMessage").value(DEFAULT_ERROR_MESSAGE));
    }

    @Test
    @Transactional
    void getNonExistingIntegrationLog() throws Exception {
        // Get the integrationLog
        restIntegrationLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIntegrationLog() throws Exception {
        // Initialize the database
        integrationLogRepository.saveAndFlush(integrationLog);

        int databaseSizeBeforeUpdate = integrationLogRepository.findAll().size();

        // Update the integrationLog
        IntegrationLog updatedIntegrationLog = integrationLogRepository.findById(integrationLog.getId()).get();
        // Disconnect from session so that the updates on updatedIntegrationLog are not directly saved in db
        em.detach(updatedIntegrationLog);
        updatedIntegrationLog
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .integrationName(UPDATED_INTEGRATION_NAME)
            .apiUrl(UPDATED_API_URL)
            .exitCode(UPDATED_EXIT_CODE)
            .requestData(UPDATED_REQUEST_DATA)
            .responseData(UPDATED_RESPONSE_DATA)
            .errorCode(UPDATED_ERROR_CODE)
            .errorMessage(UPDATED_ERROR_MESSAGE);
        IntegrationLogDTO integrationLogDTO = integrationLogMapper.toDto(updatedIntegrationLog);

        restIntegrationLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, integrationLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(integrationLogDTO))
            )
            .andExpect(status().isOk());

        // Validate the IntegrationLog in the database
        List<IntegrationLog> integrationLogList = integrationLogRepository.findAll();
        assertThat(integrationLogList).hasSize(databaseSizeBeforeUpdate);
        IntegrationLog testIntegrationLog = integrationLogList.get(integrationLogList.size() - 1);
        assertThat(testIntegrationLog.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testIntegrationLog.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testIntegrationLog.getIntegrationName()).isEqualTo(UPDATED_INTEGRATION_NAME);
        assertThat(testIntegrationLog.getApiUrl()).isEqualTo(UPDATED_API_URL);
        assertThat(testIntegrationLog.getExitCode()).isEqualTo(UPDATED_EXIT_CODE);
        assertThat(testIntegrationLog.getRequestData()).isEqualTo(UPDATED_REQUEST_DATA);
        assertThat(testIntegrationLog.getResponseData()).isEqualTo(UPDATED_RESPONSE_DATA);
        assertThat(testIntegrationLog.getErrorCode()).isEqualTo(UPDATED_ERROR_CODE);
        assertThat(testIntegrationLog.getErrorMessage()).isEqualTo(UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void putNonExistingIntegrationLog() throws Exception {
        int databaseSizeBeforeUpdate = integrationLogRepository.findAll().size();
        integrationLog.setId(count.incrementAndGet());

        // Create the IntegrationLog
        IntegrationLogDTO integrationLogDTO = integrationLogMapper.toDto(integrationLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntegrationLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, integrationLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(integrationLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntegrationLog in the database
        List<IntegrationLog> integrationLogList = integrationLogRepository.findAll();
        assertThat(integrationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIntegrationLog() throws Exception {
        int databaseSizeBeforeUpdate = integrationLogRepository.findAll().size();
        integrationLog.setId(count.incrementAndGet());

        // Create the IntegrationLog
        IntegrationLogDTO integrationLogDTO = integrationLogMapper.toDto(integrationLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntegrationLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(integrationLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntegrationLog in the database
        List<IntegrationLog> integrationLogList = integrationLogRepository.findAll();
        assertThat(integrationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIntegrationLog() throws Exception {
        int databaseSizeBeforeUpdate = integrationLogRepository.findAll().size();
        integrationLog.setId(count.incrementAndGet());

        // Create the IntegrationLog
        IntegrationLogDTO integrationLogDTO = integrationLogMapper.toDto(integrationLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntegrationLogMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(integrationLogDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntegrationLog in the database
        List<IntegrationLog> integrationLogList = integrationLogRepository.findAll();
        assertThat(integrationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIntegrationLogWithPatch() throws Exception {
        // Initialize the database
        integrationLogRepository.saveAndFlush(integrationLog);

        int databaseSizeBeforeUpdate = integrationLogRepository.findAll().size();

        // Update the integrationLog using partial update
        IntegrationLog partialUpdatedIntegrationLog = new IntegrationLog();
        partialUpdatedIntegrationLog.setId(integrationLog.getId());

        partialUpdatedIntegrationLog
            .createdDate(UPDATED_CREATED_DATE)
            .apiUrl(UPDATED_API_URL)
            .exitCode(UPDATED_EXIT_CODE)
            .responseData(UPDATED_RESPONSE_DATA)
            .errorMessage(UPDATED_ERROR_MESSAGE);

        restIntegrationLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntegrationLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntegrationLog))
            )
            .andExpect(status().isOk());

        // Validate the IntegrationLog in the database
        List<IntegrationLog> integrationLogList = integrationLogRepository.findAll();
        assertThat(integrationLogList).hasSize(databaseSizeBeforeUpdate);
        IntegrationLog testIntegrationLog = integrationLogList.get(integrationLogList.size() - 1);
        assertThat(testIntegrationLog.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testIntegrationLog.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testIntegrationLog.getIntegrationName()).isEqualTo(DEFAULT_INTEGRATION_NAME);
        assertThat(testIntegrationLog.getApiUrl()).isEqualTo(UPDATED_API_URL);
        assertThat(testIntegrationLog.getExitCode()).isEqualTo(UPDATED_EXIT_CODE);
        assertThat(testIntegrationLog.getRequestData()).isEqualTo(DEFAULT_REQUEST_DATA);
        assertThat(testIntegrationLog.getResponseData()).isEqualTo(UPDATED_RESPONSE_DATA);
        assertThat(testIntegrationLog.getErrorCode()).isEqualTo(DEFAULT_ERROR_CODE);
        assertThat(testIntegrationLog.getErrorMessage()).isEqualTo(UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void fullUpdateIntegrationLogWithPatch() throws Exception {
        // Initialize the database
        integrationLogRepository.saveAndFlush(integrationLog);

        int databaseSizeBeforeUpdate = integrationLogRepository.findAll().size();

        // Update the integrationLog using partial update
        IntegrationLog partialUpdatedIntegrationLog = new IntegrationLog();
        partialUpdatedIntegrationLog.setId(integrationLog.getId());

        partialUpdatedIntegrationLog
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .integrationName(UPDATED_INTEGRATION_NAME)
            .apiUrl(UPDATED_API_URL)
            .exitCode(UPDATED_EXIT_CODE)
            .requestData(UPDATED_REQUEST_DATA)
            .responseData(UPDATED_RESPONSE_DATA)
            .errorCode(UPDATED_ERROR_CODE)
            .errorMessage(UPDATED_ERROR_MESSAGE);

        restIntegrationLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntegrationLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntegrationLog))
            )
            .andExpect(status().isOk());

        // Validate the IntegrationLog in the database
        List<IntegrationLog> integrationLogList = integrationLogRepository.findAll();
        assertThat(integrationLogList).hasSize(databaseSizeBeforeUpdate);
        IntegrationLog testIntegrationLog = integrationLogList.get(integrationLogList.size() - 1);
        assertThat(testIntegrationLog.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testIntegrationLog.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testIntegrationLog.getIntegrationName()).isEqualTo(UPDATED_INTEGRATION_NAME);
        assertThat(testIntegrationLog.getApiUrl()).isEqualTo(UPDATED_API_URL);
        assertThat(testIntegrationLog.getExitCode()).isEqualTo(UPDATED_EXIT_CODE);
        assertThat(testIntegrationLog.getRequestData()).isEqualTo(UPDATED_REQUEST_DATA);
        assertThat(testIntegrationLog.getResponseData()).isEqualTo(UPDATED_RESPONSE_DATA);
        assertThat(testIntegrationLog.getErrorCode()).isEqualTo(UPDATED_ERROR_CODE);
        assertThat(testIntegrationLog.getErrorMessage()).isEqualTo(UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    void patchNonExistingIntegrationLog() throws Exception {
        int databaseSizeBeforeUpdate = integrationLogRepository.findAll().size();
        integrationLog.setId(count.incrementAndGet());

        // Create the IntegrationLog
        IntegrationLogDTO integrationLogDTO = integrationLogMapper.toDto(integrationLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntegrationLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, integrationLogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(integrationLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntegrationLog in the database
        List<IntegrationLog> integrationLogList = integrationLogRepository.findAll();
        assertThat(integrationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIntegrationLog() throws Exception {
        int databaseSizeBeforeUpdate = integrationLogRepository.findAll().size();
        integrationLog.setId(count.incrementAndGet());

        // Create the IntegrationLog
        IntegrationLogDTO integrationLogDTO = integrationLogMapper.toDto(integrationLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntegrationLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(integrationLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntegrationLog in the database
        List<IntegrationLog> integrationLogList = integrationLogRepository.findAll();
        assertThat(integrationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIntegrationLog() throws Exception {
        int databaseSizeBeforeUpdate = integrationLogRepository.findAll().size();
        integrationLog.setId(count.incrementAndGet());

        // Create the IntegrationLog
        IntegrationLogDTO integrationLogDTO = integrationLogMapper.toDto(integrationLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntegrationLogMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(integrationLogDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntegrationLog in the database
        List<IntegrationLog> integrationLogList = integrationLogRepository.findAll();
        assertThat(integrationLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIntegrationLog() throws Exception {
        // Initialize the database
        integrationLogRepository.saveAndFlush(integrationLog);

        int databaseSizeBeforeDelete = integrationLogRepository.findAll().size();

        // Delete the integrationLog
        restIntegrationLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, integrationLog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IntegrationLog> integrationLogList = integrationLogRepository.findAll();
        assertThat(integrationLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
