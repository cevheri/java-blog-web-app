package com.cevheri.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cevheri.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThirdPartyAppTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThirdPartyApp.class);
        ThirdPartyApp thirdPartyApp1 = new ThirdPartyApp();
        thirdPartyApp1.setId(1L);
        ThirdPartyApp thirdPartyApp2 = new ThirdPartyApp();
        thirdPartyApp2.setId(thirdPartyApp1.getId());
        assertThat(thirdPartyApp1).isEqualTo(thirdPartyApp2);
        thirdPartyApp2.setId(2L);
        assertThat(thirdPartyApp1).isNotEqualTo(thirdPartyApp2);
        thirdPartyApp1.setId(null);
        assertThat(thirdPartyApp1).isNotEqualTo(thirdPartyApp2);
    }
}
