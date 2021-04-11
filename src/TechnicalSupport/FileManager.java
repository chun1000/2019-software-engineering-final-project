package TechnicalSupport;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileManager 
{
	private static  String file_directory = "Data";
	private static String file_of_food = "Food.dat";
	private static String file_of_kitchen = "kitchen.dat";
	
	private static void makeNewDirectoryNFile()
	{
		try
		{
			File folder = new File(file_directory);
			if(!folder.exists())
			{
					folder.mkdir();
			}
			
			File file = new File(file_directory + "\\" + file_of_food);
			PrintWriter print_writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			print_writer.println("노루향\t짜장면\t6000");
			print_writer.println("노루향\t짬뽕\t7000");
			print_writer.println("노루향\t탕수육\t12000");
			print_writer.println("팔매\t된장찌개\t8000");
			print_writer.println("팔매\t막국수\t4000");
			print_writer.println("팔매\t순두부찌개\t7000");
			print_writer.println("스페셜쉐프\t스테이크\t9000");
			print_writer.println("스페셜쉐프\t스파게티\t8000");
			print_writer.println("CongCong\tnoodle\t9000");
			print_writer.println("CongCong\trice\t8000");
			print_writer.close();
			
			file = new File(file_directory + "\\" + file_of_kitchen);
			print_writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			print_writer.println("노루향");
			print_writer.println("팔매");
			print_writer.println("스페셜쉐프");
			print_writer.println("CongCong");
			print_writer.close();
			
			System.out.println("데이터 폴더가 손상되어, 새로 생성하였습니다. 프로그램을 다시 시작해주세요.");
			System.exit(0);
		}
		catch(Exception ee)
		{
			
		}
	}
	
	//파라미터로 들어온 kitchen_name과 food_name, food_price를 파일로부터 읽어와서 줍니다. max_food_number에 배열의 크기를 입력해주어야합니다.
	//예를들어 중국집의 6000원짜리 짜장면은 kitchen_name[0] = "중국집" food_name[0] = "짜장면" food_price[0] = "6000"으로 반환될 것입니다.
	public static void loadFoodFile(String[] kitchen_name, String[] food_name, String[] food_price, int max_food_number )
	{
		try
		{
			int index = 0;
			File file = new File(file_directory + "\\" + file_of_food);
			FileReader file_reader = new FileReader(file);
			BufferedReader buffered_reader = new BufferedReader(file_reader);
			String tmp = ""; String[] splited_string = new String[3];
			
			while(((tmp = buffered_reader.readLine()) != null)&&(index < max_food_number))
			{
				splited_string = tmp.split("\t");
				kitchen_name[index] = splited_string[0];
				food_name[index] = splited_string[1];
				food_price[index] = splited_string[2];
				
				index++;
			}
			
			buffered_reader.close();
		}
		catch (FileNotFoundException e)
		{
			try
			{
				makeNewDirectoryNFile();
			}
			catch(Exception ee)
			{
				
			}
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
	
	//파라미터로 입력된 kitchen_name에 파일의 내용을 불러서 반환해줍니다. max_kitchen에 배열의 크기를 입력해야 합니다.
	//예를들어 중식을 다루는 중국집은 kitchen_name[0] = "중국집"과 같이 반환될 것입니다.
	public static void loadCategoryFile(String[] kitchen_name, int max_kitchen_number)
	{
		try
		{
			int index = 0;
			File file = new File(file_directory + "\\" + file_of_kitchen);
			FileReader file_reader = new FileReader(file);
			BufferedReader buffered_reader = new BufferedReader(file_reader);
			String tmp = "";
			
			while(((tmp = buffered_reader.readLine()) != null)&&(index < max_kitchen_number))
			{
				kitchen_name[index] = tmp;
				index++;
			}
			
			buffered_reader.close();
			file_reader.close();
		}
		catch (FileNotFoundException e)
		{
			makeNewDirectoryNFile();
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
	
	//파라미터로 들어온 주방명, 음식이름, 음식가격의 쌍을 저장합니다.
	public static void saveFoodFile(String[] kitchen_name, String[] food_name, String[] food_price, int max_food_number )
	{
		try
		{
			File folder = new File(file_directory);
			if(!folder.exists())
			{
					folder.mkdir();
			}
			
			int index = 0;
			File file = new File(file_directory + "\\" + file_of_food);
			PrintWriter print_writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			
			while(index < max_food_number&&(kitchen_name[index] != null))
			{
				print_writer.printf("%s\t%s\t%s\n", kitchen_name[index], food_name[index], food_price[index]);
				index++;
			}
			
			print_writer.close();
		}
		catch (FileNotFoundException e)
		{
		}
		catch(IOException e)
		{
		}
	} 
	
	//파라미터로 들어온 주방명을 저장합니다.
	public static void saveCategoryFile(String[] kitchen_name, int max_kitchen_number)
	{
		try
		{
			File folder = new File(file_directory);
			if(!folder.exists())
			{
					folder.mkdir();
			}
			
			int index = 0;
			File file = new File(file_directory + "\\" + file_of_kitchen);
			PrintWriter print_writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			
			while((index < max_kitchen_number )&&(kitchen_name[index] != null))
			{
				print_writer.printf("%s\n", kitchen_name[index]);
				index++;
			}
			
			print_writer.close();
		}
		catch (FileNotFoundException e)
		{
		}
		catch(IOException e)
		{
		}
	}
	
	
}