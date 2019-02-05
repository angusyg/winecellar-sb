package com.angusyg.winecellar.core.model.web.handler;

import com.angusyg.winecellar.core.model.web.arguments.Limiteable;
import lombok.Setter;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Extracts limit information from web requests and thus allows injecting
 * {@link Limiteable} instances into controller methods.
 *
 * @since 0.0.1
 */
public class LimitHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
  // Default parameter name for limit
  private static final String DEFAULT_LIMIT_PARAMETER = "limit";

  // Default limit value
  private static final int DEFAULT_LIMIT_VALUE = 1000;

  // Maximal value for limit
  private int maxLimit;

  // Sets default parameter name
  @Setter
  private String limitParameterName = DEFAULT_LIMIT_PARAMETER;

  /**
   * Creates an instance of {@link LimitHandlerMethodArgumentResolver} with
   * a specified maximal value or default value for limit
   *
   * @param maxLimit value of maximal limit
   */
  public LimitHandlerMethodArgumentResolver(int maxLimit) {
    this.maxLimit = maxLimit > 0 ? maxLimit : DEFAULT_LIMIT_VALUE;
  }

  /**
   * Whether the given {@linkplain MethodParameter method parameter} is
   * supported by this resolver
   *
   * @param methodParameter the method parameter to check
   * @return {@code true} if this resolver supports the supplied parameter;
   * {@code false} otherwise
   */
  @Override
  public boolean supportsParameter(MethodParameter methodParameter) {
    return methodParameter.getParameterType().equals(Limiteable.class);
  }

  /**
   * Resolves a {@link Limiteable} method parameter into an argument value from a given request.
   * @param methodParameter the method parameter to resolve
   * @param mavContainer the ModelAndViewContainer for the current request
   * @param webRequest the current request
   * @param binderFactory a factory for creating {@link WebDataBinder} instances
   * @return the resolved {@link Limiteable} argument value, or {@code null} if not resolvable
   * @throws Exception in case of errors with the preparation of argument values
   */
  @Override
  public Limiteable resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
    // Gets parameter value from current request
    String limitString = webRequest.getParameter(limitParameterName);

    // Checks if parameter is present
    if (!StringUtils.hasText(limitString)) {
      // Returns default
      return new Limiteable();
    }

    try {
      // Tries to parse retrieved parameter to create Limiteable instance
      int parsed = Integer.parseInt(limitString);
      return new Limiteable(parsed < 0 ? 0 : parsed > maxLimit ? maxLimit : parsed);
    } catch (NumberFormatException e) {
      // No valid value, returns default
      return new Limiteable();
    }
  }
}
