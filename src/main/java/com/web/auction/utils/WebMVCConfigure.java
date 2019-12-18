package com.web.auction.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.web.auction.interceptor.CheckUserInterceptor;

@Configuration
public class WebMVCConfigure extends WebMvcConfigurerAdapter {
	
	@Autowired
	private FileProperties fileProperties;
//	@Autowired
//	private CheckUserInterceptor checkUserInterceptor;
	
	@Override	//把http:/localhost:8080/update/xxx.jpg的路径 映射到物理文件夹：D:/file/xxx.jpg
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//registry.addResourceHandler("/update/**") 映射的虚拟路径
		//.addResourceLocations("file:d:/file/") 设置物理文件夹
		//registry.addResourceHandler("/update/**").addResourceLocations("file:d:/file/");
		System.out.println(fileProperties.getStaticAccessPath());
		System.out.println(fileProperties.getUploadFileFolder());
		registry.addResourceHandler(fileProperties.getStaticAccessPath()).addResourceLocations("file:"+fileProperties.getUploadFileFolder()+"/");
	}
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		List<String> paths = new ArrayList<>();
//		paths.add("/js/**");
//		paths.add("/image/**");
//		paths.add("/css/**");
//		paths.add("/login");
//		paths.add("/toRegister");
//		paths.add("/doLogin");
//		paths.add("/register");
//		paths.add("/defaultKaptcha");
//		registry.addInterceptor(new CheckUserInterceptor()).addPathPatterns("/**").excludePathPatterns(paths);
//	}
}
