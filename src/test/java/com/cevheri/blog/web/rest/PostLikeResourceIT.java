package com.cevheri.blog.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cevheri.blog.IntegrationTest;
import com.cevheri.blog.domain.PostLike;
import com.cevheri.blog.repository.PostLikeRepository;
import com.cevheri.blog.service.PostLikeService;
import com.cevheri.blog.service.dto.PostLikeDTO;
import com.cevheri.blog.service.mapper.PostLikeMapper;
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
 * Integration tests for the {@link PostLikeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PostLikeResourceIT {

    private static final String ENTITY_API_URL = "/api/post-likes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Mock
    private PostLikeRepository postLikeRepositoryMock;

    @Autowired
    private PostLikeMapper postLikeMapper;

    @Mock
    private PostLikeService postLikeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostLikeMockMvc;

    private PostLike postLike;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostLike createEntity(EntityManager em) {
        PostLike postLike = new PostLike();
        return postLike;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostLike createUpdatedEntity(EntityManager em) {
        PostLike postLike = new PostLike();
        return postLike;
    }

    @BeforeEach
    public void initTest() {
        postLike = createEntity(em);
    }

    @Test
    @Transactional
    void createPostLike() throws Exception {
        int databaseSizeBeforeCreate = postLikeRepository.findAll().size();
        // Create the PostLike
        PostLikeDTO postLikeDTO = postLikeMapper.toDto(postLike);
        restPostLikeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postLikeDTO)))
            .andExpect(status().isCreated());

        // Validate the PostLike in the database
        List<PostLike> postLikeList = postLikeRepository.findAll();
        assertThat(postLikeList).hasSize(databaseSizeBeforeCreate + 1);
        PostLike testPostLike = postLikeList.get(postLikeList.size() - 1);
    }

    @Test
    @Transactional
    void createPostLikeWithExistingId() throws Exception {
        // Create the PostLike with an existing ID
        postLike.setId(1L);
        PostLikeDTO postLikeDTO = postLikeMapper.toDto(postLike);

        int databaseSizeBeforeCreate = postLikeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostLikeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postLikeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostLike in the database
        List<PostLike> postLikeList = postLikeRepository.findAll();
        assertThat(postLikeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPostLikes() throws Exception {
        // Initialize the database
        postLikeRepository.saveAndFlush(postLike);

        // Get all the postLikeList
        restPostLikeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postLike.getId().intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPostLikesWithEagerRelationshipsIsEnabled() throws Exception {
        when(postLikeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostLikeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(postLikeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPostLikesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(postLikeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostLikeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(postLikeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPostLike() throws Exception {
        // Initialize the database
        postLikeRepository.saveAndFlush(postLike);

        // Get the postLike
        restPostLikeMockMvc
            .perform(get(ENTITY_API_URL_ID, postLike.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postLike.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPostLike() throws Exception {
        // Get the postLike
        restPostLikeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPostLike() throws Exception {
        // Initialize the database
        postLikeRepository.saveAndFlush(postLike);

        int databaseSizeBeforeUpdate = postLikeRepository.findAll().size();

        // Update the postLike
        PostLike updatedPostLike = postLikeRepository.findById(postLike.getId()).get();
        // Disconnect from session so that the updates on updatedPostLike are not directly saved in db
        em.detach(updatedPostLike);
        PostLikeDTO postLikeDTO = postLikeMapper.toDto(updatedPostLike);

        restPostLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postLikeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postLikeDTO))
            )
            .andExpect(status().isOk());

        // Validate the PostLike in the database
        List<PostLike> postLikeList = postLikeRepository.findAll();
        assertThat(postLikeList).hasSize(databaseSizeBeforeUpdate);
        PostLike testPostLike = postLikeList.get(postLikeList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingPostLike() throws Exception {
        int databaseSizeBeforeUpdate = postLikeRepository.findAll().size();
        postLike.setId(count.incrementAndGet());

        // Create the PostLike
        PostLikeDTO postLikeDTO = postLikeMapper.toDto(postLike);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postLikeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostLike in the database
        List<PostLike> postLikeList = postLikeRepository.findAll();
        assertThat(postLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPostLike() throws Exception {
        int databaseSizeBeforeUpdate = postLikeRepository.findAll().size();
        postLike.setId(count.incrementAndGet());

        // Create the PostLike
        PostLikeDTO postLikeDTO = postLikeMapper.toDto(postLike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostLike in the database
        List<PostLike> postLikeList = postLikeRepository.findAll();
        assertThat(postLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPostLike() throws Exception {
        int databaseSizeBeforeUpdate = postLikeRepository.findAll().size();
        postLike.setId(count.incrementAndGet());

        // Create the PostLike
        PostLikeDTO postLikeDTO = postLikeMapper.toDto(postLike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostLikeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postLikeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PostLike in the database
        List<PostLike> postLikeList = postLikeRepository.findAll();
        assertThat(postLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePostLikeWithPatch() throws Exception {
        // Initialize the database
        postLikeRepository.saveAndFlush(postLike);

        int databaseSizeBeforeUpdate = postLikeRepository.findAll().size();

        // Update the postLike using partial update
        PostLike partialUpdatedPostLike = new PostLike();
        partialUpdatedPostLike.setId(postLike.getId());

        restPostLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostLike.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPostLike))
            )
            .andExpect(status().isOk());

        // Validate the PostLike in the database
        List<PostLike> postLikeList = postLikeRepository.findAll();
        assertThat(postLikeList).hasSize(databaseSizeBeforeUpdate);
        PostLike testPostLike = postLikeList.get(postLikeList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdatePostLikeWithPatch() throws Exception {
        // Initialize the database
        postLikeRepository.saveAndFlush(postLike);

        int databaseSizeBeforeUpdate = postLikeRepository.findAll().size();

        // Update the postLike using partial update
        PostLike partialUpdatedPostLike = new PostLike();
        partialUpdatedPostLike.setId(postLike.getId());

        restPostLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostLike.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPostLike))
            )
            .andExpect(status().isOk());

        // Validate the PostLike in the database
        List<PostLike> postLikeList = postLikeRepository.findAll();
        assertThat(postLikeList).hasSize(databaseSizeBeforeUpdate);
        PostLike testPostLike = postLikeList.get(postLikeList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingPostLike() throws Exception {
        int databaseSizeBeforeUpdate = postLikeRepository.findAll().size();
        postLike.setId(count.incrementAndGet());

        // Create the PostLike
        PostLikeDTO postLikeDTO = postLikeMapper.toDto(postLike);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, postLikeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostLike in the database
        List<PostLike> postLikeList = postLikeRepository.findAll();
        assertThat(postLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPostLike() throws Exception {
        int databaseSizeBeforeUpdate = postLikeRepository.findAll().size();
        postLike.setId(count.incrementAndGet());

        // Create the PostLike
        PostLikeDTO postLikeDTO = postLikeMapper.toDto(postLike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostLike in the database
        List<PostLike> postLikeList = postLikeRepository.findAll();
        assertThat(postLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPostLike() throws Exception {
        int databaseSizeBeforeUpdate = postLikeRepository.findAll().size();
        postLike.setId(count.incrementAndGet());

        // Create the PostLike
        PostLikeDTO postLikeDTO = postLikeMapper.toDto(postLike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostLikeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(postLikeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PostLike in the database
        List<PostLike> postLikeList = postLikeRepository.findAll();
        assertThat(postLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePostLike() throws Exception {
        // Initialize the database
        postLikeRepository.saveAndFlush(postLike);

        int databaseSizeBeforeDelete = postLikeRepository.findAll().size();

        // Delete the postLike
        restPostLikeMockMvc
            .perform(delete(ENTITY_API_URL_ID, postLike.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostLike> postLikeList = postLikeRepository.findAll();
        assertThat(postLikeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
