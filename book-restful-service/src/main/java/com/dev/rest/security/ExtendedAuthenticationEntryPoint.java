package com.dev.rest.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.util.Assert;

public class ExtendedAuthenticationEntryPoint extends BasicAuthenticationEntryPoint implements AccessDeniedHandler, InitializingBean {
    private String realmName;
    private static Logger logger = LoggerFactory.getLogger(ExtendedAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof UsernameNotFoundException) {
            internalCommence(response, HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage(), true);
        } else if (authException instanceof BadCredentialsException) {
            internalCommence(response, HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage(), true);
        } else {
        		internalCommence(response, HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage(), true);
        }
    }

    private void internalCommence(HttpServletResponse response, int errorCode, String message, boolean authHeader) throws IOException {
        logger.debug("Spring security internalCommence : {}, {}, {}, {}", response, errorCode, message, authHeader);
        if (authHeader) {
            response.addHeader("WWW-Authenticate", "Bearer realm=\"" + realmName + "\"");
        }
        response.addHeader("X-Status-Message", URLEncoder.encode(message, "UTF-8").replace("+", "%20"));
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setCharacterEncoding(Charset.defaultCharset().toString());
        response.setStatus(errorCode);
        response.getWriter().println(message);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(realmName, "realmName must be specified");
    }

    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        internalCommence(response, HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage(), false);
    }

}
