package com.web.auction.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.auction.mapper.AuctionuserMapper;
import com.web.auction.pojo.Auctionuser;
import com.web.auction.pojo.AuctionuserExample;
import com.web.auction.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private AuctionuserMapper auctionuserMapper;
	
	@Override
	public Auctionuser findUserByUserName(String username) {
		AuctionuserExample example = new AuctionuserExample();
		AuctionuserExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		
		List<Auctionuser> list = auctionuserMapper.selectByExample(example);
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void addUser(Auctionuser user) {
		user.setUserisadmin(0);
		
		auctionuserMapper.insert(user);
		
	}

}
