package hello.enums;

/**
 * @author Louis
 * 商品上下架狀態
 * 2018-06-13
 */
public enum FoodStatus {
	
	/*上架*/
	ONSHELF(1, true, "上架"),
	/*下架*/
	OFFSHELF(0, false, "下架");
	
	private Integer statusNo;
	
	private boolean isSell;
	
	private String statusName;
	
	private FoodStatus(Integer statusNo, Boolean isSell, String statusName) {
		this.statusNo = statusNo;
		this.isSell = isSell;
		this.statusName = statusName;
	}

	/**
	 * Get 狀態碼
	 * @return statusNo
	 */
	public Integer getStatusNo() {
		return statusNo;
	}

	/**
	 * Get 狀態布林(T:上架 F:下架)
	 * @return isSell
	 */
	public boolean isSell() {
		return isSell;
	}

	/**
	 * Get 狀態名稱
	 * @return statusName
	 */
	public String getStatusName() {
		return statusName;
	}
	
}
