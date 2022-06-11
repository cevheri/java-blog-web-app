package com.cevheri.blog.service.mapper;

import com.cevheri.blog.domain.Blog;
import com.cevheri.blog.domain.User;
import com.cevheri.blog.service.dto.BlogDTO;
import com.cevheri.blog.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Blog} and its DTO {@link BlogDTO}.
 */
@Mapper(componentModel = "spring")
public interface BlogMapper extends EntityMapper<BlogDTO, Blog> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    BlogDTO toDto(Blog s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
