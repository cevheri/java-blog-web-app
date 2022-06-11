package com.cevheri.blog.service;

import com.cevheri.blog.service.dto.PostCommentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.cevheri.blog.domain.PostComment}.
 */
public interface PostCommentService {
    /**
     * Save a postComment.
     *
     * @param postCommentDTO the entity to save.
     * @return the persisted entity.
     */
    PostCommentDTO save(PostCommentDTO postCommentDTO);

    /**
     * Updates a postComment.
     *
     * @param postCommentDTO the entity to update.
     * @return the persisted entity.
     */
    PostCommentDTO update(PostCommentDTO postCommentDTO);

    /**
     * Partially updates a postComment.
     *
     * @param postCommentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PostCommentDTO> partialUpdate(PostCommentDTO postCommentDTO);

    /**
     * Get all the postComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostCommentDTO> findAll(Pageable pageable);

    /**
     * Get all the postComments with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostCommentDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" postComment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostCommentDTO> findOne(Long id);

    /**
     * Delete the "id" postComment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
