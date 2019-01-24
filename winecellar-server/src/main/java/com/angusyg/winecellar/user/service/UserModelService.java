package com.angusyg.winecellar.user.service;

import com.angusyg.winecellar.core.model.service.ModelService;
import com.angusyg.winecellar.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserModelService extends ModelService<User, Long> {}
