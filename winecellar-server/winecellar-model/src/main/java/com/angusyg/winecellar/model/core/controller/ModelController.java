package com.angusyg.winecellar.model.core.controller;

import com.angusyg.winecellar.model.core.utils.MapperUtils;
import com.angusyg.winecellar.model.core.exception.NoResourceModelException;
import com.angusyg.winecellar.model.core.service.ModelService;
import com.angusyg.winecellar.model.core.web.arguments.Limiteable;
import com.angusyg.winecellar.core.web.controller.ApiController;
import com.angusyg.winecellar.core.web.dto.ApiResponse;
import com.angusyg.winecellar.core.web.dto.ErrorApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Model resource controller with default methods to handle REST resource operations
 * Supported operations:
 * <ul>
 * <li>LIST resource (GET /)</li>
 * <li>READ resource (GET /id)</li>
 * </ul>
 *
 * @param <T>   resource type
 * @param <ID>  resource id type
 * @param <DTO> DTO type to return
 * @since 0.0.1
 */
public class ModelController<T, ID, DTO> extends ApiController {
  // Model service accessing Dao resource
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private ModelService<T, ID> modelService;

  // Stores DTO type for mapper
  private Type dtoType;

  /**
   * Retrieves DTO type from controller generics
   * DTO type is used by model mapper
   */
  @PostConstruct
  private void initDtoType() {
    ParameterizedType genericTypes = (ParameterizedType) this.getClass().getGenericSuperclass();
    this.dtoType = genericTypes.getActualTypeArguments()[2];
  }

  /**
   * Lists all resource elements
   *
   * @param pageable request page argument values
   * @param limit    request limit argument value
   * @param sort     request sort argument values
   * @return a list of resource elements depending on request arguments
   */
  @Transactional(readOnly = true)
  @GetMapping
  public ApiResponse findAll(Pageable pageable, Limiteable limit, Sort sort) {
    // Retrieves entities
    Iterable<T> items = modelService.findAll(pageable, limit, sort);
    // Maps entities to dto and returns an API response
    return new ApiResponse(MapperUtils.mapAll(items, this.dtoType));
  }

  /**
   * Retrieves resource element by its id
   *
   * @param id id of resource to retrieve
   * @return a resource element
   * @throws NoResourceModelException when no resource element found with id
   */
  @Transactional(readOnly = true)
  @GetMapping("/{id}")
  public ApiResponse findById(@PathVariable("id") ID id) throws NoResourceModelException {
    // Retrieves entity by its id
    T item = modelService.findById(id);
    // Maps entity to dto and returns an API response
    return new ApiResponse(MapperUtils.map(item, this.dtoType));
  }

  /**
   * Handles {@link NoResourceModelException} thrown when no resource element were found
   * <b>Status code of response is {@code 404}</b>
   *
   * @param req request
   * @param ex  {@link NoResourceModelException} thrown
   * @return an api error response with {@link NoResourceModelException#getCode()} error code
   */
  @ExceptionHandler(NoResourceModelException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorApiResponse handleNoResourceModelException(HttpServletRequest req, NoResourceModelException ex) {
    // Returns API error
    return new ErrorApiResponse(ex);
  }
}
