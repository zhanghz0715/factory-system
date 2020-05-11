/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-07-16 11:38:41
 * @LastEditTime: 2019-11-07 09:24:12
 * @LastEditors: Please set LastEditors
 */
package com.factory.boot.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.factory.boot.model.User;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringMVCFilter implements Filter {

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        SpringMVCUtil.setRequestAndResponse(req, res);
        String path = SpringMVCUtil.getRequest().getServletPath();
        HttpSession session = SpringMVCUtil.getRequest().getSession();

        req.setAttribute("path", req.getContextPath());
        req.setAttribute("basePath",
                req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath());
        req.setAttribute("basePath_no_port", req.getScheme() + "://" + req.getServerName() + req.getContextPath());
        req.setAttribute("domainName",
                req.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());

        // 如果是静态资源文件放行
        if (path.contains("front") || path.contains("static") || path.contains("swagger")||path.contains("/img/")||path.contains("/qrCode/")
                || path.contains("api-docs")) {
            chain.doFilter(request, response);
            return;
        }

        // 获取公共链接-登陆前
        List<String> urlList = ResourcesUtil.getkeyList();
        // 检验URL是否在公共链接中
        for (String url : urlList) {
            if (path.contains(url)) {
                // 放行
                chain.doFilter(request, response);
                return;
            }
        }

        if (session != null) {
            User user = (User) session.getAttribute("login_user");
            if (user == null) {
                request.getRequestDispatcher("/login").forward(req, res);
                return;
            } else if ("/".equals(path)) {
                request.getRequestDispatcher("/index").forward(req, res);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

}
