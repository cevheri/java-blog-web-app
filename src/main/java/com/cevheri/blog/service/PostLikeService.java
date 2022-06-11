package com.cevheri.blog.service;

import com.cevheri.blog.service.dto.PostLikeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.cevheri.blog.domain.PostLike}.
 */
public interface PostLikeService {
    /**
     * Save a postLike.
     *
     * @param postLikeDTO the entity to save.
     * @return the persisted entity.
     */
    PostLikeDTO save(PostLikeDTO postLikeDTO);

    /**
     * Updates a postLike.
     *
     * @param postLikeDTO the entity to update.
     * @return the persisted entity.
     */
    PostLikeDTO update(PostLikeDTO postLikeDTO);

    /**
     * Partially updates a postLike.
     *
     * @param postLikeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PostLikeDTO> partialUpdate(PostLikeDTO postLikeDTO);

    /**
     * Get all the postLikes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostLikeDTO> findAll(Pageable pageable);

    /**
     * Get all the postLikes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostLikeDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" postLike.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostLikeDTO> findOne(Long id);

    /**
     * Delete the "id" postLike.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
