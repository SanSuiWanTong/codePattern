//decorate java
//base class
abstract class Beverage{
	String description = "Unknown Beverage";
	public String getDescription(){
		return description;
	}
	public abstract double cost();
}
abstract class CondimentDecorator extends Beverage{
	public abstract String getDescription();
}

//Espresso
class Espresso extends Beverage{
	public Espresso(){
		description = "Espresso";
	}
	public double cost(){
		return 1.99;
	}
}

//HouseBlend
class HouseBlend extends Beverage{
	public HouseBlend(){
		description = "House Blend coffee";
	}
	public double cost(){
		return 0.89;
	}
}
//Mocha
class Mocha extends CondimentDecorator{
	Beverage beverage;
	public Mocha(Beverage beverage){
		this.beverage = beverage;
	}
	public String getDescription(){
		return beverage.getDescription() + ",Mocha";
	}
	public double cost(){
		return .20 + beverage.cost();
	}
}

//Whip
class Whip extends CondimentDecorator{
	Beverage beverage;
	public Whip(Beverage beverage){
		this.beverage = beverage;
	}
	public String getDescription(){
		return beverage.getDescription() + ",Whip";
	}
	public double cost(){
		return .30 + beverage.cost();
	}
}
//main
public class StarbuzzCoffee{
	public static void main(String[] args){
		//a beverage
		Beverage beverage = new Espresso();
		System.out.println(beverage.getDescription() + " $" + beverage.cost());
		
		//Mocha*2 Whip*1
		Beverage beverage2 = new Espresso();
		beverage2 = new Mocha(beverage2);
		beverage2 = new Mocha(beverage2);
		beverage2 = new Whip(beverage2);
		System.out.println(beverage2.getDescription() + " $" + beverage2.cost());
	}
}