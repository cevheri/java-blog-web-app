package com.cevheri.blog.service.dto;

import com.cevheri.blog.domain.enumeration.ExitCodeType;
import com.cevheri.blog.domain.enumeration.ThirdPartyAppName;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.cevheri.blog.domain.IntegrationLog} entity.
 */
public class IntegrationLogDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String createdBy;

    private Instant createdDate;

    private ThirdPartyAppName integrationName;

    @Size(max = 1000)
    private String apiUrl;

    private ExitCodeType exitCode;

    @Lob
    private String requestData;

    @Lob
    private String responseData;

    @Size(max = 1000)
    private String errorCode;

    @Size(max = 4000)
    private String errorMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public ThirdPartyAppName getIntegrationName() {
        return integrationName;
    }

    public void setIntegrationName(ThirdPartyAppName integrationName) {
        this.integrationName = integrationName;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public ExitCodeType getExitCode() {
        return exitCode;
    }

    public void setExitCode(ExitCodeType exitCode) {
        this.exitCode = exitCode;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntegrationLogDTO)) {
            return false;
        }

        IntegrationLogDTO integrationLogDTO = (IntegrationLogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, integrationLogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntegrationLogDTO{" +
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
