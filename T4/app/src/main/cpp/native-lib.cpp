#include <iostream>
#include <jni.h>
#include <pthread.h>
#include <unistd.h>
#include <string>
#include <android/log.h>

#define LOGD(MSG) __android_log_print(ANDROID_LOG_DEBUG , "System.out", MSG);
#define LOGD1(MSG, VALUE) __android_log_print(ANDROID_LOG_DEBUG , "System.out", MSG, VALUE);
#define LOGD2(MSG, VALUE1, VALUE2, SUM) __android_log_print(ANDROID_LOG_DEBUG , "System.out", MSG, VALUE1, VALUE2, SUM);

#define LOGI(MSG) __android_log_print(ANDROID_LOG_INFO , "System.out", MSG);
#define LOGE(MSG) __android_log_print(ANDROID_LOG_ERROR , "System.out", MSG);

extern "C"
void logExample() {
    LOGD("Log message in C++");
    LOGI("Log Info in C++");
    LOGE("Log error in C++");

    int age = 100;
    LOGD1("Log integer : %d", age);

    int a = 3;
    int b = 5;
    int sum = a + b;
    LOGD2("Sum of %d and %d is : %d", a, b, sum);

}

extern "C"
void exceptionExample(JNIEnv *env, int age) {
    if (age <= 0) {
        jclass cl = env->FindClass("java/lang/Exception");
        env->ThrowNew(cl, "Age must be greater than 0");
    }
}

typedef void (*Callback)(int);

extern "C"
void syncCallbackExample(Callback call) {
    //simulating long running task
    sleep(5);
    call(1000);
}

void *task(void *arg) {
    Callback call = (Callback) arg;

    //simulating long running task
    sleep(5);
    call(1000);
    pthread_exit(NULL);
}

extern "C"
void asyncCallbackExample(Callback call) {
    pthread_t t1;
    pthread_create(&t1, NULL, task, (void *) call);
}
