package CLI;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import FoodCourt.*;
import Sales.*;
import Output.*;
import Payment.Payment;


//CliFrameÀÇ ÇÔ¼öµéÀº »ç¿ëÀÚ°¡ º¸°í ÀÔ·ÂÇÏ´Â ºÎºÐ¸¸ ´ã´çÇÕ´Ï´Ù.
public class CliFrame {
	private static ArrayList<String> cli_list_first_col = new ArrayList<String>();
	private static ArrayList<String> cli_list_second_col = new ArrayList<String>();
	private static ArrayList<String> cli_list_third_col = new ArrayList<String>();
	
	/*-----------------------------------------------------
	 * 
	 * °øÅë¸Þ´º ºÎºÐ.
	 * 
	 *-------------------------------------------------------*/
	public static void clearConsole()
	{
		for(int i =0; i < 300; i++) System.out.println("");
	}
	
	
	public static void showStartMenuUi(FoodCourt foodcourt)
	{
		clearConsole();
		Scanner sc = new Scanner(System.in);
		boolean isExit = false;
		char input = 'a'; String str;
		
		
		while(isExit == false)
		{
			System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
			System.out.println("¦¢                                  ¦¢");
			System.out.println("¦¢               POFS               ¦¢");
			System.out.println("¦¢                                  ¦¢");
			System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
			System.out.println("¼±ÅÃÇÏ½Ç ¸Þ´º ¹øÈ£¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.");
			System.out.println("1.ÁÖ¹®Ã³¸®\t2.¸Þ´º°ü¸®\t4.Á¾·á");
			str = sc.nextLine();
			if(!str.equals(""))input = str.charAt(0);
			clearConsole();
			
			switch(input)
			{
			case '1':
				showOrderProcessUi(foodcourt.getOrderbook());
				break;
			case '2':
				showMenuManagementUi(foodcourt);
				break;
			case '3':
				//´ÙÀ½¿¡ ±¸ÇöµÉ ºÎºÐ.
				break;
			case '4':
				isExit = true;
				sc.close();
				break;
			default:
				
				System.out.println("[¿Ã¹Ù¸£Áö ¾ÊÀº ÀÔ·ÂÀÔ´Ï´Ù.]");
				break;
			}
		}
	}
	
	
	/*----------------------------------------------------------------
	 * 
	 * 
	 * ÁÖ¹® Ã³¸® ÆÄÆ®.
	 * 
	 * 
	 * ------------------------------------------------------------*/
	
	public static void showOrderProcessUi(OrderBook orderbook)
	{	
		Scanner sc = new Scanner(System.in);
		boolean is_exit = false;
		int food_number = 0, food_price = 0;
		String food_name = ""; int input_price;
		char input = 'a'; boolean is_food_exists = true;
		int total = 0;
		String str;
		clearConsole();
		
		
		
		showOrderedFoodList(total);
		orderbook.makeNewSale();
		Sale cur_sale = orderbook.getSales().last();
		
		while(is_exit == false)
		{
			
			System.out.println("1.À½½Ä ÀÔ·Â\t2.°áÁ¦ÇÏ±â\t3.Á¾·á");
			str = sc.nextLine();
			if(!str.equals(""))input = str.charAt(0);
		
			switch(input)
			{
			case '1':
				try
				{
					//°í°´ÀÌ Cashier ¿¡°Ô ÁÖ¹®ÇÒ À½½ÄÀ» ¸»ÇÑ´Ù.
					System.out.println("ÁÖ¹®ÇÒ À½½Ä ÀÌ¸§À» ÀÔ·ÂÇÏ¼¼¿ä.");
					food_name = sc.nextLine();
					System.out.println("ÁÖ¹®ÇÒ À½½ÄÀÇ ¼ö·®À» ÀÔ·ÂÇÏ¼¼¿ä.");
					food_number = sc.nextInt(); sc.nextLine();
					
					if(is_food_exists && (food_number < 100) && (food_number > 0))
					{
						
						FoodDescription fd = orderbook.getFoodcourt().getFoodCatalogByFoodName(food_name).getFoodDescription(food_name);
						food_price = fd.getPrice();

						
						orderbook.enterFood(food_name, food_number);
						addOrderedFoodList(food_name, food_number, food_price*food_number);
						//CLI ¸®½ºÆ®¿¡ À½½ÄÀ» Ãß°¡ÇÕ´Ï´Ù. ´Ü¼øÈ÷ º¸¿©ÁÖ´Â ¸®½ºÆ®¿¡ Ãß°¡ÇÏ´Â ¿ªÇÒÀÔ´Ï´Ù.
						clearConsole();
						
						cur_sale.calculateTotal();
						total = cur_sale.getTotal();
						showOrderedFoodList(total);
						System.out.println("[À½½ÄÀÌ ÀÔ·ÂµÇ¾ú½À´Ï´Ù.]");
						//ÁÖ¹®ÇÑ À½½ÄÀ» Ã³¸®ÇÏ´Â ·çÆ¾ÀÌ µé¾î°¡¾ß ÇÕ´Ï´Ù. isFoodExists¸¦ ÅëÇØ ÇØ´ç À½½ÄÀÌ Á¸ÀçÇÏ´Â À½½ÄÀÎÁö
						//¾Ë·ÁÁà¾ßÇÕ´Ï´Ù. food_price¿¡ À½½ÄÀÇ °¡°ÝÀ» ¾Ë·ÁÁà¾ß ÇÕ´Ï´Ù.
						
					}
					else
					{
						clearConsole();
						showOrderedFoodList(total);
						System.out.println("[Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.]");
					}
				}
				catch(InputMismatchException e)
				{
					sc.nextLine();
					clearConsole();
					showOrderedFoodList(total);
					System.out.println("[¼ö·®Àº Á¤¼ö·Î ÀÔ·ÂµÇ¾î¾ß ÇÕ´Ï´Ù.]");
				}
				break;
			case '2':
				try
				{
					//Cashier°¡ °í°´¿¡°Ô ÃÑ °¡°ÝÀ» ¸»ÇØÁÖ°í °áÁ¦¸¦ ¿äÃ»ÇÑ´Ù.
					
					cur_sale.calculateTotal();
					total = cur_sale.getTotal();
					System.out.printf("%d¿ø °áÁ¦ÇÏ½Ã°Ú½À´Ï±î?\n1.¿¹\t2.¾Æ´Ï¿À\n", total);
					input = sc.nextLine().charAt(0);
				
					if(input != '1') 
					{
						clearConsole();
						showOrderedFoodList(total);
						System.out.println("[°áÁ¦¸¦ Ãë¼ÒÇÏ¿´½À´Ï´Ù.]");
						break;
					}
					//Cashier°¡ ¹ÞÀº ±Ý¾×À» ÀÔ·ÂÇÑ´Ù.
					System.out.println("¹ÞÀº ±Ý¾×À» ÀÔ·ÂÇØÁÖ½Ê½Ã¿À.");
					input_price = sc.nextInt(); sc.nextLine();
					
					
					orderbook.makeCashPayment(total, input_price);
					Payment cur_payment = cur_sale.getPayment();
					//½Ã½ºÅÛÀÌ °Å½½·¯ÁÙ ±Ý¾×À» º¸¿©ÁØ´Ù.
					System.out.printf("°Å½½·¯ÁÙ ±Ý¾×Àº %d ÀÔ´Ï´Ù. °è¼ÓÇÏ·Á¸é ¾Æ¹«Å°³ª ÀÔ·ÂÇÏ½Ê½Ã¿À.\n", cur_payment.getChange());
					sc.nextLine();
					
					//½Ã½ºÅÛÀÌ ¿Ï·áµÈ ÆÇ¸Å¸¦ ±â·ÏÇÏ°í ¸ÅÃâ¿¡ ¹Ý¿µÇÑ µÚ ÁÖ¹æÀ¸·Î Àü´ÞÇØÁØ´Ù.
				
					
					orderbook.endSale();
					
					
					//½Ã½ºÅÛÀÌ ¿µ¼öÁõÀ» Ãâ·ÂÇÑ´Ù.
					System.out.printf("°è¼ÓÇÏ·Á¸é ¾Æ¹«Å°³ª ÀÔ·ÂÇÏ½Ê½Ã¿À.\n");
					sc.nextLine();
					
		
					//½Ã½ºÅÛÀÌ ÁÖ¹æ°ú °è»ê´ë¿¡ ¹øÈ£Ç¥¸¦ Ãâ·ÂÇÑ´Ù.
					//°è»ê´ëÀÇ ¿µ¼öÁõ°ú ¹øÈ£Ç¥´Â ºü¸¥ Ã³¸®¸¦ À§ÇØ¼­ CLI°¡ µé°íÀÖ´Â ¸®½ºÆ®¸¦ º¯¼ö·Î ¹Þ°í ÀÖ½À´Ï´Ù.
					//Domain Layer ¼³°è½Ã µû·Î °Çµå¸± ÇÊ¿ä°¡ ¾ø½À´Ï´Ù.
				
					//Cashier°¡ ÆÇ¸Å ¿Ï·á Ã³¸®¸¦ ÇÑ´Ù.
				
					clearConsole(); 
				
					cli_list_first_col = new ArrayList<String>();
					cli_list_second_col = new ArrayList<String>();
					cli_list_third_col = new ArrayList<String>();
				
					total = 0;
					showOrderedFoodList(total);
					System.out.println("[°áÁ¦¸¦ ¿Ï·áÇÏ¿´½À´Ï´Ù.]");
					orderbook.makeNewSale();
					cur_sale = orderbook.getSales().last();
					
				}
				catch(InputMismatchException e)
				{
					clearConsole();
					showOrderedFoodList(total);
					System.out.println("[°¡°ÝÀº Á¤¼ö·Î ÀÔ·ÂµÇ¾î¾ß ÇÕ´Ï´Ù.]");
				}
				break;
			case '3':
				clearConsole();
				
				cli_list_first_col = new ArrayList<String>();
				cli_list_second_col = new ArrayList<String>();
				cli_list_third_col = new ArrayList<String>();
				
				is_exit = true;
				break;
			default:
				clearConsole();
				showOrderedFoodList(total);
				System.out.println("[Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.]");
			}
			
		}
	}
	
	private static void addOrderedFoodList(String food_name, int food_number, int food_price)
	{
		cli_list_first_col.add(food_name);
		cli_list_second_col.add(String.format("%d°³", food_number));
		cli_list_third_col.add(String.format("%d¿ø", food_price));
	}
	
	public static void showOrderedFoodList(int total)
	{
		System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
		System.out.println("¦¢  À½½Ä¸í --- À½½Ä °³¼ö --- À½½Ä°¡°Ý.           ¦¢");
		System.out.println("¦§¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦©");
		for(int i =0; i < cli_list_first_col.size(); i++)
		{
			System.out.printf("   %s --- %s --- %s\n", cli_list_first_col.get(i),
					cli_list_second_col.get(i), cli_list_third_col.get(i));
		}
		System.out.println("¦§¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦©");
		System.out.printf("                ÇÕ°è: %d\n", total);
		System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
	}
	
	public static void printRecepit(int receipt_number, int total, SortedSet<SaleLineitem> lineitems) 
	{
		String time;
		SimpleDateFormat time_format = new SimpleDateFormat("yy³â MM¿ù ddÀÏ hh½Ã mmºÐ ssÃÊ");
		Calendar calender = Calendar.getInstance();
		time = time_format.format(calender.getTime());
		SaleLineitem sli;
		
		System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
		System.out.println("¦¢  ¿µ¼öÁõ.                               ¦¢");
		System.out.println("¦¢  À½½Ä¸í --- À½½Ä °³¼ö --- À½½Ä°¡°Ý.           ¦¢");
		System.out.println("¦§¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦©");
		System.out.printf("   ¿µ¼öÁõ ¹øÈ£: %d\n", receipt_number);
		System.out.printf("   ¹ß±Þ ½Ã°£   : %s\n", time);
		System.out.println("¦§¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦©");
		
		Iterator<SaleLineitem> it = lineitems.iterator();
		while(it.hasNext()) {
			sli = it.next();
			System.out.printf("   %s --- %s --- %s\n", sli.getfoodname(),
					sli.getquantity(), sli.getSubTotal());
		}
		
		System.out.println("¦§¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦©");
		System.out.printf("              ±Ý¾× ÇÕ°è: %d\n", total);
		System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
		System.out.println("[¿µ¼öÁõÀÌ ¹ß±ÞµÇ¾ú½À´Ï´Ù.]");
	}
	
	public static void printNumberTicket(int food_number, String food_name)
	{
			System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
			System.out.println("¦¢  ¹øÈ£Ç¥.                    ¦¢");
			System.out.println("¦§¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦©");
			System.out.printf("   ¼ø¹ø: %d\n", food_number);
			System.out.printf("   À½½Ä: %s\n", food_name);
			System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
	}
	

	/*----------------------------------------------------------
	 * 
	 * ¸Þ´º °ü¸® ÆÄÆ®
	 * 
	 * 
 	------------------------------------------------------------*/

	public static void showMenuManagementUi(FoodCourt foodcourt)
	{		
		char input = 'a';	boolean is_exit = false;
		Scanner sc = new Scanner(System.in);
		String food_name, kitchen_name; int food_price;
		boolean is_exists_kitchen_name = true;
		String str;
		
		
		initKitchenNFoodList(foodcourt);
		clearConsole();
		showKitchenNFoodList();
		
		
		foodcourt.startFoodModification();
		
		
		while(is_exit == false)
		{
			System.out.println("1.À½½Ä Ãß°¡\t2.Á¾·á");
			str = sc.nextLine();
			if(!str.equals(""))input = str.charAt(0);
			
			
			switch(input)
			{
			case '1':
				try
				{
					//Manager°¡ Ãß°¡ÇÒ À½½ÄÀÇ ÀÌ¸§°ú °¡°ÝÀ» ÀÔ·ÂÇÑ´Ù. Manager°¡ Ãß°¡ÇÒ À½½ÄÀÌ ¼ÓÇÏ´Â ÁÖ¹æ ÀÌ¸§À» ÀÔ·ÂÇÑ´Ù.
					System.out.println("Ãß°¡ÇÒ À½½ÄÀÇ ÀÌ¸§À» ÀÔ·ÂÇÏ¼¼¿ä.");
					food_name = sc.nextLine();
					System.out.println("Ãß°¡ÇÒ À½½ÄÀÇ °¡°ÝÀ» ÀÔ·ÂÇÏ¼¼¿ä.");
					food_price = sc.nextInt(); sc.nextLine();
					System.out.println("Ãß°¡ÇÒ À½½ÄÀÌ ¼ÓÇÏ´Â ÁÖ¹æÀ» ÀÔ·ÂÇÏ¼¼¿ä.");
					kitchen_name = sc.nextLine();
					//½Ã½ºÅÛÀÌ ÀÔ·ÂµÈ Á¤º¸¸¦ ÅëÇØ¼­ ÁÖ¹æ°ú À½½ÄÀÇ ¸®½ºÆ®¸¦ ¾÷µ¥ÀÌÆ®ÇÑ´Ù.
				
					if(is_exists_kitchen_name)
					{
						addKitchenNFoodList(kitchen_name, food_name, food_price);
						foodcourt.addFood(food_name, food_price, kitchen_name);
						//½ÇÁ¦ ÀÔ·ÂµÈ Á¤º¸¸¦ DCD¿¡ ÀÖ´Â ÀûÀýÇÑ °´Ã¼¿¡ Ãß°¡ÇÏ´Â ·çÆ¾ÀÌ ÇÊ¿äÇÕ´Ï´Ù.
				 
						//½Ã½ºÅÛÀÌ Manager¿¡°Ô ¾÷µ¥ÀÌÆ®µÈ ÁÖ¹æ°ú À½½ÄÀÇ ¸®½ºÆ®¸¦ º¸¿©ÁØ´Ù.
						clearConsole();
						showKitchenNFoodList();
						System.out.println("[ÀÔ·Â¿¡ ¼º°øÇÏ¿´½À´Ï´Ù.]");
					}
					else
					{
						//¾ÆÁ÷ °í·ÁÇÏÁö ¾Ê´Â ºÎºÐÀÔ´Ï´Ù.(ÀÍ½ºÅÙ¼Ç ½Ã³ª¸®¿À)
					}
				}
				catch(InputMismatchException e)
				{
					sc.nextLine();
					clearConsole();
					showKitchenNFoodList();
					System.out.println("[°¡°ÝÀº Á¤¼ö·Î ÀÔ·ÂµÇ¾î¾ß ÇÕ´Ï´Ù.]");
				}
				break;
			case '2':
				//½ÇÁ¦ ÀÔ·ÂµÈ Á¤º¸¸¦ ÀúÀåÇÏ´Â ·çÆ¾ÀÌ ÇÊ¿äÇÕ´Ï´Ù.
				
				is_exit = true;
				cli_list_first_col = new ArrayList<String>();
				cli_list_second_col = new ArrayList<String>();
				cli_list_third_col = new ArrayList<String>();
				foodcourt.endFoodModification();
				clearConsole();
				break;
			default:
				clearConsole();
				showKitchenNFoodList();
				System.out.println("[Àß¸ø ÀÔ·ÂµÇ¾ú½À´Ï´Ù.]");
				break;
			}
		}
	}
	
	public static void showKitchenNFoodList()
	{
		System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
		System.out.println("¦¢  ÁÖ¹æ¸í --- À½½Ä ÀÌ¸§ --- À½½Ä°¡°Ý.           ¦¢");
		System.out.println("¦§¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦©");
		for(int i =0; i < cli_list_first_col.size(); i++)
		{
			System.out.printf("   %s --- %s --- %s\n", cli_list_first_col.get(i),
					cli_list_second_col.get(i), cli_list_third_col.get(i));
		}
		System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
	}
	
	private static void addKitchenNFoodList(String kitchen_name, String food_name, int food_price)
	{
		cli_list_first_col.add(kitchen_name);
		cli_list_second_col.add(food_name);
		cli_list_third_col.add(String.format("%s", food_price));
	}
	
	public static void initKitchenNFoodList(FoodCourt foodcourt)
	{
		SortedSet<FoodCatalog> fc = foodcourt.getAllFoodCatalog();
		String food_name, kitchen_name; int food_price;
		
		Iterator<FoodCatalog> cit = fc.iterator();
		Iterator<FoodDescription> dit;
		FoodCatalog cur = null; FoodDescription fd = null;
		int max_food_number = foodcourt.getMaxFoodNumber();
		int max_kitchen_number = foodcourt.getMaxKitchenNumber();
		int index = 0;
		int inner_index = 0;
		
		SortedSet<FoodDescription> fooddescriptions = new TreeSet<FoodDescription>();
		
		while(cit.hasNext() && index < max_kitchen_number) {
			cur = cit.next();
			kitchen_name = cur.getKitchen_name();
			fooddescriptions = cur.getAllFooddescriptions();
			dit = fooddescriptions.iterator();
			while(dit.hasNext() && inner_index < max_food_number) {
				fd = dit.next();
				food_name = fd.getFood_name();
				food_price = fd.getPrice();
				addKitchenNFoodList(kitchen_name, food_name, food_price);
				inner_index++;
			}
			
			index++;
		}
	}
	
}









