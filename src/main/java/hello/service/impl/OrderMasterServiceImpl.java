package hello.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.domain.OrderMaster;
import hello.domain.dto.StatusVo;
import hello.repository.OrderMasterRepository;
import hello.service.OrderMasterService;
import hello.util.DateUtil;

@Service("orderMasterSrvc")
public class OrderMasterServiceImpl implements OrderMasterService {

	private static final String OMDATE = "omDate";
	private static final String OMSTATUS = "omStatus";
	
	@Autowired
	private OrderMasterRepository orderMasterRepository;
	
	@Override
	public List<OrderMaster> findAll() {
		List<OrderMaster> omList = orderMasterRepository.findAll();
		return sumTheOrder(omList);
	}
	
	@Override
	public List<OrderMaster> findByDate(Date date, StatusVo status){
		
		List<OrderMaster> result = orderMasterRepository.findAll((Root<OrderMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			// 加入複合條件
			Path<Date> omDate = root.get(OMDATE);
			if(date != null) {
				predicates.add(cb.lessThan(omDate, DateUtil.getNextDayStart(date)));
				predicates.add(cb.greaterThanOrEqualTo(omDate, DateUtil.getDayStart(date)));
			}
			if(status.getStatusCode() != null) {
				predicates.add(cb.equal(root.get(OMSTATUS), status.getStatusCode()));
			}
			if(!predicates.isEmpty()){
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
			return cb.conjunction();
			
		});
			
		return sumTheOrder(result);
	}

	@Override
	public OrderMaster save(OrderMaster om) {
		return orderMasterRepository.save(om);
	}
	
	public List<OrderMaster> sumTheOrder(List<OrderMaster> list) {
		
		list.stream().forEach(om -> om.getOdList().stream().forEach(od -> {
			om.setOmSum(om.getOmSum()+ od.getFoodId().getF_price() * od.getOdQua());
		}));
	
		return list;
	}

}
 