package com.web.auction.pojo;
/*
 * 
 * 商品扩展类
 * 
 * */
public class AuctionCustom extends Auction {
	private Double auctionPrice;
	private String userName;
	public Double getAuctionPrice() {
		return auctionPrice;
	}
	public void setAuctionPrice(Double auctionPrice) {
		this.auctionPrice = auctionPrice;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
