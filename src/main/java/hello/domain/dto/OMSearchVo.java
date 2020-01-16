package hello.domain.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import hello.enums.BandoStatus;
import hello.exception.BandoException;

public class OMSearchVo implements Serializable {
	
	private String yearAndMonth;
	private Integer payStatus;
	
	public String getYearAndMonth() {
		return yearAndMonth;
	}
	public void setYearAndMonth(String yearAndMonth) {
		this.yearAndMonth = yearAndMonth;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	
	public void checkForm() {
		if (!StringUtils.isEmpty(yearAndMonth)) {
			if (yearAndMonth.length() != 6) {
				throw new BandoException(BandoStatus.INPUT_ERROR);
			}
		}
	}
	
	public Date toDate() throws Exception {
		String yearText = StringUtils.substring(yearAndMonth, 0, 4);
		String monthText = StringUtils.substring(yearAndMonth, 4, 6);
		System.out.println("Year: " + yearText + ", Month: " + monthText);
		DateTime datetime = new DateTime(Integer.valueOf(yearText), Integer.valueOf(monthText), 1, 0 ,0, 0);
		return datetime.toDate();
	}
	
}
