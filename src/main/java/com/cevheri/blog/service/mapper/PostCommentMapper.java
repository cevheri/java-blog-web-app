package com.cevheri.blog.service.mapper;

import com.cevheri.blog.domain.Post;
import com.cevheri.blog.domain.PostComment;
import com.cevheri.blog.service.dto.PostCommentDTO;
import com.cevheri.blog.service.dto.PostDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostComment} and its DTO {@link PostCommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostCommentMapper extends EntityMapper<PostCommentDTO, PostComment> {
    @Mapping(target = "post", source = "post", qualifiedByName = "postTitle")
    PostCommentDTO toDto(PostComment s);

    @Named("postTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    PostDTO toDtoPostTitle(Post post);
}
