package com.yukiice.filter;

import com.alibaba.fastjson.JSON;
import com.yukiice.common.BaseContext;
import com.yukiice.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否登录的过滤器
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/6 15:09
 */

@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

//   路径匹配符，支持通配符
    public static final AntPathMatcher  PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/upload",
                "/doc.html/**",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"

        };
        boolean check =  checkUrl(urls,requestURI);

//        不需要处理，直接放行
        if (check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

//        判断登录状态
        if (request.getSession().getAttribute("user") != null){
            log.info("用户已经登录了，用户的id为：{}",request.getSession().getAttribute("user"));
            Long empId =(Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(empId); // 存储当前线程的用户id
            filterChain.doFilter(request,response);
            return;
        }else{
            Long id = 9999L;
            BaseContext.setCurrentId(id); // 存储当前线程的用户id
        }
        filterChain.doFilter(request,response);
//        如果为登录则返回未登录结果，通过输出流方式，向客户端页面响应数据
//        response.getWriter().write(JSON.toJSONString(R.error("您未登录")));
//        log.info("拦截到请求：{}",request.getRequestURI());
    }

//    判断是否在安全名单中
    public boolean checkUrl(String urls[],String requestURI){
        for (String url:urls){
            boolean match =  PATH_MATCHER.match(url,requestURI);
            if (match) return true;
        }
        return  false;
    }
}
