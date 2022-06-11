package com.cevheri.blog.service.impl;

import com.cevheri.blog.domain.PostView;
import com.cevheri.blog.repository.PostViewRepository;
import com.cevheri.blog.service.PostViewService;
import com.cevheri.blog.service.dto.PostViewDTO;
import com.cevheri.blog.service.mapper.PostViewMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PostView}.
 */
@Service
@Transactional
public class PostViewServiceImpl implements PostViewService {

    private final Logger log = LoggerFactory.getLogger(PostViewServiceImpl.class);

    private final PostViewRepository postViewRepository;

    private final PostViewMapper postViewMapper;

    public PostViewServiceImpl(PostViewRepository postViewRepository, PostViewMapper postViewMapper) {
        this.postViewRepository = postViewRepository;
        this.postViewMapper = postViewMapper;
    }

    @Override
    public PostViewDTO save(PostViewDTO postViewDTO) {
        log.debug("Request to save PostView : {}", postViewDTO);
        PostView postView = postViewMapper.toEntity(postViewDTO);
        postView = postViewRepository.save(postView);
        return postViewMapper.toDto(postView);
    }

    @Override
    public PostViewDTO update(PostViewDTO postViewDTO) {
        log.debug("Request to save PostView : {}", postViewDTO);
        PostView postView = postViewMapper.toEntity(postViewDTO);
        postView = postViewRepository.save(postView);
        return postViewMapper.toDto(postView);
    }

    @Override
    public Optional<PostViewDTO> partialUpdate(PostViewDTO postViewDTO) {
        log.debug("Request to partially update PostView : {}", postViewDTO);

        return postViewRepository
            .findById(postViewDTO.getId())
            .map(existingPostView -> {
                postViewMapper.partialUpdate(existingPostView, postViewDTO);

                return existingPostView;
            })
            .map(postViewRepository::save)
            .map(postViewMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostViewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PostViews");
        return postViewRepository.findAll(pageable).map(postViewMapper::toDto);
    }

    public Page<PostViewDTO> findAllWithEagerRelationships(Pageable pageable) {
        return postViewRepository.findAllWithEagerRelationships(pageable).map(postViewMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PostViewDTO> findOne(Long id) {
        log.debug("Request to get PostView : {}", id);
        return postViewRepository.findOneWithEagerRelationships(id).map(postViewMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PostView : {}", id);
        postViewRepository.deleteById(id);
    }
}
