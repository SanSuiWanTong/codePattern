//factory c++
#include <iostream>
#include <string>
using namespace std;

//面团 
class Dough{
public:
	virtual string getIngredientInfo() = 0;
};
//薄饼
class ThinCrustDough : public Dough {
public:
	string getIngredientInfo() {
		return "Thin Crust Dough";
	}
};
//厚的
class ThickCrustDough : public Dough {
public:
	string getIngredientInfo(){
		return "ThickCrust style extra thick crust dough";
	}
};

//酱料
class Sauce {
public:
	virtual string getIngredientInfo() = 0;
};
//意大利红酱
class MarinaraSauce : public Sauce {
public:
	string getIngredientInfo(){
		return "Marinara Sauce";
	}
};
//番茄酱
class PlumTomatoSauce : public Sauce {
public:
	string getIngredientInfo() {
		return "Tomato sauce with plum tomatoes";
	}
};

//奶酪
class Cheese {
public:
	virtual string getIngredientInfo() = 0;
};
//莫泽雷勒干酪
class MozzarellaCheese : public Cheese {
public:
	string getIngredientInfo(){
		return "Shredded Mozzarella";
	}
};
//巴马干酪
class ReggianoCheese : public Cheese {
public:
	string getIngredientInfo(){
		return "Reggiano Cheese";
	}
};

//原材料
class PizzaIngredientFactory{
public:
	virtual Dough  * createDough()  = 0;
	virtual Sauce  * createSauce()  = 0;
	virtual Cheese * createCheese() = 0;
	//...
};

//生产 纽约口味
class NYPizzaIngredientFactory : public PizzaIngredientFactory{
public:
	Dough* createDough(){
		return new ThinCrustDough();
	}
	Sauce* createSauce(){
		return new MarinaraSauce();
	}
	Cheese* createCheese(){
		return new ReggianoCheese();
	}
};

//生产 芝加哥口味
class ChicagoPizzaIngredientFactory : public PizzaIngredientFactory{
public: 
	Dough* createDough(){
		return new ThickCrustDough();
	}
	Sauce* createSauce(){
		return new PlumTomatoSauce();
	}
	Cheese* createCheese(){
		return new MozzarellaCheese();
	}
};

//披萨
class Pizza{
public:
	virtual void prepare() = 0;           //统一原材料配给
	
	void bake()                { cout<<"Bake for 25 minutes..."<<endl; }
	void box()                 { cout<<"box done"<<endl; }
	
	void   setName(string name){ this->name = name; }
	string getName()           { return this->name; }
	string getIngredientInfo() {
		string str = "---- ";
			   str += this->name;
			   str += " ----\n";
		if (dough) {
			str += dough->getIngredientInfo() + "\n\r";
		}
		if (sauce) {
			str += sauce->getIngredientInfo() + "\n\r";
		}
		if (cheese) {
			str += cheese->getIngredientInfo() + "\n\r";
		}
		return str;
	}

	string  name;
	Dough * dough;  //饼
	Sauce * sauce;  //酱料
	Cheese* cheese; //奶酪
};

//生产奶酪披萨
class CheesePizza : public Pizza{
public:
	CheesePizza(PizzaIngredientFactory* factory): m_ingredientFactory(factory){}
	void prepare(){
		cout<<"Preparing "<<this->getName()<<endl;
		dough  = m_ingredientFactory->createDough();
		sauce  = m_ingredientFactory->createSauce();
		cheese = m_ingredientFactory->createCheese(); 
	}
private:
	PizzaIngredientFactory* m_ingredientFactory;
};

//披萨店
class PizzaStore{
public:
	virtual Pizza* createPizza(string type) = 0; //不同地域口味的披萨
	Pizza* orderPizza(string type)              //统一品牌流程
	{
		Pizza* pizza = createPizza(type);
		pizza->prepare();
		pizza->bake();
		pizza->box();
		return pizza;
	}
};

//纽约的加盟店
class NYPizzaStore : public PizzaStore{
public:
	Pizza* createPizza(string type){
		Pizza *pizza = NULL;
		PizzaIngredientFactory* ingredientFactory = new NYPizzaIngredientFactory();
		if("cheese" == type){
			pizza = new CheesePizza(ingredientFactory);
			pizza->setName("New York Style Cheese Pizza");
		}
		else if("veggie" == type){
			//...
		}
		return pizza;
	}
};

//芝加哥的
class ChicagoPizzaStore : public PizzaStore{
public:
	Pizza* createPizza(string type){
		Pizza *pizza = NULL;
		PizzaIngredientFactory* ingredientFactory = new ChicagoPizzaIngredientFactory();
		if("cheese" == type){
			pizza = new CheesePizza(ingredientFactory);
			pizza->setName("Chicago Style Cheese Pizza");
		}
		else if("veggie" == type){
			//...
		}
		return pizza;
	}
};

//点餐
int main()
{
	//1
	PizzaStore *nyStore = new NYPizzaStore();
	Pizza      *pizza   = nyStore->orderPizza("cheese");
	cout<<"order a " <<pizza->getIngredientInfo()<<endl;
	//2
	PizzaStore *chStore = new ChicagoPizzaStore();
				pizza   = chStore->orderPizza("cheese");
	cout<<"order a " <<pizza->getIngredientInfo();
	return 0;
}