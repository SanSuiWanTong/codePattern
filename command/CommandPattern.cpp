//command c++
#include <iostream>
#include <vector>
#include <string>
using namespace std;

enum WEAPON_MAGIC_TYPE{ KNIFE,SWORD,WAND,FAIR,WIND,ICE_STORM };
typedef int MAGIC_DIMENSION;

class aSoldier{
public:	
	MAGIC_DIMENSION COST_VALUE = 20;
	
	aSoldier(string s):name(s){}
	void changeWeapon(WEAPON_MAGIC_TYPE type) { this->weapen = type; cout<<this->name<<" take weapon:"<<this->weapen<<endl;}
	WEAPON_MAGIC_TYPE getWeapen()             { return this->weapen; } 
	void releaseMagic(WEAPON_MAGIC_TYPE type) { this->magicValue -= COST_VALUE; cout<<this->name<<" release magic:"<<type<<" left magic:"<<this->magicValue<<endl; }
	int  getMagicValue()                      { return magicValue; }
	void recover()                            { this->magicValue += COST_VALUE; cout<<this->name<<" left magic: "<<this->magicValue<<endl; }

private:
	string name;
	WEAPON_MAGIC_TYPE weapen = WEAPON_MAGIC_TYPE::KNIFE;
	MAGIC_DIMENSION magicValue = 100;
};

class Command{
public:
	virtual void setCmd(WEAPON_MAGIC_TYPE) = 0;
	virtual void execute()                 = 0;
	virtual void undo()                    = 0;
};

class useMagicCmd : public Command{
public:
	useMagicCmd(aSoldier* p):m_pSoldier(p){}
	void setCmd(WEAPON_MAGIC_TYPE argc) {
		this->useMagic = argc; 
	}
	void execute(){
		m_pSoldier->releaseMagic(this->useMagic);
	}
	void undo(){
		m_pSoldier->recover();
	}
private:
	aSoldier* m_pSoldier;
	WEAPON_MAGIC_TYPE useMagic;
};

class changeWeapenCmd : public Command{
public:
	changeWeapenCmd(aSoldier* p):m_pSoldier(p){}
	void setCmd(WEAPON_MAGIC_TYPE argc) {
		lastWeapon = m_pSoldier->getWeapen();
		changeWeapon = argc; 
	}
	void execute(){
		m_pSoldier->changeWeapon(changeWeapon);
	}
	void undo(){
		m_pSoldier->changeWeapon(lastWeapon);
	}
private:
	aSoldier* m_pSoldier;
	WEAPON_MAGIC_TYPE lastWeapon;
	WEAPON_MAGIC_TYPE changeWeapon;
};

class Control{
public:
	void setObj(Command* obj){ objVec.push_back(obj); }
	void notify(){
		for(auto c : objVec)
			c->execute();
		
		lastObjVec = objVec; 
		objVec.clear();
	}
	void undo(){
		for(auto c : lastObjVec)
			c->undo();
		lastObjVec.clear();
	}
private:
	vector<Command*> objVec;
	vector<Command*> lastObjVec;
};

int main(){
	aSoldier* dragonSolder  = new aSoldier("S_S_WanTong");
	
	Command* magicCmd       = new useMagicCmd(dragonSolder);
	magicCmd->setCmd(WEAPON_MAGIC_TYPE::ICE_STORM);             
	
	Command* weapenCmd      = new changeWeapenCmd(dragonSolder);
	weapenCmd->setCmd(WEAPON_MAGIC_TYPE::SWORD);               
	
	Control* control         = new Control();
	control->setObj(weapenCmd);                                //换剑
	control->notify();                                         //执行
	
	weapenCmd->setCmd(WEAPON_MAGIC_TYPE::WAND);                
	control->setObj(weapenCmd);                                //换魔杖
	control->setObj(magicCmd);                                 //释放暴风雪
	control->notify();                                         //执行
	
	control->undo();                                           //撤销上一步操作
	
	return 0;
}