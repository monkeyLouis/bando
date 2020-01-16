package hello.domain;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISOPeriodFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="SCHEDULE")
public class Schedule {
	
	@Id
	@GenericGenerator(name="sequence_sch_id", strategy="hello.domain.generator.ScheduleGen")
	@GeneratedValue(generator="sequence_sch_id")
	@Column(name="UUID")
	private String scheduleId;
	
	@JsonIgnore
	@Column(name="SCHE_START_DATE")
	private Date startDate;
	
	@JsonIgnore
	@Column(name="SCHE_END_DATE")
	private Date endDate;
	
	@Column(name="SCHE_STATUS")
	private String status;
	
	@ManyToOne
	@JoinColumn(name="SHOPID", nullable=false)
	private Shop theShopOfDay;
	
	@JsonIgnore
	@OneToMany(mappedBy="scheduleId", fetch=FetchType.LAZY, targetEntity=OrderMaster.class)
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
	public Long getStartDateMilSec() {
		return startDate.getTime();
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public Long getEndDateMilSec() {
		return endDate.getTime();
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getTWStartTime() {
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.TAIWAN);
		return df.format(startDate);
	}
	public String getTWEndTime() {
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.TAIWAN);
		return df.format(endDate);
	}
	public boolean isOpenOrder() {
		return new Date().after(this.startDate) && new Date().before(this.endDate);
	}
	public boolean isCloseOrder() {
		return new Date().after(this.endDate);
	}
	public String getOrderStatus() {
		if (!isOpenOrder() && !isCloseOrder()) {
			return "1";  // 在訂購開始時間前
		} else if (isOpenOrder() && !isCloseOrder()) {
			return "2";  // 在訂購時間中
		} else if (!isOpenOrder() && isCloseOrder()) {
			return "3";  // 在訂購結束 時間後
		}
		return "";
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
