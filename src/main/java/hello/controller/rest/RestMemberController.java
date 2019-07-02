package hello.controller.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hello.domain.dto.EditUserVo;
import hello.domain.rest.RestResponse;
import hello.enums.BandoStatus;
import hello.service.UserService;

@RestController
@RequestMapping("/edit")
public class RestMemberController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public RestResponse editMember(@RequestBody EditUserVo editUserVo, Principal principal) {
		userService.editMember(principal.getName(), editUserVo);
		return new RestResponse(BandoStatus.SUCCESS);
	}
	
}
