package com.shoes.scarecrow.web.filter;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Create with IntelliJ IDEA
 * Create by zz
 * Date 18-5-1
 * Time 上午11:43
 */
public class LoginFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String ctxPath = request.getContextPath();
        String request_uri = request.getRequestURI();
        String uri = request_uri.substring(ctxPath.length());
        request.setCharacterEncoding("utf-8");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String loginUserName = (String)session.getAttribute("userName");
        if (uri.length()<=1||uri.contains("css")||uri.contains("js")||uri.contains("image")||uri.contains(".jpg")||uri.contains(".png")||uri.contains("index")||uri.contains("register")) {
            chain.doFilter(request, response);
        }else{
            if (loginUserName != null && !loginUserName.trim().equals("")) {
                chain.doFilter(request, response);
            } else {
                response.sendRedirect(ctxPath);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
