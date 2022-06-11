package com.cevheri.blog.domain;

import com.cevheri.blog.domain.enumeration.ExitCodeType;
import com.cevheri.blog.domain.enumeration.ThirdPartyAppName;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A IntegrationLog.
 */
@Entity
@Table(name = "integration_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IntegrationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "integration_name")
    private ThirdPartyAppName integrationName;

    @Size(max = 1000)
    @Column(name = "api_url", length = 1000)
    private String apiUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "exit_code")
    private ExitCodeType exitCode;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "request_data")
    private String requestData;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "response_data")
    private String responseData;

    @Size(max = 1000)
    @Column(name = "error_code", length = 1000)
    private String errorCode;

    @Size(max = 4000)
    @Column(name = "error_message", length = 4000)
    private String errorMessage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IntegrationLog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public IntegrationLog createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public IntegrationLog createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public ThirdPartyAppName getIntegrationName() {
        return this.integrationName;
    }

    public IntegrationLog integrationName(ThirdPartyAppName integrationName) {
        this.setIntegrationName(integrationName);
        return this;
    }

    public void setIntegrationName(ThirdPartyAppName integrationName) {
        this.integrationName = integrationName;
    }

    public String getApiUrl() {
        return this.apiUrl;
    }

    public IntegrationLog apiUrl(String apiUrl) {
        this.setApiUrl(apiUrl);
        return this;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public ExitCodeType getExitCode() {
        return this.exitCode;
    }

    public IntegrationLog exitCode(ExitCodeType exitCode) {
        this.setExitCode(exitCode);
        return this;
    }

    public void setExitCode(ExitCodeType exitCode) {
        this.exitCode = exitCode;
    }

    public String getRequestData() {
        return this.requestData;
    }

    public IntegrationLog requestData(String requestData) {
        this.setRequestData(requestData);
        return this;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponseData() {
        return this.responseData;
    }

    public IntegrationLog responseData(String responseData) {
        this.setResponseData(responseData);
        return this;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public IntegrationLog errorCode(String errorCode) {
        this.setErrorCode(errorCode);
        return this;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public IntegrationLog errorMessage(String errorMessage) {
        this.setErrorMessage(errorMessage);
        return this;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntegrationLog)) {
            return false;
        }
        return id != null && id.equals(((IntegrationLog) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntegrationLog{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", integrationName='" + getIntegrationName() + "'" +
            ", apiUrl='" + getApiUrl() + "'" +
            ", exitCode='" + getExitCode() + "'" +
            ", requestData='" + getRequestData() + "'" +
            ", responseData='" + getResponseData() + "'" +
            ", errorCode='" + getErrorCode() + "'" +
            ", errorMessage='" + getErrorMessage() + "'" +
            "}";
    }
}
