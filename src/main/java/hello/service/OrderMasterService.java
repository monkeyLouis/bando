package hello.service;

import java.util.Date;
import java.util.List;

import hello.domain.OrderMaster;
import hello.domain.dto.StatusVo;

public interface OrderMasterService {
	
	OrderMaster save(OrderMaster om);
	List<OrderMaster> findByDate(Date date,  StatusVo status);
	List<OrderMaster> findAll();
		
}
