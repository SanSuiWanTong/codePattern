//strategy c++
#include <iostream>
#include <memory>
using namespace std;

//FlyBehavior
class FlyBehavior{
public:
	virtual void fly() = 0;
};

class FlyWithWings : public FlyBehavior{
public:
	void fly(){
		cout<<"I`m flying!"<<endl;
	}
};

class FlyNoWay : public FlyBehavior{
public:
	void fly(){
		cout<<"I can`t fly!"<<endl;
	}
};

//quack
class QuackBehavior{
public:
	virtual void quack() = 0;
};

class Quack : public QuackBehavior{
public:
	void quack(){
		cout<<"Quack"<<endl;
	}
};

class MuteQuack : public QuackBehavior{
public:
	void quack(){
		cout<<"Silence"<<endl;
	}
};

//Duck
class Duck{

public:
	Duck(){};
	Duck(shared_ptr<FlyBehavior> pfb,shared_ptr<QuackBehavior> pqb) :
	m_pflyBehavior(pfb),
	m_pquackBehavior(pqb){};

	virtual void display() = 0;								//pure virtual 不能实例化
	
	void setFlyBehavior(shared_ptr<FlyBehavior> pfb)		{ m_pflyBehavior = pfb;  }
	void performFly() 										{ m_pflyBehavior->fly(); }
	
	void setQuackBehavior(shared_ptr<QuackBehavior> pqb)	{ m_pquackBehavior = pqb;    }
	void performQuack() 									{ m_pquackBehavior->quack(); }
	
	void swim()												{ cout<<"All ducks can swim."<<endl; }
	
private:
	shared_ptr<FlyBehavior>   m_pflyBehavior;   //fly
	shared_ptr<QuackBehavior> m_pquackBehavior; //quack
};

class MallardDuck : public Duck{

public:
	MallardDuck(){
		setFlyBehavior(make_shared<FlyWithWings>());
		setQuackBehavior(make_shared<Quack>());
	}

	MallardDuck(shared_ptr<FlyBehavior> fb,shared_ptr<QuackBehavior> pb) : Duck(fb,pb){}

	void display(){
		cout<<"I`m a real Mallard duck"<<endl;
	}
};

//main
int main()
{
	//mallard_1
	Duck* mallard_1 = new MallardDuck();
	mallard_1->performFly();
	mallard_1->performQuack();
	mallard_1->display();
	mallard_1->swim();
	
	//mallard_2
	cout<<"\r\nmallard_2:\r\n";
	auto fly = make_shared<FlyWithWings>();
	auto quk = make_shared<MuteQuack>();
	Duck* mallard_2 = new MallardDuck(fly,quk);
	mallard_2->performFly();
	mallard_2->performQuack();
	
	//set fly
	[](){cout<<"\r\nset fly:\r\n";}();
	mallard_2->setFlyBehavior(make_shared<FlyNoWay>());
	mallard_2->performFly();

	return 0;
}


