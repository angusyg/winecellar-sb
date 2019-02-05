package com.angusyg.winecellar.core.i18n.configuration;

import com.angusyg.winecellar.core.i18n.resource.ClasspathReloadableResourceBundleMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

/**
 * Internationalization configuration.
 *
 * @since 0.0.1
 */
@Configuration
public class I18nConfiguration {
  /**
   * Creates an instance of {@link MessageSource}
   * It loads properties files containing messages and
   * gives access to their values.
   *
   * @return an instance of {@link MessageSource}
   */
  @Bean
  public MessageSource messageSource() {
    return new ClasspathReloadableResourceBundleMessageSource();
  }

  /**
   * Creates an instance of {@link MessageSourceAccessor}.
   * Helper to easy access to {@link MessageSource}.
   *
   * @return an instance of {@link MessageSourceAccessor}
   */
  @Bean
  public MessageSourceAccessor messageSourceAccessor() {
    return new MessageSourceAccessor(messageSource());
  }
}
