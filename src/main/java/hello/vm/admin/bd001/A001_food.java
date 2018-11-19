package hello.vm.admin.bd001;

import java.util.HashMap;
import java.util.Map;

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

import hello.domain.Food;
import hello.domain.Shop;
import hello.service.FoodService;

/**
 * 新增商品
 * @author Louis
 * 2018-06-14
 */

@VariableResolver(DelegatingVariableResolver.class)
public class A001_food {
	private static final Logger LOG = LoggerFactory.getLogger(A001_food.class);
	
	@WireVariable
	private FoodService foodSrvc;
	
	private Food food = new Food();
	
	private Shop shop;
	private Component view;
	
	@Init
	public void init(@ExecutionArgParam("shopSel") Shop shop,
			@ContextParam(ContextType.VIEW) Component view) {
		this.shop = shop;
		this.view = view;
	}
	
	@Command
	public void addFood(){
		LOG.warn("Selected Shop: " + shop.getS_name());
		StringBuffer sb = new StringBuffer();
		sb.append("確認新增商品資訊:\n 品名: ").append(food.getF_name()).append("\n價格: ").append(food.getF_price());
		Messagebox.show(sb.toString(), "新增商品", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
		        new EventListener<Event>() {
			        @Override
			        public void onEvent(Event evt) throws Exception {
				        try {
					        if ((Messagebox.ON_OK).equals(evt.getName())) {
					        	food.setShop(shop);
					        	food.setF_count_acc(0);
					        	food.setF_on(1);
					        	foodSrvc.save(food);
					        	Map<String, Object> args = new HashMap<>();
					        	args.put("shopSel", shop);
					        	
					        	BindUtils.postGlobalCommand(null, null, "refreshFoodListAll", args);
					        	close();
					        	
					        	Messagebox.show("新增成功", "系統訊息", Messagebox.OK , Messagebox.INFORMATION, new EventListener<Event>() {
					        		@Override
					        		public void onEvent(Event evt) throws Exception {
					        			try {
					        				if ((Messagebox.ON_OK).equals(evt.getName())) {
					        					close();
					        				}
					        			} catch (Exception e) {
					        				LOG.error("returnEquiment", e);
					        			}
					        		}
					        	});
					        }
				        } catch (Exception e) {
					        LOG.error("returnEquiment", e);
				        }
			        }
		        });
	}
	
	@Command
	public void close(){
		view.detach();
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}
	
	public Component getView() {
		return view;
	}

	public void setView(Component view) {
		this.view = view;
	}
	
}
