package gprs.com.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private UserInterceptor userInterceptor;
    @Autowired
    private CrossDomainInterceptor crossDomainInterceptor;
    @Autowired
    private AdminInterceptor adminInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(crossDomainInterceptor).addPathPatterns("/**");
        registry.addInterceptor(userInterceptor).addPathPatterns("/front/**");
        registry.addInterceptor(adminInterceptor).addPathPatterns("/back/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
    
    
    
    
}
