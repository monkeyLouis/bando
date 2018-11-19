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
import hello.service.FoodService;

/**
 * 編輯商品 
 * @author Louis
 * 2018-06-13
 */
@VariableResolver(DelegatingVariableResolver.class)
public class U001 {

	private static final Logger LOG = LoggerFactory.getLogger(U001.class);
	
	@WireVariable
	private FoodService foodSrvc;
	
	private Component view;
	private Integer index;
	private Food food;
	
	@Init
	public void init(@ExecutionArgParam("foodSel") Food food,
			@ExecutionArgParam("index") Integer index,
			@ContextParam(ContextType.VIEW) Component view) {
		this.food = food;
		this.view = view;
		this.index = index;
	}
	
	@Command
	public void update(){
		StringBuffer sb = new StringBuffer();
		sb.append("確認更改資訊:\n 品名: ").append(food.getF_name()).append("\n價格: ").append(food.getF_price());
		Messagebox.show(sb.toString(), "編輯商品資訊", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
		        new EventListener<Event>() {
			        @Override
			        public void onEvent(Event evt) throws Exception {
				        try {
					        if ((Messagebox.ON_OK).equals(evt.getName())) {
					        	foodSrvc.save(food);
					        	Map<String, Object> args = new HashMap<>();
					        	args.put("food", food);
					        	args.put("index", index);
					        	BindUtils.postGlobalCommand(null, null, "refreshFoodList", args);
					        	close();
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
	
}
