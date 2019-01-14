
//
// Created by koppe on 2018-11-13.

#include "RecycleSearch.h"
#include<string>

RecycleItem::RecycleItem(std::string name, std::string binType, unsigned long long int upc, std::string description) {
    this->name = name;
    this->binType = binType;
    this->upc = upc;
    this->description = description;
}

std::string RecycleItem::getName(){
    return name;
}

std::string RecycleItem::getBinType(){
    return binType;
}
unsigned long long int RecycleItem::getUpc(){
    return upc;
}
std::string RecycleItem::getDescription(){
    return description;
}

 //getBinTypeFromC(){

//    return 0;
//}

// Name: "Gatorade Bottle"
// UPC: 55577420751
// binType: "Blue Bin"
// Description: "All parts can go in the blue bin, make sure to leave the cap on!






