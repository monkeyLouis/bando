package hello.domain.dto;

import java.io.Serializable;

import hello.domain.OrderDetail;

public class OrderMasterQSubResult implements Serializable {
	
	private String foodName;
	private Integer count;
	private Integer price;
	
	public OrderMasterQSubResult() { }
	
	public OrderMasterQSubResult(OrderDetail detail) {
		this.foodName = detail.getFoodId().getF_name();
		this.count = detail.getOdQua();
		this.price = detail.getFoodId().getF_price() * detail.getOdQua();
	}
	
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
}
