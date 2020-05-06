//proxy c++
#include <iostream>
#include <string>
using namespace std;

class Interface{
public:
	virtual void Request() = 0;
};

class RealClass : public Interface{
public:
	virtual void Request(){
		cout<<"Real request"<<endl;
	}
};

class ProxyClass : public Interface{
public:
	virtual void Request(){
		m_realClass = new RealClass();
		m_realClass->Request();
		delete m_realClass;
	}
private:
	RealClass * m_realClass;
};

int main(){
	ProxyClass* proxy = new ProxyClass();
	proxy->Request();
	return 0;
}