package hello.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="FOOD")
public class Food implements Serializable {

	@Id
	@GenericGenerator(name="sequence_food_id", strategy="hello.domain.generator.FoodGen")
	@GeneratedValue(generator="sequence_food_id")
	@Column(name="FOODID")
	private String foodId;
	
	@ManyToOne
	@JoinColumn(name="SHOPID", nullable=false)
	private Shop shop;
	
	@Column(name="F_PRICE")
	private Integer f_price;
	
	@Column(name="F_NAME")
	private String f_name;
	
	@Column(name="F_COUNT_ACC")
	private Integer f_count_acc;
	
	@Column(name="F_ON")
	private Integer f_on;
	
	public String getFoodId() {
		return foodId;
	}

	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Integer getF_price() {
		return f_price;
	}

	public void setF_price(Integer f_price) {
		this.f_price = f_price;
	}

	public String getF_name() {
		return f_name;
	}

	public void setF_name(String f_name) {
		this.f_name = f_name;
	}

	public Integer getF_count_acc() {
		return f_count_acc;
	}

	public void setF_count_acc(Integer f_count_acc) {
		this.f_count_acc = f_count_acc;
	}

	public Integer getF_on() {
		return f_on;
	}

	public void setF_on(Integer f_on) {
		this.f_on = f_on;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((f_name == null) ? 0 : f_name.hashCode());
		result = prime * result + ((f_price == null) ? 0 : f_price.hashCode());
		result = prime * result + ((foodId == null) ? 0 : foodId.hashCode());
		result = prime * result + ((shop == null) ? 0 : shop.hashCode());
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
		Food other = (Food) obj;
		if (f_name == null) {
			if (other.f_name != null)
				return false;
		} else if (!f_name.equals(other.f_name))
			return false;
		if (f_price == null) {
			if (other.f_price != null)
				return false;
		} else if (!f_price.equals(other.f_price))
			return false;
		if (foodId == null) {
			if (other.foodId != null)
				return false;
		} else if (!foodId.equals(other.foodId))
			return false;
		if (shop == null) {
			if (other.shop != null)
				return false;
		} else if (!shop.equals(other.shop))
			return false;
		return true;
	}
	
}
