package br.com.fusion.banck.shared.config;

import br.com.fusion.banck.application.service.FusionTimeInterception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebTime implements WebMvcConfigurer {

    @Autowired
    private FusionTimeInterception fusionTimeInterception;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fusionTimeInterception).addPathPatterns("/**");
    }
}