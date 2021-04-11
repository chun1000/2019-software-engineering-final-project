package FoodCourt;

public class Food implements Comparable<Food> {
	
	private String food_name;
	private Integer price;
	
	//접근 함수들입니다.
	public String getFoodName() {
		return food_name;
	}
	
	public Integer getPrice() {
		return price;
	}

	//접근 함수들을 제외한 함수들입니다.
	
	public Food(String food_name, Integer price) {
		
		this.food_name = food_name;
		this.price = price;
	}

	@Override
	public int compareTo(Food o) {
		return this.food_name.compareTo(o.getFoodName());
	}
	
}