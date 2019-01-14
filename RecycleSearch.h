
#include <string>

#ifndef ALBERTREPO_RECYCLESEARCH_H
#define ALBERTREPO_RECYCLESEARCH_H

#endif //ALBERTREPO_RECYCLESEARCH_H

class recycleItem {
public:
    //constructor
    recycleItem(std::string name, std::string binType, unsigned long long int upc, std::string description);
    //getters
    std::string getName();
    std::string getBinType();
    unsigned long long int getUpc();
    std::string getDescription();
private:
    std::string name;
    std::string binType;
    unsigned long long int upc;
    std::string description;
};