package com.cevheri.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cevheri.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostViewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostView.class);
        PostView postView1 = new PostView();
        postView1.setId(1L);
        PostView postView2 = new PostView();
        postView2.setId(postView1.getId());
        assertThat(postView1).isEqualTo(postView2);
        postView2.setId(2L);
        assertThat(postView1).isNotEqualTo(postView2);
        postView1.setId(null);
        assertThat(postView1).isNotEqualTo(postView2);
    }
}
