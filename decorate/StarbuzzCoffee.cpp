//decorate c++
#include <iostream>
#include <string>
using namespace std;

//基类 饮料
class Beverage{
public:
	Beverage(string s): description(s){}
	virtual string getDescription(){                 //配料需覆盖，饮料不用
		return description;
	}
	virtual double cost() = 0;
private:
	string description;
};

//子类 饮料:浓咖啡
class Espresso : public Beverage{
public:
	Espresso() : Beverage("Espresso"){}               //不能在子类初始化列表初始化父类成员
	double cost(){
		return 1.99;
	}
};

//子类 饮料:混合
class HouseBlend : public Beverage{
public:
	HouseBlend() : Beverage("House Blend coffee"){}
	double cost(){
		return 0.89;
	}
};

//配料
class CondimentDecorator : public Beverage{
public:
	CondimentDecorator(string s): Beverage(s){}
};

//配料:摩卡
class Mocha : public CondimentDecorator{
public:
	Mocha(Beverage* beverage) : m_pbeverage(beverage),CondimentDecorator("Mocha"){}
	string getDescription(){
		return m_pbeverage->getDescription() + ",Mocha";
	}
	double cost(){
		return .20 + m_pbeverage->cost();
	}
private:
	Beverage* m_pbeverage;
};

//配料:鲜奶油
class Whip : public CondimentDecorator{
public:
	Whip(Beverage* beverage) : m_pbeverage(beverage),CondimentDecorator("Whip"){}
	string getDescription(){
		return m_pbeverage->getDescription() + ",Whip";
	}
	double cost(){
		return .30 + m_pbeverage->cost();
	}
private:
	Beverage* m_pbeverage;
};

//
int main(){
	//beverage*1
	Beverage* beverage_1 = new Espresso();
	cout<<beverage_1->getDescription()<<" $"<<beverage_1->cost()<<endl;
	
	//Mocha*2 Whip*1
	Beverage* beverage_2 = new Espresso();
	beverage_2 = new Mocha(beverage_2);
	beverage_2 = new Mocha(beverage_2);
	beverage_2 = new Whip(beverage_2);
	cout<<beverage_2->getDescription()<<" $"<<beverage_2->cost()<<endl;

	return 0;
}