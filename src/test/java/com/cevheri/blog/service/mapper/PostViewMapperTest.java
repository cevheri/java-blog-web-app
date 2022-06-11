package com.cevheri.blog.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostViewMapperTest {

    private PostViewMapper postViewMapper;

    @BeforeEach
    public void setUp() {
        postViewMapper = new PostViewMapperImpl();
    }
}
