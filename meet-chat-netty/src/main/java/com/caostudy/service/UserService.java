package com.caostudy.service;

import com.caostudy.pojo.Users;
import com.caostudy.pojo.vo.FriendRequestVO;
import com.caostudy.pojo.vo.MyFriendsVO;

import java.util.List;

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

    /**
     * 更新用户数据
     * @param user
     * @return
     */
    Users updateUserInfo(Users user);


    /**
     * 判断添加好友请求是否符合逻辑
     * @param myUserId
     * @param friendUsername
     * @return
     */
    Integer preconditionSearchFriends(String myUserId, String friendUsername);

    /**
     * 按用户名查找用户信息
     * @param friendUsername
     * @return
     */
    Users queryUserInfoByUsername(String friendUsername);

    /**
     * 发送添加好友请求消息
     * @param myUserId
     * @param friendUsername
     */
    void sendFriendRequest(String myUserId, String friendUsername);

    /**
     * 查询添加好友请求
     * @param  acceptUserId
     * @return
     */
    List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

    /**
     * @Description: 删除好友请求记录
     */
    public void deleteFriendRequest(String sendUserId, String acceptUserId);

    /**
     * @Description: 通过好友请求
     * 				1. 保存好友
     * 				2. 逆向保存好友
     * 				3. 删除好友请求记录
     */
    public void passFriendRequest(String sendUserId, String acceptUserId);

    /**
     * @Description: 查询好友列表
     */
    public List<MyFriendsVO> queryMyFriends(String userId);
}
