package hello.controller.rest;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hello.domain.OrderMaster;
import hello.domain.dto.OMSearchVo;
import hello.domain.dto.OrderMasterQueryResp;
import hello.domain.rest.RestResponse;
import hello.enums.BandoStatus;
import hello.exception.BandoException;
import hello.service.OrderMasterService;

@RestController
@RequestMapping("/order")
public class RestOrderMasterController {

	private static final Logger LOG = LoggerFactory.getLogger(RestOrderMasterController.class);
	
	@Autowired
	private OrderMasterService orderMasterService;
	
	@RequestMapping(value="/{page}", method=RequestMethod.POST)
	public RestResponse<OrderMasterQueryResp> query(Principal principal, @PathVariable("page") int currentPage, @RequestBody OMSearchVo vo) {
		String username = principal.getName();
		LOG.info("~~~~~ Username: {}", username);
		Page<OrderMaster> page = orderMasterService.findByCondition(vo, username, currentPage);
		LOG.info("~~~~~ Page.contents size: {}", page.getContent().size());
		if(page.getContent().isEmpty()) {
			throw new BandoException(BandoStatus.NO_DATA);
		}
		OrderMasterQueryResp resp = new OrderMasterQueryResp(page.getContent(), page.getTotalElements(), page.getTotalPages());
		return new RestResponse<>(BandoStatus.SUCCESS, resp);
	}
	
	@RequestMapping(value="/{omId}", method=RequestMethod.DELETE)
	public RestResponse delete(Principal principal, @PathVariable("omId") String omId) {
		LOG.info("===== User:{} delete the order: {} ", principal.getName(), omId);
		orderMasterService.clientDelete(omId);
		return new RestResponse<>(BandoStatus.SUCCESS);
	}
	
	
}
