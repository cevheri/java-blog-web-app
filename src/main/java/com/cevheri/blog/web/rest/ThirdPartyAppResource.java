package com.cevheri.blog.web.rest;

import com.cevheri.blog.repository.ThirdPartyAppRepository;
import com.cevheri.blog.service.ThirdPartyAppService;
import com.cevheri.blog.service.dto.ThirdPartyAppDTO;
import com.cevheri.blog.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.cevheri.blog.domain.ThirdPartyApp}.
 */
@RestController
@RequestMapping("/api")
public class ThirdPartyAppResource {

    private final Logger log = LoggerFactory.getLogger(ThirdPartyAppResource.class);

    private static final String ENTITY_NAME = "thirdPartyApp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThirdPartyAppService thirdPartyAppService;

    private final ThirdPartyAppRepository thirdPartyAppRepository;

    public ThirdPartyAppResource(ThirdPartyAppService thirdPartyAppService, ThirdPartyAppRepository thirdPartyAppRepository) {
        this.thirdPartyAppService = thirdPartyAppService;
        this.thirdPartyAppRepository = thirdPartyAppRepository;
    }

    /**
     * {@code POST  /third-party-apps} : Create a new thirdPartyApp.
     *
     * @param thirdPartyAppDTO the thirdPartyAppDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thirdPartyAppDTO, or with status {@code 400 (Bad Request)} if the thirdPartyApp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/third-party-apps")
    public ResponseEntity<ThirdPartyAppDTO> createThirdPartyApp(@Valid @RequestBody ThirdPartyAppDTO thirdPartyAppDTO)
        throws URISyntaxException {
        log.debug("REST request to save ThirdPartyApp : {}", thirdPartyAppDTO);
        if (thirdPartyAppDTO.getId() != null) {
            throw new BadRequestAlertException("A new thirdPartyApp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThirdPartyAppDTO result = thirdPartyAppService.save(thirdPartyAppDTO);
        return ResponseEntity
            .created(new URI("/api/third-party-apps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /third-party-apps/:id} : Updates an existing thirdPartyApp.
     *
     * @param id the id of the thirdPartyAppDTO to save.
     * @param thirdPartyAppDTO the thirdPartyAppDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thirdPartyAppDTO,
     * or with status {@code 400 (Bad Request)} if the thirdPartyAppDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thirdPartyAppDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/third-party-apps/{id}")
    public ResponseEntity<ThirdPartyAppDTO> updateThirdPartyApp(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ThirdPartyAppDTO thirdPartyAppDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ThirdPartyApp : {}, {}", id, thirdPartyAppDTO);
        if (thirdPartyAppDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thirdPartyAppDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thirdPartyAppRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ThirdPartyAppDTO result = thirdPartyAppService.update(thirdPartyAppDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thirdPartyAppDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /third-party-apps/:id} : Partial updates given fields of an existing thirdPartyApp, field will ignore if it is null
     *
     * @param id the id of the thirdPartyAppDTO to save.
     * @param thirdPartyAppDTO the thirdPartyAppDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thirdPartyAppDTO,
     * or with status {@code 400 (Bad Request)} if the thirdPartyAppDTO is not valid,
     * or with status {@code 404 (Not Found)} if the thirdPartyAppDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the thirdPartyAppDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/third-party-apps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ThirdPartyAppDTO> partialUpdateThirdPartyApp(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ThirdPartyAppDTO thirdPartyAppDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ThirdPartyApp partially : {}, {}", id, thirdPartyAppDTO);
        if (thirdPartyAppDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thirdPartyAppDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thirdPartyAppRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ThirdPartyAppDTO> result = thirdPartyAppService.partialUpdate(thirdPartyAppDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thirdPartyAppDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /third-party-apps} : get all the thirdPartyApps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thirdPartyApps in body.
     */
    @GetMapping("/third-party-apps")
    public ResponseEntity<List<ThirdPartyAppDTO>> getAllThirdPartyApps(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ThirdPartyApps");
        Page<ThirdPartyAppDTO> page = thirdPartyAppService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /third-party-apps/:id} : get the "id" thirdPartyApp.
     *
     * @param id the id of the thirdPartyAppDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thirdPartyAppDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/third-party-apps/{id}")
    public ResponseEntity<ThirdPartyAppDTO> getThirdPartyApp(@PathVariable Long id) {
        log.debug("REST request to get ThirdPartyApp : {}", id);
        Optional<ThirdPartyAppDTO> thirdPartyAppDTO = thirdPartyAppService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thirdPartyAppDTO);
    }

    /**
     * {@code DELETE  /third-party-apps/:id} : delete the "id" thirdPartyApp.
     *
     * @param id the id of the thirdPartyAppDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/third-party-apps/{id}")
    public ResponseEntity<Void> deleteThirdPartyApp(@PathVariable Long id) {
        log.debug("REST request to delete ThirdPartyApp : {}", id);
        thirdPartyAppService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
