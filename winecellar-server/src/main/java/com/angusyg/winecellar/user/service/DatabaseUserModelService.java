package com.angusyg.winecellar.user.service;

import com.angusyg.winecellar.user.entity.User;
import com.angusyg.winecellar.core.model.service.DatabaseModelService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"h2", "postgresql"})
public class DatabaseUserModelService extends DatabaseModelService<User, Long> implements UserModelService {
}
