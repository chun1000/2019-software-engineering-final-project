package FoodCourt;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import TechnicalSupport.*;

public class FoodModification {
	private SortedSet<FoodCatalog> foodcatalogs;
	
	public FoodModification(SortedSet<FoodCatalog> foodcatalogs) {
		
		this.foodcatalogs = foodcatalogs;
	}
	private FoodCatalog searchFoodCatalog(String kitchen_name) {
		Iterator<FoodCatalog> it = foodcatalogs.iterator();
		FoodCatalog cur = null;
		
		while(it.hasNext()) {
			cur = it.next();
			if(cur.getKitchen_name().equals(kitchen_name)) return cur;
		}
		return null;
	}
	
	
	public void endFoodModification(DataManagementAdapter dma, int max_food_number, int max_kitchen_number) {
		dma.saveFoodCourtData(max_food_number, max_kitchen_number, this.foodcatalogs);
	}
	
	public void makeFoodDescription(String food_name, Integer price, String kitchen_name) {
		FoodCatalog fc = searchFoodCatalog(kitchen_name);
		
		if(fc != null) {
			fc.makeFoodDescription(food_name, price);
		} else {
			
		}
	}
}