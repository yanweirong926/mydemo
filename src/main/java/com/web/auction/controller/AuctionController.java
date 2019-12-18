package com.web.auction.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.auction.pojo.Auction;
import com.web.auction.pojo.AuctionCustom;
import com.web.auction.pojo.Auctionrecord;
import com.web.auction.pojo.Auctionuser;
import com.web.auction.service.AuctionService;

@Controller
public class AuctionController {
	@Autowired
	private AuctionService auctionService;
	
	public static final int PAGE_SIZE=10;
	
	@GetMapping("/toAdd")
	public String toAdd() {
		return "addAuction";
	}
	
	@RequestMapping("/queryAuctions")
	public ModelAndView queryAuctions(
			@ModelAttribute("condition") Auction auction,
			@RequestParam(value = "pageNum",required = false, defaultValue = "1" )int pageNum) {
		
		PageHelper.startPage(pageNum, PAGE_SIZE);
		List<Auction> list = auctionService.findAuction(auction);
		PageInfo<Auction> page = new PageInfo<>(list);
		//获取shiro上下文的用户身份的对象
		Auctionuser user = (Auctionuser) SecurityUtils.getSubject().getPrincipal();
		ModelAndView mv = new ModelAndView();
		mv.addObject("auctionList", list);
		mv.addObject("page", page);
		mv.addObject("user", user);
		mv.setViewName("index");
		return mv;
		
	}
	@RequestMapping("/publishAuctions")
	public String publishAuctions(Auction auction, MultipartFile pic){
		
		try {
			//1、处理文件的保存目录
			String path = "D:/file";
			File targetFile = new File(path,pic.getOriginalFilename());
			pic.transferTo(targetFile);
			
			//2、保存商品数据到db
			auction.setAuctionpic(pic.getOriginalFilename());
			auction.setAuctionpictype(pic.getContentType());
			auctionService.addAuction(auction);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/queryAuctions";
		
	}
	@RequestMapping("/toDetail/{id}")
	public ModelAndView toDetail(@PathVariable int id) {
		ModelAndView mv = new ModelAndView();
		Auction auction = auctionService.findAuctionById(id);
		mv.addObject("auction", auction);
		mv.setViewName("updateAuction");
		return mv;
		
	}
	@RequestMapping("/updateAuctions")
	public String updateAuctions(Auction auction,MultipartFile pic) {
		try {
			//1、处理文件的保存目录
			String path = "D:/file";
			File targetFile = new File(path,pic.getOriginalFilename());
			pic.transferTo(targetFile);
			
			//2、保存商品数据到db
			auction.setAuctionpic(pic.getOriginalFilename());
			auction.setAuctionpictype(pic.getContentType());
			auctionService.updateAuction(auction);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/queryAuctions";
		
	}
	@RequestMapping("/toAuctionDetail/{id}")
	public ModelAndView toAuctionDetail(@PathVariable int id) {
		Auction auction = auctionService.findAuctionAndRecordlistByAuctionid(id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("auctionDetail", auction);
		mv.setViewName("auctionDetail");
		return mv;
		
	}
	@RequestMapping("/saveAuctionRecord")
	public String saveAuctionRecord(Auctionrecord record,Model model) throws Exception {
		//竞拍时间：当前时间
		record.setAuctiontime(new Date());
		//竞拍人
		Auctionuser user = (Auctionuser) SecurityUtils.getSubject().getPrincipal();
		record.setUserid(user.getUserid());
		
		auctionService.addAuctionRecord(record);
		
		return "redirect:/toAuctionDetail/"+record.getAuctionid();
		
	}
	@RequestMapping("/toAuctionResult")
	public ModelAndView toAuctionResult(){
		ModelAndView mv = new ModelAndView();
		List<AuctionCustom> endtimeList = auctionService.findAuctionListEnding();
		List<Auction> noendtimeList = auctionService.findAuctionListNoEnding();
		//竞拍登录人
		Auctionuser user = (Auctionuser) SecurityUtils.getSubject().getPrincipal();
		mv.addObject("endtimeList", endtimeList);
		mv.addObject("noendtimeList", noendtimeList);
		mv.addObject("user", user);
		mv.setViewName("auctionResult");
		return mv;
		
	}
	
	
	
}
