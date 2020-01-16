package hello.service;

import java.util.Date;
import java.util.List;

import hello.domain.Schedule;

public interface ScheduleService {
	List<Schedule> findAll();
	List<Schedule> findByDate(Date dateStart, Date dateEnd);
	List<Schedule> findByDate(Date date);
	List<Schedule> findByTime(Date date, int min);
	List<Schedule> findByTimeBetween(Date dateStart, Date dateEnd);
	Schedule saveSchedule(Schedule schedule);
}
