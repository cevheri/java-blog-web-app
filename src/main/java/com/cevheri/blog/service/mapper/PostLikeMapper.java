package com.cevheri.blog.service.mapper;

import com.cevheri.blog.domain.Post;
import com.cevheri.blog.domain.PostLike;
import com.cevheri.blog.service.dto.PostDTO;
import com.cevheri.blog.service.dto.PostLikeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostLike} and its DTO {@link PostLikeDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostLikeMapper extends EntityMapper<PostLikeDTO, PostLike> {
    @Mapping(target = "post", source = "post", qualifiedByName = "postTitle")
    PostLikeDTO toDto(PostLike s);

    @Named("postTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    PostDTO toDtoPostTitle(Post post);
}
