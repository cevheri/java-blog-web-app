package com.cevheri.blog.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.cevheri.blog.domain.Blog} entity.
 */
@Schema(description = "Blog page for users post. System users only!")
public class BlogDTO implements Serializable {

    private Long id;

    /**
     * Blog name information
     */
    @NotNull
    @Size(min = 3, max = 250)
    @Schema(description = "Blog name information", required = true)
    private String name;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogDTO)) {
            return false;
        }

        BlogDTO blogDTO = (BlogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, blogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlogDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
