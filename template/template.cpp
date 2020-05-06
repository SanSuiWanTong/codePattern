//template c++
#include <iostream>
using namespace std;

class Dog
{
public:
	virtual void introduce() = 0; 
	
};

class Alaska : public Dog{
	void introduce(){
		cout<<"Alaska is a huge dog,balabla..."<<endl;
	}
};

class Golden : public Dog{
	void introduce(){
		cout<<"Golden is a nice guy,balabla..."<<endl;
	}
};

int main(){
	Dog * p = NULL;
	p = new Alaska();
	p->introduce();
	delete p;
	
	p = new Golden();
	p->introduce();
	
	return 0;
}