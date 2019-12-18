package com.web.auction.utils;
/**
 * 竞拍价格的自定义异常
 * 
 * 
 * 
 * 
 * */
public class AuctionPriceException extends Exception {
		private String message;

		
		
		public AuctionPriceException() {
			super();
		}

		public AuctionPriceException(String message) {
			super();
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
}
