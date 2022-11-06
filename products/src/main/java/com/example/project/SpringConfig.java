package com.example.project;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.example.project.services.FileStorageProperties;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@EnableConfigurationProperties({ FileStorageProperties.class })
public class SpringConfig {

    // Etags

    /*
     * @Bean public FilterRegistrationBean<ShallowEtagHeaderFilter>
     * shallowEtagHeaderFilter() { FilterRegistrationBean<ShallowEtagHeaderFilter>
     * filterRegistrationBean = new FilterRegistrationBean<>( new
     * ShallowEtagHeaderFilter()); filterRegistrationBean.addUrlPatterns("/foos/*");
     * filterRegistrationBean.setName("etagFilter"); return filterRegistrationBean;
     * }
     */

    // We can also just declare the filter directly
    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }


    // OpenAPI
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(new Info().title("SIDIS Product APP").description("Product API").version("v1.0")
                .termsOfService("TOC"));
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
