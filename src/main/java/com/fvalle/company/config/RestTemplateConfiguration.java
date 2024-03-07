package com.fvalle.company.config;

import com.exisoft.javatemplate.config.RestTemplateConfig;
import com.exisoft.javatemplate.interceptor.LoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public LoggingInterceptor loggingInterceptor(){
        return new LoggingInterceptor();
    }

    @Bean
    public RestTemplateConfig restTemplateConfig(){
        return new RestTemplateConfig();
    }

    /*@Bean
    public RestTemplate restJavaTemplate(){

        return new RestTemplateBuilder()
                .additionalInterceptors(loggingInterceptor())
                .build();

        final RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new
                SimpleClientHttpRequestFactory()));
        final List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LoggingInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;

    }*/





}
