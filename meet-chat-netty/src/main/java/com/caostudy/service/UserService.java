package com.caostudy.service;

import com.caostudy.pojo.Users;

/**
 * @author Cao Study
 * @description <h1>UserService</h1>
 * @date 2021-10-10 17:23
 */
public interface UserService {
    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 查询用户是否存在
     * @param username
     * @param md5Str
     * @return
     */
    Users queryUserForLogin(String username, String md5Str);

    /**
     * 用户注册
     * @param user
     * @return
     */
    Users saveUser(Users user);
}
