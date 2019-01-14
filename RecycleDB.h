#pragma once
#include <mysql.h>
#include <string>

class RecycleDB{
public:
    RecycleDB(const std::string = "", const std::string = "", const std::string = "", const std::string = "", const std::string = "");
//	~recycledb();
    void create();
    void printAll();
    // should update these to actually return the values
    void printBy_(std::string column_name, int value);
    void printBy_(std::string column_name, std::string value);
    void add(int id_value, std::string name_value, std::string binType_value, unsigned long long int UPC_value);
    // auto_increment primary key id integer
    void add(std::string name_value, std::string binType_value, unsigned long long int UPC_value);
    void deleteAll();
    void deleteBy_(std::string column_name, int value);
    void deleteBy_(std::string column_name, std::string value);
private:
    MYSQL* db_conn;
    std::string table_name;
};
