package com.cevheri.blog.service.impl;

import com.cevheri.blog.domain.Post;
import com.cevheri.blog.repository.PostLikeRepository;
import com.cevheri.blog.repository.PostRepository;
import com.cevheri.blog.repository.PostViewRepository;
import com.cevheri.blog.service.PostService;
import com.cevheri.blog.service.PostViewService;
import com.cevheri.blog.service.ThirdPartyBlogService;
import com.cevheri.blog.service.dto.PostDTO;
import com.cevheri.blog.service.dto.PostViewDTO;
import com.cevheri.blog.service.dto.UpdatePostDTO;
import com.cevheri.blog.service.mapper.PostMapper;

import java.util.Optional;

import com.cevheri.blog.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Post}.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;
    private final PostViewRepository postViewRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostMapper postMapper;
    private final ThirdPartyBlogService thirdPartyBlogService;
    private final PostViewService postViewService;

    public PostServiceImpl(PostRepository postRepository,
                           PostViewRepository postViewRepository,
                           PostLikeRepository postLikeRepository,
                           PostMapper postMapper,
                           ThirdPartyBlogService thirdPartyBlogService,
                           PostViewService postViewService) {
        this.postRepository = postRepository;
        this.postViewRepository = postViewRepository;
        this.postLikeRepository = postLikeRepository;
        this.postMapper = postMapper;
        this.thirdPartyBlogService = thirdPartyBlogService;
        this.postViewService = postViewService;
    }

    @Override
    public PostDTO save(PostDTO postDTO) {
        log.debug("Request to save Post : {}", postDTO);
        Post post = postMapper.toEntity(postDTO);
        post = postRepository.save(post);
        if (postDTO.getPublishThirdPartyApp()) {

            String integrationId = thirdPartyBlogService.sendPost(postDTO);

            post.setIntegrationId(integrationId);
            postRepository.save(post);
        }
        return postMapper.toDto(post);
    }


    @Override
    public PostDTO update(UpdatePostDTO postDTO) {
        log.debug("Request to save Post : {}", postDTO);
        Post post = postMapper.toEntity(postDTO);
        post = postRepository.save(post);
        var result = postMapper.toDto(post);
        if (post.getIntegrationId() != null) {
            thirdPartyBlogService.sendPost(result);
        }
        return result;
    }

    @Override
    public Optional<PostDTO> partialUpdate(PostDTO postDTO) {
        log.debug("Request to partially update Post : {}", postDTO);

        return postRepository
            .findById(postDTO.getId())
            .map(existingPost -> {
                postMapper.partialUpdate(existingPost, postDTO);

                return existingPost;
            })
            .map(postRepository::save)
            .map(postMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Posts");
        return postRepository
            .findAll(pageable)
            .map(postMapper::toDto)
            ;
    }

    public Page<PostDTO> findAllWithEagerRelationships(Pageable pageable) {
        return postRepository.findAllWithEagerRelationships(pageable).map(postMapper::toDto);
    }

    @Override
    public Optional<PostDTO> findOne(Long id) {
        log.debug("Request to get Post : {}", id);
        var result = postRepository.findOneWithEagerRelationships(id)
            .map(postMapper::toDto);
        result.ifPresentOrElse(t -> {
                PostViewDTO postView = new PostViewDTO();
                postView.setPost(t);
                postViewService.save(postView);
            },
            () -> {
                throw new BadRequestAlertException("Entity not found", "post", "idnotfound");
            }
        );
        PostDTO postDTO = result.get();
        postDTO.setLikeCount(postLikeRepository.countByPost_Id(postDTO.getId()));
        postDTO.setViewCount(postViewRepository.countByPost_Id(postDTO.getId()));
        return result;
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.deleteById(id);
    }

    @Override
    public Integer viewCount(Long id) {
        log.debug("Request to ViewCount Post : {}", id);
        return postViewRepository.countByPost_Id(id);
    }
}
