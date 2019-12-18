package com.web.auction.mapper;

import java.util.List;

import com.web.auction.pojo.Auction;
import com.web.auction.pojo.AuctionCustom;

public interface AuctionMapperCustom {
	
	
	public Auction findAuctionAndRecordlistByAuctionid(int id);
	
	public List<AuctionCustom> findAuctionListEnding();
	
	public List<Auction> findAuctionListNoEnding();
}
