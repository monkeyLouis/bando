package hello.util;

import static hello.enums.PayStatus.*;

import java.util.ArrayList;
import java.util.List;

import hello.domain.dto.StatusVo;

public abstract class BandoUtil {
	
	private static final String ALL = "全部";
	
	/**
	 * Get Pay Status
	 * (needAll: Need to add "ALL" Status ?)
	 * @param needAll
	 * @return
	 */
	public static List<StatusVo> getPayStatus(boolean needAll) {
		List<StatusVo> result = new ArrayList<>();
		if (needAll) {
			result.add(StatusVo.generateStatusVo(ALL, null));
		}
		result.add(StatusVo.generateStatusVo(NOPAY.getPayStatusName(), NOPAY.getPayStatusNo()));
		result.add(StatusVo.generateStatusVo(PAYED.getPayStatusName(), PAYED.getPayStatusNo()));
		
		return result;
	}
	
	
}
