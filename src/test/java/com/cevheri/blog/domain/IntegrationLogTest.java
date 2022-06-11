package com.cevheri.blog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cevheri.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntegrationLogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntegrationLog.class);
        IntegrationLog integrationLog1 = new IntegrationLog();
        integrationLog1.setId(1L);
        IntegrationLog integrationLog2 = new IntegrationLog();
        integrationLog2.setId(integrationLog1.getId());
        assertThat(integrationLog1).isEqualTo(integrationLog2);
        integrationLog2.setId(2L);
        assertThat(integrationLog1).isNotEqualTo(integrationLog2);
        integrationLog1.setId(null);
        assertThat(integrationLog1).isNotEqualTo(integrationLog2);
    }
}
