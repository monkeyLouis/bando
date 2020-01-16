package hello.enums;

/**
 * 
 * @author Louis
 * 訂單付款狀態
 * 2018-07-26
 * 
 */
public enum PayStatus {
	
	/**
	 * 1 : 已付款
	 */
	PAYED(1, "已付款"),
	
	/**
	 * 0 : 未付款
	 */
	NOPAY(0, "未付款");
	
	private Integer payStatusNo;
	
	private String payStatusName;
	
	private PayStatus(Integer payStatusNo, String payStatusName){
		this.payStatusNo = payStatusNo;
		this.payStatusName = payStatusName;
	}
	
	/**
	 * Get 付款狀態碼
	 * 0: 未付款
	 * 1: 已付款
	 * @return payStatusNo
	 */
	public Integer getPayStatusNo() {
		return payStatusNo;
	}

	/**
	 * Get 狀態名稱
	 * @return payStatusName
	 */
	public String getPayStatusName() {
		return payStatusName;
	}
	
}
