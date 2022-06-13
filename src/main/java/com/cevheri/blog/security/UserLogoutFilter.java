package com.cevheri.blog.security;

import com.cevheri.blog.domain.User;
import com.cevheri.blog.domain.enumeration.UserLoginStatus;
import com.cevheri.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Configuration
public class UserLogoutFilter extends GenericFilterBean {

    private final Logger log = LoggerFactory.getLogger(UserLogoutFilter.class);
    private final UserService userService;

    public UserLogoutFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        String url = "";
        try {
            url = ((HttpServletRequest) servletRequest).getRequestURL().toString();
        } catch (Exception e) {
            log.error("UserLogoutFilter url convert error.");
        }
        log.trace(url);
        if (!url.contains("authenticate")) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            Optional<User> currentUser = userService.getCurrentUser();
            log.trace("UserLogoutFilter, user: {}", SecurityUtils.getCurrentUserLogin());
            currentUser.ifPresent(t -> {
                if (UserLoginStatus.LOGOUT.equals(t.getLoginStatus())) {
                    log.error("User token expired!. User: {}", t);
                    throw new UserTokenExpiredException("User access token was expired!", "user", "userTokenExpired");
                }
            });
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
