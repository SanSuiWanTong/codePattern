//state c++
#include <iostream>
using namespace std;


class State;
class Alaskan{
public:
	Alaskan(){}
	void setState(State * p){
		m_pState = p;
	}
	void nextState();
public:
	State  * eatState;
	State  * sleepState;
	State  * playState;
private:
	State * m_pState;
};
class State{
public:
	virtual void next() = 0; 
};
//这个实现放到类里面是不行的
void Alaskan::nextState(){
		m_pState->next();
	}
class EatState : public State{
public:
	EatState(Alaskan *p) : m_p(p){}
	void next(){
		cout<<"sleeping..."<<endl;
		m_p->setState(m_p->sleepState);
	}
private:
	Alaskan *m_p;
};

class SleepState : public State{
public:
	SleepState(Alaskan *p) : m_p(p){}
	void next(){
		cout<<"play..."<<endl;
		m_p->setState(m_p->playState);
	}
private:
	Alaskan *m_p;
};

class PlayState : public State{
public:
	PlayState(Alaskan *p) : m_p(p){}
	void next(){
		cout<<"eating..."<<endl;
		m_p->setState(m_p->eatState);
	}
private:
	Alaskan *m_p;
};

int main(){
	Alaskan * alaskanDog   = new Alaskan();
	alaskanDog->eatState   = new EatState(alaskanDog);
	alaskanDog->sleepState = new SleepState(alaskanDog);
	alaskanDog->playState  = new PlayState(alaskanDog);
	
	cout<<"Alaskan is sleeping... "<<endl;
	alaskanDog->setState(alaskanDog->sleepState);
	
	cout<<"1.Alaskan Next: ";
	alaskanDog->nextState();
	
	cout<<"2.Alaskan Next: ";
	alaskanDog->nextState();
	
	cout<<"3.Alaskan Next: ";
	alaskanDog->nextState();
	
	return 0;
}