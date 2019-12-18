package com.web.auction.service;


import com.web.auction.pojo.Auctionuser;

public interface UserService {

	public Auctionuser findUserByUserName(String username);

	public void addUser(Auctionuser user);
}
