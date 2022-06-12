package com.cevheri.blog.domain;

import com.cevheri.blog.domain.enumeration.ThirdPartyAppName;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Third party integration configuration. BaseURL, accessKey etc.
 */
@Entity
@Table(name = "third_party_app")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ThirdPartyApp extends AbstractAuditingEntity
    implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * App Name : MEDIUM etc.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private ThirdPartyAppName name;

    /**
     * app base url : https:
     */
    @Size(max = 1000)
    @Column(name = "base_url", length = 1000)
    private String baseUrl;

    /**
     * app access key :2418f8eabee116684110f01ec98631f35f7528a88c33312e9a339fb1e9de46ec9
     */
    @Size(max = 1000)
    @Column(name = "access_key", length = 1000)
    private String accessKey;

    /**
     * app access key :2418f8eabee116684110f01ec98631f35f7528a88c33312e9a339fb1e9de46ec9
     */
    @Size(max = 1000)
    @Column(name = "author_id", length = 1000)
    private String authorId;

    /**
     * create post api endpoint - POST /users/{{authorId}}/posts
     */
    @Size(max = 1000)
    @Column(name = "creating_post_api", length = 1000)
    private String creatingPostApi;

    /**
     * Listing the user’s publications api endpoint - GET  /users/{{userId}}/publications
     */
    @Size(max = 1000)
    @Column(name = "read_post_api", length = 1000)
    private String readPostApi;

    /**
     * Integration active or passive information
     */
    @Column(name = "active")
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ThirdPartyApp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ThirdPartyAppName getName() {
        return this.name;
    }

    public ThirdPartyApp name(ThirdPartyAppName name) {
        this.setName(name);
        return this;
    }

    public void setName(ThirdPartyAppName name) {
        this.name = name;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public ThirdPartyApp baseUrl(String baseUrl) {
        this.setBaseUrl(baseUrl);
        return this;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAccessKey() {
        return this.accessKey;
    }

    public ThirdPartyApp accessKey(String accessKey) {
        this.setAccessKey(accessKey);
        return this;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAuthorId() {
        return this.authorId;
    }

    public ThirdPartyApp authorId(String authorId) {
        this.setAuthorId(authorId);
        return this;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getCreatingPostApi() {
        return this.creatingPostApi;
    }

    public ThirdPartyApp creatingPostApi(String creatingPostApi) {
        this.setCreatingPostApi(creatingPostApi);
        return this;
    }

    public void setCreatingPostApi(String creatingPostApi) {
        this.creatingPostApi = creatingPostApi;
    }

    public String getReadPostApi() {
        return this.readPostApi;
    }

    public ThirdPartyApp readPostApi(String readPostApi) {
        this.setReadPostApi(readPostApi);
        return this;
    }

    public void setReadPostApi(String readPostApi) {
        this.readPostApi = readPostApi;
    }

    public Boolean getActive() {
        return this.active;
    }

    public ThirdPartyApp active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThirdPartyApp)) {
            return false;
        }
        return id != null && id.equals(((ThirdPartyApp) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThirdPartyApp{" +
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
