package com.angusyg.winecellar.user.dao;

import com.angusyg.winecellar.user.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
  List<User> findByUsername(String username);
}
