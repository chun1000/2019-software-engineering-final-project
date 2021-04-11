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


//CliFrame�� �Լ����� ����ڰ� ���� �Է��ϴ� �κи� ����մϴ�.
public class CliFrame {
	private static ArrayList<String> cli_list_first_col = new ArrayList<String>();
	private static ArrayList<String> cli_list_second_col = new ArrayList<String>();
	private static ArrayList<String> cli_list_third_col = new ArrayList<String>();
	
	/*-----------------------------------------------------
	 * 
	 * ����޴� �κ�.
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
			System.out.println("������������������������������������������������������������������������");
			System.out.println("��                                  ��");
			System.out.println("��               POFS               ��");
			System.out.println("��                                  ��");
			System.out.println("������������������������������������������������������������������������");
			System.out.println("�����Ͻ� �޴� ��ȣ�� �Է����ּ���.");
			System.out.println("1.�ֹ�ó��\t2.�޴�����\t4.����");
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
				//������ ������ �κ�.
				break;
			case '4':
				isExit = true;
				sc.close();
				break;
			default:
				
				System.out.println("[�ùٸ��� ���� �Է��Դϴ�.]");
				break;
			}
		}
	}
	
	
	/*----------------------------------------------------------------
	 * 
	 * 
	 * �ֹ� ó�� ��Ʈ.
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
			
			System.out.println("1.���� �Է�\t2.�����ϱ�\t3.����");
			str = sc.nextLine();
			if(!str.equals(""))input = str.charAt(0);
		
			switch(input)
			{
			case '1':
				try
				{
					//���� Cashier ���� �ֹ��� ������ ���Ѵ�.
					System.out.println("�ֹ��� ���� �̸��� �Է��ϼ���.");
					food_name = sc.nextLine();
					System.out.println("�ֹ��� ������ ������ �Է��ϼ���.");
					food_number = sc.nextInt(); sc.nextLine();
					
					if(is_food_exists && (food_number < 100) && (food_number > 0))
					{
						
						FoodDescription fd = orderbook.getFoodcourt().getFoodCatalogByFoodName(food_name).getFoodDescription(food_name);
						food_price = fd.getPrice();

						
						orderbook.enterFood(food_name, food_number);
						addOrderedFoodList(food_name, food_number, food_price*food_number);
						//CLI ����Ʈ�� ������ �߰��մϴ�. �ܼ��� �����ִ� ����Ʈ�� �߰��ϴ� �����Դϴ�.
						clearConsole();
						
						cur_sale.calculateTotal();
						total = cur_sale.getTotal();
						showOrderedFoodList(total);
						System.out.println("[������ �ԷµǾ����ϴ�.]");
						//�ֹ��� ������ ó���ϴ� ��ƾ�� ���� �մϴ�. isFoodExists�� ���� �ش� ������ �����ϴ� ��������
						//�˷�����մϴ�. food_price�� ������ ������ �˷���� �մϴ�.
						
					}
					else
					{
						clearConsole();
						showOrderedFoodList(total);
						System.out.println("[�߸��� �Է��Դϴ�.]");
					}
				}
				catch(InputMismatchException e)
				{
					sc.nextLine();
					clearConsole();
					showOrderedFoodList(total);
					System.out.println("[������ ������ �ԷµǾ�� �մϴ�.]");
				}
				break;
			case '2':
				try
				{
					//Cashier�� ������ �� ������ �����ְ� ������ ��û�Ѵ�.
					
					cur_sale.calculateTotal();
					total = cur_sale.getTotal();
					System.out.printf("%d�� �����Ͻðڽ��ϱ�?\n1.��\t2.�ƴϿ�\n", total);
					input = sc.nextLine().charAt(0);
				
					if(input != '1') 
					{
						clearConsole();
						showOrderedFoodList(total);
						System.out.println("[������ ����Ͽ����ϴ�.]");
						break;
					}
					//Cashier�� ���� �ݾ��� �Է��Ѵ�.
					System.out.println("���� �ݾ��� �Է����ֽʽÿ�.");
					input_price = sc.nextInt(); sc.nextLine();
					
					
					orderbook.makeCashPayment(total, input_price);
					Payment cur_payment = cur_sale.getPayment();
					//�ý����� �Ž����� �ݾ��� �����ش�.
					System.out.printf("�Ž����� �ݾ��� %d �Դϴ�. ����Ϸ��� �ƹ�Ű�� �Է��Ͻʽÿ�.\n", cur_payment.getChange());
					sc.nextLine();
					
					//�ý����� �Ϸ�� �ǸŸ� ����ϰ� ���⿡ �ݿ��� �� �ֹ����� �������ش�.
				
					
					orderbook.endSale();
					
					
					//�ý����� �������� ����Ѵ�.
					System.out.printf("����Ϸ��� �ƹ�Ű�� �Է��Ͻʽÿ�.\n");
					sc.nextLine();
					
		
					//�ý����� �ֹ�� ���뿡 ��ȣǥ�� ����Ѵ�.
					//������ �������� ��ȣǥ�� ���� ó���� ���ؼ� CLI�� ����ִ� ����Ʈ�� ������ �ް� �ֽ��ϴ�.
					//Domain Layer ����� ���� �ǵ帱 �ʿ䰡 �����ϴ�.
				
					//Cashier�� �Ǹ� �Ϸ� ó���� �Ѵ�.
				
					clearConsole(); 
				
					cli_list_first_col = new ArrayList<String>();
					cli_list_second_col = new ArrayList<String>();
					cli_list_third_col = new ArrayList<String>();
				
					total = 0;
					showOrderedFoodList(total);
					System.out.println("[������ �Ϸ��Ͽ����ϴ�.]");
					orderbook.makeNewSale();
					cur_sale = orderbook.getSales().last();
					
				}
				catch(InputMismatchException e)
				{
					clearConsole();
					showOrderedFoodList(total);
					System.out.println("[������ ������ �ԷµǾ�� �մϴ�.]");
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
				System.out.println("[�߸��� �Է��Դϴ�.]");
			}
			
		}
	}
	
	private static void addOrderedFoodList(String food_name, int food_number, int food_price)
	{
		cli_list_first_col.add(food_name);
		cli_list_second_col.add(String.format("%d��", food_number));
		cli_list_third_col.add(String.format("%d��", food_price));
	}
	
	public static void showOrderedFoodList(int total)
	{
		System.out.println("��������������������������������������������������������������������������������");
		System.out.println("��  ���ĸ� --- ���� ���� --- ���İ���.           ��");
		System.out.println("��������������������������������������������������������������������������������");
		for(int i =0; i < cli_list_first_col.size(); i++)
		{
			System.out.printf("   %s --- %s --- %s\n", cli_list_first_col.get(i),
					cli_list_second_col.get(i), cli_list_third_col.get(i));
		}
		System.out.println("��������������������������������������������������������������������������������");
		System.out.printf("                �հ�: %d\n", total);
		System.out.println("��������������������������������������������������������������������������������");
	}
	
	public static void printRecepit(int receipt_number, int total, SortedSet<SaleLineitem> lineitems) 
	{
		String time;
		SimpleDateFormat time_format = new SimpleDateFormat("yy�� MM�� dd�� hh�� mm�� ss��");
		Calendar calender = Calendar.getInstance();
		time = time_format.format(calender.getTime());
		SaleLineitem sli;
		
		System.out.println("��������������������������������������������������������������������������������");
		System.out.println("��  ������.                               ��");
		System.out.println("��  ���ĸ� --- ���� ���� --- ���İ���.           ��");
		System.out.println("��������������������������������������������������������������������������������");
		System.out.printf("   ������ ��ȣ: %d\n", receipt_number);
		System.out.printf("   �߱� �ð�   : %s\n", time);
		System.out.println("��������������������������������������������������������������������������������");
		
		Iterator<SaleLineitem> it = lineitems.iterator();
		while(it.hasNext()) {
			sli = it.next();
			System.out.printf("   %s --- %s --- %s\n", sli.getfoodname(),
					sli.getquantity(), sli.getSubTotal());
		}
		
		System.out.println("��������������������������������������������������������������������������������");
		System.out.printf("              �ݾ� �հ�: %d\n", total);
		System.out.println("��������������������������������������������������������������������������������");
		System.out.println("[�������� �߱޵Ǿ����ϴ�.]");
	}
	
	public static void printNumberTicket(int food_number, String food_name)
	{
			System.out.println("����������������������������������������������������������");
			System.out.println("��  ��ȣǥ.                    ��");
			System.out.println("����������������������������������������������������������");
			System.out.printf("   ����: %d\n", food_number);
			System.out.printf("   ����: %s\n", food_name);
			System.out.println("����������������������������������������������������������");
	}
	

	/*----------------------------------------------------------
	 * 
	 * �޴� ���� ��Ʈ
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
			System.out.println("1.���� �߰�\t2.����");
			str = sc.nextLine();
			if(!str.equals(""))input = str.charAt(0);
			
			
			switch(input)
			{
			case '1':
				try
				{
					//Manager�� �߰��� ������ �̸��� ������ �Է��Ѵ�. Manager�� �߰��� ������ ���ϴ� �ֹ� �̸��� �Է��Ѵ�.
					System.out.println("�߰��� ������ �̸��� �Է��ϼ���.");
					food_name = sc.nextLine();
					System.out.println("�߰��� ������ ������ �Է��ϼ���.");
					food_price = sc.nextInt(); sc.nextLine();
					System.out.println("�߰��� ������ ���ϴ� �ֹ��� �Է��ϼ���.");
					kitchen_name = sc.nextLine();
					//�ý����� �Էµ� ������ ���ؼ� �ֹ�� ������ ����Ʈ�� ������Ʈ�Ѵ�.
				
					if(is_exists_kitchen_name)
					{
						addKitchenNFoodList(kitchen_name, food_name, food_price);
						foodcourt.addFood(food_name, food_price, kitchen_name);
						//���� �Էµ� ������ DCD�� �ִ� ������ ��ü�� �߰��ϴ� ��ƾ�� �ʿ��մϴ�.
				 
						//�ý����� Manager���� ������Ʈ�� �ֹ�� ������ ����Ʈ�� �����ش�.
						clearConsole();
						showKitchenNFoodList();
						System.out.println("[�Է¿� �����Ͽ����ϴ�.]");
					}
					else
					{
						//���� ������� �ʴ� �κ��Դϴ�.(�ͽ��ټ� �ó�����)
					}
				}
				catch(InputMismatchException e)
				{
					sc.nextLine();
					clearConsole();
					showKitchenNFoodList();
					System.out.println("[������ ������ �ԷµǾ�� �մϴ�.]");
				}
				break;
			case '2':
				//���� �Էµ� ������ �����ϴ� ��ƾ�� �ʿ��մϴ�.
				
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
				System.out.println("[�߸� �ԷµǾ����ϴ�.]");
				break;
			}
		}
	}
	
	public static void showKitchenNFoodList()
	{
		System.out.println("��������������������������������������������������������������������������������");
		System.out.println("��  �ֹ�� --- ���� �̸� --- ���İ���.           ��");
		System.out.println("��������������������������������������������������������������������������������");
		for(int i =0; i < cli_list_first_col.size(); i++)
		{
			System.out.printf("   %s --- %s --- %s\n", cli_list_first_col.get(i),
					cli_list_second_col.get(i), cli_list_third_col.get(i));
		}
		System.out.println("��������������������������������������������������������������������������������");
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









