package hello.scheduler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import hello.service.CartService;

@Component
public class ScheduleTask {
	
	@Autowired
	private CartService cartService;
	
	@Scheduled(cron="0 0 * * * ? ")
	public void cleanCartCache() {
		cartService.cleanAllCartCache(new Date());
	}
	
}
