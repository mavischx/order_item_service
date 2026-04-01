package ie.order_service.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class EtagConfig {

//    @Bean
//    public FilterRegistrationBean<ShallowEtagHeaderFilter> etagFilter() {
//        FilterRegistrationBean<ShallowEtagHeaderFilter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(new ShallowEtagHeaderFilter());
//        registration.addUrlPatterns("/*");
//        return registration;
//    }
	@Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}