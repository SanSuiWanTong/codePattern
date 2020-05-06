//observer c++
#include <iostream>
#include <list>
using namespace std;

//Observer
class Observer{
public:
	virtual void update(float temp,float humidity,float pressure) = 0;
};

//DisplayElement
class DisplayElement{
public:
	virtual void display() = 0;
};

//Subject
class Subject{
public:
	virtual void registerObserver(Observer* o) = 0;
	virtual void removeObserver  (Observer* o) = 0;
	virtual void notifyObservers ()            = 0;
	
};

class WeatherData : public Subject{
public:
	WeatherData(){}
	void registerObserver(Observer* o){
		if(o)
			observers.push_back(o);
	}
	void removeObserver(Observer* o){
		auto it = observers.begin();                   //begin()返回的类型由对象是否为const决定
		while(it != observers.end()){                  //只要执行了erase(it),那么it就会变得无效，
													   //那么执行it++就肯定会出错,erase 返回指向删除后位置，要写 it = erase(it);
			if(*it == o)
				it = observers.erase(it);
		}
	}
	void notifyObservers(){
		for(Observer* obs : observers){
			obs->update(temperature,humidity,pressure);
		}
	}
	void measurementsChanged(){
		notifyObservers();
	}
	void setMeasurements(float temperature,float humidity,float pressure){
		this->temperature = temperature;
		this->humidity    = humidity;
		this->pressure    = pressure;
		measurementsChanged();
	}

private:
	list<Observer*> observers;
	float temperature;
	float humidity;
	float pressure;
};

//Current Display
class CurrentConditonDisplay : public Observer,DisplayElement{

public:
	//抽象类型不能用作形参类型，函数返回类型
	CurrentConditonDisplay(WeatherData* weatherData){
		this->weatherData = weatherData;
		weatherData->registerObserver(this);
	}
	void update(float temperature,float humidity,float pressure){
		this->temperature = temperature;
		this->humidity    = humidity;
		display();
	}

	void display(){
		cout<<"\r\nCurrent condition:"<<temperature<<"F degree and "<<humidity<<"%humidity";
	}
	
private:
	float temperature;
	float humidity;
	WeatherData* weatherData;
};

//
int main(){
	WeatherData* weatherData = new WeatherData();        //WeatherData
	CurrentConditonDisplay* currentDisplay = new CurrentConditonDisplay(weatherData);

	weatherData->setMeasurements(80,65,26.3f);
	weatherData->setMeasurements(89,61,30.3f);
	
	//remove
	weatherData->removeObserver(currentDisplay);
	weatherData->setMeasurements(76,67,12.3f);

	return 0;
}