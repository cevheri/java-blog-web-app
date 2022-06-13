package com.cevheri.blog.web.rest;

import com.cevheri.blog.repository.PostLikeRepository;
import com.cevheri.blog.service.PostLikeService;
import com.cevheri.blog.service.dto.PostLikeDTO;
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
 * REST controller for managing {@link com.cevheri.blog.domain.PostLike}.
 */
@RestController
@RequestMapping("/api")
public class PostLikeResource {

    private final Logger log = LoggerFactory.getLogger(PostLikeResource.class);

    private static final String ENTITY_NAME = "postLike";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostLikeService postLikeService;

    private final PostLikeRepository postLikeRepository;

    public PostLikeResource(PostLikeService postLikeService, PostLikeRepository postLikeRepository) {
        this.postLikeService = postLikeService;
        this.postLikeRepository = postLikeRepository;
    }

    /**
     * {@code POST  /post-likes} : Create a new postLike.
     *
     * @param postLikeDTO the postLikeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postLikeDTO, or with status {@code 400 (Bad Request)} if the postLike has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-likes")
    public ResponseEntity<PostLikeDTO> createPostLike(@RequestBody PostLikeDTO postLikeDTO) throws URISyntaxException {
        log.debug("REST request to save PostLike : {}", postLikeDTO);
        if (postLikeDTO.getId() != null) {
            throw new BadRequestAlertException("A new postLike cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostLikeDTO result = postLikeService.save(postLikeDTO);
        return ResponseEntity
            .created(new URI("/api/post-likes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /post-likes} : get all the postLikes.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postLikes in body.
     */
    @GetMapping("/post-likes")
    public ResponseEntity<List<PostLikeDTO>> getAllPostLikes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of PostLikes");
        Page<PostLikeDTO> page;
        if (eagerload) {
            page = postLikeService.findAllWithEagerRelationships(pageable);
        } else {
            page = postLikeService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /post-likes/:id} : get the "id" postLike.
     *
     * @param id the id of the postLikeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postLikeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-likes/{id}")
    public ResponseEntity<PostLikeDTO> getPostLike(@PathVariable Long id) {
        log.debug("REST request to get PostLike : {}", id);
        Optional<PostLikeDTO> postLikeDTO = postLikeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postLikeDTO);
    }

    /**
     * {@code DELETE  /post-likes/:id} : delete the "id" postLike.
     *
     * @param id the id of the postLikeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-likes/{id}")
    public ResponseEntity<Void> deletePostLike(@PathVariable Long id) {
        log.debug("REST request to delete PostLike : {}", id);
        postLikeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
