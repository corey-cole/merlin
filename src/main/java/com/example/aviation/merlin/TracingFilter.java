package com.example.aviation.merlin;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class TracingFilter implements Filter {
    
    private static final String AMZN_TRACE_ID = "X-Amzn-Trace-Id";

    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String traceId = req.getHeader(AMZN_TRACE_ID);
        final boolean hasTraceId = traceId != null && !traceId.trim().isEmpty();
        if (hasTraceId) {
            MDC.put(AMZN_TRACE_ID, traceId);
        }
        chain.doFilter(request, response);
        if (hasTraceId) {
            MDC.remove(AMZN_TRACE_ID);
        }
    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig filterConfig) {}

}
