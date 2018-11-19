package hello.util;

import java.util.Date;

import org.joda.time.DateTime;

public class DateUtil {
	
	/**
	 * 取得明天 00:00:00
	 * @param date
	 * @return
	 */
	public static Date getNextDayStart(Date date) {
		return new DateTime(date).plusDays(1).withTimeAtStartOfDay().toDate();
	}
	
	/**
	 * 取得當天 00:00:00
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date) {
		return new DateTime(date).withTimeAtStartOfDay().toDate();
	}
	
	public static void main(String[] args) {
		
		Date today = new Date();
		
		System.out.println(getDayStart(today));
		System.out.println(getNextDayStart(today));
	}
}
