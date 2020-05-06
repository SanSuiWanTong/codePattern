//Singleton java
class Singleton{
	private volatile static Singleton uniqueInstance;
	private Singleton(){}
	public static Singleton getInstance(){
		if(null == uniqueInstance){
			synchronized(Singleton.class){					//synchronized 
				if(null == uniqueInstance)
					uniqueInstance = new Singleton();
					count++;
			}
		}
		return uniqueInstance;
	}
	public static int count = 0;							//need static
}

public class SingleClient{
	public static void main(String[] args){
		Singleton single_1 = Singleton.getInstance();
		Singleton single_2 = Singleton.getInstance();
		System.out.println("1:" + single_1 + " count:" + single_1.count);
		System.out.println("2:" + single_2 + " count:" + single_2.count);
	}
}