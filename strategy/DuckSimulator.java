//strategy java
abstract class Duck{

	public Duck(){}
	
	public abstract void display();
	
	public void performFly(){
		flyBehavior.fly();			//delegate to fly
	}
	
	public void performQuack(){
		quackBehavior.quack();		//delegate to quack
	}
	
	public void swim(){
		System.out.println("All ducks can swim.");
	}

	FlyBehavior   flyBehavior;		//fly
	QuackBehavior quackBehavior;	//quack
}

//MallardDuck
class MallardDuck extends Duck{
	public MallardDuck(){
		flyBehavior   = new FlyWithWings();
		quackBehavior = new Quack();
	}
	
	public MallardDuck(FlyBehavior fb,QuackBehavior qb){
		flyBehavior   = fb;
		quackBehavior = qb;
	}
	
	public void display(){
		System.out.println("I`m a real Mallard duck");
	}
}

//FlyBehavior
interface FlyBehavior{
	public void fly();
}

class FlyWithWings implements FlyBehavior{
	public void fly(){
		System.out.println("I`m flying!");
	}
}

class FlyNoWay implements FlyBehavior{
	public void fly(){
		System.out.println("I can`t fly!");
	}
}

//quack
interface QuackBehavior{
	public void quack();
}

class Quack implements QuackBehavior{
	public void quack(){
		System.out.println("Quack");
	}
}

class MuteQuack implements QuackBehavior{
	public void quack(){
		System.out.println("Silence");
	}
}

class Squack implements QuackBehavior{
	public void quack(){
		System.out.println("Squack");
	}
}

//main
public class DuckSimulator{
	public static void main(String[] args){
		//1
		Duck mallard_1 = new MallardDuck();
		mallard_1.performFly();
		mallard_1.performQuack();
		mallard_1.display();
		mallard_1.swim();
				
		//2
		FlyBehavior 	fly = () -> System.out.println("\r\nflying...");
		QuackBehavior	quk = () -> System.out.println("quacking...");
		Duck mallard_2 = new MallardDuck(fly,quk);
		mallard_2.performFly();
		mallard_2.performQuack();
		
		//set fly
		mallard_2.flyBehavior = () -> System.out.println("\r\nstop flying...");
		mallard_2.performFly();
	}
}
