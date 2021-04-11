package Output;

import java.util.SortedSet;

import CLI.CliFrame;
import FoodCourt.FoodDescription;

public class NumberTicket implements Comparable<NumberTicket>{
	private Integer food_number;
	private String food_name;
	
	
	
	   public Integer getFood_number() {
		return food_number;
	}



	public String getFood_name() {
		return food_name;
	}



	public NumberTicket(Integer food_number, String food_name) {
	   this.food_number = food_number;
	   this.food_name = food_name;
	}
	
	public void printNumberTicket() {
		CliFrame.printNumberTicket(this.food_number, this.food_name);
	}

	@Override
	public int compareTo(NumberTicket o) {
		return this.food_number.compareTo(o.getFood_number());
	}
	   

}