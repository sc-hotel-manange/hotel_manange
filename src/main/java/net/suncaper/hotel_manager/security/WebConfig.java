package net.suncaper.hotel_manager.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/user/",
//                                     "/user/login",
//                                     "/dist/**",
//                                     "/plugins/**",
//                                     "/bower_components/**",
//                                    "/css/**",
//                                    "/js/**",
//                                    "/img/**",
//                                    "/assets/**",
//                        "/logo/**",
//                        "/sass/**");
    }
}
