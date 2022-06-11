package com.cevheri.blog.web.rest;

import com.cevheri.blog.repository.PostViewRepository;
import com.cevheri.blog.service.PostViewService;
import com.cevheri.blog.service.dto.PostViewDTO;
import com.cevheri.blog.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.cevheri.blog.domain.PostView}.
 */
@RestController
@RequestMapping("/api")
public class PostViewResource {

    private final Logger log = LoggerFactory.getLogger(PostViewResource.class);

    private static final String ENTITY_NAME = "postView";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostViewService postViewService;

    private final PostViewRepository postViewRepository;

    public PostViewResource(PostViewService postViewService, PostViewRepository postViewRepository) {
        this.postViewService = postViewService;
        this.postViewRepository = postViewRepository;
    }

    /**
     * {@code POST  /post-views} : Create a new postView.
     *
     * @param postViewDTO the postViewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postViewDTO, or with status {@code 400 (Bad Request)} if the postView has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-views")
    public ResponseEntity<PostViewDTO> createPostView(@RequestBody PostViewDTO postViewDTO) throws URISyntaxException {
        log.debug("REST request to save PostView : {}", postViewDTO);
        if (postViewDTO.getId() != null) {
            throw new BadRequestAlertException("A new postView cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostViewDTO result = postViewService.save(postViewDTO);
        return ResponseEntity
            .created(new URI("/api/post-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-views/:id} : Updates an existing postView.
     *
     * @param id the id of the postViewDTO to save.
     * @param postViewDTO the postViewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postViewDTO,
     * or with status {@code 400 (Bad Request)} if the postViewDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postViewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-views/{id}")
    public ResponseEntity<PostViewDTO> updatePostView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PostViewDTO postViewDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PostView : {}, {}", id, postViewDTO);
        if (postViewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postViewDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PostViewDTO result = postViewService.update(postViewDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postViewDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /post-views/:id} : Partial updates given fields of an existing postView, field will ignore if it is null
     *
     * @param id the id of the postViewDTO to save.
     * @param postViewDTO the postViewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postViewDTO,
     * or with status {@code 400 (Bad Request)} if the postViewDTO is not valid,
     * or with status {@code 404 (Not Found)} if the postViewDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the postViewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/post-views/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PostViewDTO> partialUpdatePostView(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PostViewDTO postViewDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PostView partially : {}, {}", id, postViewDTO);
        if (postViewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postViewDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PostViewDTO> result = postViewService.partialUpdate(postViewDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postViewDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /post-views} : get all the postViews.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postViews in body.
     */
    @GetMapping("/post-views")
    public ResponseEntity<List<PostViewDTO>> getAllPostViews(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of PostViews");
        Page<PostViewDTO> page;
        if (eagerload) {
            page = postViewService.findAllWithEagerRelationships(pageable);
        } else {
            page = postViewService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /post-views/:id} : get the "id" postView.
     *
     * @param id the id of the postViewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postViewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-views/{id}")
    public ResponseEntity<PostViewDTO> getPostView(@PathVariable Long id) {
        log.debug("REST request to get PostView : {}", id);
        Optional<PostViewDTO> postViewDTO = postViewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postViewDTO);
    }

    /**
     * {@code DELETE  /post-views/:id} : delete the "id" postView.
     *
     * @param id the id of the postViewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-views/{id}")
    public ResponseEntity<Void> deletePostView(@PathVariable Long id) {
        log.debug("REST request to delete PostView : {}", id);
        postViewService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
