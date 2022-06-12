package com.cevheri.blog.service.medium;

import java.io.Serializable;

public class ApiError implements Serializable {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiError apiError = (ApiError) o;

        if (code != null ? !code.equals(apiError.code) : apiError.code != null) return false;
        return message != null ? message.equals(apiError.message) : apiError.message == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApiError{" +
            "code='" + code + '\'' +
            ", message='" + message + '\'' +
            '}';
    }
}
