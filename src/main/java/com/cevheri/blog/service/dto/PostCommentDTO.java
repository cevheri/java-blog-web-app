package com.cevheri.blog.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.cevheri.blog.domain.PostComment} entity.
 */
@Schema(description = "System User Post Comments")
public class PostCommentDTO implements Serializable {

    private Long id;

    /**
     * Comment Text information
     */
    @NotNull
    @Size(min = 2, max = 4000)
    @Schema(description = "Comment Text information", required = true)
    private String commentText;

    private PostDTO post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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
        if (!(o instanceof PostCommentDTO)) {
            return false;
        }

        PostCommentDTO postCommentDTO = (PostCommentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, postCommentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostCommentDTO{" +
            "id=" + getId() +
            ", commentText='" + getCommentText() + "'" +
            ", post=" + getPost() +
            "}";
    }
}
