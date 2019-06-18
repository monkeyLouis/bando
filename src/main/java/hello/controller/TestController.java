package hello.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.service.UserService;

@RestController
public class TestController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value="/addMocks")
	public String addMocks() {	
		return userService.addMocks();
	}
	
	@RequestMapping(value="/addMock")
	public String addMock() {
		return messageSource.getMessage("member.memId.empty", null, Locale.TAIWAN);
	}
	
}
