import java.util.ArrayList;

//Subject
interface Subject{
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers();
}

class WeatherData implements Subject{
	private ArrayList<Observer> observers;
	private float temperature;
	private float humidity;
	private float pressure;
	
	public WeatherData(){
		observers = new ArrayList<Observer>();
	}
	//+
	public void registerObserver(Observer o){
		observers.add(o);
	}
	//-
	public void removeObserver(Observer o){
		int i = observers.indexOf(o);
		if(i >= 0)
			observers.remove(i);
	}
	// )))
	public void notifyObservers(){
		for (Observer observer : observers){
			observer.update(temperature,humidity,pressure);
		}
	}
	public void measurementsChanged(){
		notifyObservers();
	}
	public void setMeasurements(float temperature,float humidity,float pressure){
		this.temperature = temperature;
		this.humidity    = humidity;
		this.pressure    = pressure;
		measurementsChanged();
	}
}
//Observer
interface Observer{
	public void update(float temp,float humidity,float pressure);
}

interface DisplayElement{
	public void display();
}

//Current Display
class CurrentConditonDisplay implements Observer,DisplayElement{
	private float temperature;
	private float humidity;
	private Subject weatherData;                           //Subject
	
	public CurrentConditonDisplay(Subject weatherData){
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
	}
	public void update(float temperature,float humidity,float pressure){
		this.temperature = temperature;
		this.humidity    = humidity;
		display();
	}

	public void display(){
		System.out.println("\r\nCurrent condition:" + temperature + "F degree and " + humidity + "%humidity");
	}
}
public class WeatherStation{
	public static void main(String[] args){
		WeatherData weatherData = new WeatherData();        //WeatherData
		CurrentConditonDisplay currentDisplay = new CurrentConditonDisplay(weatherData);
		//CurrentConditonDisplay currentDisplay2 = new CurrentConditonDisplay(weatherData);
		
		weatherData.setMeasurements(80,65,26.3f);
		weatherData.setMeasurements(89,61,30.3f);
		
		//remove
		weatherData.removeObserver(currentDisplay);
		weatherData.setMeasurements(76,67,12.3f);
	}
}