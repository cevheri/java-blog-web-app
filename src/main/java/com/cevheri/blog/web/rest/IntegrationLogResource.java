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

}
