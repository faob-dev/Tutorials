#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring
JNICALL
Java_com_faob_aar_MyUtils_greetFromJNI(JNIEnv *env, jobject instance) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}