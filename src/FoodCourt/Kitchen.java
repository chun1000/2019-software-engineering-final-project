package FoodCourt;

import java.util.SortedSet;
import java.util.TreeSet;

import Output.NumberTicket;
import Sales.Sale;


public class Kitchen implements Comparable<Kitchen> {
	private FoodCatalog foodcatalog;
	private SortedSet<NumberTicket> numbertickets;
	
	public FoodCatalog getFoodCatalog() {
		return foodcatalog;
	}
	
	public void addNumberTicket(NumberTicket nt) {
		numbertickets.add(nt);
	}
	
	public Kitchen(FoodCatalog fc) {
		this.foodcatalog = fc;
		this.numbertickets = new TreeSet<NumberTicket>();
	}
	
	public int compareTo(Kitchen k) {
		return this.foodcatalog.getKitchen_name().compareTo(k.foodcatalog.getKitchen_name());
	}
}