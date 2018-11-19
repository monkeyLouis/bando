package hello.domain.dto;

import java.util.ArrayList;
import java.util.List;

import hello.domain.Food;

public class OrderSummaryVo {
	
	private Food food;
	private Integer quantity;
	private List<String> bookerList = new ArrayList<>();
	
	public Food getFood() {
		return food;
	}
	public void setFood(Food food) {
		this.food = food;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public List<String> getBookerList() {
		return bookerList;
	}
	public void setBookerList(List<String> bookerList) {
		this.bookerList = bookerList;
	}
	
	public void addBooker(String memberName) {
		bookerList.add(memberName);
	}
	
}
