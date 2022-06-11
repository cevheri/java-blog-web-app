package com.cevheri.blog.service;

import com.cevheri.blog.service.dto.PostViewDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.cevheri.blog.domain.PostView}.
 */
public interface PostViewService {
    /**
     * Save a postView.
     *
     * @param postViewDTO the entity to save.
     * @return the persisted entity.
     */
    PostViewDTO save(PostViewDTO postViewDTO);

    /**
     * Updates a postView.
     *
     * @param postViewDTO the entity to update.
     * @return the persisted entity.
     */
    PostViewDTO update(PostViewDTO postViewDTO);

    /**
     * Partially updates a postView.
     *
     * @param postViewDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PostViewDTO> partialUpdate(PostViewDTO postViewDTO);

    /**
     * Get all the postViews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostViewDTO> findAll(Pageable pageable);

    /**
     * Get all the postViews with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostViewDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" postView.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostViewDTO> findOne(Long id);

    /**
     * Delete the "id" postView.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
