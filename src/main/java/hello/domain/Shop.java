package hello.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="SHOP")
public class Shop implements Serializable {
	
	@Id
	@GenericGenerator(name="sequence_shop", strategy="hello.domain.generator.ShopGen")
	@GeneratedValue(generator="sequence_shop")
	@Column(name="SHOPID")
	private String shopId;
	
	@Column(name="S_NAME")
	private String s_name;
	
	@Column(name="S_PHONE")
	private String s_phone;
	
	@Column(name="S_ADDR")
	private String s_addr;
	
	@OneToMany(mappedBy="shop", fetch=FetchType.LAZY, targetEntity=Food.class)
	private List<Food> foodList;

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public String getS_phone() {
		return s_phone;
	}

	public void setS_phone(String s_phone) {
		this.s_phone = s_phone;
	}

	public String getS_addr() {
		return s_addr;
	}

	public void setS_addr(String s_addr) {
		this.s_addr = s_addr;
	}

	public List<Food> getFoodList() {
		return foodList;
	}

	public void setFoodList(List<Food> foodList) {
		this.foodList = foodList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((s_name == null) ? 0 : s_name.hashCode());
		result = prime * result + ((s_phone == null) ? 0 : s_phone.hashCode());
		result = prime * result + ((shopId == null) ? 0 : shopId.hashCode());
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
		Shop other = (Shop) obj;
		if (s_name == null) {
			if (other.s_name != null)
				return false;
		} else if (!s_name.equals(other.s_name))
			return false;
		if (s_phone == null) {
			if (other.s_phone != null)
				return false;
		} else if (!s_phone.equals(other.s_phone))
			return false;
		if (shopId == null) {
			if (other.shopId != null)
				return false;
		} else if (!shopId.equals(other.shopId))
			return false;
		return true;
	}
	
}
