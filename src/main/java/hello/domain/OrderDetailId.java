package hello.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderDetailId implements Serializable {
	
	@ManyToOne
	@JoinColumn(name="OMID", nullable=false)
	private OrderMaster omId;
	
	@ManyToOne
	@JoinColumn(name="FOODID")
	private Food foodId;

	public OrderMaster getOmId() {
		return omId;
	}

	public void setOmId(OrderMaster omId) {
		this.omId = omId;
	}

	public Food getFoodId() {
		return foodId;
	}

	public void setFoodId(Food foodId) {
		this.foodId = foodId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((foodId == null) ? 0 : foodId.hashCode());
		result = prime * result + ((omId == null) ? 0 : omId.hashCode());
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
		OrderDetailId other = (OrderDetailId) obj;
		if (foodId == null) {
			if (other.foodId != null)
				return false;
		} else if (!foodId.equals(other.foodId))
			return false;
		if (omId == null) {
			if (other.omId != null)
				return false;
		} else if (!omId.equals(other.omId))
			return false;
		return true;
	}
	
}
