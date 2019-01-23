package com.angusyg.winecellar.core.model.controller;

import com.angusyg.winecellar.core.web.controller.ApiController;
import com.angusyg.winecellar.core.web.dto.ApiResponse;
import com.angusyg.winecellar.core.web.dto.ErrorApiResponse;
import com.angusyg.winecellar.core.model.service.ModelService;
import com.angusyg.winecellar.core.model.web.arguments.Limiteable;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ModelController<T, ID, DTO> extends ApiController {
  private static final String NO_ENTITY_ERROR_CODE = "NO_ENTITY_ERROR";

  @Autowired
  private ModelMapper modelMapper;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private ModelService<T, ID> modelService;

  @Transactional(readOnly = true)
  @GetMapping
  public ApiResponse findAll(Pageable pageable, Limiteable limit, Sort sort) {
    Iterable<T> items = modelService.findAll(pageable, limit, sort);
    Type listT = new TypeToken<Iterable<DTO>>() {}.getType();
    return new ApiResponse(modelMapper.map(items, listT));
  }

  @Transactional(readOnly = true)
  @GetMapping("/{id}")
  public ApiResponse findById(@PathVariable("id") ID id) {
    T item = modelService.findById(id);
    ParameterizedType genericTypes = (ParameterizedType) this.getClass().getGenericSuperclass();
    return new ApiResponse(modelMapper.map(item, genericTypes.getActualTypeArguments()[2]));
  }

  @ExceptionHandler(NoResultException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorApiResponse handleNoResultException(HttpServletRequest req, Exception ex) {
    return new ErrorApiResponse(NO_ENTITY_ERROR_CODE, req);
  }
}
