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

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * App Model configuration
 * <ul>
 *   <li>Creates a {@link ModelMapper} entity to DTO mapper</li>
 *   <li>Sets default {@link org.springframework.data.domain.Pageable} values</li>
 *   <li>Adds argument resolver for {@link com.angusyg.winecellar.core.model.web.arguments.Limiteable} argument</li>
 * </ul>
 * @since 0.0.1
 */
@Configuration
@EnableSpringDataWebSupport // Adds Pageable and Sort argument resolvers
public class ModelConfiguration {
  /**
   * Creates an instance of {@link ModelMapper} to map entity to DTO
   *
   * @return a {@link ModelMapper} instance
   */
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  /**
   * Creates a {@link PageableHandlerMethodArgumentResolverCustomizer} to set default {@link org.springframework.data.domain.Pageable} argument values
   *
   * @param pagesize default page size for page request
   * @return a {@link PageableHandlerMethodArgumentResolverCustomizer}
   */
  @Bean
  public PageableHandlerMethodArgumentResolverCustomizer customPageableHandlerMethodArgumentResolver(@Value("${app.data.default.pagesize}") int pagesize) {
    return p -> p.setFallbackPageable(PageRequest.of(0, pagesize));
  }

  /**
   * Creates a {@link WebMvcConfigurer} to add {@link com.angusyg.winecellar.core.model.web.arguments.Limiteable} argument resolver
   *
   * @return a {@link WebMvcConfigurer} with {@link com.angusyg.winecellar.core.model.web.arguments.Limiteable} argument resolver
   */
  @Bean
  public WebMvcConfigurer modelWebMvcConfigurer() {
    return new WebMvcConfigurer() {
      // Default limit size for Limiteable argument
      @Value("${app.data.default.limit.size}")
      private int maxLimit;

      /**
       * Adds an argument resolver for {@link com.angusyg.winecellar.core.model.web.arguments.Limiteable} argument
       *
       * @param argumentResolvers list of predefined argument resolver
       */
      @Override
      public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LimitHandlerMethodArgumentResolver(maxLimit));
      }
    };
  }
}
