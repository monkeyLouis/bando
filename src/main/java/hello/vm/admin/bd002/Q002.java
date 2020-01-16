package hello.vm.admin.bd002;

import static hello.enums.PayStatus.NOPAY;
import static hello.enums.PayStatus.PAYED;

import java.util.Date;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import hello.domain.OrderMaster;
import hello.domain.dto.StatusVo;
import hello.service.OrderMasterService;
import hello.util.BandoUtil;

/**
 * 便當付款管理 ViewModel
 * @author Louis
 * 2018-06-13
 */

@VariableResolver(DelegatingVariableResolver.class)
public class Q002 {
	
	private static final Logger LOG = LoggerFactory.getLogger(Q002.class);
	
	@WireVariable
	private OrderMasterService orderMasterSrvc;
	
	private OrderMaster omSel;
	
	private Date dateSelected;
	
	private ListModelList<StatusVo> statusList;
	
	private StatusVo statusSelected;
	
	private ListModelList<OrderMaster> omListModel = new ListModelList<>();
	
	@Init
	public void init() {
		statusList = new ListModelList<StatusVo>(BandoUtil.getPayStatus(true));
		statusSelected = statusList.get(0);
		omListModel.addAll(orderMasterSrvc.findAll());
		LOG.info("######## OrderMaster Size: {}", orderMasterSrvc.findAll().size());
	}

	@Command
	@NotifyChange("omSel")
	public void showOmInfo(@BindingParam("omSel") OrderMaster om) {
		omSel = om;
		LOG.info("######## in showOmInfo ########");
	}
	
	@Command
	@NotifyChange({"omListModel","omSel"})
	public void updatePay(@BindingParam("omTarget") OrderMaster om) {
		Integer index = omListModel.indexOf(om);
		if(NOPAY.getPayStatusNo().equals(om.getOmStatus())) {
			om.setOmStatus(1);
		} else if(PAYED.getPayStatusNo().equals(om.getOmStatus())){
			om.setOmStatus(0);
		}
		orderMasterSrvc.save(om);
		omSel = om;
		omListModel.set(index, om);
	}
	
	@Command
	@NotifyChange({"omListModel","omSel"})
	public void deletePay(@BindingParam("omTarget") OrderMaster om) {
		StringBuilder sb = new StringBuilder();
		sb.append("確認刪除訂單:\n 訂購日期: ")
		  .append(timeStr(om.getOmDate()))
		  .append("\n訂購者: ")
		  .append(om.getMember().getName());
		Messagebox.show(sb.toString(), "刪除訂單", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
		        new EventListener<Event>() {
			        @Override
			        public void onEvent(Event evt) throws Exception {
				        try {
					        if ((Messagebox.ON_OK).equals(evt.getName())) {

					    		orderMasterSrvc.delete(om);
					    		omSel = null;
					    		omListModel.remove(om);
					        	
					        	Messagebox.show("刪除成功", "系統訊息", Messagebox.OK , Messagebox.INFORMATION, new EventListener<Event>() {
					        		@Override
					        		public void onEvent(Event evt) throws Exception {
					        			try {
					        				if ((Messagebox.ON_OK).equals(evt.getName())) {
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
	@NotifyChange({"omListModel", "omSel"})
	public void query(){
		doQuery();
	}
	
	public void doQuery() {
		try{
			omSel = null;
			omListModel.clear();
			omListModel.addAll(orderMasterSrvc.findByDate(dateSelected, statusSelected));
		} catch (Exception e) {
			LOG.error("doQuery", e);
		}
	}
	
	public OrderMaster getOmSel() {
		return omSel;
	}

	public void setOmSel(OrderMaster omSel) {
		this.omSel = omSel;
	}

	public ListModelList<OrderMaster> getOmListModel() {
		return omListModel;
	}

	public void setOmListModel(ListModelList<OrderMaster> omListModel) {
		this.omListModel = omListModel;
	}

	public Date getDateSelected() {
		return dateSelected;
	}

	public void setDateSelected(Date dateSelected) {
		this.dateSelected = dateSelected;
	}

	public ListModelList<StatusVo> getStatusList() {
		return statusList;
	}

	public void setStatusList(ListModelList<StatusVo> statusList) {
		this.statusList = statusList;
	}

	public StatusVo getStatusSelected() {
		return statusSelected;
	}

	public void setStatusSelected(StatusVo statusSelected) {
		this.statusSelected = statusSelected;
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
