package com.angusyg.winecellar.core.model.service;

import com.angusyg.winecellar.core.model.exception.NoResourceModelException;
import com.angusyg.winecellar.core.model.web.arguments.Limiteable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Service to handle resource interactions
 *
 * @param <T>  resource type
 * @param <ID> resource id type
 * @since 0.0.1
 */
public class ModelService<T, ID> {
  // Resource repository
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  protected CrudRepository<T, ID> repository;

  // Flag to indicate if resource repository is a paging and sorting one
  private boolean isPagingAndSortingRepository;

  /**
   * Checks type of resource repository
   */
  @PostConstruct
  private void getRepositoryType() {
    this.isPagingAndSortingRepository = repository instanceof PagingAndSortingRepository;
  }

  /**
   * Lists all resource elements
   * Search can be done using page, limit and sort
   *
   * @param pageable search page parameter
   * @param limit    search limit parameter
   * @param sort     search sort parameter
   * @return an {@code Iterable<T>} with found elements
   */
  public Iterable<T> findAll(Pageable pageable, Limiteable limit, Sort sort) {
    if (isPagingAndSortingRepository) {
      // Paging and sorting, checks all parameters to configure final search
      Pageable p;
      if (sort.isSorted()) {
        // Applies if needed limit on search page and adds sort configuration to search configuration
        p = PageRequest.of(pageable.getPageNumber(), (limit.isLimited() && pageable.getPageSize() > limit.getLimit()) ? limit.getLimit() : pageable.getPageSize(), sort);
      } else {
        // Applies if needed limit on search page
        p = PageRequest.of(pageable.getPageNumber(), (limit.isLimited() && pageable.getPageSize() > limit.getLimit()) ? limit.getLimit() : pageable.getPageSize());
      }
      // Searches in repository
      return ((PagingAndSortingRepository) repository).findAll(p);
    } else {
      // Searches in repository
      return repository.findAll();
    }
  }

  /**
   * Retrieves a resource element by its id
   *
   * @param id id of resource to retrieve
   * @return a resource element
   * @throws NoResourceModelException when no resource element found with id
   */
  public T findById(ID id) throws NoResourceModelException {
    // Searches in repository
    Optional<T> item = repository.findById(id);
    // Returns found element, if no element found throws exception
    return item.orElseThrow(() -> new NoResourceModelException());
  }
}
