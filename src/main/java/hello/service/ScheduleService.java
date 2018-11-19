package hello.service;

import java.util.Date;
import java.util.List;

import hello.domain.Schedule;

public interface ScheduleService {
	List<Schedule> findAll();
	List<Schedule> findByDate(Date date);
}
