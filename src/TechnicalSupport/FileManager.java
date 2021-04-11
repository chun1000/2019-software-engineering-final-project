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
			print_writer.println("�����\t¥���\t6000");
			print_writer.println("�����\t«��\t7000");
			print_writer.println("�����\t������\t12000");
			print_writer.println("�ȸ�\t�����\t8000");
			print_writer.println("�ȸ�\t������\t4000");
			print_writer.println("�ȸ�\t���κ��\t7000");
			print_writer.println("����Ƚ���\t������ũ\t9000");
			print_writer.println("����Ƚ���\t���İ�Ƽ\t8000");
			print_writer.println("CongCong\tnoodle\t9000");
			print_writer.println("CongCong\trice\t8000");
			print_writer.close();
			
			file = new File(file_directory + "\\" + file_of_kitchen);
			print_writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			print_writer.println("�����");
			print_writer.println("�ȸ�");
			print_writer.println("����Ƚ���");
			print_writer.println("CongCong");
			print_writer.close();
			
			System.out.println("������ ������ �ջ�Ǿ�, ���� �����Ͽ����ϴ�. ���α׷��� �ٽ� �������ּ���.");
			System.exit(0);
		}
		catch(Exception ee)
		{
			
		}
	}
	
	//�Ķ���ͷ� ���� kitchen_name�� food_name, food_price�� ���Ϸκ��� �о�ͼ� �ݴϴ�. max_food_number�� �迭�� ũ�⸦ �Է����־���մϴ�.
	//������� �߱����� 6000��¥�� ¥����� kitchen_name[0] = "�߱���" food_name[0] = "¥���" food_price[0] = "6000"���� ��ȯ�� ���Դϴ�.
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
	
	//�Ķ���ͷ� �Էµ� kitchen_name�� ������ ������ �ҷ��� ��ȯ���ݴϴ�. max_kitchen�� �迭�� ũ�⸦ �Է��ؾ� �մϴ�.
	//������� �߽��� �ٷ�� �߱����� kitchen_name[0] = "�߱���"�� ���� ��ȯ�� ���Դϴ�.
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
	
	//�Ķ���ͷ� ���� �ֹ��, �����̸�, ���İ����� ���� �����մϴ�.
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
	
	//�Ķ���ͷ� ���� �ֹ���� �����մϴ�.
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