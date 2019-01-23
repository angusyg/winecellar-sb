package com.angusyg.winecellar.core.model.web.handler;

import com.angusyg.winecellar.core.model.web.arguments.Limiteable;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LimitHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
  private int maxLimit;

  public LimitHandlerMethodArgumentResolver(int maxLimit) {
    this.maxLimit = maxLimit;
  }

  @Override
  public boolean supportsParameter(MethodParameter methodParameter) {
    return methodParameter.getParameterType().equals(Limiteable.class);
  }

  @Override
  public Limiteable resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
    String limitString = webRequest.getParameter("limit");

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
