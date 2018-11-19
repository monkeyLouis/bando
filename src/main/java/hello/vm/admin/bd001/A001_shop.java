package hello.vm.admin.bd001;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Messagebox;

import hello.domain.Shop;
import hello.service.ShopService;
import hello.vm.validator.AddShopValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class A001_shop {
	private static final Logger LOG = LoggerFactory.getLogger(A001_shop.class);
	
	@WireVariable
	private ShopService shopSrvc;
	
	private Shop shop = new Shop();
	
	private Component view;
	
	private Integer index;
	
	private AddShopValidator validator;
	
	/**
	 *	true: 新增 
	 * 	false: 修改
	 */
	private Boolean mode = true;
	
	@Init
	public void init(@ExecutionArgParam("shopSel") Shop shop,
			@ExecutionArgParam("index") Integer index,
			@ExecutionArgParam("mode") Boolean mode,
			@ContextParam(ContextType.VIEW) Component view) {
		this.view = view;
		if(mode != null && !mode){
			this.mode = mode;
			this.index = index;
			this.shop = shop;
		}
		validator = new AddShopValidator();
	}
	
	@Command
	public void addShop() {
		StringBuffer sb = new StringBuffer();
		sb.append("確認").append( mode ? "新增":"修改").append("店家資訊:\n 店名: ").append(shop.getS_name()).append("\n電話: ").append(shop.getS_phone()).append("\n地址: ");
		Optional<String> addrOpt = Optional.ofNullable(shop.getS_addr());
		sb.append(addrOpt.orElse("無地址資訊"));
		Messagebox.show(sb.toString(), mode ? "新增":"修改", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
		        new EventListener<Event>() {
			        @Override
			        public void onEvent(Event evt) throws Exception {
				        try {
					        if ((Messagebox.ON_OK).equals(evt.getName())) {
					        	shopSrvc.save(shop);
					        	
					        	Map<String, Object> args = new HashMap<>();
					        	
					        	if(!mode)
					        		args.put("index", index);
					        	
					        	args.put("shopSel", shop);
				        		BindUtils.postGlobalCommand(null, null, "refreshShopList", args);
					        	close();
					        	
					        	Messagebox.show(mode ? "新增店家成功":"修改店家成功", "系統訊息", Messagebox.OK , Messagebox.INFORMATION, new EventListener<Event>() {
					        		@Override
					    		    public void onEvent(Event evt) throws Exception {
					        			try {
					        				if ((Messagebox.ON_OK).equals(evt.getName())) {
					        					close();
					    			        }
					        			} catch (Exception e) {
					        				LOG.error("Confirm add shop: ", e);
					        			}
					    		    }
					    		});
					        } 
				        } catch (Exception e) {
					        LOG.error("return Q001: ", e);
				        } 
			        }
		        });
		
	}

	@Command
	public void close() {
		view.detach();
	}
	
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Boolean getMode() {
		return mode;
	}

	public void setMode(Boolean mode) {
		this.mode = mode;
	}

	public AddShopValidator getValidator() {
		return validator;
	}

	public void setValidator(AddShopValidator validator) {
		this.validator = validator;
	}
		
}
