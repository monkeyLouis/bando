package hello.domain.dto;

import java.io.Serializable;

public class GoodVo implements Serializable {
	
	private FoodVo food;
	private Integer count;
	private String note;
	
	public FoodVo getFood() {
		return food;
	}
	public void setFood(FoodVo food) {
		this.food = food;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void addGoodCount(int num) {
		this.count += num;
	}
	
	public static class FoodVo implements Serializable {
		private String id;
		private String name;
		private String price;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			FoodVo other = (FoodVo) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
	}
	
}
