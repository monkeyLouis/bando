package hello.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.domain.Member;
import hello.domain.form.RegisterForm;
import hello.service.UserService;
import hello.service.impl.UserServiceImpl;
import hello.validator.SignUpValidator;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private SignUpValidator signUpValidator;
	@InitBinder("registerForm")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(signUpValidator);
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Model model, HttpServletRequest req) {
		model.addAttribute("mem", new Member());
		model.addAttribute("registerForm", new RegisterForm());
		
		return "jsp/login";
	}
	
	@RequestMapping(value="/signUp", method=RequestMethod.POST) 
	public @ResponseBody UserServiceImpl.ProcessResp register(HttpServletRequest req, @ModelAttribute("registerForm") @Validated RegisterForm form, BindingResult result){
		
		UserServiceImpl.ProcessResp resp;
		if(result.hasErrors()) {
			resp = new UserServiceImpl.ProcessResp("failed");
		} else {
			resp = userService.add(form, req);
		}
		
		return resp;
	}
	
	@RequestMapping(value="/403", method=RequestMethod.GET)
	public String forbidden() {
		return "member/403";
	}
	
}
