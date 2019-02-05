package com.angusyg.winecellar.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Global core configuration.
 *
 * @since 0.0.1
 */
@Configuration
public class CoreConfiguration {
  /**
   * Creates an instance of {@link LocalValidatorFactoryBean} in
   * order to be able to autowire a validator.
   *
   * @return an instance of {@link LocalValidatorFactoryBean}
   */
  @Bean
  public LocalValidatorFactoryBean localValidatorFactoryBean() {
    return new LocalValidatorFactoryBean();
  }
}
