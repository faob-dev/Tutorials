#include <jni.h>

//returns library version as string
extern "C"
JNIEXPORT jstring JNICALL
Java_com_faob_mavenaar_MyLib_getLibVersion(JNIEnv *env, jobject instance) {
    return env->NewStringUTF("Lib Version: 1.0.0");
}
