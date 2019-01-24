package com.angusyg.winecellar.core.model.web.handler;

import com.angusyg.winecellar.core.model.web.arguments.Limiteable;
import lombok.Setter;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
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
  private static final String DEFAULT_LIMIT_PARAMETER = "limit";

  private static final int DEFAULT_LIMIT_VALUE = 1000;

  private int maxLimit;

  @Setter
  private String limitParameterName = DEFAULT_LIMIT_PARAMETER;


  public LimitHandlerMethodArgumentResolver(int maxLimit) {
    this.maxLimit = maxLimit > 0 ? maxLimit : DEFAULT_LIMIT_VALUE;
  }

  @Override
  public boolean supportsParameter(MethodParameter methodParameter) {
    return methodParameter.getParameterType().equals(Limiteable.class);
  }

  @Override
  public Limiteable resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
    String limitString = webRequest.getParameter(limitParameterName);

    if (!StringUtils.hasText(limitString)) {
      return new Limiteable();
    }

    try {
      int parsed = Integer.parseInt(limitString);
      return new Limiteable(parsed < 0 ? 0 : parsed > maxLimit ? maxLimit : parsed);
    } catch (NumberFormatException e) {
      return new Limiteable();
    }
  }
}
