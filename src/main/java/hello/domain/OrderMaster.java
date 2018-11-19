package hello.domain;

import java.io.Serializable;
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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="ORDER_MASTER")
public class OrderMaster implements Serializable {

	@Id
	@GenericGenerator(name="sequence_om_id", strategy="hello.domain.generator.OrderMasterGen")
	@GeneratedValue(generator="sequence_om_id")
	@Column(name="OMID")
	private String omId;
	
	@ManyToOne
	@JoinColumn(name="MEMID", nullable=false)
	private Member member;
	
	@ManyToOne
	@JoinColumn(name="SCHEDULE_ID", nullable=false)
	private Schedule scheduleId;
	
	@Column(name="OMDATE")
	private Date omDate;
	
	@Column(name="OMSTATUS")
	private Integer omStatus;
	
	@Column(name="OMREMARK")
	private String omRemark;
	
	@OneToMany(mappedBy="omId", targetEntity=OrderDetail.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<OrderDetail> odList;
	
	@Transient
	private Integer omSum = 0;
	
	public String getOmId() {
		return omId;
	}

	public void setOmId(String omId) {
		this.omId = omId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Date getOmDate() {
		return omDate;
	}

	public void setOmDate(Date omDate) {
		this.omDate = omDate;
	}

	public Integer getOmStatus() {
		return omStatus;
	}

	public void setOmStatus(Integer omStatus) {
		this.omStatus = omStatus;
	}

	public String getOmRemark() {
		return omRemark;
	}

	public void setOmRemark(String omRemark) {
		this.omRemark = omRemark;
	}

	public List<OrderDetail> getOdList() {
		return odList;
	}

	public void setOdList(List<OrderDetail> odList) {
		this.odList = odList;
	}

	public Integer getOmSum() {
		return omSum;
	}

	public void setOmSum(Integer omSum) {
		this.omSum = omSum;
	}

	public Schedule getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Schedule scheduleId) {
		this.scheduleId = scheduleId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		result = prime * result + ((omDate == null) ? 0 : omDate.hashCode());
		result = prime * result + ((omId == null) ? 0 : omId.hashCode());
		result = prime * result + ((omRemark == null) ? 0 : omRemark.hashCode());
		result = prime * result + ((omStatus == null) ? 0 : omStatus.hashCode());
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
		OrderMaster other = (OrderMaster) obj;
		if (member == null) {
			if (other.member != null)
				return false;
		} else if (!member.equals(other.member))
			return false;
		if (omDate == null) {
			if (other.omDate != null)
				return false;
		} else if (!omDate.equals(other.omDate))
			return false;
		if (omId == null) {
			if (other.omId != null)
				return false;
		} else if (!omId.equals(other.omId))
			return false;
		if (omRemark == null) {
			if (other.omRemark != null)
				return false;
		} else if (!omRemark.equals(other.omRemark))
			return false;
		if (omStatus == null) {
			if (other.omStatus != null)
				return false;
		} else if (!omStatus.equals(other.omStatus))
			return false;
		return true;
	}
	
}
