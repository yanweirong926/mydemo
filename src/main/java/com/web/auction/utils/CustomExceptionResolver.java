package com.web.auction.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CustomExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView mv= new ModelAndView(); 
		AuctionPriceException auctionPriceException= null;
		if(ex instanceof AuctionPriceException) {
			auctionPriceException = (AuctionPriceException) ex; //向下转型
			mv.addObject("errorMsg", auctionPriceException.getMessage());
		}else {
			mv.addObject("errorMsg", "未知异常");
		}
		mv.setViewName("error");
		return mv;
	}

}
