package com.sr.config;

import com.sr.aop.AuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author cyh
 * @Date 2021/11/5 15:09
 */
@Configuration
public class FilterConfig {
    @Value("${spring.profiles.active}")
    private String env;

    @Bean //将方法中返回的对象注入到IOC容器中
    public FilterRegistrationBean filterRegister(){
        FilterRegistrationBean reFilter = new FilterRegistrationBean();
        reFilter.setFilter(new AuthFilter()); //创建并注册TestFilter
        reFilter.addUrlPatterns("/*"); //拦截的路径（对所有请求拦截）
        reFilter.setName("AuthFilter"); //拦截器的名称
        reFilter.setOrder(1); //拦截器的执行顺序。数字越小越先执行
        reFilter.addInitParameter("env", env);
        return  reFilter;
    }
}
