package com.cocus.challenge.config.interceptor;

import com.cocus.challenge.exception.NotAcceptableHeaderException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (Objects.equals(request.getHeader("Accept"), MediaType.APPLICATION_XML_VALUE)) {
            throw new NotAcceptableHeaderException();
        }
        return true;
    }
}
