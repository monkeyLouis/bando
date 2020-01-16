package hello.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.domain.Food;
import hello.domain.Member;
import hello.domain.OrderDetail;
import hello.domain.OrderMaster;
import hello.domain.Schedule;
import hello.domain.dto.CartVo;
import hello.domain.dto.GoodVo;
import hello.enums.BandoStatus;
import hello.exception.BandoException;
import hello.repository.OrderDetailRepository;
import hello.repository.OrderMasterRepository;
import hello.repository.ScheduleRepository;
import hello.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	private static final Logger LOG = LoggerFactory.getLogger(CartServiceImpl.class); 
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private OrderMasterRepository orderMasterRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Override
	@Cacheable(value="carts", key="#userId")
	public CartVo getCart(String userId) {
		CartVo cart = new CartVo();
		cart.setContents(new ArrayList<>());
		return cart;
	}

	@Override
	@CachePut(value="carts", key="#userId")
	public CartVo updateCart(CartVo cart, String userId) {
		return cart;
	}

	@Override
	@CacheEvict(value="carts", key="#userId")
	@Transactional
	public void checkout(CartVo cart, String userId) {
		String scheduleId = cart.getScheduleId();
		OrderMaster om = new OrderMaster();
		Member member = entityManager.getReference(Member.class, userId);
		Optional<Schedule> scheduleOpt = scheduleRepository.findById(scheduleId);
		if(!scheduleOpt.isPresent()) {
			throw new BandoException(BandoStatus.INPUT_ERROR);
		}
		Schedule schedule = scheduleOpt.get();
		if(isExpired(schedule.getEndDate())) {
			throw new BandoException(BandoStatus.EXPIRED);
		}
		om.setMember(member);
		om.setOmStatus(0);
		om.setScheduleId(schedule);
		om.setOmDate(new Date());
		OrderMaster omInDB = orderMasterRepository.save(om);
		List<GoodVo> goods = cart.getContents();
		for(GoodVo good : goods) {
			Food food = entityManager.getReference(Food.class, good.getFood().getId());
			OrderDetail od = new OrderDetail();
			od.setFoodId(food);
			od.setOdQua(good.getCount());
			od.setOdRemark(good.getNote());
			od.setOmId(omInDB);
			orderDetailRepository.save(od);
		}
	}
	
	private boolean isExpired(Date endTime) {
		Date now = new Date();
		return now.after(endTime);
	}

	@Override
	@CacheEvict(value="carts", allEntries=true)
	public void cleanAllCartCache(Date cleanTime) {
		LOG.info("===== Cache has been clean @{}", cleanTime);
	}

}
