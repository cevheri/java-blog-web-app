package com.cevheri.blog.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cevheri.blog.domain.PostView} entity.
 */
@Schema(description = "System User view for Post Analytics")
public class PostViewDTO implements Serializable {

    private Long id;

    private PostDTO post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PostDTO getPost() {
        return post;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostViewDTO)) {
            return false;
        }

        PostViewDTO postViewDTO = (PostViewDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, postViewDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostViewDTO{" +
            "id=" + getId() +
            ", post=" + getPost() +
            "}";
    }
}
