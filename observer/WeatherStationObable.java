//use java Observable
//Observable is a class not interface
import java.util.Observable;
import java.util.Observer;

class WeatherData extends Observable{
	private float temperature;
	private float humidity;
	private float pressure;
	
	public WeatherData(){}
	public void measurementsChanged(){
		setChanged();
		notifyObservers();
	}
	public void setMeasuremants(float temperature,float humidity,float pressure){
		this.temperature = temperature;
		this.humidity    = humidity;
		this.pressure    = pressure;
		measurementsChanged();
	}
	public float getTempreature(){
		return temperature;
	}
	public float getHumidity(){
		return humidity;
	}
	public float getPressure(){
		return pressure;
	}
}

//
interface DisplayElement {
	public void display();
}

class CurrentConditionDisplay implements Observer,DisplayElement{
	Observable observable;
	private float temperature;
	private float humidity;
	
	public CurrentConditionDisplay(Observable observable){
		this.observable = observable;
		observable.addObserver(this);
	}
	@Override
	public void update(Observable obs,Object arg){
		if(obs instanceof WeatherData){
			WeatherData weatherData = (WeatherData)obs;
			this.temperature        = weatherData.getTempreature();
			this.humidity           = weatherData.getHumidity();
			display();
		}
	}
	public void display(){
		System.out.println("Current conditions: " + temperature + "F degrees and " + humidity + "% humidity");
	}
}
             
public class WeatherStationObable{
	public static void main(String[] args){
		WeatherData weatherData = new WeatherData();
		CurrentConditionDisplay display_1 = new CurrentConditionDisplay(weatherData);
		
		weatherData.setMeasuremants(21,34,65);
		weatherData.setMeasuremants(31,42,75);
	}
}

