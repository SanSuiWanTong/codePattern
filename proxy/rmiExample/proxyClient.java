//proxy java client
import java.rmi.*;

public  class proxyClient{
	public static void main(String[] args){
		new proxyClient().go();
	}
	public void go(){
		try{
			MyRemote service = (MyRemote) Naming.lookup("rmi://127.0.0.1/RemoteHello");
			String s = service.sayHello();
			System.out.println(s);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}