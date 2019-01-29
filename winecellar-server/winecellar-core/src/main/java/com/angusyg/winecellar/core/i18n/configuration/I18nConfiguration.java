package com.angusyg.winecellar.core.i18n.configuration;

import com.angusyg.winecellar.core.i18n.resource.ClasspathReloadableResourceBundleMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

@Configuration
public class I18nConfiguration {
  @Bean
  public MessageSource getMessageSource() {
    return new ClasspathReloadableResourceBundleMessageSource();
  }

  @Bean
  public MessageSourceAccessor getMessageSourceAccessor() {
    return new MessageSourceAccessor(getMessageSource());
  }
}
