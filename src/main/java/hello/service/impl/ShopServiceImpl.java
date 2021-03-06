package hello.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.domain.Shop;
import hello.enums.BandoStatus;
import hello.exception.BandoException;
import hello.repository.ShopRepository;
import hello.service.ShopService;
import hello.util.BeanCopierUtil;

@Service("shopSrvc")
public class ShopServiceImpl implements ShopService{
	
	private static final Logger LOG = LoggerFactory.getLogger(ShopServiceImpl.class);

	@Autowired
	private ShopRepository shopRepository;

	@Override
	@Transactional(readOnly=true)
	public List<Shop> findAll() {
		return shopRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Shop findByIdWithList(String shopId) {
		Shop result;
		Optional<Shop> shopOpt = shopRepository.findById(shopId);
		
		if(shopOpt.isPresent()){
			LOG.info("##### Shop menu size: " + shopOpt.get().getFoodList().size() + " #####");
			result = shopOpt.get();
		} else {
			throw new BandoException(BandoStatus.NO_DATA);
		}
		
		return result;
	}

	@Override
	public Shop save(Shop shop) {
		return shopRepository.save(shop);
	}
	
	
	
}
