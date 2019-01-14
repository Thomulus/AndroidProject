//
// Created by Cache Angus on 2018-11-19.
//

#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_bigfoot_bigfoot_InstructionActivity_getInstr1(JNIEnv *env) {

    std::string in1 = "Welcome to BIGFOOT, your go to recycling app!";

    return env->NewStringUTF(in1.c_str());
 }

extern "C" JNIEXPORT jstring JNICALL
Java_com_bigfoot_bigfoot_InstructionActivity_getInstr2(JNIEnv *env) {

    std::string in2 = "Simply scan your item's barcode and follow the recycling instructions.";

    return env->NewStringUTF(in2.c_str());
 }

 extern "C" JNIEXPORT jstring JNICALL
 Java_com_bigfoot_bigfoot_InstructionActivity_getInstr3(JNIEnv *env) {

     std::string in3 = "Checkout the menu for other features.";

     return env->NewStringUTF(in3.c_str());
  }

  extern "C" JNIEXPORT jstring JNICALL
  Java_com_bigfoot_bigfoot_InstructionActivity_getInstr4(JNIEnv *env) {

      std::string in4 = "We're here to help you and the planet!";

      return env->NewStringUTF(in4.c_str());
   }



