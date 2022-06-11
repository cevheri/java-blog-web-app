package com.cevheri.blog.service.mapper;

import com.cevheri.blog.domain.Post;
import com.cevheri.blog.domain.PostView;
import com.cevheri.blog.service.dto.PostDTO;
import com.cevheri.blog.service.dto.PostViewDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostView} and its DTO {@link PostViewDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostViewMapper extends EntityMapper<PostViewDTO, PostView> {
    @Mapping(target = "post", source = "post", qualifiedByName = "postTitle")
    PostViewDTO toDto(PostView s);

    @Named("postTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    PostDTO toDtoPostTitle(Post post);
}
