package com.cocus.challenge.config.interceptor;

import com.cocus.challenge.exception.NotAcceptableHeaderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (Objects.equals(request.getHeader("Accept"), MediaType.APPLICATION_XML_VALUE)) {
            throw new NotAcceptableHeaderException();
        }
        UUID uuid = UUID.randomUUID();
        request.setAttribute("start", System.currentTimeMillis());
        request.setAttribute("request-id", uuid);
        log.info("{} - calling {}", uuid, request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {
        log.info("{} - response in {}ms",
                request.getAttribute("request-id"),
                System.currentTimeMillis() - (long) request.getAttribute("start")
        );
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception exception) {
        log.info("{} - completed in {}ms",
                request.getAttribute("request-id"),
                System.currentTimeMillis() - (long) request.getAttribute("start"));
    }
}
