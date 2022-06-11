package com.cevheri.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cevheri.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostLikeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostLikeDTO.class);
        PostLikeDTO postLikeDTO1 = new PostLikeDTO();
        postLikeDTO1.setId(1L);
        PostLikeDTO postLikeDTO2 = new PostLikeDTO();
        assertThat(postLikeDTO1).isNotEqualTo(postLikeDTO2);
        postLikeDTO2.setId(postLikeDTO1.getId());
        assertThat(postLikeDTO1).isEqualTo(postLikeDTO2);
        postLikeDTO2.setId(2L);
        assertThat(postLikeDTO1).isNotEqualTo(postLikeDTO2);
        postLikeDTO1.setId(null);
        assertThat(postLikeDTO1).isNotEqualTo(postLikeDTO2);
    }
}
