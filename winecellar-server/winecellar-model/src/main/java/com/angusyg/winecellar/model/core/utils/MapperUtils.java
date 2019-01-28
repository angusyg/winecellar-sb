package com.angusyg.winecellar.model.core.utils;

import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Util to map entity to DTO.
 * Provides methods to map one or more entities at one time.
 *
 * @since 0.0.1
 */
public class MapperUtils {
  // Instance of modelmapper as base mapper
  private static final ModelMapper modelMapper = new ModelMapper();

  /**
   * Maps a given entity to a DTO instance given its type
   *
   * @param <D>    type of DTO instance
   * @param <T>    type of entity
   * @param entity entity to map
   * @param out    type of DTO
   * @return a DTO instance mapped from given entity
   */
  public static <D, T> D map(final T entity, Type out) {
    return modelMapper.map(entity, out);
  }

  /**
   * Maps a collection of entities to a collection of DTO instance given a DTO type
   *
   * @param <D>      type of DTO instance
   * @param <T>      type of entity
   * @param iterable collection of entities to map
   * @param out      type of DTO
   * @return a collection of DTO instance mapped from given entities collection
   */
  public static <D, T> Collection<D> mapAll(final Iterable<T> iterable, Type out) {
    return (Collection<D>) StreamSupport.stream(iterable.spliterator(), false)
            .map(element -> modelMapper.map(element, out))
            .collect(Collectors.toList());
  }
}
