package com.cevheri.blog.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cevheri.blog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThirdPartyAppDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThirdPartyAppDTO.class);
        ThirdPartyAppDTO thirdPartyAppDTO1 = new ThirdPartyAppDTO();
        thirdPartyAppDTO1.setId(1L);
        ThirdPartyAppDTO thirdPartyAppDTO2 = new ThirdPartyAppDTO();
        assertThat(thirdPartyAppDTO1).isNotEqualTo(thirdPartyAppDTO2);
        thirdPartyAppDTO2.setId(thirdPartyAppDTO1.getId());
        assertThat(thirdPartyAppDTO1).isEqualTo(thirdPartyAppDTO2);
        thirdPartyAppDTO2.setId(2L);
        assertThat(thirdPartyAppDTO1).isNotEqualTo(thirdPartyAppDTO2);
        thirdPartyAppDTO1.setId(null);
        assertThat(thirdPartyAppDTO1).isNotEqualTo(thirdPartyAppDTO2);
    }
}
