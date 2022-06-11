package com.cevheri.blog.service.dto;

import com.cevheri.blog.domain.enumeration.ThirdPartyAppName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.cevheri.blog.domain.ThirdPartyApp} entity.
 */
@Schema(description = "Third party integration configuration. BaseURL, accessKey etc.")
public class ThirdPartyAppDTO implements Serializable {

    private Long id;

    /**
     * App Name : MEDIUM etc.
     */
    @Schema(description = "App Name : MEDIUM etc.")
    private ThirdPartyAppName name;

    /**
     * app base url : https:
     */
    @Size(max = 1000)
    @Schema(description = "app base url : https:")
    private String baseUrl;

    /**
     * app access key :2418f8eabee116684110f01ec98631f35f7528a88c33312e9a339fb1e9de46ec9
     */
    @Size(max = 1000)
    @Schema(description = "app access key :2418f8eabee116684110f01ec98631f35f7528a88c33312e9a339fb1e9de46ec9")
    private String accessKey;

    /**
     * app access key :2418f8eabee116684110f01ec98631f35f7528a88c33312e9a339fb1e9de46ec9
     */
    @Size(max = 1000)
    @Schema(description = "app access key :2418f8eabee116684110f01ec98631f35f7528a88c33312e9a339fb1e9de46ec9")
    private String authorId;

    /**
     * create post api endpoint - POST /users/{{authorId}}/posts
     */
    @Size(max = 1000)
    @Schema(description = "create post api endpoint - POST /users/{{authorId}}/posts")
    private String creatingPostApi;

    /**
     * Listing the user’s publications api endpoint - GET  /users/{{userId}}/publications
     */
    @Size(max = 1000)
    @Schema(description = "Listing the user’s publications api endpoint - GET  /users/{{userId}}/publications")
    private String readPostApi;

    /**
     * Integration active or passive information
     */
    @Schema(description = "Integration active or passive information")
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ThirdPartyAppName getName() {
        return name;
    }

    public void setName(ThirdPartyAppName name) {
        this.name = name;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getCreatingPostApi() {
        return creatingPostApi;
    }

    public void setCreatingPostApi(String creatingPostApi) {
        this.creatingPostApi = creatingPostApi;
    }

    public String getReadPostApi() {
        return readPostApi;
    }

    public void setReadPostApi(String readPostApi) {
        this.readPostApi = readPostApi;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThirdPartyAppDTO)) {
            return false;
        }

        ThirdPartyAppDTO thirdPartyAppDTO = (ThirdPartyAppDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, thirdPartyAppDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThirdPartyAppDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", baseUrl='" + getBaseUrl() + "'" +
            ", accessKey='" + getAccessKey() + "'" +
            ", authorId='" + getAuthorId() + "'" +
            ", creatingPostApi='" + getCreatingPostApi() + "'" +
            ", readPostApi='" + getReadPostApi() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
