package com.cevheri.blog.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.cevheri.blog.domain.Post} entity.
 */
@Schema(description = "Post page information. System users only!")
public class PostDTO implements Serializable {

    private Long id;

    /**
     * Post title information
     */
    @NotNull
    @Size(min = 3, max = 250)
    @Schema(description = "Post title information", required = true)
    private String title;

    /**
     * Post content information
     */

    @Schema(description = "Post content information", required = true)
    @Lob
    private String content;

    /**
     * Paid Membership
     */
    @Schema(description = "Paid Membership")
    private Boolean paidMemberOnly;

    /**
     * Publish third party app. for example Medium.
     */
    @Schema(description = "Publish third party app. for example Medium.")
    private Boolean publishThirdPartyApp;

    private UserDTO user;

    private BlogDTO blog;

    private Set<TagDTO> tags = new HashSet<>();


    private String integrationId;

    private Integer likeCount;
    private Integer viewCount;

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getIntegrationId() {
        return integrationId;
    }

    public void setIntegrationId(String integrationId) {
        this.integrationId = integrationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getPaidMemberOnly() {
        return paidMemberOnly;
    }

    public void setPaidMemberOnly(Boolean paidMemberOnly) {
        this.paidMemberOnly = paidMemberOnly;
    }

    public Boolean getPublishThirdPartyApp() {
        return publishThirdPartyApp;
    }

    public void setPublishThirdPartyApp(Boolean publishThirdPartyApp) {
        this.publishThirdPartyApp = publishThirdPartyApp;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public BlogDTO getBlog() {
        return blog;
    }

    public void setBlog(BlogDTO blog) {
        this.blog = blog;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostDTO)) {
            return false;
        }

        PostDTO postDTO = (PostDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, postDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", paidMemberOnly='" + getPaidMemberOnly() + "'" +
            ", publishThirdPartyApp='" + getPublishThirdPartyApp() + "'" +
            ", integrationId='" + getIntegrationId() + "'" +
            ", user=" + getUser() +
            ", blog=" + getBlog() +
            ", tags=" + getTags() +
            "}";
    }
}
