package com.web.auction.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.web.auction.pojo.Auctionuser;
import com.web.auction.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
		
	}
	@GetMapping("/toRegister")
	public String toRegister(@ModelAttribute("registerUser")Auctionuser user) {
		return "register";
		
	}
	@RequestMapping("/doLogin")
	public String doLogin(HttpServletRequest request,Model model) {
		String exceptionError = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if(exceptionError !=null) {
			if(UnknownAccountException.class.getName().equals(exceptionError)) {
				model.addAttribute("errorMsg", "账号错误");
			}
			if (IncorrectCredentialsException.class.getName().equals(exceptionError)) {
				model.addAttribute("errorMsg", "密码错误");
			}
			if ("validateCodeError".equals(exceptionError)) {
				model.addAttribute("errorMsg", "验证码错误");
			}
		}
		return "login";
		
	}
//	@RequestMapping("/doLogin")
//	public String doLogin(String username,String password,String validateCode,
//									HttpSession session,Model model) {
//		//1、判断验证码
//		String vrifyCode = (String) session.getAttribute("vrifyCode");
//		if(!vrifyCode.equals(validateCode)) {
//			model.addAttribute("errorMsg", "验证码错误");
//			return "login";
//		}
//		//2、调用业务方法认证
//		Auctionuser loginUser = userService.findUserByUserName(username);
//		if(loginUser!=null) {
//			if(loginUser.getUserpassword().equals(password)) {
//				session.setAttribute("user", loginUser);
//				return "redirect:/queryAuctions";
//			}else {
//				model.addAttribute("errorMsg", "用户或密码错误");
//				return "login";
//			}
//		}else {
//			model.addAttribute("errorMsg", "用户或密码错误");
//			return "login";
//		}				
//	}
	@RequestMapping("/register")
	public String register(Model model,
			@ModelAttribute("registerUser")
			@Validated Auctionuser user,String validateCode,
				HttpSession session,BindingResult bindingResult) {
		//1.处理验证的错误的提示
		if (bindingResult.hasErrors()) {//验证不通过
			//提取验证的错误信息
			List<FieldError> errors=bindingResult.getFieldErrors();
			//保存到作用域中
			for (FieldError fieldError : errors) {
				model.addAttribute(fieldError.getField(), fieldError.getDefaultMessage());
			}
			//返回到register.html
			return "register";
		}
		//1、判断验证码
		String vrifyCode = (String) session.getAttribute("vrifyCode");
		if(!vrifyCode.equals(validateCode)) {
			model.addAttribute("validateCode", "验证码错误");
			return "register";
		}
		//2.保存到数据库中
		userService.addUser(user);
		return "login";
		
	}
	
	
	
	
	
	
	
	
	@Autowired
	private DefaultKaptcha captchaProducer;

	/**
	 * 获取验证码
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @throws Exception
	 */
	@RequestMapping("/defaultKaptcha")
	public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws Exception {
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			// 生产验证码字符串并保存到session中
			String createText = captchaProducer.createText();
			httpServletRequest.getSession().setAttribute("vrifyCode", createText);
			// 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
			BufferedImage challenge = captchaProducer.createImage(createText);
			ImageIO.write(challenge, "jpg", jpegOutputStream);
		} catch (IllegalArgumentException e) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		httpServletResponse.setHeader("Cache-Control", "no-store");
		httpServletResponse.setHeader("Pragma", "no-cache");
		httpServletResponse.setDateHeader("Expires", 0);
		httpServletResponse.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}
}
