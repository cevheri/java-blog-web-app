package com.cevheri.blog.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThirdPartyAppMapperTest {

    private ThirdPartyAppMapper thirdPartyAppMapper;

    @BeforeEach
    public void setUp() {
        thirdPartyAppMapper = new ThirdPartyAppMapperImpl();
    }
}
