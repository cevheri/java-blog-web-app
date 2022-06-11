package com.cevheri.blog.service.mapper;

import com.cevheri.blog.domain.Tag;
import com.cevheri.blog.service.dto.TagDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDTO}.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagDTO, Tag> {}
