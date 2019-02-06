package com.angusyg.winecellar.core.i18n.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Properties;

/**
 * Class to load multiple properties files from the classpath.
 * It looks in all classpath of application (current, jar dependencies ...)
 *
 * @since 0.0.1
 */
@Slf4j
public class ClasspathReloadableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource implements InitializingBean {
  // Properties files suffix
  private static final String PROPERTIES_SUFFIX = ".properties";

  // Resource loader to look in all classpath
  private final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

  // Default charset of properties files
  private final Charset encoding = Charset.forName("UTF-8");

  @Autowired
  private Environment environment;

  /**
   * Returns the resource bundle corresponding to the given locale.
   *
   * @param locale locale of resource to load
   * @return properties values of given locale
   */
  public Properties getResourceBundle(Locale locale) {
    clearCacheIncludingAncestors();
    return getMergedProperties(locale).getProperties();
  }

  /**
   * Sets default values of configuration
   */
  @Override
  public void afterPropertiesSet() {
    setBasename("classpath*:/" + environment.getProperty("spring.messages.basename", "messages"));
    setDefaultEncoding(environment.getProperty("spring.messages.encoding", encoding.name()));
    setCacheSeconds(environment.getProperty("spring.messages.cache-seconds", int.class, -1));
    setFallbackToSystemLocale(environment.getProperty("spring.messages.fallback-to-system-locale",
        boolean.class, true));
  }

  /**
   * Refreshes the PropertiesHolder for the given bundle filename.
   *
   * @param filename   the bundle filename (basename + Locale)
   * @param propHolder the current PropertiesHolder for the bundle
   * @return the refreshed {@link PropertiesHolder}
   */
  @Override
  protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {
    final Properties properties = new Properties();
    long lastModified = -1;
    try {
      for (Resource resource : resolver.getResources(filename + PROPERTIES_SUFFIX)) {
        final PropertiesHolder holder = super.refreshProperties(cleanPath(resource), propHolder);
        properties.putAll(holder.getProperties());
        if (lastModified < resource.lastModified()) {
          lastModified = resource.lastModified();
        }
      }
    } catch (IOException ex) {
      // Error during refresh
      log.warn("Error during properties refresh: {}", ex.getMessage(), ex.getMessage());
    }
    return new PropertiesHolder(properties, lastModified);
  }

  /**
   * Cleans resource path removing suffix.
   *
   * @param resource resource to clean
   * @return cleaned resource path
   * @throws IOException if the resource cannot be resolved as URI.
   */
  private String cleanPath(Resource resource) throws IOException {
    return resource.getURI()
        .toString()
        .replace(PROPERTIES_SUFFIX, "");
  }
}
