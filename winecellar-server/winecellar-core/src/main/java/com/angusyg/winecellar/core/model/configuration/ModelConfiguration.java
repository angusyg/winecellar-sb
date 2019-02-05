package com.angusyg.winecellar.core.model.configuration;

import com.angusyg.winecellar.core.model.web.arguments.Limiteable;
import com.angusyg.winecellar.core.model.web.handler.LimitHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Model configuration
 * <ul>
 * <li>Sets default {@link org.springframework.data.domain.Pageable} values</li>
 * <li>Adds argument resolver for {@link Limiteable} argument</li>
 * </ul>
 *
 * @since 0.0.1
 */
@Configuration
@EnableSpringDataWebSupport // Adds Pageable and Sort argument resolvers
public class ModelConfiguration {
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
   * Creates a {@link WebMvcConfigurer} to add {@link Limiteable} argument resolver
   *
   * @return a {@link WebMvcConfigurer} with {@link Limiteable} argument resolver
   */
  @Bean
  public WebMvcConfigurer webMvcConfigurer() {
    return new WebMvcConfigurer() {
      // Default limit size for Limiteable argument
      @Value("${app.data.default.limit.size}")
      private int maxLimit;

      /**
       * Adds an argument resolver for {@link Limiteable} argument
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
