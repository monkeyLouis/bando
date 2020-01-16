package hello.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hello.domain.Schedule;
import hello.enums.PayStatus;
import hello.repository.ScheduleRepository;
import hello.service.ScheduleService;
import hello.util.DateUtil;

@Service("scheduleSrvc")
public class ScheduleServiceImpl implements ScheduleService {
	
	private static final String STATUS = "status";
	private static final String STARTDATE = "startDate";
	private static final String ENDDATE = "endDate";
	private static Logger LOG = LoggerFactory.getLogger(ScheduleServiceImpl.class);
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Schedule> findAll() {
		List<Schedule> result = scheduleRepository.findAllByOrderByEndDateDesc();
		result = result.stream()
					   .filter(sche -> StringUtils.equals(sche.getStatus(), "0"))
					   .collect(Collectors.toList());
		result.forEach(r -> LOG.info("The num of list : {}", r.getOrderMasterListOfDay().size()));
		return this.calculateTotalPriceAndTotalQuantity(result);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Schedule> findByDate(Date date) {
		Date dateStart = DateUtil.getDayStart(date);
		Date dateEnd = DateUtil.getNextDayStart(date);
		return findByDate(dateStart, dateEnd);
	}
	
	@Override
	public List<Schedule> findByDate(Date dateStart, Date dateEnd) {
		List<Schedule> result = scheduleRepository.findAll((Root<Schedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			// 加入複合條件
			Path<Date> scheduleDate = root.get(ENDDATE);
			if(dateStart != null && dateEnd != null) {
				predicates.add(cb.lessThan(scheduleDate, dateEnd));
				predicates.add(cb.greaterThanOrEqualTo(scheduleDate, dateStart));
			}
			
			query.orderBy(cb.desc(root.get(ENDDATE)));
			
			if(!predicates.isEmpty()){
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			return cb.conjunction();
		});
		result.forEach(r -> LOG.info("The num of list : {}", r.getOrderMasterListOfDay().size()));
		return calculateTotalPriceAndTotalQuantity(result);
	}
	
	@Override
	public List<Schedule> findByTime(Date date, int min) {
		Date dateAfterMins = DateUtils.addMinutes(date, min);
		Date dateBeforeMins = DateUtils.addMinutes(date, -10);
		
		return findByTimeBetween(dateAfterMins, dateBeforeMins);
	}
	
	@Override
	public List<Schedule> findByTimeBetween(Date dateStart, Date dateEnd) {
		return scheduleRepository.findAll((Root<Schedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			// 加入複合條件
			Path<Date> scheduleStartDate = root.get(STARTDATE);
			Path<Date> scheduleEndDate = root.get(ENDDATE);
			Path<Date> scheduleStatus = root.get(STATUS);
			
			predicates.add(cb.lessThan(scheduleStartDate, dateStart));
			predicates.add(cb.greaterThanOrEqualTo(scheduleEndDate, dateEnd));
			predicates.add(cb.equal(scheduleStatus, "0"));
			
			query.orderBy(cb.desc(root.get(ENDDATE)));
			
			if(!predicates.isEmpty()){
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			return cb.conjunction();
		});
	}
	
	private List<Schedule> calculateTotalPriceAndTotalQuantity(List<Schedule> list){
		
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
	
	public Schedule saveSchedule(Schedule schedule) {
		return scheduleRepository.save(schedule);
	}

}
