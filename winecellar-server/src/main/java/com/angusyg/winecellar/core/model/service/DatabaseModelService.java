package com.angusyg.winecellar.core.model.service;

import com.angusyg.winecellar.core.model.web.arguments.Limiteable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;
import java.util.Optional;

public class DatabaseModelService<T, ID> implements ModelService<T, ID> {
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  protected CrudRepository<T, ID> repository;

  private boolean isPagingAndSortingRepository;

  @PostConstruct
  private void getRepositoryType() {
    this.isPagingAndSortingRepository = repository instanceof PagingAndSortingRepository;
  }

  @Override
  public Iterable<T> findAll(Pageable pageable, Limiteable limit, Sort sort) {
    if (isPagingAndSortingRepository) {
      Pageable p;
      if (sort.isSorted()) {
        p = PageRequest.of(pageable.getPageNumber(), (limit.isLimited() && pageable.getPageSize() > limit.getLimit()) ? limit.getLimit() : pageable.getPageSize(), sort);
      } else {
        p = PageRequest.of(pageable.getPageNumber(), (limit.isLimited() && pageable.getPageSize() > limit.getLimit()) ? limit.getLimit() : pageable.getPageSize());
      }
      return ((PagingAndSortingRepository) repository).findAll(p);
    } else {
      return repository.findAll();
    }
  }

  @Override
  public T findById(ID id) {
    Optional<T> item = repository.findById(id);
    return item.orElseThrow(() -> new NoResultException(""));
  }
}
