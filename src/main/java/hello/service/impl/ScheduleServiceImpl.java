package hello.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.domain.Schedule;
import hello.repository.ScheduleRepository;
import hello.service.ScheduleService;
import hello.util.DateUtil;

@Service("scheduleSrvc")
public class ScheduleServiceImpl implements ScheduleService {
	
	private static final String ENDDATE = "endDate";
	private static Logger LOG = LoggerFactory.getLogger(ScheduleServiceImpl.class);
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Schedule> findAll() {
		List<Schedule> result = scheduleRepository.findAllByOrderByEndDateDesc();
		result.forEach(r -> LOG.info("The num of list : {}", r.getOrderMasterListOfDay().size()));
		return this.calculateTotalPriceAndTotalQuantity(result);
	}

	@Override
	public List<Schedule> findByDate(Date date) {
		List<Schedule> result = scheduleRepository.findAll((Root<Schedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			// 加入複合條件
			Path<Date> scheduleDate = root.get(ENDDATE);
			if(date != null) {
				predicates.add(cb.lessThan(scheduleDate, DateUtil.getNextDayStart(date)));
				predicates.add(cb.greaterThanOrEqualTo(scheduleDate, DateUtil.getDayStart(date)));
			}
			
			query.orderBy(cb.desc(root.get(ENDDATE)));
			
			if(!predicates.isEmpty()){
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			return cb.conjunction();
		});
		
		return calculateTotalPriceAndTotalQuantity(result);
	}
	
	public List<Schedule> calculateTotalPriceAndTotalQuantity(List<Schedule> list){
		
		list.stream().forEach(schedule -> {
			schedule.getOrderMasterListOfDay().stream()
			.flatMap(om -> om.getOdList().stream())
			.forEach(od -> {
				schedule.setTotalQuantity(schedule.getTotalQuantity() + od.getOdQua());
				int subtotal = od.getFoodId().getF_price()*od.getOdQua();
				schedule.setTotalPrice(schedule.getTotalPrice() + subtotal);
			});
		});
		
		return list;
	}
	
}
