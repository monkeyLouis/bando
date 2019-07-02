package hello.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.domain.Member;
import hello.exception.BandoException;
import hello.service.UserService;
import hello.service.impl.UserServiceImpl;

@Controller
public class MemberController {

	private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private UserService userSvc;
	
	@RequestMapping(value = "/index")
	public String goIndex() {
		return "member/index";
	}
	
	@RequestMapping(value = "/forgot", produces = "application/json", method=RequestMethod.POST)
	public @ResponseBody UserServiceImpl.ProcessResp forgotPassword(@RequestBody ForgetInfo info) {
		LOG.info("### In forgotPassword - email: {} ###", info.getEmail());
		return userSvc.forgetPassword(info.getEmail());
	}
	
	@RequestMapping(value="/turnUp")
	public String enableAndLogin(@RequestParam("pid") String user) {
		String page;
		try {
			LOG.info("GET IN TURN UP");
			userSvc.changeMemberStatus(user);
			page = "redirect:member/index";
		} catch(BandoException be) {
			page = "jsp/login";
		}
		
		return page;
	}
	
	private static class ForgetInfo {
		private String email;
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
	}
	
}
