#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_bigfoot_bigfoot_ScanActivity_getBinTypeFromUpc(
        JNIEnv *env,
        jobject /* this */, jlong UPC) {

    //you caught us, the following is some ugly code that we did not want to write...
    //Issues setting up the database forced us to resort to a series of if statements as a demo...
    //If this project was carried forward we probably would not have used c++ and we would've moved
    //all of this code to a database, and would have it linked to your location.
    // It should be noted that we did write the code for the database (see RecycleDB)
    //we just couldn't get it working. Anyways, enjoy our if statements!
    std::string binType;
    long long int upc = (long long int) UPC;
    long long int gatorade = 55577420751;
    long long int fiestaSaladFromArc = 2500136000007;
    long long int natureValleyGranolaBar = 6563350226;
    long long int nesteaCan = 8390937;

    if(upc == gatorade){
        binType = "Blue Bin. Make sure to leave to cap on!";
    }
    else if(upc == fiestaSaladFromArc){
        binType = "Blue Bin. Ensure all contents are clean";
    }
    else if(upc == natureValleyGranolaBar){
        binType = "Garbage";
    }
    else if(upc == nesteaCan){
        binType = "Blue Bin";
    }
    else {
        binType = "";
    }
    return env->NewStringUTF(binType.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_bigfoot_bigfoot_ScanActivity_getNameFromUpc(
        JNIEnv *env,
        jobject /* this */, jlong UPC) {

    std::string name;
    long long int upc = (long long int) UPC;
    long long int gatorade = 55577420751;
    long long int fiestaSaladFromArc = 2500136000007;
    long long int natureValleyGranolaBar = 6563350226;
    long long int nesteaCan = 8390937;

    if(upc == gatorade){
        name = "gatorade";
    }
    else if(upc == fiestaSaladFromArc){
        name = "fiesta Salad From Arc";
    }
    else if(upc == natureValleyGranolaBar){
        name = "Nature Valley Granola Bar";
    }
    else if(upc == nesteaCan){
        name = "Nestea Can";
    }
    else {
       // name = std::to_string(UPC);
        name = "Could not find item. Suggest a new item?";
    }
    return env->NewStringUTF(name.c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_bigfoot_bigfoot_ScanActivity_getRecycleTypeFromUpc(
        JNIEnv *env,
        jobject /* this */, jlong UPC) {

    std::string recycleType;
    long long int upc = (long long int) UPC;
    long long int gatorade = 55577420751;
    long long int fiestaSaladFromArc = 2500136000007;
    long long int natureValleyGranolaBar = 6563350226;
    long long int nesteaCan = 8390937;

    if(upc == gatorade){
        recycleType = "Plastic";
    }
    else if(upc == fiestaSaladFromArc){
        recycleType = "Plastic";
    }
    else if(upc == natureValleyGranolaBar){
        recycleType = "Garbage";
    }
    else if(upc == nesteaCan){
        recycleType = "Cans";
    }
    else {
        recycleType = "No available info for: " + std::to_string(UPC);
    }
    return env->NewStringUTF(recycleType.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_bigfoot_bigfoot_MainActivity_getBinTypeFromName(
        JNIEnv *env,
        jobject , jstring name) {

    const char *str = env->GetStringUTFChars(name, NULL);
    std::string NAME(str);

    //figure out how to compare jstrings to cstrings
    std::string binType;
    //long long int upc = (long long int) UPC;
    //long long int gatorade = 55577420751;
    //long long int fiestaSaladFromArc = 2500136000007;
    //long long int natureValleyGranolaBar = 6563350226;

    if(NAME == "gatorade"){
        binType = "Blue Bin. Leave the cap on!";
    }
    else if(NAME == "nestea"){
        binType = "Blue Bin";
    }
    else if(NAME == "banana") {
        binType = "Compost";
    }
    else if(NAME == "My Class Notes"){
        binType = "Your Class Notes not found";
    }
    else {
        binType = NAME;
    }
    return env->NewStringUTF(binType.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_bigfoot_bigfoot_ScanActivity_getBinTypeFromName(
        JNIEnv *env,
        jobject , jstring name) {

    const char *str = env->GetStringUTFChars(name, NULL);
    std::string NAME(str);

    //figure out how to compare jstrings to cstrings
    std::string binType;
    //long long int upc = (long long int) UPC;
    //long long int gatorade = 55577420751;
    //long long int fiestaSaladFromArc = 2500136000007;
    //long long int natureValleyGranolaBar = 6563350226;

    if(NAME == "gatorade"){
        binType = "Blue Bin. Leave the cap on!";
    }
    else if(NAME == "nestea"){
        binType = "Blue Bin";
    }
    else if(NAME == "banana") {
        binType = "Compost";
    }
    else if(NAME == "My Class Notes"){
        binType = "Your Class Notes not found";
    }
    else {
        binType = NAME;
    }
    return env->NewStringUTF(binType.c_str());
}
