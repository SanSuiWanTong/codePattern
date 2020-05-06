//composite c++
#include <iostream>
#include <string>
#include <vector>
using namespace std;

class Component{
public:
	Component(string str): m_str(str){}
	virtual void add(Component*) = 0;
	virtual void display(int )   = 0;

	string m_str;
};

class Leaf : public Component{
public:
	Leaf(string strName): Component(strName){}
	void add(Component* comp){ cout<<"can`t add in leaf"<<endl; }
	void display(int nDepth){
		string tStr;
		for(int i = 0;i<nDepth;i++) tStr += "-";
		tStr += m_str;
		cout<<tStr<<endl;
	}
};

class Composite : public Component{
public:
	Composite(string str):Component(str){}
	void add(Component* pComp){
		m_component.push_back(pComp);
	}
	void display(int nDepth){
		string tStr;
		for(int i = 0;i<nDepth;i++) tStr += "-";
		
		tStr += m_str;
		cout<<tStr<<endl;
		//
		vector<Component*>::iterator it = m_component.begin();
		while(it != m_component.end()){
			(*it)->display(nDepth + 2);
			it++;
		}
	}
private:
	vector<Component*> m_component;
};

int main(){
	Component* p_root = new Composite("root");
	p_root->add(new Leaf("leaf_1"));
	p_root->add(new Leaf("leaf_2"));
	
	Component* p_L1 = new Composite("node_1");
	p_L1->add(new Leaf("node_1_1"));
	p_L1->add(new Leaf("node_1_2"));
	p_root->add(p_L1);
	
	p_root->display(0);
	
	return 0;
}