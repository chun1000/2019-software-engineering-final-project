package Output;

import java.util.List;
import java.util.SortedSet;

import CLI.CliFrame;
import Sales.SaleLineitem;

public class Receipt {

	private Integer receipt_number;
	private Integer total;
	private SortedSet<SaleLineitem> lineitems;
	
	 public Integer getReceiptNumber() {
	     return receipt_number;
	 }
	
	public Receipt(Integer receipt_number, int total, SortedSet<SaleLineitem> lineitem) {
      this.receipt_number = receipt_number;
      this.total = total;
      this.lineitems = lineitem;
   }
	
	public void printReceipt() {
		CliFrame.printRecepit(this.receipt_number, this.total, this.lineitems);
	}
   
  

}
