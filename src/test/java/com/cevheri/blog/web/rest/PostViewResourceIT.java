package com.cevheri.blog.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cevheri.blog.IntegrationTest;
import com.cevheri.blog.domain.PostView;
import com.cevheri.blog.repository.PostViewRepository;
import com.cevheri.blog.service.PostViewService;
import com.cevheri.blog.service.dto.PostViewDTO;
import com.cevheri.blog.service.mapper.PostViewMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PostViewResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PostViewResourceIT {

    private static final String ENTITY_API_URL = "/api/post-views";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PostViewRepository postViewRepository;

    @Mock
    private PostViewRepository postViewRepositoryMock;

    @Autowired
    private PostViewMapper postViewMapper;

    @Mock
    private PostViewService postViewServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostViewMockMvc;

    private PostView postView;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostView createEntity(EntityManager em) {
        PostView postView = new PostView();
        return postView;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostView createUpdatedEntity(EntityManager em) {
        PostView postView = new PostView();
        return postView;
    }

    @BeforeEach
    public void initTest() {
        postView = createEntity(em);
    }

    @Test
    @Transactional
    void createPostView() throws Exception {
        int databaseSizeBeforeCreate = postViewRepository.findAll().size();
        // Create the PostView
        PostViewDTO postViewDTO = postViewMapper.toDto(postView);
        restPostViewMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postViewDTO)))
            .andExpect(status().isCreated());

        // Validate the PostView in the database
        List<PostView> postViewList = postViewRepository.findAll();
        assertThat(postViewList).hasSize(databaseSizeBeforeCreate + 1);
        PostView testPostView = postViewList.get(postViewList.size() - 1);
    }

    @Test
    @Transactional
    void getPostView() throws Exception {
        // Initialize the database
        postViewRepository.saveAndFlush(postView);

        // Get the postView
        restPostViewMockMvc
            .perform(get(ENTITY_API_URL_ID, postView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postView.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPostView() throws Exception {
        // Get the postView
        restPostViewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void deletePostView() throws Exception {
        // Initialize the database
        postViewRepository.saveAndFlush(postView);

        int databaseSizeBeforeDelete = postViewRepository.findAll().size();

        // Delete the postView
        restPostViewMockMvc
            .perform(delete(ENTITY_API_URL_ID, postView.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostView> postViewList = postViewRepository.findAll();
        assertThat(postViewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
