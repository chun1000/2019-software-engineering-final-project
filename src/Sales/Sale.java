package Sales;
import java.util.SortedSet;
import java.util.TreeSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import Output.*;
import Payment.*;
import FoodCourt.*;

public class Sale implements Comparable<Sale>{
	
	private SortedSet<NumberTicket> numbertickets;
	private Receipt receipt;
	private Payment payment;
	private SortedSet<SaleLineitem> lineitems;
	private String dateTime;
	private Integer food_number;
	private Integer receipt_number;
	private Integer total;
	
	//접근함수들입니다.
	
	public Payment getPayment() {
		return this.payment;
	}
	
	public Integer getTotal() {
		return this.total;
	}
	
	public SortedSet<NumberTicket> getNumbertickets() {
		return numbertickets;
	}

	public Integer getfoodnumber() {
		return food_number;
	}

	public Integer getreceiptnumber() {
		return receipt_number;
	}
	
	//접근을 제외한 함수들입니다.
	
	public Sale(int last_food_number, int last_receipt_number) {
		this.total = 0;
		Date from = new Date();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.dateTime = transFormat.format(from);
		this.food_number = last_food_number;
		this.receipt_number = last_receipt_number;
		this.lineitems = new TreeSet<>();
		this.numbertickets = new TreeSet<>();
	}

	public void makeCashPayment(Integer cash_amount, Integer recv_amount) { 
		payment = new Payment(cash_amount, recv_amount);
	}

	public void endSale() { 
		
		NumberTicket ntcur;
		
		Iterator <SaleLineitem> it = lineitems.iterator();
		SaleLineitem cur = null;
		
		SortedSet<NumberTicket> nt = new TreeSet<>();
		Food f;
		
		while(it.hasNext()) {
			cur = it.next();
			
			Iterator <Food> itt = cur.getfoods().iterator();
			while(itt.hasNext()) {
				f = itt.next();
				ntcur = new NumberTicket(food_number, f.getFoodName());
				this.food_number += 1;
				nt.add(ntcur);
				ntcur.printNumberTicket();
			}
		}
		
		numbertickets.addAll(nt);
		
		calculateTotal();
		this.receipt = new Receipt(receipt_number, this.total, this.lineitems);
		this.receipt.printReceipt();
	}

	public SaleLineitem makeSaleLineitem() { 
		SaleLineitem temp = new SaleLineitem();
		this.lineitems.add(temp);
		return temp;
	}
	
	
	public int compareTo(Sale sale) { 
		return this.dateTime.compareTo(sale.dateTime);
	}
	
	public void calculateTotal()
	{
		int total = 0;
		Iterator<SaleLineitem> it = this.lineitems.iterator();
		SaleLineitem sli;
		while(it.hasNext()) {
			sli = it.next();
			total += sli.getSubTotal();
		}
		
		this.total = total;
	}
	
	
}