package com.web.auction.junit;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.web.auction.App;
import com.web.auction.pojo.Auction;
import com.web.auction.pojo.AuctionCustom;
import com.web.auction.pojo.Auctionrecord;
import com.web.auction.service.AuctionService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {App.class})
public class TestAuctionResultMap {
	@Autowired
	private AuctionService auctionService;
	
	@Test
	public void testAuctionMap() {
		Auction auction = auctionService.findAuctionAndRecordlistByAuctionid(2);
		System.out.println(auction.getAuctionname()+","+auction.getAuctionstartprice());
		System.out.println("商品的竞拍列表");
		List<Auctionrecord> recordList = auction.getAuctionRecordList();
		for (Auctionrecord auctionrecord : recordList) {
			System.out.println(auctionrecord.getAuctiontime()+","+auctionrecord.getAuctionprice()+","+auctionrecord.getAuctionuser().getUsername());
		}
	}
	@Test
	public void testAuctionListEnding() {
		List<AuctionCustom> endtimeList = auctionService.findAuctionListEnding();
		/* <li>名称</li>
        <li>开始时间</li>
        <li>结束时间</li>
        <li>起拍价</li>
        <li class="list-wd">成交价</li>
        <li class="borderno">买家</li>*/
		for (AuctionCustom auctionCustom : endtimeList) {
			System.out.println(auctionCustom.getAuctionname());
			System.out.println(auctionCustom.getAuctionstarttime());
			System.out.println(auctionCustom.getAuctionendtime());
			System.out.println(auctionCustom.getAuctionstartprice());
			System.out.println(auctionCustom.getAuctionPrice());
			System.out.println(auctionCustom.getUserName());
		}
	}
	@Test
	public void testAuctionListNoEnding() {
		List<Auction> list = auctionService.findAuctionListNoEnding();
		for (Auction auction : list) {
			System.out.println("拍卖中的商品");
			System.out.println(auction.getAuctionname()+","+auction.getAuctionstartprice());
			System.out.println("商品的竞拍列表");
			List<Auctionrecord> recordList = auction.getAuctionRecordList();
			for (Auctionrecord auctionrecord : recordList) {
				System.out.println(auctionrecord.getAuctiontime()+","+auctionrecord.getAuctionprice()+","+auctionrecord.getAuctionuser().getUsername());
			}
		}
		
	}
	
}
