//factory java

//PizzaStore
abstract class PizzaStore{
	public final Pizza orderPizza(String type){ //order same
		Pizza pizza;
		pizza = createPizza(type);              //
		pizza.prepare();
		pizza.bake();
		pizza.box();
		return pizza;
	}
	abstract Pizza createPizza(String type);    //taste different in other area
}

//New York Pizza Store
class NYPizzaStore extends PizzaStore{
	Pizza createPizza(String item){
		Pizza pizza = null;
		PizzaIngredientFactory ingredientFactory = new NYPizzaIngredientFactory();
		if(item.equals("cheese")){
			pizza = new CheesePizza(ingredientFactory);
			pizza.setName("New York Style Cheese Pizza");
		}
		else if(item.equals("veggie")){
			//...
		}
		return pizza;
	}
}
//Chicago Pizza Store
class ChicagoPizzaStore extends PizzaStore{
	Pizza createPizza(String item){
		Pizza pizza = null;
		PizzaIngredientFactory ingredientFactory = new ChicagoPizzaIngredientFactory();
		if(item.equals("cheese")){
			pizza = new CheesePizza(ingredientFactory);
			pizza.setName("Chicago Style Cheese Pizza");
		}
		else if(item.equals("veggie")){
			//...
		}
		return pizza;
	}
}

//Pizza
abstract class Pizza{
	String name;
	Dough  dough;
	Sauce  sauce;
	Cheese cheese;
	//ArrayList<String> toppings = new ArrayList<String>();
	
	abstract void prepare();   //
	void bake(){
		System.out.println("Bake for 25 minutes...");
	}
	//...
	void box(){
		System.out.println("box done");
	}
	void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("---- " + name + " ----\n");
		if (dough != null) {
			result.append(dough);
			result.append("\n");
		}
		if (sauce != null) {
			result.append(sauce);
			result.append("\n");
		}
		if (cheese != null) {
			result.append(cheese);
			result.append("\n");
		}
		return result.toString();
	}
}

//CheesePizza
//do not need class NYStyleCheesePizza and class ChicagoStyleCheesePizza
class CheesePizza extends Pizza{
	PizzaIngredientFactory ingredientFactory;
	public CheesePizza(PizzaIngredientFactory ingredientFactory){
		this.ingredientFactory = ingredientFactory;
	}
	void prepare(){
		System.out.println("Preparing " + name);
		dough  = ingredientFactory.createDough();
		sauce  = ingredientFactory.createSauce();
		cheese = ingredientFactory.createCheese(); 
	}
}

//ingredient factory
interface PizzaIngredientFactory{
	public Dough  createDough();
	public Sauce  createSauce();
	public Cheese createCheese();
	//...
}

//NYPizzaIngredientFactory
class NYPizzaIngredientFactory implements PizzaIngredientFactory{
	public Dough createDough(){
		return new ThinCrustDough();
	}
	public Sauce createSauce(){
		return new MarinaraSauce();
	}
	public Cheese createCheese(){
		return new ReggianoCheese();
	}
} 

//ChicagoPizzaIngredientFactory
class ChicagoPizzaIngredientFactory implements PizzaIngredientFactory{
	public Dough createDough(){
		return new ThickCrustDough();
	}
	public Sauce createSauce(){
		return new PlumTomatoSauce();
	}
	public Cheese createCheese(){
		return new MozzarellaCheese();
	}
}

//Dough
interface Dough {
	public String toString();
}
class ThinCrustDough implements Dough {
	public String toString() {
		return "Thin Crust Dough";
	}
}
class ThickCrustDough implements Dough {
	public String toString() {
		return "ThickCrust style extra thick crust dough";
	}
}

//Sauce
interface Sauce {
	public String toString();
}
class MarinaraSauce implements Sauce {
	public String toString() {
		return "Marinara Sauce";
	}
}
class PlumTomatoSauce implements Sauce {
	public String toString() {
		return "Tomato sauce with plum tomatoes";
	}
}

//Cheese
interface Cheese {
	public String toString();
}
class MozzarellaCheese implements Cheese {

	public String toString() {
		return "Shredded Mozzarella";
	}
}
class ReggianoCheese implements Cheese {

	public String toString() {
		return "Reggiano Cheese";
	}
}

//SimpleFactory
public class SimpleFactory{
	public static void main(String[] args){
		//1
		PizzaStore nyStore = new NYPizzaStore();
		Pizza      pizza   = nyStore.orderPizza("cheese");
		System.out.println("order a " + pizza);
		
		//2
		PizzaStore chStore = new ChicagoPizzaStore();
		           pizza   = chStore.orderPizza("cheese");
		System.out.println("order a " + pizza);
	}
}

