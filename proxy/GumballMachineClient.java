//proxy GumballMachineRemote client
import java.rmi.*;
class GumballMonitor{
	GumballMachineRemote machine;
	public GumballMonitor(GumballMachineRemote machine){
		this.machine = machine;
	}
	public void report(){
		try{
			System.out.println("Machine:"   + machine.getLocation());
			System.out.println("Inventory:" + machine.getCount());
		}catch(RemoteException e){
			e.printStackTrace();
		}
	}
}

public class GumballMachineClient{
	public static void main(String[] args){
		if(args.length < 1){
			System.out.println("java GumballMachineClient localtion");
			System.exit(1);
		}
		try{
			GumballMachineRemote machine = (GumballMachineRemote)Naming.lookup(args[0]);		
			GumballMonitor monitor = new GumballMonitor(machine);
			monitor.report();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}