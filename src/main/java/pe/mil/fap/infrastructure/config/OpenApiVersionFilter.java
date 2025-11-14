package pe.mil.fap.infrastructure.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

/**
 * Filter that rewrites the OpenAPI version in the /v3/api-docs response
 * from 3.1.0 to 3.0.0 to keep compatibility with Swagger UI instances
 * that only support OpenAPI 3.0.x.
 */
@Component
public class OpenApiVersionFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !"/v3/api-docs".equals(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingResponseWrapper wrapped = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(request, wrapped);

        byte[] content = wrapped.getContentAsByteArray();
        if (content.length > 0) {
            String charset = wrapped.getCharacterEncoding() != null ? wrapped.getCharacterEncoding() : "UTF-8";
            String body = new String(content, charset);
            // Replace OpenAPI 3.1.0 with 3.0.0 for compatibility
            String modified = body.replace("\"openapi\":\"3.1.0\"", "\"openapi\":\"3.0.0\"");

            byte[] newBytes = modified.getBytes(charset);
            // reset the response and write modified content
            response.resetBuffer();
            response.setContentLength(newBytes.length);
            response.getOutputStream().write(newBytes);
        }

        wrapped.copyBodyToResponse();
    }
}
