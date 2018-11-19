package hello.vm.admin.bd001;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import hello.domain.Food;
import hello.domain.Shop;
import hello.enums.FoodStatus;
import hello.service.FoodService;
import hello.service.ShopService;

@VariableResolver(DelegatingVariableResolver.class)
public class Q001 {
	private static final Logger LOG = LoggerFactory.getLogger(Q001.class);
	private static final String FOODSEL = "foodSel";
	private static final String INDEX = "index";
	private static final String SHOPSEL = "shopSel";
	private static final String MODE = "mode";

	@WireVariable
	private ShopService shopSrvc;
	
	@WireVariable
	private FoodService foodSrvc;
	
	private ListModelList<Shop> shopListModel = new ListModelList<>();
	
	private Shop shopSel;
	
	private ListModelList<Food> foodListModel = new ListModelList<>();
	
	@Init
	public void init() {
		shopListModel.addAll(shopSrvc.findAll());
		shopSel = shopListModel.get(0);
		query();	
	}
	
	@Command
	@NotifyChange("foodListModel")
	public void query(){
		foodListModel.clear();
		foodListModel.addAll(shopSrvc.findByIdWithList(shopSel.getShopId()).getFoodList());
	}
	
	@Command
	public void editFood(@BindingParam("food") Food food){
		Integer index = foodListModel.indexOf(food);
		Map<String, Object> args = new HashMap<>();
		args.put(FOODSEL, food);
		args.put(INDEX, index);
		Executions.createComponents("~./zul/admin/bd001/u001.zul", null, args);
	}
	
	@Command
	public void editShop(){
		Map<String, Object> args = new HashMap<>();
		Integer index = shopListModel.indexOf(shopSel);
		args.put(SHOPSEL, shopSel);
		args.put(MODE, false);
		args.put(INDEX, index);
		Executions.createComponents("~./zul/admin/bd001/a001_shop.zul", null, args);
	}
	
	@Command
	public void addShop(){
		Executions.createComponents("~./zul/admin/bd001/a001_shop.zul", null, null);
	}
	
	@Command
	public void addFood(){
		Map<String, Object> args = new HashMap<>();
		args.put(SHOPSEL, shopSel);
		Executions.createComponents("~./zul/admin/bd001/a001_food.zul", null, args);
	}
	
	@Command
	@NotifyChange("foodListModel")
	public void changeStatus(@BindingParam("food") Food food){
		
		StringBuilder sb = new StringBuilder();
		sb.append("確認將 ").append(food.getF_name()).append(" ");
		if(FoodStatus.ONSHELF.getStatusNo().equals(food.getF_on())) {
			sb.append(FoodStatus.OFFSHELF.getStatusName()).append("?");
		} else {
			sb.append(FoodStatus.ONSHELF.getStatusName()).append("?");
		}
		
		Messagebox.show(sb.toString(), "商品上下架", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
		        new EventListener<Event>() {
			        @Override
			        public void onEvent(Event evt) throws Exception {
				        try {
					        if ((Messagebox.ON_OK).equals(evt.getName())) {
						        Integer index = foodListModel.indexOf(food);
						        if(FoodStatus.ONSHELF.getStatusNo().equals(food.getF_on())){
						        	food.setF_on(FoodStatus.OFFSHELF.getStatusNo());
						        } else {
						        	food.setF_on(FoodStatus.ONSHELF.getStatusNo());
						        }
					        	foodSrvc.save(food);
					        	foodListModel.set(index, food);
					        }
				        } catch (Exception e) {
					        LOG.error("returnEquiment", e);
				        }
			        }
		        });
	}
	
	@GlobalCommand
	@NotifyChange("foodListModel")
	public void refreshFoodListAll(@BindingParam("shopSel") Shop shop) {
		foodListModel.clear();
		foodListModel.addAll(shopSrvc.findByIdWithList(shop.getShopId()).getFoodList());
	}
	
	@GlobalCommand
	@NotifyChange("foodListModel")
	public void refreshFoodListOne(@BindingParam("index") Integer index, @BindingParam("food") Food food){
		foodListModel.set(index, food);
	}
	
	@GlobalCommand
	@NotifyChange({"shopListModel", "shopSel"})
	public void refreshShopList(@BindingParam("shopSel")Shop shop, @BindingParam("index") Integer index) {
		if(index==null) {
			shopListModel.add(shop);
		} else {
			shopListModel.set(index,shop);
			shopSel = shop;
		}
	}

	public ListModelList<Shop> getShopListModel() {
		return shopListModel;
	}

	public void setShopListModel(ListModelList<Shop> shopListModel) {
		this.shopListModel = shopListModel;
	}

	public Shop getShopSel() {
		return shopSel;
	}

	public void setShopSel(Shop shopSel) {
		this.shopSel = shopSel;
	}

	public ListModelList<Food> getFoodListModel() {
		return foodListModel;
	}

	public void setFoodListModel(ListModelList<Food> foodListModel) {
		this.foodListModel = foodListModel;
	}
	
}
