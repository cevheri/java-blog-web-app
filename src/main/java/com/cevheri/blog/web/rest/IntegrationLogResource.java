package com.cevheri.blog.web.rest;

import com.cevheri.blog.repository.IntegrationLogRepository;
import com.cevheri.blog.service.IntegrationLogService;
import com.cevheri.blog.service.dto.IntegrationLogDTO;
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
 * REST controller for managing {@link com.cevheri.blog.domain.IntegrationLog}.
 */
@RestController
@RequestMapping("/api")
public class IntegrationLogResource {

    private final Logger log = LoggerFactory.getLogger(IntegrationLogResource.class);

    private static final String ENTITY_NAME = "integrationLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntegrationLogService integrationLogService;

    private final IntegrationLogRepository integrationLogRepository;

    public IntegrationLogResource(IntegrationLogService integrationLogService, IntegrationLogRepository integrationLogRepository) {
        this.integrationLogService = integrationLogService;
        this.integrationLogRepository = integrationLogRepository;
    }

    /**
     * {@code POST  /integration-logs} : Create a new integrationLog.
     *
     * @param integrationLogDTO the integrationLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new integrationLogDTO, or with status {@code 400 (Bad Request)} if the integrationLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/integration-logs")
    public ResponseEntity<IntegrationLogDTO> createIntegrationLog(@Valid @RequestBody IntegrationLogDTO integrationLogDTO)
        throws URISyntaxException {
        log.debug("REST request to save IntegrationLog : {}", integrationLogDTO);
        if (integrationLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new integrationLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntegrationLogDTO result = integrationLogService.save(integrationLogDTO);
        return ResponseEntity
            .created(new URI("/api/integration-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /integration-logs/:id} : Updates an existing integrationLog.
     *
     * @param id the id of the integrationLogDTO to save.
     * @param integrationLogDTO the integrationLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated integrationLogDTO,
     * or with status {@code 400 (Bad Request)} if the integrationLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the integrationLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/integration-logs/{id}")
    public ResponseEntity<IntegrationLogDTO> updateIntegrationLog(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IntegrationLogDTO integrationLogDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IntegrationLog : {}, {}", id, integrationLogDTO);
        if (integrationLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, integrationLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!integrationLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IntegrationLogDTO result = integrationLogService.update(integrationLogDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, integrationLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /integration-logs/:id} : Partial updates given fields of an existing integrationLog, field will ignore if it is null
     *
     * @param id the id of the integrationLogDTO to save.
     * @param integrationLogDTO the integrationLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated integrationLogDTO,
     * or with status {@code 400 (Bad Request)} if the integrationLogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the integrationLogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the integrationLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/integration-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IntegrationLogDTO> partialUpdateIntegrationLog(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IntegrationLogDTO integrationLogDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IntegrationLog partially : {}, {}", id, integrationLogDTO);
        if (integrationLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, integrationLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!integrationLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IntegrationLogDTO> result = integrationLogService.partialUpdate(integrationLogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, integrationLogDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /integration-logs} : get all the integrationLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of integrationLogs in body.
     */
    @GetMapping("/integration-logs")
    public ResponseEntity<List<IntegrationLogDTO>> getAllIntegrationLogs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of IntegrationLogs");
        Page<IntegrationLogDTO> page = integrationLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /integration-logs/:id} : get the "id" integrationLog.
     *
     * @param id the id of the integrationLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the integrationLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/integration-logs/{id}")
    public ResponseEntity<IntegrationLogDTO> getIntegrationLog(@PathVariable Long id) {
        log.debug("REST request to get IntegrationLog : {}", id);
        Optional<IntegrationLogDTO> integrationLogDTO = integrationLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(integrationLogDTO);
    }

    /**
     * {@code DELETE  /integration-logs/:id} : delete the "id" integrationLog.
     *
     * @param id the id of the integrationLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/integration-logs/{id}")
    public ResponseEntity<Void> deleteIntegrationLog(@PathVariable Long id) {
        log.debug("REST request to delete IntegrationLog : {}", id);
        integrationLogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
