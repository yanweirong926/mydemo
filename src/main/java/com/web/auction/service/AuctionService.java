package com.web.auction.service;

import java.util.List;

import com.web.auction.pojo.Auction;
import com.web.auction.pojo.AuctionCustom;
import com.web.auction.pojo.Auctionrecord;

public interface AuctionService {

	public List<Auction> findAuction(Auction auction);

	public void addAuction(Auction auction);
	
	public Auction findAuctionById(Integer id);
	
	public Auction findAuctionAndRecordlistByAuctionid(int id);

	public void addAuctionRecord(Auctionrecord record) throws Exception;
	
	public List<AuctionCustom> findAuctionListEnding();
	
	public List<Auction> findAuctionListNoEnding();

	public void updateAuction(Auction auction);
}
