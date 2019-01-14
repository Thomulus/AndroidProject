#include<deque>
#include "jni.h"

extern "C" {
    JNIEXPORT void JNICALL
    Java_com_bigfoot_bigfoot_RollingArray_init(JNIEnv *env, jobject obj, jint maxSize){
        std::deque<jlong>* q = new std::deque<jlong>(100);
        jclass jcls = env->FindClass("com/bigfoot/bigfoot/RollingArray");
        jfieldID valId = env->GetFieldID(jcls, "arrayAddress", "J");
        env->SetLongField(obj,valId,(jlong) q);
    }

    JNIEXPORT void JNICALL
    Java_com_bigfoot_bigfoot_RollingArray_add(JNIEnv *env, jobject obj, jlong upc){
        jclass jcls = env->FindClass("com/bigfoot/bigfoot/RollingArray");
        jfieldID valId = env->GetFieldID(jcls, "arrayAddress", "J");
        jlong addr = env->GetLongField(obj,valId);

        std::deque<jlong>& q = *(std::deque<jlong>*) addr;

        jfieldID maxSizeId = env->GetFieldID(jcls,"arraySize","I");
        jint maxSize = env->GetIntField(obj,maxSizeId);
        q.push_back(upc);

        if(q.size() > maxSize){
            q.pop_front();
        }
    }

    JNIEXPORT jint JNICALL
    Java_com_bigfoot_bigfoot_RollingArray_getNumOccurences(JNIEnv *env, jobject obj, jlong upc){
        int ctr = 0;
        //get queue
        jclass jcls = env->FindClass("com/bigfoot/bigfoot/RollingArray");
        jfieldID valId = env->GetFieldID(jcls, "arrayAddress", "J");
        jlong addr = env->GetLongField(obj,valId);

        std::deque<jlong>& q = *(std::deque<jlong>*) addr;

        for(jlong code : q){
            if(code == upc)
                ctr++;
        }
        return (jint) ctr;
    }
}