//Singleton c++
//VS2013 编译通过,MinGW 更新至9.2.0-1 <mutex>仍报错,
//网站在线编译 https://zh.cppreference.com/w/cpp/thread/mutex
#include <iostream>
#include <mutex>
#include <map>
#include <string>
#include <thread>
#include <iterator>

using namespace std;

int g_count = 0;

class Singleton{
public:
	static Singleton* getInstance(){
		Singleton* result = nullptr;                  //1.防止A线程尚未完全构造完毕，但此时非null,返回未完全初始化对象,崩溃
		if (NULL == m_pSingleton){                    //2.double check 防止每一次调用都执行lock,影响速度
			lock_guard<mutex> lock(s_mutex);          //3.线程安全,防止还是null时有其他线程进入,效率有损失			
			if (NULL == m_pSingleton){
				result = new Singleton();				
				m_pSingleton = result;                //4.构造完毕
			}
		}
		return m_pSingleton;
	}

	void doSomething(const string& args);

	template<typename It>
	void printInsertPages(It it, bool success){
		cout << "insert:" << it->first << " " << it->second << (success ? "; success\n" : "; fail\n");
	}

	static mutex s_mutex;                             //在这里编译器并未给分配内存
	map<int, std::string> m_pages;

private:
	Singleton() = default;
	static Singleton* m_pSingleton;		
};

void Singleton::doSomething(const string& args){	
	auto ret = m_pages.insert({ g_count++, args });   //m_pages[g_count++] = args; ret类型: std::pair<map<int, std::string>::iterator, bool>
	printInsertPages(ret.first, ret.second);
}

//静态成员初始化,否连接错误
Singleton* Singleton::m_pSingleton = nullptr;         
mutex Singleton::s_mutex;

//模拟下载
void download(const string &url)
{
	Singleton* single = Singleton::getInstance();
	lock_guard<mutex> lock(Singleton::s_mutex);      //确保map插入正确，无将乱序
	std::this_thread::sleep_for(std::chrono::seconds(1));     
	single->doSomething(url);	
}
	

int main()
{
	thread t1(download,"http://AAA");
	thread t2(download,"http://BBB");
	t1.join();
	t2.join();	//”join”的意思是等待线程返回后再终结
	
	//
	Singleton* single = Singleton::getInstance();
	for (const auto &pair : single->m_pages) {
		std::cout << pair.first << " => " << pair.second << '\n';
	}
	return 0;
}