package hello.vm.admin.bd003;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModelList;

import hello.domain.Food;
import hello.domain.OrderDetail;
import hello.domain.Schedule;
import hello.domain.dto.OrderSummaryVo;
import hello.service.ScheduleService;

/**
 * 訂單總結 ViewModel
 * @author Louis
 * 2018-07-27
 */

@VariableResolver(DelegatingVariableResolver.class)
public class Q003 {
	
	private static final Logger LOG = LoggerFactory.getLogger(Q003.class);
	
	@WireVariable
	private ScheduleService scheduleSrvc;
	
	private Date dateSelected;
	
	private hello.domain.Schedule scheduleSelected;
	
	private OrderSummaryVo orderSummarySelected;
	
	private ListModelList<Schedule> scheduleList = new ListModelList<>();
	
	private ListModelList<OrderSummaryVo> orderSummaryList = new ListModelList<>();
	
	private ListModelList<String> bookerList = new ListModelList<>();
	
	@Init
	public void init() {
		dateSelected = new Date();
		scheduleList.addAll(scheduleSrvc.findAll());
	}
	
	@Command
	@NotifyChange({"scheduleList", "scheduleSelected", "orderSummarySelected"})
	public void query(){
		scheduleSelected = null;
		orderSummarySelected = null;
		scheduleList.clear();
		scheduleList.addAll(scheduleSrvc.findByDate(dateSelected));
		
	}
	
	@Command
	@NotifyChange({"orderSummaryList", "scheduleSelected", "orderSummarySelected"})
	public void showOrderSummary(@BindingParam("schelSelected") Schedule scheduleSelected) {
		this.orderSummarySelected = null;
		this.scheduleSelected = scheduleSelected;
		Map<Food ,List<OrderDetail>> foodMap = scheduleSelected.getOrderMasterListOfDay().stream()
				.flatMap(om -> om.getOdList().stream())
				.collect(groupingBy(OrderDetail::getFoodId));
		
		List<OrderSummaryVo> odsList = sumTotalQuantityOfFood(foodMap);

		orderSummaryList.clear();
		orderSummaryList.addAll(odsList);
	}
	
	@Command
	@NotifyChange({"bookerList", "orderSummarySelected"})
	public void showBooker(@BindingParam("orderSummarySelected") OrderSummaryVo odrSmVo) {
		this.orderSummarySelected = odrSmVo;
		bookerList.clear();
		bookerList.addAll(odrSmVo.getBookerList());
	}
	
	public List<OrderSummaryVo> sumTotalQuantityOfFood(Map<Food ,List<OrderDetail>> foodMap) {
		List<OrderSummaryVo> result = new ArrayList<>();
		
		foodMap.keySet().stream().forEach(food -> {
			OrderSummaryVo element = new OrderSummaryVo();
			int sum = foodMap.get(food).stream().collect(summingInt(OrderDetail::getOdQua));
			element.setQuantity(sum);
			element.setFood(food);
			foodMap.get(food).stream().forEach(od -> {
				element.addBooker(od.getOmId().getMember().getMemName());
			});
			result.add(element);
		});
		
		return result;
	}

	public Date getDateSelected() {
		return dateSelected;
	}

	public void setDateSelected(Date dateSelected) {
		this.dateSelected = dateSelected;
	}

	public ListModelList<Schedule> getScheduleList() {
		return scheduleList;
	}

	public void setScheduleList(ListModelList<Schedule> scheduleList) {
		this.scheduleList = scheduleList;
	}

	public ListModelList<OrderSummaryVo> getOrderSummaryList() {
		return orderSummaryList;
	}

	public void setOrderSummaryList(ListModelList<OrderSummaryVo> orderSummaryList) {
		this.orderSummaryList = orderSummaryList;
	}

	public Schedule getScheduleSelected() {
		return scheduleSelected;
	}

	public void setScheduleSelected(Schedule scheduleSelected) {
		this.scheduleSelected = scheduleSelected;
	}

	public OrderSummaryVo getOrderSummarySelected() {
		return orderSummarySelected;
	}

	public void setOrderSummarySelected(OrderSummaryVo orderSummarySelected) {
		this.orderSummarySelected = orderSummarySelected;
	}

	public ListModelList<String> getBookerList() {
		return bookerList;
	}

	public void setBookerList(ListModelList<String> bookerList) {
		this.bookerList = bookerList;
	}
	
}
