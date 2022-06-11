package com.cevheri.blog.service;

import com.cevheri.blog.service.dto.ThirdPartyAppDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.cevheri.blog.domain.ThirdPartyApp}.
 */
public interface ThirdPartyAppService {
    /**
     * Save a thirdPartyApp.
     *
     * @param thirdPartyAppDTO the entity to save.
     * @return the persisted entity.
     */
    ThirdPartyAppDTO save(ThirdPartyAppDTO thirdPartyAppDTO);

    /**
     * Updates a thirdPartyApp.
     *
     * @param thirdPartyAppDTO the entity to update.
     * @return the persisted entity.
     */
    ThirdPartyAppDTO update(ThirdPartyAppDTO thirdPartyAppDTO);

    /**
     * Partially updates a thirdPartyApp.
     *
     * @param thirdPartyAppDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ThirdPartyAppDTO> partialUpdate(ThirdPartyAppDTO thirdPartyAppDTO);

    /**
     * Get all the thirdPartyApps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ThirdPartyAppDTO> findAll(Pageable pageable);

    /**
     * Get the "id" thirdPartyApp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThirdPartyAppDTO> findOne(Long id);

    /**
     * Delete the "id" thirdPartyApp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
