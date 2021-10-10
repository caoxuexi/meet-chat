package com.caostudy.service.impl;

import com.caostudy.mapper.UsersMapper;
import com.caostudy.pojo.Users;
import com.caostudy.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * @author Cao Study
 * @description <h1>UserServiceImpl</h1>
 * @date 2021-10-10 17:33
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;
    //注入随机id生成工具类
    @Autowired
    private Sid sid;

    @Override
    public boolean queryUsernameIsExist(String username) {
        Users user=new Users();
        user.setUsername(username);
        Users result=usersMapper.selectOne(user);

        return result!=null?true:false;
    }

    @Override
    public Users queryUserForLogin(String username, String md5Str) {
        Example userExample=new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username",username);
        criteria.andEqualTo("password",md5Str);
        Users result = usersMapper.selectOneByExample(userExample);
        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users saveUser(Users user) {
        //随机生成一个id
        String userId=sid.nextShort();

        //TODO 为每个用户生成一个唯一的二维码
        user.setQrcode("");
        user.setId(userId);
        usersMapper.insert(user);
        return user;
    }
}
