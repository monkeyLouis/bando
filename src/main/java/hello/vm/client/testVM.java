package hello.vm.client;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModelList;

import hello.domain.Member;
import hello.service.MemberService;


/**
 * 首頁View Model
 * @author Louis
 *
 */

@VariableResolver(DelegatingVariableResolver.class)
public class testVM {
	private static final Logger LOG = LoggerFactory.getLogger(testVM.class);
	
	@WireVariable
	private MemberService memSrvc;
	
	private ListModelList<Member> memListModel = new ListModelList<>();
	
	Map<String, String> pages = new HashMap<>();
	private String currentUri;
	
	@Init
	public void init() {
		
		memListModel.addAll(memSrvc.findAll());
		pages.put("q001", "~./zul/admin/bd001/q001.zul");
		currentUri = pages.get("q001");
	
	}

	public ListModelList<Member> getMemListModel() {
		return memListModel;
	}

	public void setMemListModel(ListModelList<Member> memListModel) {
		this.memListModel = memListModel;
	}

	public String getCurrentUri() {
		return currentUri;
	}

	
}
