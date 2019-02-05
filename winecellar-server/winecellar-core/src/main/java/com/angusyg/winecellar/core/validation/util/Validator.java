package com.angusyg.winecellar.core.validation.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Component
public class Validator {
  @Autowired
  private javax.validation.Validator validator;

  public <T> Set<ConstraintViolation<T>> validate(T toValidate) {
    return validator.validate(toValidate);
  }
}
