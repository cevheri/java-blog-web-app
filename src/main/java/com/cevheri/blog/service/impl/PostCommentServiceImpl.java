package com.cevheri.blog.service.impl;

import com.cevheri.blog.domain.PostComment;
import com.cevheri.blog.repository.PostCommentRepository;
import com.cevheri.blog.service.PostCommentService;
import com.cevheri.blog.service.dto.PostCommentDTO;
import com.cevheri.blog.service.mapper.PostCommentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PostComment}.
 */
@Service
@Transactional
public class PostCommentServiceImpl implements PostCommentService {

    private final Logger log = LoggerFactory.getLogger(PostCommentServiceImpl.class);

    private final PostCommentRepository postCommentRepository;

    private final PostCommentMapper postCommentMapper;

    public PostCommentServiceImpl(PostCommentRepository postCommentRepository, PostCommentMapper postCommentMapper) {
        this.postCommentRepository = postCommentRepository;
        this.postCommentMapper = postCommentMapper;
    }

    @Override
    public PostCommentDTO save(PostCommentDTO postCommentDTO) {
        log.debug("Request to save PostComment : {}", postCommentDTO);
        PostComment postComment = postCommentMapper.toEntity(postCommentDTO);
        postComment = postCommentRepository.save(postComment);
        return postCommentMapper.toDto(postComment);
    }

    @Override
    public PostCommentDTO update(PostCommentDTO postCommentDTO) {
        log.debug("Request to save PostComment : {}", postCommentDTO);
        PostComment postComment = postCommentMapper.toEntity(postCommentDTO);
        postComment = postCommentRepository.save(postComment);
        return postCommentMapper.toDto(postComment);
    }

    @Override
    public Optional<PostCommentDTO> partialUpdate(PostCommentDTO postCommentDTO) {
        log.debug("Request to partially update PostComment : {}", postCommentDTO);

        return postCommentRepository
            .findById(postCommentDTO.getId())
            .map(existingPostComment -> {
                postCommentMapper.partialUpdate(existingPostComment, postCommentDTO);

                return existingPostComment;
            })
            .map(postCommentRepository::save)
            .map(postCommentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PostComments");
        return postCommentRepository.findAll(pageable).map(postCommentMapper::toDto);
    }

    public Page<PostCommentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return postCommentRepository.findAllWithEagerRelationships(pageable).map(postCommentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PostCommentDTO> findOne(Long id) {
        log.debug("Request to get PostComment : {}", id);
        return postCommentRepository.findOneWithEagerRelationships(id).map(postCommentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PostComment : {}", id);
        postCommentRepository.deleteById(id);
    }
}
