package com.cevheri.blog.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntegrationLogMapperTest {

    private IntegrationLogMapper integrationLogMapper;

    @BeforeEach
    public void setUp() {
        integrationLogMapper = new IntegrationLogMapperImpl();
    }
}
