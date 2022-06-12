package com.cevheri.blog.service.mapper;

import com.cevheri.blog.domain.Blog;
import com.cevheri.blog.domain.Post;
import com.cevheri.blog.domain.Tag;
import com.cevheri.blog.domain.User;
import com.cevheri.blog.service.dto.*;

import java.util.Set;
import java.util.stream.Collectors;

import com.cevheri.blog.service.medium.PostResponse;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDTO, Post> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "blog", source = "blog", qualifiedByName = "blogName")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "tagNameSet")
    PostDTO toDto(Post s);

    @Mapping(target = "removeTag", ignore = true)
    Post toEntity(PostDTO postDTO);

    @Mapping(target = "removeTag", ignore = true)
    Post toEntity(UpdatePostDTO postDTO);


    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("blogName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BlogDTO toDtoBlogName(Blog blog);

    @Named("tagName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    TagDTO toDtoTagName(Tag tag);

    @Named("tagNameSet")
    default Set<TagDTO> toDtoTagNameSet(Set<Tag> tag) {
        return tag.stream().map(this::toDtoTagName).collect(Collectors.toSet());
    }

    default PostResponse toPostModel(PostDTO postDTO) {
        PostResponse result = new PostResponse();
        PostResponse.PostData model = result.getData();
        if (postDTO.getIntegrationId() != null) {
            model.setId(postDTO.getIntegrationId());
        }
        model.setTitle(postDTO.getTitle());
        model.setTags(postDTO.getTags().stream().map(TagDTO::getName).collect(Collectors.toList()));
        model.setCanonicalUrl("https://cevheri-blog.herokuapp.com");
        model.setContent(postDTO.getContent());
        model.setLicense("all-rights-reserved");
        model.setContentFormat("html");
        model.setPublishStatus("public");
        result.setData(model);
        return result;
    }

}
