package hello.controller.rest;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.domain.Schedule;
import hello.domain.rest.RestResponse;
import hello.enums.BandoStatus;
import hello.exception.BandoException;
import hello.service.ScheduleService;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	@RequestMapping(value="")
	public RestResponse<Schedule> queryNearSchedule() {
		List<Schedule> schedules = scheduleService.findByTime(new Date(), 10);
		if(schedules != null && !schedules.isEmpty()) {
			return new RestResponse<>(BandoStatus.SUCCESS, schedules.get(0));
		} else {
			return new RestResponse<>(BandoStatus.NO_DATA);
		}
	}
}
