package com.cevheri.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cevheri.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostCommentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostCommentDTO.class);
        PostCommentDTO postCommentDTO1 = new PostCommentDTO();
        postCommentDTO1.setId(1L);
        PostCommentDTO postCommentDTO2 = new PostCommentDTO();
        assertThat(postCommentDTO1).isNotEqualTo(postCommentDTO2);
        postCommentDTO2.setId(postCommentDTO1.getId());
        assertThat(postCommentDTO1).isEqualTo(postCommentDTO2);
        postCommentDTO2.setId(2L);
        assertThat(postCommentDTO1).isNotEqualTo(postCommentDTO2);
        postCommentDTO1.setId(null);
        assertThat(postCommentDTO1).isNotEqualTo(postCommentDTO2);
    }
}
