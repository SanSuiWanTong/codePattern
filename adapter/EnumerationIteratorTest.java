//adapter java
import java.util.*;

class EnumerationIterator implements Iterator<Object>{
	Enumeration<?> m_enum;
	public EnumerationIterator(Enumeration<?> t_enum){
		this.m_enum = t_enum;
	}
	public boolean hasNext(){
		return m_enum.hasMoreElements();
	}
	public Object next(){
		return m_enum.nextElement();
	}
	public void remove(){
		throw new UnsupportedOperationException();
	}
}

class IteratorEnumeration implements Enumeration<Object>{
	Iterator<?> iterator;
	public IteratorEnumeration(Iterator<?> iterator){
		this.iterator = iterator;
	}
	public boolean hasMoreElements(){
		return iterator.hasNext();
	}
	public Object nextElement(){
		return iterator.next();
	}
}

public class EnumerationIteratorTest{
	public static void main(String[] args){
		//
		Vector<String> v = new Vector<String>(Arrays.asList(args));
		Iterator<?> iterator = new EnumerationIterator(v.elements());
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		System.out.print("\n");
		//
		Enumeration<?> enumeraton = new IteratorEnumeration(v.iterator());
		while(enumeraton.hasMoreElements()){
			System.out.println(enumeraton.nextElement());
		}
	}
}