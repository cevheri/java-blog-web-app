package com.cevheri.blog.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostLikeMapperTest {

    private PostLikeMapper postLikeMapper;

    @BeforeEach
    public void setUp() {
        postLikeMapper = new PostLikeMapperImpl();
    }
}
