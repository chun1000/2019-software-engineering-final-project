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


//CliFrame의 함수들은 사용자가 보고 입력하는 부분만 담당합니다.
public class CliFrame {
	private static ArrayList<String> cli_list_first_col = new ArrayList<String>();
	private static ArrayList<String> cli_list_second_col = new ArrayList<String>();
	private static ArrayList<String> cli_list_third_col = new ArrayList<String>();
	
	/*-----------------------------------------------------
	 * 
	 * 공통메뉴 부분.
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
			System.out.println("┌──────────────────────────────────┐");
			System.out.println("│                                  │");
			System.out.println("│               POFS               │");
			System.out.println("│                                  │");
			System.out.println("└──────────────────────────────────┘");
			System.out.println("선택하실 메뉴 번호를 입력해주세요.");
			System.out.println("1.주문처리\t2.메뉴관리\t4.종료");
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
				//다음에 구현될 부분.
				break;
			case '4':
				isExit = true;
				sc.close();
				break;
			default:
				
				System.out.println("[올바르지 않은 입력입니다.]");
				break;
			}
		}
	}
	
	
	/*----------------------------------------------------------------
	 * 
	 * 
	 * 주문 처리 파트.
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
			
			System.out.println("1.음식 입력\t2.결제하기\t3.종료");
			str = sc.nextLine();
			if(!str.equals(""))input = str.charAt(0);
		
			switch(input)
			{
			case '1':
				try
				{
					//고객이 Cashier 에게 주문할 음식을 말한다.
					System.out.println("주문할 음식 이름을 입력하세요.");
					food_name = sc.nextLine();
					System.out.println("주문할 음식의 수량을 입력하세요.");
					food_number = sc.nextInt(); sc.nextLine();
					
					if(is_food_exists && (food_number < 100) && (food_number > 0))
					{
						
						FoodDescription fd = orderbook.getFoodcourt().getFoodCatalogByFoodName(food_name).getFoodDescription(food_name);
						food_price = fd.getPrice();

						
						orderbook.enterFood(food_name, food_number);
						addOrderedFoodList(food_name, food_number, food_price*food_number);
						//CLI 리스트에 음식을 추가합니다. 단순히 보여주는 리스트에 추가하는 역할입니다.
						clearConsole();
						
						cur_sale.calculateTotal();
						total = cur_sale.getTotal();
						showOrderedFoodList(total);
						System.out.println("[음식이 입력되었습니다.]");
						//주문한 음식을 처리하는 루틴이 들어가야 합니다. isFoodExists를 통해 해당 음식이 존재하는 음식인지
						//알려줘야합니다. food_price에 음식의 가격을 알려줘야 합니다.
						
					}
					else
					{
						clearConsole();
						showOrderedFoodList(total);
						System.out.println("[잘못된 입력입니다.]");
					}
				}
				catch(InputMismatchException e)
				{
					sc.nextLine();
					clearConsole();
					showOrderedFoodList(total);
					System.out.println("[수량은 정수로 입력되어야 합니다.]");
				}
				break;
			case '2':
				try
				{
					//Cashier가 고객에게 총 가격을 말해주고 결제를 요청한다.
					
					cur_sale.calculateTotal();
					total = cur_sale.getTotal();
					System.out.printf("%d원 결제하시겠습니까?\n1.예\t2.아니오\n", total);
					input = sc.nextLine().charAt(0);
				
					if(input != '1') 
					{
						clearConsole();
						showOrderedFoodList(total);
						System.out.println("[결제를 취소하였습니다.]");
						break;
					}
					//Cashier가 받은 금액을 입력한다.
					System.out.println("받은 금액을 입력해주십시오.");
					input_price = sc.nextInt(); sc.nextLine();
					
					
					orderbook.makeCashPayment(total, input_price);
					Payment cur_payment = cur_sale.getPayment();
					//시스템이 거슬러줄 금액을 보여준다.
					System.out.printf("거슬러줄 금액은 %d 입니다. 계속하려면 아무키나 입력하십시오.\n", cur_payment.getChange());
					sc.nextLine();
					
					//시스템이 완료된 판매를 기록하고 매출에 반영한 뒤 주방으로 전달해준다.
				
					
					orderbook.endSale();
					
					
					//시스템이 영수증을 출력한다.
					System.out.printf("계속하려면 아무키나 입력하십시오.\n");
					sc.nextLine();
					
		
					//시스템이 주방과 계산대에 번호표를 출력한다.
					//계산대의 영수증과 번호표는 빠른 처리를 위해서 CLI가 들고있는 리스트를 변수로 받고 있습니다.
					//Domain Layer 설계시 따로 건드릴 필요가 없습니다.
				
					//Cashier가 판매 완료 처리를 한다.
				
					clearConsole(); 
				
					cli_list_first_col = new ArrayList<String>();
					cli_list_second_col = new ArrayList<String>();
					cli_list_third_col = new ArrayList<String>();
				
					total = 0;
					showOrderedFoodList(total);
					System.out.println("[결제를 완료하였습니다.]");
					orderbook.makeNewSale();
					cur_sale = orderbook.getSales().last();
					
				}
				catch(InputMismatchException e)
				{
					clearConsole();
					showOrderedFoodList(total);
					System.out.println("[가격은 정수로 입력되어야 합니다.]");
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
				System.out.println("[잘못된 입력입니다.]");
			}
			
		}
	}
	
	private static void addOrderedFoodList(String food_name, int food_number, int food_price)
	{
		cli_list_first_col.add(food_name);
		cli_list_second_col.add(String.format("%d개", food_number));
		cli_list_third_col.add(String.format("%d원", food_price));
	}
	
	public static void showOrderedFoodList(int total)
	{
		System.out.println("┌──────────────────────────────────────┐");
		System.out.println("│  음식명 --- 음식 개수 --- 음식가격.           │");
		System.out.println("├──────────────────────────────────────┤");
		for(int i =0; i < cli_list_first_col.size(); i++)
		{
			System.out.printf("   %s --- %s --- %s\n", cli_list_first_col.get(i),
					cli_list_second_col.get(i), cli_list_third_col.get(i));
		}
		System.out.println("├──────────────────────────────────────┤");
		System.out.printf("                합계: %d\n", total);
		System.out.println("└──────────────────────────────────────┘");
	}
	
	public static void printRecepit(int receipt_number, int total, SortedSet<SaleLineitem> lineitems) 
	{
		String time;
		SimpleDateFormat time_format = new SimpleDateFormat("yy년 MM월 dd일 hh시 mm분 ss초");
		Calendar calender = Calendar.getInstance();
		time = time_format.format(calender.getTime());
		SaleLineitem sli;
		
		System.out.println("┌──────────────────────────────────────┐");
		System.out.println("│  영수증.                               │");
		System.out.println("│  음식명 --- 음식 개수 --- 음식가격.           │");
		System.out.println("├──────────────────────────────────────┤");
		System.out.printf("   영수증 번호: %d\n", receipt_number);
		System.out.printf("   발급 시간   : %s\n", time);
		System.out.println("├──────────────────────────────────────┤");
		
		Iterator<SaleLineitem> it = lineitems.iterator();
		while(it.hasNext()) {
			sli = it.next();
			System.out.printf("   %s --- %s --- %s\n", sli.getfoodname(),
					sli.getquantity(), sli.getSubTotal());
		}
		
		System.out.println("├──────────────────────────────────────┤");
		System.out.printf("              금액 합계: %d\n", total);
		System.out.println("└──────────────────────────────────────┘");
		System.out.println("[영수증이 발급되었습니다.]");
	}
	
	public static void printNumberTicket(int food_number, String food_name)
	{
			System.out.println("┌───────────────────────────┐");
			System.out.println("│  번호표.                    │");
			System.out.println("├───────────────────────────┤");
			System.out.printf("   순번: %d\n", food_number);
			System.out.printf("   음식: %s\n", food_name);
			System.out.println("└───────────────────────────┘");
	}
	

	/*----------------------------------------------------------
	 * 
	 * 메뉴 관리 파트
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
			System.out.println("1.음식 추가\t2.종료");
			str = sc.nextLine();
			if(!str.equals(""))input = str.charAt(0);
			
			
			switch(input)
			{
			case '1':
				try
				{
					//Manager가 추가할 음식의 이름과 가격을 입력한다. Manager가 추가할 음식이 속하는 주방 이름을 입력한다.
					System.out.println("추가할 음식의 이름을 입력하세요.");
					food_name = sc.nextLine();
					System.out.println("추가할 음식의 가격을 입력하세요.");
					food_price = sc.nextInt(); sc.nextLine();
					System.out.println("추가할 음식이 속하는 주방을 입력하세요.");
					kitchen_name = sc.nextLine();
					//시스템이 입력된 정보를 통해서 주방과 음식의 리스트를 업데이트한다.
				
					if(is_exists_kitchen_name)
					{
						addKitchenNFoodList(kitchen_name, food_name, food_price);
						foodcourt.addFood(food_name, food_price, kitchen_name);
						//실제 입력된 정보를 DCD에 있는 적절한 객체에 추가하는 루틴이 필요합니다.
				 
						//시스템이 Manager에게 업데이트된 주방과 음식의 리스트를 보여준다.
						clearConsole();
						showKitchenNFoodList();
						System.out.println("[입력에 성공하였습니다.]");
					}
					else
					{
						//아직 고려하지 않는 부분입니다.(익스텐션 시나리오)
					}
				}
				catch(InputMismatchException e)
				{
					sc.nextLine();
					clearConsole();
					showKitchenNFoodList();
					System.out.println("[가격은 정수로 입력되어야 합니다.]");
				}
				break;
			case '2':
				//실제 입력된 정보를 저장하는 루틴이 필요합니다.
				
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
				System.out.println("[잘못 입력되었습니다.]");
				break;
			}
		}
	}
	
	public static void showKitchenNFoodList()
	{
		System.out.println("┌──────────────────────────────────────┐");
		System.out.println("│  주방명 --- 음식 이름 --- 음식가격.           │");
		System.out.println("├──────────────────────────────────────┤");
		for(int i =0; i < cli_list_first_col.size(); i++)
		{
			System.out.printf("   %s --- %s --- %s\n", cli_list_first_col.get(i),
					cli_list_second_col.get(i), cli_list_third_col.get(i));
		}
		System.out.println("└──────────────────────────────────────┘");
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









