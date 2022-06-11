package com.cevheri.blog.service.mapper;

import com.cevheri.blog.domain.IntegrationLog;
import com.cevheri.blog.service.dto.IntegrationLogDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IntegrationLog} and its DTO {@link IntegrationLogDTO}.
 */
@Mapper(componentModel = "spring")
public interface IntegrationLogMapper extends EntityMapper<IntegrationLogDTO, IntegrationLog> {}
