package com.cevheri.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * Post page information. System users only!
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Post extends AbstractAuditingEntity
    implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * Post title information
     */
    @NotNull
    @Size(min = 3, max = 250)
    @Column(name = "title", length = 250, nullable = false)
    private String title;

    /**
     * Post content information
     */
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content", nullable = false)
    private String content;

    /**
     * Paid Membership
     */
    @Column(name = "paid_member_only")
    private Boolean paidMemberOnly;

    /**
     * Publish third party app. for example Medium.
     */
    @Column(name = "publish_third_party_app")
    private Boolean publishThirdPartyApp;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = {"user"}, allowSetters = true)
    private Blog blog;

    @ManyToMany
    @JoinTable(name = "rel_post__tag", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = {"posts"}, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Post id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Post title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public Post content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getPaidMemberOnly() {
        return this.paidMemberOnly;
    }

    public Post paidMemberOnly(Boolean paidMemberOnly) {
        this.setPaidMemberOnly(paidMemberOnly);
        return this;
    }

    public void setPaidMemberOnly(Boolean paidMemberOnly) {
        this.paidMemberOnly = paidMemberOnly;
    }

    public Boolean getPublishThirdPartyApp() {
        return this.publishThirdPartyApp;
    }

    public Post publishThirdPartyApp(Boolean publishThirdPartyApp) {
        this.setPublishThirdPartyApp(publishThirdPartyApp);
        return this;
    }

    public void setPublishThirdPartyApp(Boolean publishThirdPartyApp) {
        this.publishThirdPartyApp = publishThirdPartyApp;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post user(User user) {
        this.setUser(user);
        return this;
    }

    public Blog getBlog() {
        return this.blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Post blog(Blog blog) {
        this.setBlog(blog);
        return this;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Post tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public Post addTag(Tag tag) {
        this.tags.add(tag);
        tag.getPosts().add(this);
        return this;
    }

    public Post removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getPosts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", paidMemberOnly='" + getPaidMemberOnly() + "'" +
            ", publishThirdPartyApp='" + getPublishThirdPartyApp() + "'" +
            "}";
    }
}
