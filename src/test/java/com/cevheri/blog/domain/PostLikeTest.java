package com.cevheri.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cevheri.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostLikeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostLike.class);
        PostLike postLike1 = new PostLike();
        postLike1.setId(1L);
        PostLike postLike2 = new PostLike();
        postLike2.setId(postLike1.getId());
        assertThat(postLike1).isEqualTo(postLike2);
        postLike2.setId(2L);
        assertThat(postLike1).isNotEqualTo(postLike2);
        postLike1.setId(null);
        assertThat(postLike1).isNotEqualTo(postLike2);
    }
}
