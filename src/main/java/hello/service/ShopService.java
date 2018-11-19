package hello.service;

import java.util.List;

import hello.domain.Shop;

public interface ShopService {
	
	Shop save(Shop shop);
	List<Shop> findAll();
	Shop findByIdWithList(String shopId);
	
}
