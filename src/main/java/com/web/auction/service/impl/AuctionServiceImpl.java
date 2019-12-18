package com.web.auction.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.auction.mapper.AuctionMapper;
import com.web.auction.mapper.AuctionMapperCustom;
import com.web.auction.mapper.AuctionrecordMapper;
import com.web.auction.pojo.Auction;
import com.web.auction.pojo.AuctionCustom;
import com.web.auction.pojo.AuctionExample;
import com.web.auction.pojo.Auctionrecord;
import com.web.auction.service.AuctionService;
import com.web.auction.utils.AuctionPriceException;

@Service
@Transactional
public class AuctionServiceImpl implements AuctionService {

	@Autowired
	private AuctionMapper auctionMapper;
	
	@Autowired
	private AuctionMapperCustom auctionMapperCustom;
	
	@Autowired
	private AuctionrecordMapper auctionrecordMapper;
	@Override
	public List<Auction> findAuction(Auction auction) {
		AuctionExample example = new AuctionExample();
		AuctionExample.Criteria criteria = example.createCriteria();
		if(auction!=null) {
			//1、商品名称模糊查询
			if(auction.getAuctionname()!=null&&!"".equals(auction.getAuctionname())) {
				criteria.andAuctionnameLike("%"+auction.getAuctionname()+"%");
			}
			//2、商品描述模糊查询
			if(auction.getAuctiondesc()!=null&&!"".equals(auction.getAuctiondesc())) {
				criteria.andAuctiondescLike("%"+auction.getAuctiondesc()+"%");
			}
			//3、起拍时间 大于
			if(auction.getAuctionstarttime()!=null) {
				criteria.andAuctionstarttimeGreaterThan(auction.getAuctionstarttime());
			}
			//4、结束时间 小于
			if(auction.getAuctionendtime()!=null) {
				criteria.andAuctionendtimeLessThan(auction.getAuctionendtime());
			}
			//5、起拍价  大于
			if(auction.getAuctionstartprice()!=null) {
				criteria.andAuctionstartpriceGreaterThan(auction.getAuctionstartprice());
			}
		}
		//按照起拍时间降序排列
		example.setOrderByClause("auctionstarttime desc");
		List<Auction> list = auctionMapper.selectByExample(example );
		return list;
	}

	@Override
	public void addAuction(Auction auction) {
		auctionMapper.insert(auction);
		
	}

	@Override
	public Auction findAuctionById(Integer id) {
		// TODO Auto-generated method stub
		return auctionMapper.selectByPrimaryKey(id);
	}

	@Override
	public Auction findAuctionAndRecordlistByAuctionid(int id) {
		// TODO Auto-generated method stub
		return this.auctionMapperCustom.findAuctionAndRecordlistByAuctionid(id);
	}
	/*
	 * 			1.竞拍时间不能过期
	 * 			2.如果首次竞价，价格不能低于起拍价
	 * 			3.如果是后续竞价，价格是所有竞价的最高价
	 * 
	 * */
	@Override
	public void addAuctionRecord(Auctionrecord record) throws Exception {
		//查询当前竞拍商品的信息
		Auction auction= this.findAuctionAndRecordlistByAuctionid(record.getAuctionid());
		//1.竞拍时间不能过期
		if(auction.getAuctionendtime().after(new Date()) == false) {	//表示过期
			throw new AuctionPriceException("竞拍时间不能过期");
		}else {
			//判断是否首次竞价
			if(auction.getAuctionRecordList().size()>0){
				//最高竞价记录
				Auctionrecord maxRecord=auction.getAuctionRecordList().get(0);
				if(record.getAuctionprice()<=maxRecord.getAuctionprice()) {
					throw new AuctionPriceException("竞拍价格是所有竞价的最高价");
				}
			}else {
				//首次竞价
				if(record.getAuctionprice()<=auction.getAuctionstartprice()) {
					throw new AuctionPriceException("竞拍价格不能低于起拍价");
				}
			}
		}
		auctionrecordMapper.insert(record);
	}

	@Override
	public List<AuctionCustom> findAuctionListEnding() {
		return this.auctionMapperCustom.findAuctionListEnding();
	}

	@Override
	public List<Auction> findAuctionListNoEnding() {
		// TODO Auto-generated method stub
		return this.auctionMapperCustom.findAuctionListNoEnding();
	}

	@Override
	public void updateAuction(Auction auction) {
		auctionMapper.updateByPrimaryKey(auction);
	}

}
