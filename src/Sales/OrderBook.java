package Sales;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import FoodCourt.Food;
import FoodCourt.FoodCourt;
import Output.NumberTicket;


public class OrderBook {
	private Integer last_food_number;
	private Integer last_receipt_number;
	private SortedSet<Sale> sales;
	private FoodCourt foodcourt;
	
	
	public OrderBook(FoodCourt foodcourt) {
		sales = new TreeSet<>();
		last_food_number = 0;
		last_receipt_number =0;
		this.foodcourt = foodcourt;
	}
	
	public SortedSet<Sale> getSales() {
		return sales;
	}

	public FoodCourt getFoodcourt() {
		return foodcourt;
	}

	public void makeNewSale() {
		sales.add(new Sale(last_food_number, last_receipt_number));
	}


	public void enterFood(String food_name, Integer quantity) {
		SaleLineitem sl = sales.last().makeSaleLineitem();
		foodcourt.makeFoods(food_name, quantity, sl);
	}


	public void makeCashPayment(Integer cash_amount, Integer recv_amount) {
		sales.last().makeCashPayment(cash_amount,recv_amount);
	}


	public void endSale() {
		sales.last().endSale();
		
		if(sales.size() != 0) {
			updatenumbers();
		}
		
		Iterator<NumberTicket> it = sales.last().getNumbertickets().iterator();
		NumberTicket nt;
		
		while(it.hasNext()) {
			nt = it.next();
			foodcourt.getKichen((foodcourt.getFoodCatalogByFoodName(nt.getFood_name()))).addNumberTicket(nt);
				
			}
	}

	
	public void updatenumbers() {
		last_receipt_number = sales.last().getreceiptnumber() + 1;
		last_food_number = sales.last().getfoodnumber() + 1;
	}
}