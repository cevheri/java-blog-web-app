package com.cevheri.blog.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cevheri.blog.IntegrationTest;
import com.cevheri.blog.domain.PostComment;
import com.cevheri.blog.repository.PostCommentRepository;
import com.cevheri.blog.service.PostCommentService;
import com.cevheri.blog.service.dto.PostCommentDTO;
import com.cevheri.blog.service.mapper.PostCommentMapper;
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
 * Integration tests for the {@link PostCommentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PostCommentResourceIT {

    private static final String DEFAULT_COMMENT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_TEXT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/post-comments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Mock
    private PostCommentRepository postCommentRepositoryMock;

    @Autowired
    private PostCommentMapper postCommentMapper;

    @Mock
    private PostCommentService postCommentServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostCommentMockMvc;

    private PostComment postComment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostComment createEntity(EntityManager em) {
        PostComment postComment = new PostComment().commentText(DEFAULT_COMMENT_TEXT);
        return postComment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostComment createUpdatedEntity(EntityManager em) {
        PostComment postComment = new PostComment().commentText(UPDATED_COMMENT_TEXT);
        return postComment;
    }

    @BeforeEach
    public void initTest() {
        postComment = createEntity(em);
    }

    @Test
    @Transactional
    void createPostComment() throws Exception {
        int databaseSizeBeforeCreate = postCommentRepository.findAll().size();
        // Create the PostComment
        PostCommentDTO postCommentDTO = postCommentMapper.toDto(postComment);
        restPostCommentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postCommentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PostComment in the database
        List<PostComment> postCommentList = postCommentRepository.findAll();
        assertThat(postCommentList).hasSize(databaseSizeBeforeCreate + 1);
        PostComment testPostComment = postCommentList.get(postCommentList.size() - 1);
        assertThat(testPostComment.getCommentText()).isEqualTo(DEFAULT_COMMENT_TEXT);
    }

    @Test
    @Transactional
    void createPostCommentWithExistingId() throws Exception {
        // Create the PostComment with an existing ID
        postComment.setId(1L);
        PostCommentDTO postCommentDTO = postCommentMapper.toDto(postComment);

        int databaseSizeBeforeCreate = postCommentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostCommentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostComment in the database
        List<PostComment> postCommentList = postCommentRepository.findAll();
        assertThat(postCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCommentTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = postCommentRepository.findAll().size();
        // set the field null
        postComment.setCommentText(null);

        // Create the PostComment, which fails.
        PostCommentDTO postCommentDTO = postCommentMapper.toDto(postComment);

        restPostCommentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postCommentDTO))
            )
            .andExpect(status().isBadRequest());

        List<PostComment> postCommentList = postCommentRepository.findAll();
        assertThat(postCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPostComments() throws Exception {
        // Initialize the database
        postCommentRepository.saveAndFlush(postComment);

        // Get all the postCommentList
        restPostCommentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].commentText").value(hasItem(DEFAULT_COMMENT_TEXT)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPostCommentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(postCommentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostCommentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(postCommentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPostCommentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(postCommentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostCommentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(postCommentRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPostComment() throws Exception {
        // Initialize the database
        postCommentRepository.saveAndFlush(postComment);

        // Get the postComment
        restPostCommentMockMvc
            .perform(get(ENTITY_API_URL_ID, postComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postComment.getId().intValue()))
            .andExpect(jsonPath("$.commentText").value(DEFAULT_COMMENT_TEXT));
    }

    @Test
    @Transactional
    void getNonExistingPostComment() throws Exception {
        // Get the postComment
        restPostCommentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPostComment() throws Exception {
        // Initialize the database
        postCommentRepository.saveAndFlush(postComment);

        int databaseSizeBeforeUpdate = postCommentRepository.findAll().size();

        // Update the postComment
        PostComment updatedPostComment = postCommentRepository.findById(postComment.getId()).get();
        // Disconnect from session so that the updates on updatedPostComment are not directly saved in db
        em.detach(updatedPostComment);
        updatedPostComment.commentText(UPDATED_COMMENT_TEXT);
        PostCommentDTO postCommentDTO = postCommentMapper.toDto(updatedPostComment);

        restPostCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postCommentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postCommentDTO))
            )
            .andExpect(status().isOk());

        // Validate the PostComment in the database
        List<PostComment> postCommentList = postCommentRepository.findAll();
        assertThat(postCommentList).hasSize(databaseSizeBeforeUpdate);
        PostComment testPostComment = postCommentList.get(postCommentList.size() - 1);
        assertThat(testPostComment.getCommentText()).isEqualTo(UPDATED_COMMENT_TEXT);
    }

    @Test
    @Transactional
    void putNonExistingPostComment() throws Exception {
        int databaseSizeBeforeUpdate = postCommentRepository.findAll().size();
        postComment.setId(count.incrementAndGet());

        // Create the PostComment
        PostCommentDTO postCommentDTO = postCommentMapper.toDto(postComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postCommentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostComment in the database
        List<PostComment> postCommentList = postCommentRepository.findAll();
        assertThat(postCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPostComment() throws Exception {
        int databaseSizeBeforeUpdate = postCommentRepository.findAll().size();
        postComment.setId(count.incrementAndGet());

        // Create the PostComment
        PostCommentDTO postCommentDTO = postCommentMapper.toDto(postComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostComment in the database
        List<PostComment> postCommentList = postCommentRepository.findAll();
        assertThat(postCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPostComment() throws Exception {
        int databaseSizeBeforeUpdate = postCommentRepository.findAll().size();
        postComment.setId(count.incrementAndGet());

        // Create the PostComment
        PostCommentDTO postCommentDTO = postCommentMapper.toDto(postComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostCommentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postCommentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PostComment in the database
        List<PostComment> postCommentList = postCommentRepository.findAll();
        assertThat(postCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePostCommentWithPatch() throws Exception {
        // Initialize the database
        postCommentRepository.saveAndFlush(postComment);

        int databaseSizeBeforeUpdate = postCommentRepository.findAll().size();

        // Update the postComment using partial update
        PostComment partialUpdatedPostComment = new PostComment();
        partialUpdatedPostComment.setId(postComment.getId());

        partialUpdatedPostComment.commentText(UPDATED_COMMENT_TEXT);

        restPostCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostComment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPostComment))
            )
            .andExpect(status().isOk());

        // Validate the PostComment in the database
        List<PostComment> postCommentList = postCommentRepository.findAll();
        assertThat(postCommentList).hasSize(databaseSizeBeforeUpdate);
        PostComment testPostComment = postCommentList.get(postCommentList.size() - 1);
        assertThat(testPostComment.getCommentText()).isEqualTo(UPDATED_COMMENT_TEXT);
    }

    @Test
    @Transactional
    void fullUpdatePostCommentWithPatch() throws Exception {
        // Initialize the database
        postCommentRepository.saveAndFlush(postComment);

        int databaseSizeBeforeUpdate = postCommentRepository.findAll().size();

        // Update the postComment using partial update
        PostComment partialUpdatedPostComment = new PostComment();
        partialUpdatedPostComment.setId(postComment.getId());

        partialUpdatedPostComment.commentText(UPDATED_COMMENT_TEXT);

        restPostCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostComment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPostComment))
            )
            .andExpect(status().isOk());

        // Validate the PostComment in the database
        List<PostComment> postCommentList = postCommentRepository.findAll();
        assertThat(postCommentList).hasSize(databaseSizeBeforeUpdate);
        PostComment testPostComment = postCommentList.get(postCommentList.size() - 1);
        assertThat(testPostComment.getCommentText()).isEqualTo(UPDATED_COMMENT_TEXT);
    }

    @Test
    @Transactional
    void patchNonExistingPostComment() throws Exception {
        int databaseSizeBeforeUpdate = postCommentRepository.findAll().size();
        postComment.setId(count.incrementAndGet());

        // Create the PostComment
        PostCommentDTO postCommentDTO = postCommentMapper.toDto(postComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, postCommentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostComment in the database
        List<PostComment> postCommentList = postCommentRepository.findAll();
        assertThat(postCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPostComment() throws Exception {
        int databaseSizeBeforeUpdate = postCommentRepository.findAll().size();
        postComment.setId(count.incrementAndGet());

        // Create the PostComment
        PostCommentDTO postCommentDTO = postCommentMapper.toDto(postComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postCommentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostComment in the database
        List<PostComment> postCommentList = postCommentRepository.findAll();
        assertThat(postCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPostComment() throws Exception {
        int databaseSizeBeforeUpdate = postCommentRepository.findAll().size();
        postComment.setId(count.incrementAndGet());

        // Create the PostComment
        PostCommentDTO postCommentDTO = postCommentMapper.toDto(postComment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostCommentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(postCommentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PostComment in the database
        List<PostComment> postCommentList = postCommentRepository.findAll();
        assertThat(postCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePostComment() throws Exception {
        // Initialize the database
        postCommentRepository.saveAndFlush(postComment);

        int databaseSizeBeforeDelete = postCommentRepository.findAll().size();

        // Delete the postComment
        restPostCommentMockMvc
            .perform(delete(ENTITY_API_URL_ID, postComment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostComment> postCommentList = postCommentRepository.findAll();
        assertThat(postCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
