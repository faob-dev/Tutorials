#include <jni.h>
#include <android/log.h>

extern "C"
void stringMapping(jstring msg) {
    __android_log_print(ANDROID_LOG_DEBUG, "System.out", "%s", msg);
}

extern "C"
void integerMappingByValue(jshort a, jint b, jlong c) {
    __android_log_print(ANDROID_LOG_DEBUG, "System.out", "Before: %hu, %d, %ld", a, b, c);

    a = (jshort) (a + 1);
    b = (jint) (b + 1);
    c = (jlong) (c + 1);
}

extern "C"
void integerMappingByRef(jshort *a, jint *b, jlong *c) {
    __android_log_print(ANDROID_LOG_DEBUG,
                        "System.out", "Before: %hu, %d, %ld", *a, *b, *c);
    *a = (jshort) (*a + 1);
    *b = (jint) (*b + 1);
    *c = (jlong) (*c + 1);
}

extern "C"
void decimalMappingByValue(jfloat a, jdouble b) {
    __android_log_print(ANDROID_LOG_DEBUG,
                        "System.out", "Before: %ff, %fd", a, b);
    a = (jfloat) (a + 1);
    b = (jdouble) (b + 1);
}

extern "C"
void decimalMappingByRef(jfloat *a, jdouble *b) {
    __android_log_print(ANDROID_LOG_DEBUG,
                        "System.out", "Before: %ff, %fd", *a, *b);
    *a = (jfloat) (*a + 1);
    *b = (jdouble) (*b + 1);
}

extern "C"
void booleanMappingByValue(jboolean a) {
    __android_log_print(ANDROID_LOG_DEBUG,
                        "System.out",
                        "Before: a: %s", a ? "true" : "false");
    a = (jboolean) !a;
}

extern "C"
void booleanMappingByRef(jboolean *a) {
    __android_log_print(ANDROID_LOG_DEBUG,
                        "System.out",
                        "Before: a: %s", *a ? "true" : "false");
    *a = (jboolean) !*a;
}

extern "C"
void classMapping(JNIEnv *env, jclass clazz) {
    //get static field and prints it
    jfieldID fieldID = env->GetStaticFieldID(clazz, "name", "Ljava/lang/String;");
    jstring nameString = (jstring) env->GetStaticObjectField(clazz, fieldID);
    __android_log_print(ANDROID_LOG_DEBUG,
                        "System.out",
                        "%s", env->GetStringUTFChars(nameString, 0));

    //call to static method of class
    jmethodID updateMethod = env->GetStaticMethodID(clazz, "update1", "(I)V");
    env->CallStaticVoidMethod(clazz, updateMethod, 100);
}

extern "C"
void objectMapping(JNIEnv *env, jobject obj) {
    //get object phone field and prints it
    jclass clazz = env->GetObjectClass(obj);
    jfieldID fieldID = env->GetFieldID(clazz, "phone", "Ljava/lang/String;");
    jstring phoneString = (jstring) env->GetObjectField(obj, fieldID);
    __android_log_print(ANDROID_LOG_DEBUG,
                        "System.out",
                        "%s", env->GetStringUTFChars(phoneString, 0));
    //call to method of object
    jmethodID method = env->GetMethodID(clazz, "update2", "(I)V");
    env->CallVoidMethod(obj, method, 200);
}

struct TestStructure {
    double value;
};

extern "C"
void structureByValue(TestStructure str) {
    __android_log_print(ANDROID_LOG_DEBUG,
                        "System.out",
                        "Before: value = %f", str.value);
    str.value = 23.34;
}

extern "C"
void structureByRef(TestStructure *str) {
    __android_log_print(ANDROID_LOG_DEBUG,
                        "System.out",
                        "Before: value = %f", str->value);
    str->value = 23.34;
}

extern "C"
void arrayMapping(int *array, int len) {
    for (int i = 0; i < len; i++) {
        array[i] = array[i] * 2;
    }
}

extern "C"
void bufferMapping(int *buffer, int len) {
    for (int i = 0; i < len; i++) {
        buffer[i] = buffer[i] * 2;
    }
}

