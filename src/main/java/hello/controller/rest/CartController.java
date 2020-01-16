package hello.controller.rest;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.domain.dto.CartVo;
import hello.domain.dto.GoodVo;
import hello.domain.rest.RestResponse;
import hello.enums.BandoStatus;
import hello.exception.BandoException;
import hello.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	private static final Logger LOG = LoggerFactory.getLogger(CartController.class);
	
	@Autowired
	private CartService cartService;
	
	@GetMapping(value="")
	public RestResponse<List<GoodVo>> getGoods(Principal principal) {
		String username = principal.getName();
		LOG.info("### User {}, get cart contents ###", username);
		CartVo cart = cartService.getCart(username);
		return new RestResponse<>(BandoStatus.SUCCESS, cart.getContents());
	}
	
	@PostMapping(value="/add/{scheduleId}")
	public RestResponse addToCart(Principal principal, @PathVariable("scheduleId") String scheduleId, @RequestBody GoodVo newGood) {
		String username = principal.getName();
		CartVo cart = cartService.getCart(username);
		cart.setScheduleId(scheduleId);
		List<GoodVo> goods = cart.getContents();
		boolean isExistInCache = false;
		for(GoodVo good : goods) {
			if (good.getFood().equals(newGood.getFood())) {
				good.addGoodCount(newGood.getCount());
				isExistInCache = true;
			}
		}
		if (!isExistInCache) {
			goods.add(newGood);
		}
		cartService.updateCart(cart, username);
		return new RestResponse<>(BandoStatus.SUCCESS);
	}
	
	@PostMapping(value="/update")
	public RestResponse updateGoodInCart(Principal principal, @RequestBody GoodVo newGood) {
		String username = principal.getName();
		CartVo cart = cartService.getCart(username);
		List<GoodVo> goods = cart.getContents();
		boolean isExistInCache = false;
		for(GoodVo good : goods) {
			if (good.getFood().equals(newGood.getFood())) {
				good.setCount(newGood.getCount());
				good.setNote(newGood.getNote());
				isExistInCache = true;
			}
		}
		if (!isExistInCache) {
			throw new BandoException(BandoStatus.INPUT_ERROR);
		}
		cart.setContents(goods);
		cartService.updateCart(cart, username);
		return new RestResponse<>(BandoStatus.SUCCESS);
	}
	
	@PostMapping(value="/delete")
	public RestResponse deleteGoodInCart(Principal principal, @RequestBody GoodVo newGood) {
		String username = principal.getName();
		CartVo cart = cartService.getCart(username);
		List<GoodVo> goods = cart.getContents();
		int index = -1;
		int count = 0;
		for(GoodVo good : goods) {
			if (good.getFood().equals(newGood.getFood())) {
				index = count;
			}
			count++;
		}
		if (index == -1) {
			throw new BandoException(BandoStatus.INPUT_ERROR);
		}
		goods.remove(index);
		cart.setContents(goods);
		cartService.updateCart(cart, username);
		return new RestResponse<>(BandoStatus.SUCCESS);
	}
	
	@PostMapping(value="/checkout")
	public RestResponse checkout(Principal principal) {
		String username = principal.getName();
		CartVo cart = cartService.getCart(username);
		cartService.checkout(cart, username);
		return new RestResponse<>(BandoStatus.SUCCESS);
	}
	
}
