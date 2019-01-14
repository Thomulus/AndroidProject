//
// Created by koppe on 2018-11-20.
//

#include "RecycleDB.h"

// MysqlTest.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

// basic idea is that you can only instantiate one table at a time.

//#include "stdafx.h"
#include "pch.h"
#include <iostream>
#include <mysql.h>
#include <string>
using namespace std;
int qstate;
//string table_name = "recycletest2";

RecycleDB::RecycleDB(const string HOST, const string USER, const string PASSWORD, const string DATABASE, const string TABLE) {
    // build in error checking
    table_name = TABLE;
    db_conn = mysql_init(0);
    db_conn = mysql_real_connect(db_conn, HOST.c_str(), USER.c_str(), PASSWORD.c_str(), DATABASE.c_str(), 3306, NULL, 0);
    if (db_conn) {
        puts("Connection success!!!!");
    }
    else {
        puts("Connection failed.");
    }
}

// not sure if this is possible from c++? Also not sure if it is neccesary
void RecycleDB::create() {

}

// could have been only one delete function but I feel like less mistakes will be made this way
void RecycleDB::deleteAll() {
    string query = "DELETE FROM " + table_name;
    const char* q = query.c_str();
    if (mysql_query(db_conn, q)) {
        cout << "Could not delete all" << endl;
    }
    else {
        cout << "Deleted all, database " + table_name + " is now empty. Hope you meant to do that." << endl;
    }
}

//override the delete function to work with a string and int (and others if more types are added)
void RecycleDB::deleteBy_(string column_name,int value) {
    string query = "DELETE FROM " + table_name + " WHERE " + column_name + " = " + to_string(value);
    const char* q = query.c_str();
    if (mysql_query(db_conn, q)) {
        cout << "Could not delete the row in " + table_name + " WHERE " + column_name + " = " + to_string(value) << endl;
    }
    else {
        cout << "Deleted row in " + table_name + " WHERE " + column_name + " = " + to_string(value) << endl;
    }
}

void RecycleDB::deleteBy_(string column_name, string value) {
    string query = "DELETE FROM " + table_name + " WHERE " + column_name + " = '" + value + "'";
    const char* q = query.c_str();
    if (mysql_query(db_conn, q)) {
        cout << "Could not delete the row in " + table_name + " WHERE " + column_name + " = " + value << endl;
    }
    else {
        cout << "Deleted row in " + table_name + " WHERE " + column_name + " = " + value << endl;
    }
}

void RecycleDB::printAll() {
    MYSQL_ROW row;
    MYSQL_RES *res;
    string query = "SELECT * FROM " + table_name;
    const char* q = query.c_str();
    qstate = mysql_query(db_conn, q);
    if (!qstate) {
        res = mysql_store_result(db_conn);
        while (row = mysql_fetch_row(res)) {
            printf("ID: %s, Name: %s, Bin Type: %s, UPC Code: %s\n", row[0], row[1], row[2], row[3]);
        }
    }
    else {
        cout << "Query failed " << mysql_error(db_conn) << endl;
    }
}

// figure out how to make sure that we are notified if the key was not found
void RecycleDB::printBy_(string column_name, int value) {
    MYSQL_ROW row;
    MYSQL_RES *res;
    string query = "SELECT * FROM " + table_name + " WHERE " + column_name + " = " + to_string(value);
    const char* q = query.c_str();
    qstate = mysql_query(db_conn, q);
    if (!qstate) {
        res = mysql_store_result(db_conn);
        while (row = mysql_fetch_row(res)) {
            printf("ID: %s, Name: %s, Bin Type: %s, UPC Code: %s\n", row[0], row[1], row[2], row[3]);
        }
    }
    else {
        cout << "Query failed " << mysql_error(db_conn) << endl;
    }
}

//override
void RecycleDB::printBy_(string column_name, string value) {
    MYSQL_ROW row;
    MYSQL_RES *res;
    string query = "SELECT * FROM " + table_name + " WHERE " + column_name + " = '" + value + "'";
    const char* q = query.c_str();
    qstate = mysql_query(db_conn, q);
    if (!qstate) {
        res = mysql_store_result(db_conn);
        while (row = mysql_fetch_row(res)) {
            printf("ID: %s, Name: %s, Bin Type: %s, UPC Code: %s\n", row[0], row[1], row[2], row[3]);
        }
    }
    else {
        cout << "Query failed " << mysql_error(db_conn) << endl;
    }
}

// can we store UPC_code in a hex number?
void RecycleDB::add(int Vid, string Vname, string VbinType, unsigned long long int VUPC_code) {
    const char *qstr;
    string qstrstr = "INSERT INTO " + table_name + "(id, name, binType, UPC_code) VALUES(" + to_string(Vid) + ", '" + Vname + "', '" + VbinType + "', " + to_string(VUPC_code) + ")";
    qstr = qstrstr.c_str();
    mysql_query(db_conn, qstr);
}

// if id is auto-incremented
void RecycleDB::add(string Vname, string VbinType, unsigned long long int VUPC_code) {
    const char *qstr;
    string qstrstr = "INSERT INTO " + table_name + "(name, binType, UPC_code) VALUES('" + Vname + "', '" + VbinType + "', " + to_string(VUPC_code) + ")";
    qstr = qstrstr.c_str();
    mysql_query(db_conn, qstr);
}

int main()
{

    // eventually change these to unit tests

    // need to use unsigned long long int

    //RecycleDB test1 = RecycleDB("localhost", "root", "oDtst0ahc*ukhigmtpmw", "testdb");

    //test1.add(7, "Banana Peel", "Compost", 1243242);
    //test1.printAll();
    //test1.printByUPC(102394324);

    RecycleDB test2 = RecycleDB("localhost", "root", "oDtst0ahc*ukhigmtpmw", "testdb","recycletest2");

    //drop the table and re run in each time.
    // for now we will just delete each element and add it each time
    // hmmm... this messes up the auto_increment... we'll add the id manually for now. Figure out how to reset the value.
    test2.deleteAll();
    test2.add(1, "Gatorade Bottle", "Blue Bin", 36000291452);
    test2.add(2, "Plastic Water Bottle", "Blue Bin", 3263275783);
    test2.add(3, "Shampoo Bottle", "Blue Bin", 5686435000);
    test2.add(4, "Almond Milk Carton", "Grey Bin", 148234830);

    // make sure this test notifies when it does not work
    // check to make sure were not adding duplicates
    test2.add(2, "this test", "should fail", 123456789);

    //test printing. Need to be able to print only desired column not entire row.
    cout << endl << "PRINTING TESTS" << endl << endl;
    cout << "print all:" << endl;
    test2.printAll();
    cout << endl << "try printing an id that doesnt exist:" << endl;
    test2.printBy_("UPC_code", 135); // this test should fail (should notify me)
    cout << endl << "print id = 2:" << endl;
    test2.printBy_("id", 2);
    cout << endl << "print the blue bins:" << endl;
    test2.printBy_("binType", "Blue Bin");
    cout << endl << "print UPC = 36000291452" << endl;
    test2.printBy_("UPC_code", "36000291452");
    cout << endl << "print name = Plastic Water Bottle" << endl;
    test2.printBy_("name", "Plastic Water Bottle");

    cout << endl << "DELETE TESTS" << endl << endl;

    test2.deleteBy_("id", 1);
    test2.deleteBy_("binType", "Blue Bin");
    test2.printAll();

    //should fail:
    test2.deleteBy_("binType", "taco");
    test2.printAll();

    // is there a way to store this data in an easier format? hexidecimal?
    return 0;
}



// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started:
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file


/*
MYSQL* conn;
MYSQL_ROW row;
MYSQL_RES *res;
conn = mysql_init(0);

conn = mysql_real_connect(conn, "localhost", "root", "oDtst0ahc*ukhigmtpmw","testdb", 3306, NULL, 0);

if (conn) {
    puts("Connection success!!!!");

    //const char *qstr;
    //string qstrstr = "INSERT INTO recycletest(id, name, binType, UPC_code) VALUES(6,'Granola Bar Wrapper','Garbage',23534532)";
    //qstr = qstrstr.c_str();
    //mysql_query(conn, qstr);

    int cokeCanUPC = 102394324;
    string query = "SELECT * FROM recycletest"; //WHERE UPC_code = " + to_string(cokeCanUPC);
    const char* q = query.c_str();
    qstate = mysql_query(conn, q);
    if (!qstate) {
        res = mysql_store_result(conn);
        while (row = mysql_fetch_row(res)) {
            printf("ID: %s, Name: %s, Bin Type: %s, UPC Code: %s\n", row[0], row[1], row[2], row[3]);
        }
    }
    else {
        cout << "Query failed " << mysql_error(conn) << endl;
    }
}
else {
    puts("Connection failed.");
}

*/



//cpp
