package com.cevheri.blog.service.impl;

import com.cevheri.blog.domain.PostLike;
import com.cevheri.blog.repository.PostLikeRepository;
import com.cevheri.blog.service.PostLikeService;
import com.cevheri.blog.service.dto.PostLikeDTO;
import com.cevheri.blog.service.mapper.PostLikeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PostLike}.
 */
@Service
@Transactional
public class PostLikeServiceImpl implements PostLikeService {

    private final Logger log = LoggerFactory.getLogger(PostLikeServiceImpl.class);

    private final PostLikeRepository postLikeRepository;

    private final PostLikeMapper postLikeMapper;

    public PostLikeServiceImpl(PostLikeRepository postLikeRepository, PostLikeMapper postLikeMapper) {
        this.postLikeRepository = postLikeRepository;
        this.postLikeMapper = postLikeMapper;
    }

    @Override
    public PostLikeDTO save(PostLikeDTO postLikeDTO) {
        log.debug("Request to save PostLike : {}", postLikeDTO);
        PostLike postLike = postLikeMapper.toEntity(postLikeDTO);
        postLike = postLikeRepository.save(postLike);
        return postLikeMapper.toDto(postLike);
    }

    @Override
    public PostLikeDTO update(PostLikeDTO postLikeDTO) {
        log.debug("Request to save PostLike : {}", postLikeDTO);
        PostLike postLike = postLikeMapper.toEntity(postLikeDTO);
        postLike = postLikeRepository.save(postLike);
        return postLikeMapper.toDto(postLike);
    }

    @Override
    public Optional<PostLikeDTO> partialUpdate(PostLikeDTO postLikeDTO) {
        log.debug("Request to partially update PostLike : {}", postLikeDTO);

        return postLikeRepository
            .findById(postLikeDTO.getId())
            .map(existingPostLike -> {
                postLikeMapper.partialUpdate(existingPostLike, postLikeDTO);

                return existingPostLike;
            })
            .map(postLikeRepository::save)
            .map(postLikeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostLikeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PostLikes");
        return postLikeRepository.findAll(pageable).map(postLikeMapper::toDto);
    }

    public Page<PostLikeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return postLikeRepository.findAllWithEagerRelationships(pageable).map(postLikeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PostLikeDTO> findOne(Long id) {
        log.debug("Request to get PostLike : {}", id);
        return postLikeRepository.findOneWithEagerRelationships(id).map(postLikeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PostLike : {}", id);
        postLikeRepository.deleteById(id);
    }
}
