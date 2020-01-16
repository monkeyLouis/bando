package hello.service;

import java.util.Date;

import hello.domain.dto.CartVo;

public interface CartService {
	public CartVo getCart(String userId);
	public CartVo updateCart(CartVo cart, String userId);
	public void checkout(CartVo cart, String userId);
	public void cleanAllCartCache(Date cleanTime);
}
