package hello.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(OrderDetailId.class)
@Table(name="ORDER_DETAIL")
public class OrderDetail implements Serializable {
	
//	@EmbeddedId
//	private OrderDetailId odId;
	
	@Id
	@ManyToOne
	@JoinColumn(name="OMID", nullable=false)
	private OrderMaster omId;
	
	@Id
	@ManyToOne
	@JoinColumn(name="FOODID", nullable=false)
	private Food foodId;
	
	@Column(name="ODQUA")
	private Integer odQua;
	
	@Column(name="ODREMARK")
	private String odRemark;
	
//	public OrderDetailId getOdId() {
//		return odId;
//	}
//
//	public void setOdId(OrderDetailId odId) {
//		this.odId = odId;
//	}

	public Integer getOdQua() {
		return odQua;
	}

	public void setOdQua(Integer odQua) {
		this.odQua = odQua;
	}

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

	public String getOdRemark() {
		return odRemark;
	}

	public void setOdRemark(String odRemark) {
		this.odRemark = odRemark;
	}
	
}
