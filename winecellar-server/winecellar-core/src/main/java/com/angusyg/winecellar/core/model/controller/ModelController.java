package com.angusyg.winecellar.core.model.controller;

import com.angusyg.winecellar.core.exception.ApiException;
import com.angusyg.winecellar.core.model.service.ModelService;
import com.angusyg.winecellar.core.model.utils.MapperUtils;
import com.angusyg.winecellar.core.model.web.arguments.Limiteable;
import com.angusyg.winecellar.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

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
public class ModelController<T, ID, DTO> extends BaseController {
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
  public Collection<DTO> findAll(Pageable pageable, Limiteable limit, Sort sort) {
    // Retrieves entities
    Iterable<T> items = modelService.findAll(pageable, limit, sort);
    // Maps entities to dto and returns an API response
    return MapperUtils.mapAll(items, this.dtoType);
  }

  /**
   * Retrieves resource element by its id
   *
   * @param id id of resource to retrieve
   * @return a resource element
   * @throws ApiException when no resource element found with id
   */
  @Transactional(readOnly = true)
  @GetMapping("/{id}")
  public DTO findById(@PathVariable("id") ID id) throws ApiException {
    // Retrieves entity by its id
    T item = modelService.findById(id);
    // Maps entity to dto and returns an API response
    return MapperUtils.map(item, this.dtoType);
  }
}
