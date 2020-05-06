//template duckComparable java
import java.util.Arrays;

class Duck implements Comparable{
	String name;
	int    weight;
	public Duck(String name,int weight){
		this.name   = name;
		this.weight = weight;
	}
	public String toString(){
		return name + " weights " + weight;
	}
	public int compareTo(Object object){
		Duck otherDuck = (Duck)object;
		if(this.weight < otherDuck.weight){
			return -1;
		}else if(this.weight == otherDuck.weight){
			return 0;
		}else {
			return 1;
		}
	}
}

public class duckComparable{
	public static void main(String[] args){
		Duck[] ducks = {
			new Duck("kaoya",4),
			new Duck("shaoya",6),
			new Duck("dunya",3),
			new Duck("zhaya",7)
		};
		System.out.println("Before sort:");
		display(ducks);
		
		Arrays.sort(ducks);
		
		System.out.println("\nAfter sort:");
		display(ducks);
	}
	public static void display(Duck[] ducks){
	for(Duck d : ducks)
		System.out.println(d);
	}
}