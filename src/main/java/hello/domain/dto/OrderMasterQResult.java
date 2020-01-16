package hello.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import hello.domain.OrderDetail;
import hello.domain.OrderMaster;

public class OrderMasterQResult implements Serializable {

	private Date orderDate;
	private String omId;
	private String shopName;
	private Integer price = 0;
	private Integer status;
	private boolean returnable;
	private List<OrderMasterQSubResult> foods = new ArrayList<>();
	
	public OrderMasterQResult() { }
	
	public OrderMasterQResult(OrderMaster om) {
		this.orderDate = om.getOmDate();
		this.omId = om.getOmId();
		this.shopName = om.getScheduleId().getTheShopOfDay().getS_name();
		this.status = om.getOmStatus();
		for(OrderDetail od : om.getOdList()) {
			OrderMasterQSubResult subResult = new OrderMasterQSubResult(od);
			foods.add(subResult);
			this.price += subResult.getPrice();
		}
		this.returnable = om.getScheduleId().getEndDate().after(new Date());
	}
	
	public String getOmId() {
		return omId;
	}
	public void setOmId(String omId) {
		this.omId = omId;
	}
	public String getOrderDate() {
		DateTime datetime = new DateTime(orderDate);
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy年MM月dd日 HH:mm");
		return dtf.print(datetime);
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<OrderMasterQSubResult> getFoods() {
		return foods;
	}
	public void setFoods(List<OrderMasterQSubResult> foods) {
		this.foods = foods;
	}
	public void addFoods(OrderMasterQSubResult food) {
		this.foods.add(food);
	}
	public boolean isReturnable() {
		return returnable;
	}
	public void setReturnable(boolean returnable) {
		this.returnable = returnable;
	}
	
}
