//state java
class GumballMachine{
	State soldOutState;
	State noQuarterState;
	State hasQuarterState;
	State soldState;
	
	State state = soldOutState;
	int count = 0;
	public GumballMachine(int numberGumballs){
		soldOutState    = new SoldOutState(this);
		noQuarterState  = new NoQuarterState(this);
		hasQuarterState = new HasQuarterState(this);
		soldState       = new SoldState(this);
		
		this.count = numberGumballs;
		if(numberGumballs > 0){
			state = noQuarterState;
		}else{
			state = soldOutState;
		}
	}
	
	public void insertQuarter(){
		state.insertQuarter();
	}
	public void ejectQuarter(){
		state.ejectQuarter();
	}
	public void turnCrank(){
		state.turnCrank();
		state.dispense();
	}	
	void releaseBall(){
		System.out.println("A gumball comes rolling out of the slot");
		if(count != 0){
			count = count -1;
		}
	}
	//change state
	void setState(State state){
		this.state = state;
	}
	public State getState(){
		return this.state;
	}
	public State getSoldOutState(){
		return soldOutState;
	}
	public State getNoQuarterState(){
		return noQuarterState;
	}
	public State getHasQuarterStete(){
		return hasQuarterState;
	}
	public State getSoldState(){
		return soldState;
	}
	public int getCount(){
		return count;
	}
	void refill(int count){
		this.count += count;
		System.out.println("Refill: " + count);
		state.refill();		
	}
	public String toString(){
		StringBuffer result = new StringBuffer();
		result.append("\nInventory number: " + count + "\n");
		result.append("state: " + state + "\n");
		return result.toString();
	}
}

//State
interface State{
	public void insertQuarter();
	public void ejectQuarter();
	public void turnCrank();
	public void dispense();
	public void refill();
}

class SoldOutState implements State{
	GumballMachine gumballMachine;
	public SoldOutState(GumballMachine gumballMachine){
		this.gumballMachine = gumballMachine;
	}
	public void insertQuarter(){
		System.out.println("can`n do it no gumball");
	}
	public void ejectQuarter(){
		System.out.println("can`n do it no gumball");
	}
	public void turnCrank(){
		System.out.println("can`n do it no gumball");
	}	
	public void dispense(){
		System.out.println("can`n do it no gumball");
	}	
	public String toString(){
		return "sold out";
	}
	public void refill(){
		gumballMachine.setState(gumballMachine.getNoQuarterState());
	}
}

class NoQuarterState implements State{
	GumballMachine gumballMachine;
	public NoQuarterState(GumballMachine gumballMachine){
		this.gumballMachine = gumballMachine;
	}
	public void insertQuarter(){
		System.out.println("You inserted a quarter");
		gumballMachine.setState(gumballMachine.getHasQuarterStete());
	}
	public void ejectQuarter(){
		System.out.println("You haven`t nserted a quarter");
	}
	public void turnCrank(){
		System.out.println("there is no quarter");
	}
	public void dispense(){
		System.out.println("You need to pay first");
	}
	public String toString(){
		return "waiting for quarter";
	}
	public void refill(){}
}

class HasQuarterState implements State{
	GumballMachine gumballMachine;
	public HasQuarterState(GumballMachine gumballMachine){
		this.gumballMachine = gumballMachine;
	}
	public void insertQuarter(){
		System.out.println("You can`t insert another quarter");
	}
	public void ejectQuarter(){
		System.out.println("Quarter returned");
		gumballMachine.setState(gumballMachine.getNoQuarterState());
	}
	public void turnCrank(){
		System.out.println("your turn...");
		gumballMachine.setState(gumballMachine.getSoldState());
	}
	public void dispense(){
		System.out.println("No gumball dispense");
	}
	public String toString(){
		return "waiting for turn of crank";
	}
	public void refill(){}
}

class SoldState implements State{
	GumballMachine gumballMachine;
	public SoldState(GumballMachine gumballMachine){
		this.gumballMachine = gumballMachine;
	}
	public void insertQuarter(){
		System.out.println("can`n do it");
	}
	public void ejectQuarter(){
		System.out.println("can`n do it");
	}
	public void turnCrank(){
		System.out.println("can`n do it");
	}	
	public void dispense(){
		gumballMachine.releaseBall();
		if(gumballMachine.getCount()>0)
			gumballMachine.setState(gumballMachine.getNoQuarterState());
		else
			gumballMachine.setState(gumballMachine.getSoldOutState());
	}
	public String toString(){
		return "sold State";
	}
	public void refill(){}
}

class gumballMachineTest{
	public static void main(String[] args){
		GumballMachine machine = new GumballMachine(2);
		System.out.println(machine);
		
		System.out.println("1>");
		machine.insertQuarter();
		machine.turnCrank();
		
		System.out.println("\n2>");
		machine.insertQuarter();
		machine.turnCrank();
		
		System.out.println("\n3>");
		machine.insertQuarter();
		
		System.out.println("\nRefill>");
		machine.refill(3);
		
		System.out.println("\n4>");
		machine.insertQuarter();
		machine.turnCrank();
	}
}
