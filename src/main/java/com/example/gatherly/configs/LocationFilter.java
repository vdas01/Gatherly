package com.example.gatherly.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class LocationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String location = request.getHeader("X-LOCATION-ID");

        if (location == null || location.isBlank()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(),
                    "X-LOCATION-ID header is required");
            return;
        }

        try {
            TenantContext.setLocation(location);
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

}
