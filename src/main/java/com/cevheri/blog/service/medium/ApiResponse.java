package com.cevheri.blog.service.medium;

import java.io.Serializable;
import java.util.List;

public class ApiResponse<T extends AbstractModel> implements Serializable {
    private String apiUrl;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private List<ApiError> errors;
    public List<ApiError> getErrors() {
        return errors;
    }

    public void setErrors(List<ApiError> errors) {
        this.errors = errors;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiResponse<?> that = (ApiResponse<?>) o;

        if (apiUrl != null ? !apiUrl.equals(that.apiUrl) : that.apiUrl != null) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        return errors != null ? errors.equals(that.errors) : that.errors == null;
    }

    @Override
    public int hashCode() {
        int result = apiUrl != null ? apiUrl.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (errors != null ? errors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
            "apiUrl='" + apiUrl + '\'' +
            ", data=" + data +
            ", errors=" + errors +
            '}';
    }
}
