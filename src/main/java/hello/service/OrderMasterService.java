package hello.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import hello.domain.OrderMaster;
import hello.domain.dto.OMSearchVo;
import hello.domain.dto.StatusVo;

public interface OrderMasterService {
	
	OrderMaster save(OrderMaster om);
	List<OrderMaster> findByDate(Date date,  StatusVo status);
	List<OrderMaster> findAll();
	Page<OrderMaster> findByCondition(OMSearchVo vo, String username, int activePage);
	void delete(OrderMaster om);
	void clientDelete(String omId);
		
}
