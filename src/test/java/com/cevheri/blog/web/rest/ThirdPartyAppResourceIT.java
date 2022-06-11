package com.cevheri.blog.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cevheri.blog.IntegrationTest;
import com.cevheri.blog.domain.ThirdPartyApp;
import com.cevheri.blog.domain.enumeration.ThirdPartyAppName;
import com.cevheri.blog.repository.ThirdPartyAppRepository;
import com.cevheri.blog.service.dto.ThirdPartyAppDTO;
import com.cevheri.blog.service.mapper.ThirdPartyAppMapper;
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

/**
 * Integration tests for the {@link ThirdPartyAppResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThirdPartyAppResourceIT {

    private static final ThirdPartyAppName DEFAULT_NAME = ThirdPartyAppName.MEDIUM;
    private static final ThirdPartyAppName UPDATED_NAME = ThirdPartyAppName.MEDIUM;

    private static final String DEFAULT_BASE_URL = "AAAAAAAAAA";
    private static final String UPDATED_BASE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ACCESS_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR_ID = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CREATING_POST_API = "AAAAAAAAAA";
    private static final String UPDATED_CREATING_POST_API = "BBBBBBBBBB";

    private static final String DEFAULT_READ_POST_API = "AAAAAAAAAA";
    private static final String UPDATED_READ_POST_API = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/third-party-apps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ThirdPartyAppRepository thirdPartyAppRepository;

    @Autowired
    private ThirdPartyAppMapper thirdPartyAppMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThirdPartyAppMockMvc;

    private ThirdPartyApp thirdPartyApp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThirdPartyApp createEntity(EntityManager em) {
        ThirdPartyApp thirdPartyApp = new ThirdPartyApp()
            .name(DEFAULT_NAME)
            .baseUrl(DEFAULT_BASE_URL)
            .accessKey(DEFAULT_ACCESS_KEY)
            .authorId(DEFAULT_AUTHOR_ID)
            .creatingPostApi(DEFAULT_CREATING_POST_API)
            .readPostApi(DEFAULT_READ_POST_API)
            .active(DEFAULT_ACTIVE);
        return thirdPartyApp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThirdPartyApp createUpdatedEntity(EntityManager em) {
        ThirdPartyApp thirdPartyApp = new ThirdPartyApp()
            .name(UPDATED_NAME)
            .baseUrl(UPDATED_BASE_URL)
            .accessKey(UPDATED_ACCESS_KEY)
            .authorId(UPDATED_AUTHOR_ID)
            .creatingPostApi(UPDATED_CREATING_POST_API)
            .readPostApi(UPDATED_READ_POST_API)
            .active(UPDATED_ACTIVE);
        return thirdPartyApp;
    }

    @BeforeEach
    public void initTest() {
        thirdPartyApp = createEntity(em);
    }

    @Test
    @Transactional
    void createThirdPartyApp() throws Exception {
        int databaseSizeBeforeCreate = thirdPartyAppRepository.findAll().size();
        // Create the ThirdPartyApp
        ThirdPartyAppDTO thirdPartyAppDTO = thirdPartyAppMapper.toDto(thirdPartyApp);
        restThirdPartyAppMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thirdPartyAppDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ThirdPartyApp in the database
        List<ThirdPartyApp> thirdPartyAppList = thirdPartyAppRepository.findAll();
        assertThat(thirdPartyAppList).hasSize(databaseSizeBeforeCreate + 1);
        ThirdPartyApp testThirdPartyApp = thirdPartyAppList.get(thirdPartyAppList.size() - 1);
        assertThat(testThirdPartyApp.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testThirdPartyApp.getBaseUrl()).isEqualTo(DEFAULT_BASE_URL);
        assertThat(testThirdPartyApp.getAccessKey()).isEqualTo(DEFAULT_ACCESS_KEY);
        assertThat(testThirdPartyApp.getAuthorId()).isEqualTo(DEFAULT_AUTHOR_ID);
        assertThat(testThirdPartyApp.getCreatingPostApi()).isEqualTo(DEFAULT_CREATING_POST_API);
        assertThat(testThirdPartyApp.getReadPostApi()).isEqualTo(DEFAULT_READ_POST_API);
        assertThat(testThirdPartyApp.getActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    void createThirdPartyAppWithExistingId() throws Exception {
        // Create the ThirdPartyApp with an existing ID
        thirdPartyApp.setId(1L);
        ThirdPartyAppDTO thirdPartyAppDTO = thirdPartyAppMapper.toDto(thirdPartyApp);

        int databaseSizeBeforeCreate = thirdPartyAppRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThirdPartyAppMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thirdPartyAppDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThirdPartyApp in the database
        List<ThirdPartyApp> thirdPartyAppList = thirdPartyAppRepository.findAll();
        assertThat(thirdPartyAppList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllThirdPartyApps() throws Exception {
        // Initialize the database
        thirdPartyAppRepository.saveAndFlush(thirdPartyApp);

        // Get all the thirdPartyAppList
        restThirdPartyAppMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thirdPartyApp.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].baseUrl").value(hasItem(DEFAULT_BASE_URL)))
            .andExpect(jsonPath("$.[*].accessKey").value(hasItem(DEFAULT_ACCESS_KEY)))
            .andExpect(jsonPath("$.[*].authorId").value(hasItem(DEFAULT_AUTHOR_ID)))
            .andExpect(jsonPath("$.[*].creatingPostApi").value(hasItem(DEFAULT_CREATING_POST_API)))
            .andExpect(jsonPath("$.[*].readPostApi").value(hasItem(DEFAULT_READ_POST_API)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getThirdPartyApp() throws Exception {
        // Initialize the database
        thirdPartyAppRepository.saveAndFlush(thirdPartyApp);

        // Get the thirdPartyApp
        restThirdPartyAppMockMvc
            .perform(get(ENTITY_API_URL_ID, thirdPartyApp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thirdPartyApp.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.baseUrl").value(DEFAULT_BASE_URL))
            .andExpect(jsonPath("$.accessKey").value(DEFAULT_ACCESS_KEY))
            .andExpect(jsonPath("$.authorId").value(DEFAULT_AUTHOR_ID))
            .andExpect(jsonPath("$.creatingPostApi").value(DEFAULT_CREATING_POST_API))
            .andExpect(jsonPath("$.readPostApi").value(DEFAULT_READ_POST_API))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingThirdPartyApp() throws Exception {
        // Get the thirdPartyApp
        restThirdPartyAppMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewThirdPartyApp() throws Exception {
        // Initialize the database
        thirdPartyAppRepository.saveAndFlush(thirdPartyApp);

        int databaseSizeBeforeUpdate = thirdPartyAppRepository.findAll().size();

        // Update the thirdPartyApp
        ThirdPartyApp updatedThirdPartyApp = thirdPartyAppRepository.findById(thirdPartyApp.getId()).get();
        // Disconnect from session so that the updates on updatedThirdPartyApp are not directly saved in db
        em.detach(updatedThirdPartyApp);
        updatedThirdPartyApp
            .name(UPDATED_NAME)
            .baseUrl(UPDATED_BASE_URL)
            .accessKey(UPDATED_ACCESS_KEY)
            .authorId(UPDATED_AUTHOR_ID)
            .creatingPostApi(UPDATED_CREATING_POST_API)
            .readPostApi(UPDATED_READ_POST_API)
            .active(UPDATED_ACTIVE);
        ThirdPartyAppDTO thirdPartyAppDTO = thirdPartyAppMapper.toDto(updatedThirdPartyApp);

        restThirdPartyAppMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thirdPartyAppDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thirdPartyAppDTO))
            )
            .andExpect(status().isOk());

        // Validate the ThirdPartyApp in the database
        List<ThirdPartyApp> thirdPartyAppList = thirdPartyAppRepository.findAll();
        assertThat(thirdPartyAppList).hasSize(databaseSizeBeforeUpdate);
        ThirdPartyApp testThirdPartyApp = thirdPartyAppList.get(thirdPartyAppList.size() - 1);
        assertThat(testThirdPartyApp.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testThirdPartyApp.getBaseUrl()).isEqualTo(UPDATED_BASE_URL);
        assertThat(testThirdPartyApp.getAccessKey()).isEqualTo(UPDATED_ACCESS_KEY);
        assertThat(testThirdPartyApp.getAuthorId()).isEqualTo(UPDATED_AUTHOR_ID);
        assertThat(testThirdPartyApp.getCreatingPostApi()).isEqualTo(UPDATED_CREATING_POST_API);
        assertThat(testThirdPartyApp.getReadPostApi()).isEqualTo(UPDATED_READ_POST_API);
        assertThat(testThirdPartyApp.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingThirdPartyApp() throws Exception {
        int databaseSizeBeforeUpdate = thirdPartyAppRepository.findAll().size();
        thirdPartyApp.setId(count.incrementAndGet());

        // Create the ThirdPartyApp
        ThirdPartyAppDTO thirdPartyAppDTO = thirdPartyAppMapper.toDto(thirdPartyApp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThirdPartyAppMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thirdPartyAppDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thirdPartyAppDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThirdPartyApp in the database
        List<ThirdPartyApp> thirdPartyAppList = thirdPartyAppRepository.findAll();
        assertThat(thirdPartyAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThirdPartyApp() throws Exception {
        int databaseSizeBeforeUpdate = thirdPartyAppRepository.findAll().size();
        thirdPartyApp.setId(count.incrementAndGet());

        // Create the ThirdPartyApp
        ThirdPartyAppDTO thirdPartyAppDTO = thirdPartyAppMapper.toDto(thirdPartyApp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThirdPartyAppMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thirdPartyAppDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThirdPartyApp in the database
        List<ThirdPartyApp> thirdPartyAppList = thirdPartyAppRepository.findAll();
        assertThat(thirdPartyAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThirdPartyApp() throws Exception {
        int databaseSizeBeforeUpdate = thirdPartyAppRepository.findAll().size();
        thirdPartyApp.setId(count.incrementAndGet());

        // Create the ThirdPartyApp
        ThirdPartyAppDTO thirdPartyAppDTO = thirdPartyAppMapper.toDto(thirdPartyApp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThirdPartyAppMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thirdPartyAppDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThirdPartyApp in the database
        List<ThirdPartyApp> thirdPartyAppList = thirdPartyAppRepository.findAll();
        assertThat(thirdPartyAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThirdPartyAppWithPatch() throws Exception {
        // Initialize the database
        thirdPartyAppRepository.saveAndFlush(thirdPartyApp);

        int databaseSizeBeforeUpdate = thirdPartyAppRepository.findAll().size();

        // Update the thirdPartyApp using partial update
        ThirdPartyApp partialUpdatedThirdPartyApp = new ThirdPartyApp();
        partialUpdatedThirdPartyApp.setId(thirdPartyApp.getId());

        partialUpdatedThirdPartyApp.accessKey(UPDATED_ACCESS_KEY).readPostApi(UPDATED_READ_POST_API).active(UPDATED_ACTIVE);

        restThirdPartyAppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThirdPartyApp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThirdPartyApp))
            )
            .andExpect(status().isOk());

        // Validate the ThirdPartyApp in the database
        List<ThirdPartyApp> thirdPartyAppList = thirdPartyAppRepository.findAll();
        assertThat(thirdPartyAppList).hasSize(databaseSizeBeforeUpdate);
        ThirdPartyApp testThirdPartyApp = thirdPartyAppList.get(thirdPartyAppList.size() - 1);
        assertThat(testThirdPartyApp.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testThirdPartyApp.getBaseUrl()).isEqualTo(DEFAULT_BASE_URL);
        assertThat(testThirdPartyApp.getAccessKey()).isEqualTo(UPDATED_ACCESS_KEY);
        assertThat(testThirdPartyApp.getAuthorId()).isEqualTo(DEFAULT_AUTHOR_ID);
        assertThat(testThirdPartyApp.getCreatingPostApi()).isEqualTo(DEFAULT_CREATING_POST_API);
        assertThat(testThirdPartyApp.getReadPostApi()).isEqualTo(UPDATED_READ_POST_API);
        assertThat(testThirdPartyApp.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateThirdPartyAppWithPatch() throws Exception {
        // Initialize the database
        thirdPartyAppRepository.saveAndFlush(thirdPartyApp);

        int databaseSizeBeforeUpdate = thirdPartyAppRepository.findAll().size();

        // Update the thirdPartyApp using partial update
        ThirdPartyApp partialUpdatedThirdPartyApp = new ThirdPartyApp();
        partialUpdatedThirdPartyApp.setId(thirdPartyApp.getId());

        partialUpdatedThirdPartyApp
            .name(UPDATED_NAME)
            .baseUrl(UPDATED_BASE_URL)
            .accessKey(UPDATED_ACCESS_KEY)
            .authorId(UPDATED_AUTHOR_ID)
            .creatingPostApi(UPDATED_CREATING_POST_API)
            .readPostApi(UPDATED_READ_POST_API)
            .active(UPDATED_ACTIVE);

        restThirdPartyAppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThirdPartyApp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThirdPartyApp))
            )
            .andExpect(status().isOk());

        // Validate the ThirdPartyApp in the database
        List<ThirdPartyApp> thirdPartyAppList = thirdPartyAppRepository.findAll();
        assertThat(thirdPartyAppList).hasSize(databaseSizeBeforeUpdate);
        ThirdPartyApp testThirdPartyApp = thirdPartyAppList.get(thirdPartyAppList.size() - 1);
        assertThat(testThirdPartyApp.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testThirdPartyApp.getBaseUrl()).isEqualTo(UPDATED_BASE_URL);
        assertThat(testThirdPartyApp.getAccessKey()).isEqualTo(UPDATED_ACCESS_KEY);
        assertThat(testThirdPartyApp.getAuthorId()).isEqualTo(UPDATED_AUTHOR_ID);
        assertThat(testThirdPartyApp.getCreatingPostApi()).isEqualTo(UPDATED_CREATING_POST_API);
        assertThat(testThirdPartyApp.getReadPostApi()).isEqualTo(UPDATED_READ_POST_API);
        assertThat(testThirdPartyApp.getActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingThirdPartyApp() throws Exception {
        int databaseSizeBeforeUpdate = thirdPartyAppRepository.findAll().size();
        thirdPartyApp.setId(count.incrementAndGet());

        // Create the ThirdPartyApp
        ThirdPartyAppDTO thirdPartyAppDTO = thirdPartyAppMapper.toDto(thirdPartyApp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThirdPartyAppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thirdPartyAppDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thirdPartyAppDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThirdPartyApp in the database
        List<ThirdPartyApp> thirdPartyAppList = thirdPartyAppRepository.findAll();
        assertThat(thirdPartyAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThirdPartyApp() throws Exception {
        int databaseSizeBeforeUpdate = thirdPartyAppRepository.findAll().size();
        thirdPartyApp.setId(count.incrementAndGet());

        // Create the ThirdPartyApp
        ThirdPartyAppDTO thirdPartyAppDTO = thirdPartyAppMapper.toDto(thirdPartyApp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThirdPartyAppMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thirdPartyAppDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThirdPartyApp in the database
        List<ThirdPartyApp> thirdPartyAppList = thirdPartyAppRepository.findAll();
        assertThat(thirdPartyAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThirdPartyApp() throws Exception {
        int databaseSizeBeforeUpdate = thirdPartyAppRepository.findAll().size();
        thirdPartyApp.setId(count.incrementAndGet());

        // Create the ThirdPartyApp
        ThirdPartyAppDTO thirdPartyAppDTO = thirdPartyAppMapper.toDto(thirdPartyApp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThirdPartyAppMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thirdPartyAppDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThirdPartyApp in the database
        List<ThirdPartyApp> thirdPartyAppList = thirdPartyAppRepository.findAll();
        assertThat(thirdPartyAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThirdPartyApp() throws Exception {
        // Initialize the database
        thirdPartyAppRepository.saveAndFlush(thirdPartyApp);

        int databaseSizeBeforeDelete = thirdPartyAppRepository.findAll().size();

        // Delete the thirdPartyApp
        restThirdPartyAppMockMvc
            .perform(delete(ENTITY_API_URL_ID, thirdPartyApp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ThirdPartyApp> thirdPartyAppList = thirdPartyAppRepository.findAll();
        assertThat(thirdPartyAppList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
