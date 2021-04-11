package FoodCourt;

public class Food implements Comparable<Food> {
	
	private String food_name;
	private Integer price;
	
	//���� �Լ����Դϴ�.
	public String getFoodName() {
		return food_name;
	}
	
	public Integer getPrice() {
		return price;
	}

	//���� �Լ����� ������ �Լ����Դϴ�.
	
	public Food(String food_name, Integer price) {
		
		this.food_name = food_name;
		this.price = price;
	}

	@Override
	public int compareTo(Food o) {
		return this.food_name.compareTo(o.getFoodName());
	}
	
}