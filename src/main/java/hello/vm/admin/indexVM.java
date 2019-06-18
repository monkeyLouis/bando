package hello.vm.admin;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

/**
 * 後台首頁ViewModel
 * @author Louis
 * 2018-06-12
 */

@VariableResolver(DelegatingVariableResolver.class)
public class indexVM {
	private static final Logger LOG = LoggerFactory.getLogger(indexVM.class);
	
	Map<String, String> pages = new HashMap<>();
	private String currentUri;
	
	@Init
	public void init() {

		pages.put("q001", "~./zul/admin/bd001/q001.zul");
		pages.put("q002", "~./zul/admin/bd002/q002.zul");
		pages.put("q003", "~./zul/admin/bd003/q003.zul");
		currentUri = pages.get("q002");
	
	}

	public String getCurrentUri() {
		return currentUri;
	}
	
	@Command
	@NotifyChange("currentUri")
	public void navigate(@BindingParam("page") String page) {
		this.currentUri = pages.get(page);
	}
	
}
