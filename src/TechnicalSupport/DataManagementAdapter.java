package TechnicalSupport;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import FoodCourt.FoodCatalog;
import FoodCourt.FoodCourt;
import FoodCourt.FoodDescription;
import FoodCourt.Kitchen;
import Output.NumberTicket;

public class DataManagementAdapter {
	public void loadFoodCourtData(int max_food_number, int max_kitchen_number, FoodCourt foodcourt) {
		FoodCatalog fc;
		int tmp;
		
		String[] food_name = new String[max_food_number];
		String[] food_price = new String[max_food_number];
		String[] kitchen_name = new String[max_food_number];
		
		FileManager.loadCategoryFile(kitchen_name, max_kitchen_number);
		
		Set<Kitchen> kitchens = foodcourt.getKitchenSet();
		
		Kitchen k;
		
		for(int i =0; i < max_kitchen_number; i++) {
			if(kitchen_name[i] == null) break;
			fc = new FoodCatalog(kitchen_name[i]);
			foodcourt.getAllFoodCatalog().add(fc);
			k = new Kitchen(fc);
			kitchens.add(k);
		}
		
		FileManager.loadFoodFile(kitchen_name, food_name, food_price, max_food_number);
		for(int i =0; i< max_food_number; i++) {
			if(food_name[i] == null) break;
			fc = foodcourt.getFoodCatalog(kitchen_name[i]);
			tmp = Integer.parseInt(food_price[i]);
			fc.makeFoodDescription(food_name[i], tmp);
		}
		
	}
	public void saveFoodCourtData(int max_food_number, int max_kitchen_number, SortedSet<FoodCatalog> fc) {
		
		Iterator<FoodCatalog> cit = fc.iterator();
		Iterator<FoodDescription> dit;
		FoodCatalog cur = null;
		FoodDescription fd = null;
		int index = 0;
		int inner_index = 0;
		
		String[] kitchen_name = new String[max_kitchen_number];
		String[] inner_kitchen_name = new String[max_food_number];
		String[] food_name = new String[max_food_number];
		String[] food_price = new String[max_food_number];
		
		SortedSet<FoodDescription> fooddescriptions = new TreeSet<FoodDescription>();
		
		while(cit.hasNext() && index < max_kitchen_number) {
			cur = cit.next();
			kitchen_name[index] = cur.getKitchen_name();
			fooddescriptions = cur.getAllFooddescriptions();
			
			dit = fooddescriptions.iterator();
			while(dit.hasNext() && inner_index < max_food_number) {
				fd = dit.next();
				food_name[inner_index] = fd.getFood_name();
				food_price[inner_index] = String.format("%d", fd.getPrice());
				inner_kitchen_name[inner_index] = kitchen_name[index];
				inner_index++;
			}
			
			index++;
		}
		
		FileManager.saveCategoryFile(kitchen_name, max_kitchen_number);
		FileManager.saveFoodFile(inner_kitchen_name, food_name, food_price, max_food_number);
	}
}
