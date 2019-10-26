package com.ycz.dao;

import com.ycz.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    User selectUserByName(@Param("username") String username);
}
