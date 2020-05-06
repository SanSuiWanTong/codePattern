//mvc java
import java.util.*;

//
interface Observer{
	public void update(QuackObservable duck);
}
class Quackologist implements Observer{
	public void update(QuackObservable duck){
		System.out.println("Quackologist: " + duck + " quack");
	}
}
//6.observable 
interface QuackObservable{
	public void registerObserver(Observer observer);
	public void notifyObservers();
}
class Observable implements QuackObservable{
	ArrayList<Observer> observers = new ArrayList<Observer>();
	QuackObservable duck;
	
	public Observable(QuackObservable duck){
		this.duck = duck;
	}
	public void registerObserver(Observer observer){
		observers.add(observer);
	}
	public void notifyObservers(){
		for(Observer c : observers)
			c.update(duck);
	}
}
//
interface Quackable extends QuackObservable{
	public void quack();
}
class RedheadDuck implements Quackable{
	Observable observable;
	public RedheadDuck(){
		observable = new Observable(this);
	}
	public void quack(){
		System.out.println("redhead Quack");
		notifyObservers();
	}
	public void registerObserver(Observer observer){
		observable.registerObserver(observer);
	}
	public void notifyObservers(){
		observable.notifyObservers();
	}
}
class MallardDuck implements Quackable{
	Observable observable;
	public MallardDuck(){
		observable = new Observable(this);
	}
	public void quack(){
			System.out.println("mallard Quack");
			notifyObservers();
	}
	public void registerObserver(Observer observer){
		observable.registerObserver(observer);
}
	public void notifyObservers(){
		observable.notifyObservers();
	}
	public String toString() {
		return "Mallard Duck";
	}
}

//4.combinate
class Flock implements Quackable{
	ArrayList<Quackable> quackers = new ArrayList<Quackable>();
	public void add(Quackable quacker){
		quackers.add(quacker);
	}
	public void quack(){
		Iterator iterator = quackers.iterator();//5.Iterator
		while(iterator.hasNext()){
			Quackable quacker = (Quackable)iterator.next();
			quacker.quack();
		}
	}
	public void registerObserver(Observer observer){
	}
	public void notifyObservers(){
	}
}

//Goose
class Goose{
	public void honk(){
		System.out.println("Honk");
	}
}
//1.GooseAdpter
class GooseAdpter implements Quackable{
	Goose goose;
	public GooseAdpter(Goose goose){
		this.goose = goose;
	}
	public void quack(){
		goose.honk();
	}
	public void registerObserver(Observer observer){
	}
	public void notifyObservers(){
	}
}

//2.decorate
class QuackCounter implements Quackable{
	Quackable duck;
	static int numberOfQuacks;
	public QuackCounter(Quackable duck){
		this.duck = duck;
	}
	public void quack(){
		duck.quack();
		numberOfQuacks++;
	}
	public static int getQuacks(){
		return numberOfQuacks;
	}
	public void registerObserver(Observer observer){
		duck.registerObserver(observer);
	}
	public void notifyObservers(){
		duck.notifyObservers();
	}
}

//3.factory
abstract class AbstractDuckFactory{
	public abstract Quackable createMallardDuck();
	public abstract Quackable createRedheadDuck();
} 

class CountingDuckFactory extends AbstractDuckFactory{
	public Quackable createMallardDuck(){
		return new QuackCounter(new MallardDuck());
	}
	public Quackable createRedheadDuck(){
		return new QuackCounter(new RedheadDuck());
	}
}
//main
public class MvcDuck{
	public static void main(String[] args){
		MvcDuck mvcDuck = new MvcDuck();
		AbstractDuckFactory duckFactory = new CountingDuckFactory();
		mvcDuck.simulate(duckFactory);
	}
	void simulate(AbstractDuckFactory duckFactory){
		Quackable redheadDuck = duckFactory.createRedheadDuck();
		Quackable mallardDuck = duckFactory.createMallardDuck();
		Quackable gooseDuck   = new GooseAdpter(new Goose());			//Adpter

		System.out.println("Duck Simulator");
		
		//test observer
		Quackologist quackologist = new Quackologist();
		mallardDuck.registerObserver(quackologist);

		//test combinate
		Flock flockOfDucks = new Flock();
		flockOfDucks.add(redheadDuck);
		flockOfDucks.add(mallardDuck);
		
		//manage a group
		simulate(flockOfDucks);
		
		//
		simulate(gooseDuck);
		
		System.out.println("The duck quack number:" + QuackCounter.getQuacks());
	}
	void simulate(Quackable duck){
		duck.quack();
	}
}