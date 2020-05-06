//protect proxy java
//use InvocationHandler
import java.lang.reflect.*;

interface PersonBean{
	String getName();
	int getHotOrNotRating();
	void setName(String name);
	void setHotOrNotRating(int rating);
}

class PersonBeanImpl implements PersonBean{
	String name;
	int rating;
	public String getName(){
		return name;
	}
	public int getHotOrNotRating(){
		return rating;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setHotOrNotRating(int rating){
		this.rating = rating;
	}
}

class OwnerInvocationHandler implements InvocationHandler{
	PersonBean person;
	public OwnerInvocationHandler(PersonBean person){
		this.person = person;
	}
	public Object invoke(Object proxy,Method method,Object[] args)
			throws IllegalAccessException{
				try{
				if(method.getName().startsWith("get")){
					return method.invoke(person,args);
				}else if(method.getName().equals("setHotOrNotRating")){
					throw new IllegalAccessException();
				}else if(method.getName().startsWith("set")){
					return method.invoke(person,args);
				}
			}catch(InvocationTargetException e){
				e.printStackTrace();
			}
			return null;
	}
}

public class protectProxy{
	public static void main(String[] args){
		PersonBean joe = new PersonBeanImpl();
		joe.setName("joe");
		PersonBean ownerproxy = getOwnerProxy(joe);
		try{
			ownerproxy.setHotOrNotRating(10);
		}catch(Exception e){
			System.out.println("can not set");
		}
		ownerproxy.setName("joe_change");
		System.out.println(joe.getName());
	}
	
	static PersonBean getOwnerProxy(PersonBean person){
		return (PersonBean)Proxy.newProxyInstance(
				person.getClass().getClassLoader(),
				person.getClass().getInterfaces(),
				new OwnerInvocationHandler(person) );
	}
}
