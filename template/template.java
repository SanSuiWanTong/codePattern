//template java 
import java.io.*;

abstract class CaffeineBeverageWithHook{
	void prepareRecipe(){
		boilWater();
		brew();
		pourInCup();
		if(customerWantsCondiments()){				//hook
			addCondiments();
		}
	}
	abstract void brew();
	abstract void addCondiments();
	void boilWater(){
		System.out.println("Boiling water");
	}
	void pourInCup(){
		System.out.println("Pouring into cup");
	}
	boolean customerWantsCondiments(){
		return true;
	}
}

class CoffeeWithHook extends CaffeineBeverageWithHook{
	public void brew(){
		System.out.println("Dipping Coffee through filter");
	}
	public void addCondiments(){
		System.out.println("Adding Sugar and milk");
	}
	public boolean customerWantsCondiments(){
		String answer = getUserInput();
		if(answer.toLowerCase().startsWith("y")){
			return true;
		}else {
			return false;
		}
	}
	private String getUserInput(){
		String answer = null;
		System.out.print("Would you like milk and sugar add (y/n)?");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try{
			answer = in.readLine();
		}catch(IOException ioe){
			System.err.println("IO error");
		}
		if(null == answer)
			return "no";
		return answer;
	}
}

public class template{
	public static void main(String[] args){
		CoffeeWithHook coffeeHook = new CoffeeWithHook();
		System.out.println("Making coffee...");
		coffeeHook.prepareRecipe();
	}
}



