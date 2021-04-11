package Sales;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import FoodCourt.*;
import Output.NumberTicket;

public class SaleLineitem implements Comparable<SaleLineitem>{
	
	
	private Integer quantity;
	private List<Food> foods;
	private String food_name;
	
	//접근함수입니다.
	public Integer getquantity() {
		return quantity;
	}
	
	public String getfoodname() {
		return food_name;
	}
	
	public List<Food> getfoods() {
		return foods;
	}

	//접근함수를 제외한 함수들입니다.
	public SaleLineitem() {
		quantity = 0;
		this.foods = new ArrayList<Food>();
	}	
	


	public void addnode(Food food) {
		this.food_name = food.getFoodName();
		this.quantity += 1;
		foods.add(food);
	}
	
	public int getSubTotal() {
		
		Iterator <Food> it = this.foods.iterator();
		Food f; int sub_total = 0;
		
		while(it.hasNext()) {
			f = it.next();
			sub_total += f.getPrice();
		}
		
		return sub_total;
	}
	
	@Override
	public int compareTo(SaleLineitem o) {
		return this.quantity.compareTo(o.quantity);
	}
	
	
}