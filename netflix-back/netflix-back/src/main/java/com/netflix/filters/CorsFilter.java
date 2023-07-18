package com.netflix.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(asyncSupported = true, urlPatterns = {"/*"})
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Configura os filtros de requisições de diferentes origens
        // http://localhost:3000 é o frontend por onde eu mando as requisições
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        // Eu só utilizei GET, POST e OPTIONS mas deixei todas as opções permitidas
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
        // Os headers que eu permiti
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Session-Id");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "180");

        // Verifica se a requisição é do tipo OPTIONS e retorna OK caso seja
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            // Continua os filtros se a requisição não for OPTIONS
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
