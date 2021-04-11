package Payment;

public class Payment {

	private Integer cash_amount;
	private Integer recv_amount;
	private Integer change;
	private Integer card_amount;
	
	   public Integer getCashAmount()
	   {
	      return cash_amount;
	   }
	   
	   
	   public Integer getRecvAmount()
	   {
	      return recv_amount;
	   }
	   
	   public Integer getChange()
	   {
		   return change;
	   }
	
	public Payment(Integer cash_amount, Integer recv_amount)
   {
      this.recv_amount = recv_amount;
      this.cash_amount = cash_amount;
      this.change = recv_amount - cash_amount;
   }
   

	
	
}
