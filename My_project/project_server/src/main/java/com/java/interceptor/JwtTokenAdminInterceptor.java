package com.java.interceptor;

import com.java.constant.JwtClaimsConstant;
import com.java.context.BaseContext;
import com.java.properties.JwtProperties;
import com.java.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt令牌管理拦截器
 *
 * @author mz
 * @date 2023/08/31
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        try{
            log.info("jwt检验:{}",token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(),token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
            log.info("当前员工的id:{}",empId);
            BaseContext.setCurrentId(empId);
            //3、通过，放行
            return true;
        } catch (Exception ex){
            //4、不通过，响应401状态码
            response.setStatus(401);
            return false;
        }
    }

}
