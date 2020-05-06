//proxy GumballMachineRemote server
import java.rmi.*;
import java.io.*;
import java.rmi.server.*;

interface State extends Serializable{  //Serializable
/*
	public void insertQuarter();
	public void ejectQuarter();
	public void turnCrank();
	public void dispense();
*/
}

class NoQuarterState implements State{
	transient GumballMachine gumballMachine; //transient
	
}

interface GumballMachineRemote extends Remote{
	public int    getCount()    throws RemoteException;
	public String getLocation() throws RemoteException;
	public State  getState()    throws RemoteException;
}

class GumballMachine extends UnicastRemoteObject implements GumballMachineRemote{
	private int    count;
	private String location;
	private State  state;
	
	public GumballMachine(String location,int numberGumballs) throws RemoteException{
		this.location = location;
		this.count    = numberGumballs;
	}
	public int getCount(){
		return this.count;
	}
	public State getState(){
		return this.state;
	}
	public String getLocation(){
		return this.location;
	}
	public void dispense(){
		System.out.println("null");
	}
}

public class GumballMachineDrive{
	public static void main(String[] args){

		if(args.length < 2){
			System.out.println("java GumballMachineDrive location 2");
			System.exit(1);
		}
		
		try{
			int count = Integer.parseInt(args[1]);
			GumballMachine server = new GumballMachine(args[0],count);
			Naming.rebind(args[0],server);    
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}