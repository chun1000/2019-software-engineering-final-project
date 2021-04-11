package FoodCourt;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class FoodCatalog implements Comparable<FoodCatalog> {
	

	private String kitchen_name = "";
	private SortedSet<FoodDescription> fooddescriptions;
	
	//접근함수들입니다.
	public FoodCatalog(String kitchen_name) {
		this.kitchen_name = kitchen_name;
		fooddescriptions = new TreeSet<FoodDescription>();
	}
	

	public String getKitchen_name() {
		
		return this.kitchen_name;
	}
	
	public SortedSet<FoodDescription> getAllFooddescriptions() {
		return fooddescriptions;
	}

	public void setFooddescriptions(SortedSet<FoodDescription> fooddescriptions) {
		this.fooddescriptions = fooddescriptions;
	}
	
	public FoodDescription getFoodDescription(String food_name) {
		
		Iterator<FoodDescription> seek = fooddescriptions.iterator();
		FoodDescription A = null;
		
		while(seek.hasNext()) {
			A =seek.next();
			if(A.getFood_name().equals(food_name)) 
				return A;
			
		}
		return null;
	}

	//접근 함수를 제외한 함수들입니다.
	public void makeFoodDescription(String food_name, Integer price) {
		FoodDescription A = new FoodDescription(food_name, price);
		this.fooddescriptions.add(A);
	}
	
	

	

	@Override
	public int compareTo(FoodCatalog o) {
		return this.kitchen_name.compareTo(o.getKitchen_name());
	}
}