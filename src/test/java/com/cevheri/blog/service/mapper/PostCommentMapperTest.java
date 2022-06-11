package com.cevheri.blog.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostCommentMapperTest {

    private PostCommentMapper postCommentMapper;

    @BeforeEach
    public void setUp() {
        postCommentMapper = new PostCommentMapperImpl();
    }
}
