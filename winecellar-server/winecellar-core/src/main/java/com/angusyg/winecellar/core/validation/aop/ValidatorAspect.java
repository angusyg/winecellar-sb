package com.angusyg.winecellar.core.validation.aop;

import com.angusyg.winecellar.core.validation.util.Validator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Aspect to validate all method arguments in classes annotated with {@link org.springframework.stereotype.Service}.
 *
 * @since 0.0.1
 */
@Aspect
@Component
@Slf4j
public class ValidatorAspect {
  private static final String VALIDATION_FAILURE_MESSAGE = "Method argument validation failed: [method] %s - [argument %d] %s - [errors] %s";

  @Autowired
  private Validator validator;

  // Pointcut on all methods within annotated class with @Service
  @Pointcut("within(@org.springframework.stereotype.Service *)")
  public void beanAnnotatedWithService() {
  }

  // Pointcut on all methods within annotated class with @Validated
  @Pointcut("within(@org.springframework.validation.annotation.Validated *)")
  public void beanAnnotatedWithValidated() {
  }

  @Pointcut("beanAnnotatedWithService() && beanAnnotatedWithValidated()")
  public void beanAnnotatedWithServiceAndValidated() {
  }

  /**
   * Validates all method arguments of method in classes annotated with @Service and @Validated
   *
   * @param joinPoint method invocation
   * @throws {@link IllegalArgumentException} when a method argument validation failed
   */
  @Before("beanAnnotatedWithServiceAndValidated()")
  private void validateArguments(JoinPoint joinPoint) {
    // For each argument of method
    for (int i = 0; i < joinPoint.getArgs().length; i++) {
      // Get argument
      Object arg = joinPoint.getArgs()[i];
      // Validate argument
      Set<ConstraintViolation<Object>> result = validator.validate(arg);
      if (!result.isEmpty()) {
        // Validation failed
        for (ConstraintViolation<Object> cv : result) {
          // Log validation violation
          log.error(String.format(VALIDATION_FAILURE_MESSAGE, joinPoint.getSignature(), i, arg.getClass(), cv.toString()));
        }
        // Throw validation exception
        throw new IllegalArgumentException(String.format("%s is not valid", arg.getClass()));
      }
    }
  }
}
