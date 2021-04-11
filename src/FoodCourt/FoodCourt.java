package FoodCourt;
import java.util.SortedSet;
import java.util.TreeSet;

import Attribute.Address;
import CLI.CliFrame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import Sales.*;
import TechnicalSupport.*;

public class FoodCourt {
 
	private static final int max_kitchen_number = 15;
	private final static int max_food_number = 150;
	private String foodcourt_name;
	private Address address;
	private FoodModification foodmodification;
	private SortedSet<FoodCatalog> foodcatalogs;
	private List<Food> foods;
	private OrderBook orderbook;
	private Set<Kitchen> kitchens;
	private DataManagementAdapter dma;
	
	//접근함수들입니다.
	public int getMaxKitchenNumber() {
		return max_kitchen_number;
	}
	
	public int getMaxFoodNumber() {
		return max_food_number;
	}
	
	public Set<Kitchen> getKitchenSet() {
		return kitchens;
	}
	
	public Kitchen getKichen(FoodCatalog fc) {
		Iterator<Kitchen> seek = this.kitchens.iterator();
		Kitchen A = null;
		
		while(seek.hasNext()) {
			A =seek.next();
			if(A.getFoodCatalog().equals(fc)) 
				return A;	
		}
		return null;
	}
	
	public SortedSet<FoodCatalog> getAllFoodCatalog() {
		return this.foodcatalogs;
	}
	
	public FoodCatalog getFoodCatalog(String kitchen_name) {
		
		Iterator<FoodCatalog> seek = this.foodcatalogs.iterator();
		FoodCatalog A = null;
		
		while(seek.hasNext()) {
			A =seek.next();
			if(A.getKitchen_name().equals(kitchen_name)) 
				return A;	
		}
		return null;
	}
	
	public FoodCatalog getFoodCatalogByFoodName(String food_name) {
		
		FoodCatalog fc = null;
		
		Iterator <FoodCatalog> it = foodcatalogs.iterator();
		FoodCatalog catalogcur = null;
		
		while(it.hasNext()) {
			catalogcur = it.next();
			
			if(catalogcur.getFoodDescription(food_name) != null) {
				return catalogcur;
			}
		}
		
		
		return null;
	}
	//입력받은 음식 이름을 가지는 포함하는 푸드 카탈로그를 반환해야합니다.
	

	public OrderBook getOrderbook() {
		return orderbook;
	}
	
	
	//접근함수를 제외한 함수들입니다.
	public static void main(String[] args) {
		FoodCourt foodcourt = new FoodCourt(null, null);
		foodcourt.loadFoodCourtData();
		CliFrame.showStartMenuUi(foodcourt);
	}
	
	


	public FoodCourt(String foodcourt_name, Address address) {
		
		this.foodcourt_name = foodcourt_name;
		this.address = address;
		this.orderbook = new OrderBook(this);
		FoodCatalog fc;
		Integer itmp;
		this.foodcatalogs = new TreeSet<FoodCatalog>();
		this.foods = new ArrayList<>();
		this.kitchens = new TreeSet<Kitchen>();
		
	}
	
	public void loadFoodCourtData() {
		if(dma == null) dma = new DataManagementAdapter();
		this.dma.loadFoodCourtData(max_food_number, max_kitchen_number, this);
	}
	
	
	

	public void makeFoods(String food_name, Integer quantity, SaleLineitem sl) {

		Iterator<FoodCatalog> seek = foodcatalogs.iterator();
		FoodDescription desc = null;
		FoodCatalog temp = null;
		
		while(seek.hasNext()) {
			temp = seek.next();
			desc = temp.getFoodDescription(food_name);
			
			if(desc.equals(null)) 
				continue;
			
			else {
				for(int i =1; i<=quantity; i++) {
					Food food = new Food(food_name,desc.getPrice()); //
					this.foods.add(food);
					desc.addnode(food);
					sl.addnode(food); 
				}
				break;
			}
		}
	}

	public void startFoodModification() {
		foodmodification = new FoodModification(foodcatalogs);
	}

	public void addFood(String food_name, Integer price, String kitchen_name) {
		foodmodification.makeFoodDescription(food_name, price, kitchen_name);
	}

	public void endFoodModification() {
		foodmodification.endFoodModification(this.dma, this.max_food_number, this.max_kitchen_number);
	}
}