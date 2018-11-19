package hello.controller.zk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import hello.config.view.ZKEEApplication;

@ZKEEApplication
@Controller
public class ZkEntranceController {
	@GetMapping("/admin")
	public String indexPage(){
		return "/admin-index";
	}
	
	@GetMapping("/test")
	public String testPage(){
		return "test";
	}
	
	@GetMapping("/mvvm")
	public String mvvmExample() {
		return "mvvm";
	}

	@GetMapping("/resources")
	public String resourcesExample() {
		return "resources";
	}
}