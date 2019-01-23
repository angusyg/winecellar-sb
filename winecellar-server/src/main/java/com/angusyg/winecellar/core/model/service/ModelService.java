package com.angusyg.winecellar.core.model.service;

import com.angusyg.winecellar.core.model.web.arguments.Limiteable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface ModelService<T, ID> {
  Iterable<T> findAll(Pageable pageable, Limiteable limit, Sort sort);

  T findById(ID id);
}
