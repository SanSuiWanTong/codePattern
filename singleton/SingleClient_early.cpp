//Singleton c++
//饿汉模式，线程安全，在进入main函数之前就由单线程方式实例化的
#include <iostream>
using namespace std;

class Singleton{
public:
	static Singleton* getInstance(){ return m_pSingleton; }
private:
	Singleton()=default;
	static Singleton* m_pSingleton;
};

Singleton* Singleton::m_pSingleton = new Singleton();

int main(){
	auto p1 = Singleton::getInstance();
	auto p2 = Singleton::getInstance();
	
	if(p1 == p2)
		cout<<"same, "<<p1;
	
	return 0;
}