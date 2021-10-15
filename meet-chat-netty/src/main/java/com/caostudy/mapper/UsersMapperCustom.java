package com.caostudy.mapper;

import com.caostudy.pojo.Users;
import com.caostudy.pojo.vo.FriendRequestVO;
import com.caostudy.pojo.vo.MyFriendsVO;
import com.caostudy.utils.MyMapper;

import java.util.List;

public interface UsersMapperCustom extends MyMapper<Users> {
    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

    public List<MyFriendsVO> queryMyFriends(String userId);

    public void batchUpdateMsgSigned(List<String> msgIdList);
}