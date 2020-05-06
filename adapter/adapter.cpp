//adapter c++
#include<iostream>
using namespace std;

class Target{
public:
	virtual void request(){
		cout<<"Target request called"<<endl;
	}
};

class Adaptee{
public:
	void specificRequest(){
		cout<<"Adaptee specificRequest called"<<endl;
	}
};

class Adaptor : public Target{
public:
	Adaptor(Adaptee *p):p_adp(p){}
	void request(){
		p_adp->specificRequest();
	}
private:
	Adaptee *p_adp;
};

int main(){
	Adaptee* p_adp    = new Adaptee();
	Target * p_client = new Adaptor(p_adp);
	p_client->request();
	
	return 0;
}