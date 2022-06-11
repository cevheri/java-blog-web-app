package com.cevheri.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cevheri.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostViewDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostViewDTO.class);
        PostViewDTO postViewDTO1 = new PostViewDTO();
        postViewDTO1.setId(1L);
        PostViewDTO postViewDTO2 = new PostViewDTO();
        assertThat(postViewDTO1).isNotEqualTo(postViewDTO2);
        postViewDTO2.setId(postViewDTO1.getId());
        assertThat(postViewDTO1).isEqualTo(postViewDTO2);
        postViewDTO2.setId(2L);
        assertThat(postViewDTO1).isNotEqualTo(postViewDTO2);
        postViewDTO1.setId(null);
        assertThat(postViewDTO1).isNotEqualTo(postViewDTO2);
    }
}
