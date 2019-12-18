package com.web.auction.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DateConverter implements Converter<String, Date> {

	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	SimpleDateFormat[] sdfs = {	new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
			new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("yyyy/MM/dd")
		};
	@Override
	public Date convert(String source) {
		
		try {
			for (SimpleDateFormat sdf : sdfs) {
				if(source!=null&&!source.equals("")) {
					return sdf.parse(source);
				}
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
