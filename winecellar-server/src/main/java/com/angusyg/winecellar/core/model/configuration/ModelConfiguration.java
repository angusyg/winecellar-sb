package com.angusyg.winecellar.core.model.configuration;

import com.angusyg.winecellar.core.model.web.handler.LimitHandlerMethodArgumentResolver;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableSpringDataWebSupport
public class ModelConfiguration {
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public PageableHandlerMethodArgumentResolverCustomizer custom(@Value("${app.data.default.pagesize}") int pagesize) {
    return p -> p.setFallbackPageable(PageRequest.of(0, pagesize));
  }

  @Bean
  public WebMvcConfigurer modelWebMvcConfigurer() {
    return new WebMvcConfigurer() {
      @Value("${app.data.default.limit}")
      private int maxLimit;

      @Override
      public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LimitHandlerMethodArgumentResolver(maxLimit));
      }
    };
  }
}
