package com.ycz.service;

import com.ycz.pojo.User;

public interface AdminService {
    User queryAdminByName(String username);
}
