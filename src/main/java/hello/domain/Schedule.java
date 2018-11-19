package hello.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="SCHEDULE")
public class Schedule {
	
	@Id
	@GenericGenerator(name="sequence_sch_id", strategy="hello.domain.generator.ScheduleGen")
	@GeneratedValue(generator="sequence_sch_id")
	@Column(name="SCHEDULE_ID")
	private String scheduleId;
	
	@Column(name="SCHE_START_DATE")
	private Date startDate;
	
	@Column(name="SCHE_END_DATE")
	private Date endDate;
	
	@ManyToOne
	@JoinColumn(name="SHOPID", nullable=false)
	private Shop theShopOfDay;
	
	@OneToMany(mappedBy="scheduleId", fetch=FetchType.EAGER, targetEntity=OrderMaster.class)
	private List<OrderMaster> orderMasterListOfDay;
	
	@Transient
	private Integer totalQuantity = 0;
	
	@Transient
	private Integer totalPrice = 0;
	
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Shop getTheShopOfDay() {
		return theShopOfDay;
	}
	public void setTheShopOfDay(Shop theShopOfDay) {
		this.theShopOfDay = theShopOfDay;
	}
	public List<OrderMaster> getOrderMasterListOfDay() {
		return orderMasterListOfDay;
	}
	public void setOrderMasterListOfDay(List<OrderMaster> orderMasterListOfDay) {
		this.orderMasterListOfDay = orderMasterListOfDay;
	}
	public Integer getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((scheduleId == null) ? 0 : scheduleId.hashCode());
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
		Schedule other = (Schedule) obj;
		if (scheduleId == null) {
			if (other.scheduleId != null)
				return false;
		} else if (!scheduleId.equals(other.scheduleId))
			return false;
		return true;
	}
	
}
