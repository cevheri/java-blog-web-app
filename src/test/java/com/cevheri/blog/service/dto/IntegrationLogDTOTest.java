package com.cevheri.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cevheri.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IntegrationLogDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntegrationLogDTO.class);
        IntegrationLogDTO integrationLogDTO1 = new IntegrationLogDTO();
        integrationLogDTO1.setId(1L);
        IntegrationLogDTO integrationLogDTO2 = new IntegrationLogDTO();
        assertThat(integrationLogDTO1).isNotEqualTo(integrationLogDTO2);
        integrationLogDTO2.setId(integrationLogDTO1.getId());
        assertThat(integrationLogDTO1).isEqualTo(integrationLogDTO2);
        integrationLogDTO2.setId(2L);
        assertThat(integrationLogDTO1).isNotEqualTo(integrationLogDTO2);
        integrationLogDTO1.setId(null);
        assertThat(integrationLogDTO1).isNotEqualTo(integrationLogDTO2);
    }
}
