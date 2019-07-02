package hello.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.domain.Shop;
import hello.service.ShopService;

@RestController
@RequestMapping("/shop")
public class ShopController {
	
	@Autowired
	private ShopService shopService;
	
	@RequestMapping("/{id}")
	public Shop queryShop(@PathVariable("id") String id) {
		return shopService.findByIdWithList(id);
	}
}
