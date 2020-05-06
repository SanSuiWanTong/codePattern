//interator java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
//CompositeIterator
class CompositeIterator implements Iterator<MenuComponent>{
	Stack<Iterator<MenuComponent>> stack = new Stack<Iterator<MenuComponent>>();
	public CompositeIterator(Iterator<MenuComponent> iterator){
		stack.push(iterator);
	}
	public MenuComponent next(){
		if(hasNext()){
			Iterator<MenuComponent> iterator = stack.peek();
			MenuComponent component = iterator.next();
			if(component instanceof Menu){
				stack.push(component.createIterator());
			}
			return component;
		}
		else{
			return null;
		}
	}
	public boolean hasNext(){
		if(stack.empty()){
			return false;
		}else{
			Iterator<MenuComponent> iterator = stack.peek();
			if(!iterator.hasNext()){
				stack.pop();
				return hasNext();
			}else{
				return true;
			}
		}
	}
	public void remove(){
		throw new UnsupportedOperationException();
	}
}
class NullIterator implements Iterator<MenuComponent>{
	public MenuComponent next(){
		return null;
	}
	public boolean hasNext(){
		return false;
	}
	public void remove(){
		throw new UnsupportedOperationException();
	}
}

//MenuComponent
abstract class MenuComponent{
	public void add(MenuComponent menuComponent){
		throw new UnsupportedOperationException();
	}
	public void remove(MenuComponent menuComponent){
		throw new UnsupportedOperationException();
	}
	public MenuComponent getChild(int i){
		throw new UnsupportedOperationException();
	}
	public String getName(){
		throw new UnsupportedOperationException();
	}
	public String getDescription(){
		throw new UnsupportedOperationException();
	}
	public double getPrice(){
		throw new UnsupportedOperationException();
	}
	public boolean isVegetarian(){
		throw new UnsupportedOperationException();
	}
	public void print(){
		throw new UnsupportedOperationException();
	}
	//use it to composite all child
	public Iterator<MenuComponent> createIterator(){
		throw new UnsupportedOperationException();
	}
}

//
class MenuItem extends MenuComponent{
	String  name;
	String  description;
	boolean vegetarian;
	double  price;
	
	public MenuItem(String  name,
	                String  description,
					boolean vegetarian,
					double  price)
	{
		this.name        = name;
		this.description = description;
		this.vegetarian  = vegetarian;
		this.price       = price;
	}
	public String getName(){
		return this.name;
	}
	public String getDescription(){
		return this.description;
	}
	public boolean isVegetarian(){
		return vegetarian;
	}
	public double getPrice(){
		return this.price;
	}
	public void print(){
		System.out.print(" " + getName());
		if(isVegetarian()){
			System.out.print("(v)");
		}
		System.out.println(", Price: " + getPrice());
		System.out.println("       - - " + getDescription());
	}
	public Iterator<MenuComponent> createIterator(){
		return new NullIterator(); //use null object 
	}
}
//Menu is a composite
class Menu extends MenuComponent{
	ArrayList<MenuComponent> menuComponents = new ArrayList<MenuComponent>();
	String name;
	String description;
	
	public Menu(String name,String description){
		this.name        = name;
		this.description = description;
	}
	public void add(MenuComponent menuComponent){
		menuComponents.add(menuComponent);
	}
	public void remove(MenuComponent menuComponent){
		menuComponents.remove(menuComponent);
	}
	public MenuComponent getChild(int i){
		return menuComponents.get(i);
	}
	public String getName(){
		return name;
	}
	public String getDescription(){
		return description;
	}
	public void print(){
		System.out.print("\n" + getName());
		System.out.println(", " + getDescription());
		System.out.println("- - - - - - - - - - - - - - - - - ");
		//use iterator
		Iterator<MenuComponent> iterator = menuComponents.iterator();
		while(iterator.hasNext()){
			MenuComponent menuComponent = iterator.next();
			menuComponent.print();
		}
	}
	public Iterator<MenuComponent> createIterator(){
		return new CompositeIterator(menuComponents.iterator());
	}	
}

//Waitress
class Waitress{
	MenuComponent allMenus;

	public Waitress(MenuComponent allMenus){
		this.allMenus = allMenus;
	}
	public void printMenu(){
		allMenus.print();
	}
	public void printVegetarianMenu(){
		Iterator<MenuComponent> iterator = allMenus.createIterator();
		System.out.println("\nVEGETTARIAN MENU\n - - - ");
		while(iterator.hasNext()){
			MenuComponent menuComponent = iterator.next();
			try{
				if(menuComponent.isVegetarian()){
					menuComponent.print();
				}
			}catch(UnsupportedOperationException e){}
		}
	}
}

//main
class iterator_composite{
	public static void main(String[] args){
		
		MenuComponent pancakeHouseMenu = new Menu("PANCAKE HOUSE MENU","Break fast");
		MenuComponent dinerMenu        = new Menu("DINER MENU"        ,"Lunch"     );
		MenuComponent dessert          = new Menu("DESSERT MENU"      ,"Sessert"   );
		
		MenuComponent allMenus = new Menu("ALL MENUS","All menus combined");
		allMenus.add(pancakeHouseMenu);
		allMenus.add(dinerMenu);
		allMenus.add(dessert);
		
		//add:pancakeHouseMenu 
		pancakeHouseMenu.add(new MenuItem("K&B's Pancake Breakfast",
		                                  "Pancakes with scrambled eggs,and toast",
				                          true,
				                          1.99));
		pancakeHouseMenu.add(new MenuItem("Regular Pancake Breakfast",
		                                  "Pancakes with fried eggs,sausage",
				                          false,
				                          2.99));
		//add:dinerMenu
		dinerMenu.add(new MenuItem("Vegetarian BLT",
								   "Bacon with lettuce & tomato on whole wheat",
								   true,
								   1.99));
		dinerMenu.add(new MenuItem("Soup of the day",
		                           "Soup of the day,with a side of potato salad",
				                   false,
				                   2.66));
		dinerMenu.add(new MenuItem("Hotdog",
		                           "with saurkraut,relish,onions,topped with cheese",
				                   false,
				                   3.05));
		//add:dessert
		dessert.add(new MenuItem("Pasta",
		                         "with Marinara Sauce,and a slice of sourdough bread",
				                 true,
				                 0.99));
		//
		Waitress waitress = new Waitress(allMenus);
		waitress.printMenu();
		waitress.printVegetarianMenu();
	}
}