package hello.vm.admin.bd003;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import hello.domain.Schedule;
import hello.domain.Shop;
import hello.service.ScheduleService;
import hello.service.ShopService;

/**
 * 新增訂購行程
 * @author Louis
 * 2018-06-14
 */

@VariableResolver(DelegatingVariableResolver.class)
public class A003 {
	private static final Logger LOG = LoggerFactory.getLogger(A003.class);
	
	@WireVariable
	private ScheduleService scheduleSrvc;
	
	@WireVariable
	private ShopService shopSrvc;
	
	private Schedule schedule = new Schedule();
	
	private Date startDate = new Date();
	
	private Date endDate = afterTime(60);
	
	private ListModelList<Shop> shopListModel = new ListModelList<>();
	
	private Component view;
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		shopListModel.addAll(shopSrvc.findAll());
		startDate.setMinutes(0);
		this.view = view;
	}
	
	@Command
	public void addSchedule(){
		StringBuffer sb = new StringBuffer();
		sb.append("確認新增訂購行程:\n 訂購商家: ")
		  .append(schedule.getTheShopOfDay().getS_name())
		  .append("\n開始時間: ")
		  .append(timeStr(startDate))
		  .append("\n結束時間: ")
		  .append(timeStr(endDate));
		Messagebox.show(sb.toString(), "新增訂購行程", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
		        new EventListener<Event>() {
			        @Override
			        public void onEvent(Event evt) throws Exception {
				        try {
					        if ((Messagebox.ON_OK).equals(evt.getName())) {
//					        	food.setShop(shop);
//					        	food.setF_count_acc(0);
//					        	food.setF_on(1);
					        	schedule.setStartDate(startDate);
					        	schedule.setEndDate(endDate);
					        	schedule.setStatus("0");
					        	scheduleSrvc.saveSchedule(schedule);
					        	
					        	BindUtils.postGlobalCommand(null, null, "refreshScheduleListAll", null);
					        	close();
					        	
					        	Messagebox.show("新增成功", "系統訊息", Messagebox.OK , Messagebox.INFORMATION, new EventListener<Event>() {
					        		@Override
					        		public void onEvent(Event evt) throws Exception {
					        			try {
					        				if ((Messagebox.ON_OK).equals(evt.getName())) {
					        					close();
					        				}
					        			} catch (Exception e) {
					        				LOG.error("returnEquiment", e);
					        			}
					        		}
					        	});
					        }
				        } catch (Exception e) {
					        LOG.error("returnEquiment", e);
				        }
			        }
		        });
	}
	
	@Command
	public void close(){
		view.detach();
	}
	
	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Component getView() {
		return view;
	}

	public void setView(Component view) {
		this.view = view;
	}

	public ListModelList<Shop> getShopListModel() {
		return shopListModel;
	}

	public void setShopListModel(ListModelList<Shop> shopListModel) {
		this.shopListModel = shopListModel;
	}

	public Date getStartDate() {
		return startDate;
	}

	@NotifyChange({"date","constraintDate"})
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}	
	
	public String getConstraintDate () {
	    return "no empty, after " + new SimpleDateFormat("yyyyMMdd hh:mm").format(getStartDate());
	}
	
	private Date afterTime(int min) {
		DateTime date = DateTime.now().withMinuteOfHour(0);
		return DateUtils.addMinutes(date.toDate(), min);
	}
	
	private String timeStr(Date date) {
		DateTime datetime = new DateTime(date);
		StringBuilder sb = new StringBuilder();
		sb.append(datetime.getYear())
		  .append("年")
		  .append(datetime.getMonthOfYear())
		  .append("月")
		  .append(datetime.getDayOfMonth())
		  .append("日 ")
		  .append(datetime.getHourOfDay())
		  .append(":")
		  .append(datetime.getMinuteOfHour());
		return sb.toString();
	}
	
}
