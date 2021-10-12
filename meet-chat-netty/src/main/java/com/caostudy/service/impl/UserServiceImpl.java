package com.caostudy.service.impl;

import com.caostudy.enums.SearchFriendsStatusEnum;
import com.caostudy.mapper.FriendsRequestMapper;
import com.caostudy.mapper.MyFriendsMapper;
import com.caostudy.mapper.UsersMapper;
import com.caostudy.pojo.FriendsRequest;
import com.caostudy.pojo.MyFriends;
import com.caostudy.pojo.Users;
import com.caostudy.service.UserService;
import com.caostudy.utils.FastDFSClient;
import com.caostudy.utils.FileUtils;
import com.caostudy.utils.QRCodeUtils;
import org.apache.catalina.User;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.Date;

/**
 * @author Cao Study
 * @description <h1>UserServiceImpl</h1>
 * @date 2021-10-10 17:33
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private MyFriendsMapper myFriendsMapper;
    @Autowired
    private FriendsRequestMapper friendsRequestMapper;
    //注入随机id生成工具类
    @Autowired
    private Sid sid;
    //二维码生成类
    @Autowired
    private QRCodeUtils qrCodeUtils;
    //文件操作类
    @Autowired
    private FileUtils fileUtils;
    //fastdfs工具类
    @Autowired
    private FastDFSClient fastDFSClient;


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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users saveUser(Users user) {
        //随机生成一个id
        String userId=sid.nextShort();

        // 为每个用户生成一个唯一的二维码
        String qrCodePath="D:\\meet-chatStorage\\user\\"+userId+"qrcode.png";
        qrCodeUtils.createQRCode(qrCodePath,"meet_qrcode:"+user.getUsername());
        MultipartFile qrCodeFile=FileUtils.fileToMultipart(qrCodePath);

        String qrCodeUrl="";
        try {
            qrCodeUrl=fastDFSClient.uploadQRCode(qrCodeFile);
        }catch (IOException e){
            e.printStackTrace();
        }
        user.setQrcode(qrCodeUrl);

        user.setId(userId);
        usersMapper.insert(user);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserInfo(Users user) {
        usersMapper.updateByPrimaryKeySelective(user);
        return queryUserById(user.getId());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserById(String userId){
        return usersMapper.selectByPrimaryKey(userId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer preconditionSearchFriends(String myUserId, String friendUsername) {
        //1.搜索的用户如果不存在，返回[无此用户]
        Users user=queryUserInfoByUsername(friendUsername);
        if(user==null){
            return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
        }
        //2.搜索帐号是你自己，返回[不能添加自己]
        if(user.getId().equals(myUserId)) {
            return SearchFriendsStatusEnum.NOT_YOURSELF.status;
        }
        //3.搜索的朋友已经是你的好友，返回[该用户已经是你的好友了]
        Example myFriendsExample=new Example(MyFriends.class);
        Example.Criteria myFriendsCriteria=myFriendsExample.createCriteria();
        myFriendsCriteria.andEqualTo("myUserId",myUserId);
        myFriendsCriteria.andEqualTo("myFriendUserId",user.getId());
        MyFriends myFriendsResult = myFriendsMapper.selectOneByExample(myFriendsExample);
        if(myFriendsResult!=null){
            return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
        }
        return SearchFriendsStatusEnum.SUCCESS.status;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfoByUsername(String username){
        Example userExample=new Example(Users.class);
        Example.Criteria userCriteria=userExample.createCriteria();
        userCriteria.andEqualTo("username",username);
        Users users = usersMapper.selectOneByExample(userExample);
        return users;
    }

    @Override
    public void sendFriendRequest(String myUserId, String friendUsername) {
// 根据用户名把朋友信息查询出来
        Users friend = queryUserInfoByUsername(friendUsername);

        // 1. 查询发送好友请求记录表,判断之前是否已经发送过添加好友请求了
        Example fre = new Example(FriendsRequest.class);
        Example.Criteria frc = fre.createCriteria();
        frc.andEqualTo("sendUserId", myUserId);
        frc.andEqualTo("acceptUserId", friend.getId());
        FriendsRequest friendRequest = friendsRequestMapper.selectOneByExample(fre);
        if (friendRequest == null) {
            // 2. 如果不是你的好友，并且好友记录没有添加，则新增好友请求记录
            String requestId = sid.nextShort();

            FriendsRequest request = new FriendsRequest();
            request.setId(requestId);
            request.setSendUserId(myUserId);
            request.setAcceptUserId(friend.getId());
            request.setRequestDateTime(new Date());
            friendsRequestMapper.insert(request);
        }
    }
}
