package com.cevheri.blog.security;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class UserTokenExpiredException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private final String entityName;

    private final String errorKey;

    public UserTokenExpiredException(String defaultMessage, String entityName, String errorKey) {
        this(URI.create("https://www.cevheri-blog.herokuapp.com/user-token-expired-exception"), defaultMessage, entityName, errorKey);
    }

    public UserTokenExpiredException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(type, defaultMessage, Status.UNAUTHORIZED, null, null, null, getAlertParameters(entityName, errorKey));
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }

    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", "error." + errorKey);
        parameters.put("params", entityName);
        return parameters;
    }
}
