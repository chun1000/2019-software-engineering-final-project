package FoodCourt;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class FoodDescription implements Comparable<FoodDescription>{

	
	private String food_name;
	private Integer price;
	private List<Food> foods;
	
	public FoodDescription(String food_name, Integer price) {
		this.food_name = food_name;
		this.price = price;
		this.foods = new ArrayList<Food>();
		
	}
	
	public Integer getPrice() {
		return price;
	}


	public String getFood_name() {
		return food_name;
	}
	
	public void addnode(Food food) {
		
		this.foods.add(food);
	}


	@Override
	public int compareTo(FoodDescription o) {
		return this.food_name.compareTo(o.getFood_name());
	}
}