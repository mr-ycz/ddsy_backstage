package com.ycz.service;

import com.ycz.dao.AdminMapper;
import com.ycz.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public User queryAdminByName(String username) {
        return adminMapper.selectUserByName(username);
    }
}
