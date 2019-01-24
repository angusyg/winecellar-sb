package com.angusyg.winecellar.core.utils;

import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MapperUtils {
  private static final ModelMapper modelMapper = new ModelMapper();

  public static <D, T> D map(final T entity, Type out) {
    return modelMapper.map(entity, out);
  }

  public static <D, T> Collection<D> mapAll(final Iterable<T> list, Type out) {
    return (Collection<D>) StreamSupport.stream(list.spliterator(), false)
            .map(element -> modelMapper.map(element, out))
            .collect(Collectors.toList());
  }
}
