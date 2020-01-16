package hello.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.domain.OrderDetail;
import hello.domain.OrderMaster;
import hello.domain.dto.OMSearchVo;
import hello.domain.dto.StatusVo;
import hello.enums.BandoStatus;
import hello.exception.BandoException;
import hello.repository.OrderMasterRepository;
import hello.service.OrderMasterService;
import hello.util.DateUtil;

@Service("orderMasterSrvc")
public class OrderMasterServiceImpl implements OrderMasterService {

	private static final Logger LOG = LoggerFactory.getLogger(OrderMasterServiceImpl.class);
	private static final String MEMBER = "member";
	private static final String USERNAME = "username";
	private static final String OMDATE = "omDate";
	private static final String OMSTATUS = "omStatus";
	
	@Autowired
	private OrderMasterRepository orderMasterRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<OrderMaster> findAll() {
		List<OrderMaster> omList = orderMasterRepository.findAll();
		return sumTheOrder(omList);
	}
	
	@Override
	@Transactional(readOnly = true)
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
	
	private List<OrderMaster> sumTheOrder(List<OrderMaster> list) {
		
		for(OrderMaster om : list) {
			int sum = 0;
			for(OrderDetail od : om.getOdList()) {
				sum += od.getFoodId().getF_price() * od.getOdQua();
			}
			om.setOmSum(sum);
		}
	
		return list;
	}

	@Override
	public Page<OrderMaster> findByCondition(OMSearchVo vo, String username, int activePage) {
		return orderMasterRepository.findAll(new ConditionSpecOfOrderMaster(vo, username), PageRequest.of(activePage, 10, Sort.Direction.DESC, "omId"));
	}
	
	public static class ConditionSpecOfOrderMaster implements Specification<OrderMaster> {

		private OMSearchVo vo;
		private String username;
		
		public ConditionSpecOfOrderMaster(OMSearchVo vo, String username) {
			this.vo = vo;
			this.username = username;
		}
		
		@Override
		public Predicate toPredicate(Root<OrderMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
			List<Predicate> predicates = new ArrayList<>();
			
			// 加入複合條件
			predicates.add(cb.equal(root.get(MEMBER).get(USERNAME), username));
			Path<Date> omDate = root.get(OMDATE);

			if(!StringUtils.isEmpty(vo.getYearAndMonth())) {
				Date date;
				try {
					date = vo.toDate();
				} catch (Exception e) {
					LOG.error("!!!!! Exception: {}", e);
					throw new BandoException(BandoStatus.INPUT_ERROR);
				}
				predicates.add(cb.lessThan(omDate, DateUtils.addMonths(date, 1)));
				predicates.add(cb.greaterThanOrEqualTo(omDate, date));
			}
			if(vo.getPayStatus() != null) {
				predicates.add(cb.equal(root.get(OMSTATUS), vo.getPayStatus()));
			}
			if(!predicates.isEmpty()){
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
			return cb.conjunction();
		}
		
	}

	@Override
	public void delete(OrderMaster om) {
		orderMasterRepository.delete(om);
	}

	@Override
	public void clientDelete(String omId) {
		Optional<OrderMaster> omOpt = orderMasterRepository.findById(omId);
		if(!omOpt.isPresent()) {
			throw new BandoException(BandoStatus.NO_DATA);
		}
		OrderMaster orderMaster = omOpt.get();
		Date endDate = orderMaster.getScheduleId().getEndDate();
		if(endDate.before(new Date())) {
			throw new BandoException(BandoStatus.DEL_EXPIRED);
		}
		delete(orderMaster);
	}
}
 