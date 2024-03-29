package com.cevheri.blog.service.medium;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PostResponse implements Serializable {

    @JsonIgnore
    private String apiUrl = "/users/{{authorId}}/posts";

    private PostData data = new PostData();
    private List<ApiError> errors = new ArrayList<>();

    public List<ApiError> getErrors() {
        return errors;
    }

    public void setErrors(List<ApiError> errors) {
        this.errors = errors;
    }

    public PostData getData() {
        return data;
    }

    public void setData(PostData data) {
        this.data = data;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public String toString() {
        return "PostResponse{" +
            "apiUrl='" + apiUrl + '\'' +
            ", data=" + data +
            ", errors=" + errors +
            '}';
    }

    /**
     * Creates a post on the authenticated user’s profile. <br/>
     * POST https://api.medium.com/v1/users/{{authorId}}/posts
     *
     * @author cevher
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class PostData implements Serializable {
        /**
         * A unique identifier for the post.
         */
        private String id;
        /**
         * The userId of the post’s author
         */
        private String authorId;
        /**
         * The title of the post. <br/>
         * Note that this title is used for SEO and when rendering the post as a listing,
         * but will not appear in the actual post—for that,
         * the title must be specified in the content field as well.<br/>
         * Titles longer than 100 characters will be ignored. <br/>
         * In that case, a title will be synthesized from
         * the first content in the post when it is published.
         */
        private String title;
        /**
         * The format of the "content" field. <br/>
         * There are two valid values, "html", and "markdown"
         */
        private String contentFormat;
        /**
         * The body of the post,
         * in a valid, semantic, HTML fragment, or Markdown.
         * Further markups may be supported in the future.
         * For a full list of accepted HTML tags, see here.
         * If you want your title to appear on the post page,
         * you must also include it as part of the post content.
         */
        private String content;
        /**
         * Tags to classify the post. <br/>
         * Only the first three will be used. <br/>
         * Tags longer than 25 characters will be ignored. <br/>
         */
        private List<String> tags = new ArrayList<>();
        private String url;
        /**
         * The original home of this content,
         * if it was originally published elsewhere.
         */
        private String canonicalUrl;
        /**
         * The status of the post.<br/>
         * Valid values are “public”, “draft”, or “unlisted”.<br/>
         * The default is “public”.
         */
        private String publishStatus;
        /**
         * The post’s published date.
         * If created as a draft, this field will not be present.
         */
        private String publishedAt;
        /**
         * The license of the post. <br/>
         * Valid values are “all-rights-reserved”, “cc-40-by”, “cc-40-by-sa”, “cc-40-by-nd”, “cc-40-by-nc”, “cc-40-by-nc-nd”, “cc-40-by-nc-sa”, “cc-40-zero”, “public-domain”. <br/>
         * The default is “all-rights-reserved”.<br/>
         */
        private String license;
        /**
         * The URL to the license of the post.
         */
        private String licenseUrl;
        /**
         * Whether to notifyFollowers that the user has published.
         */
        private Boolean notifyFollowers;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuthorId() {
            return authorId;
        }

        public void setAuthorId(String authorId) {
            this.authorId = authorId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContentFormat() {
            return contentFormat;
        }

        public void setContentFormat(String contentFormat) {
            this.contentFormat = contentFormat;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public String getCanonicalUrl() {
            return canonicalUrl;
        }

        public void setCanonicalUrl(String canonicalUrl) {
            this.canonicalUrl = canonicalUrl;
        }

        public String getPublishStatus() {
            return publishStatus;
        }

        public void setPublishStatus(String publishStatus) {
            this.publishStatus = publishStatus;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLicenseUrl() {
            return licenseUrl;
        }

        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }

        public Boolean getNotifyFollowers() {
            return notifyFollowers;
        }

        public void setNotifyFollowers(Boolean notifyFollowers) {
            this.notifyFollowers = notifyFollowers;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "PostModel{" +
                "id='" + id + '\'' +
                ", authorId='" + authorId + '\'' +
                ", title='" + title + '\'' +
                ", contentFormat='" + contentFormat + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                ", canonicalUrl='" + canonicalUrl + '\'' +
                ", publishStatus='" + publishStatus + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", license='" + license + '\'' +
                ", licenseUrl='" + licenseUrl + '\'' +
                ", notifyFollowers=" + notifyFollowers +
                '}';
        }
    }
}
