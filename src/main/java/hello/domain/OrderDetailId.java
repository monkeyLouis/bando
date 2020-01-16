package hello.domain;

import java.io.Serializable;

public class OrderDetailId implements Serializable {
	
	private String omId;
	
	private Food foodId;

	public String getOmId() {
		return omId;
	}

	public void setOmId(String omId) {
		this.omId = omId;
	}

	public Food getFoodId() {
		return foodId;
	}

	public void setFoodId(Food foodId) {
		this.foodId = foodId;
	}


	
}
