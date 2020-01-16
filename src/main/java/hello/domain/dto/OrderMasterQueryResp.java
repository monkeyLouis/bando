package hello.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hello.domain.OrderMaster;

public class OrderMasterQueryResp implements Serializable {
	
	private Long totalSize;
	private Integer totalPage;
	private List<OrderMasterQResult> contents = new ArrayList<>();
	
	public OrderMasterQueryResp() { }
	
	public OrderMasterQueryResp(List<OrderMaster> oms, long totalSize, int totalPage) {
		this.totalSize = totalSize;
		this.totalPage = totalPage;
		for(OrderMaster om : oms) {
			addResult(new OrderMasterQResult(om));
		}
	}
	
	public Long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}
	public List<OrderMasterQResult> getContents() {
		return contents;
	}
	public void setContents(List<OrderMasterQResult> contents) {
		this.contents = contents;
	}
	public void addResult(OrderMasterQResult result) {
		contents.add(result);
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
}
