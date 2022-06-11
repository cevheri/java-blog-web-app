package com.cevheri.blog.service.mapper;

import com.cevheri.blog.domain.ThirdPartyApp;
import com.cevheri.blog.service.dto.ThirdPartyAppDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ThirdPartyApp} and its DTO {@link ThirdPartyAppDTO}.
 */
@Mapper(componentModel = "spring")
public interface ThirdPartyAppMapper extends EntityMapper<ThirdPartyAppDTO, ThirdPartyApp> {}
