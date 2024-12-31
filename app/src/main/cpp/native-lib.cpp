#include <jni.h>
#include <string>
#include <chrono>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_UpdateTimeByThread_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_UpdateTimeByThread_MainActivity_GetDateTime(JNIEnv *env, jobject thiz) {
    time_t now = time(nullptr);
    struct tm *tm_c99_fmt  = localtime(&now);
    char buf[80]={0};
    strftime(buf,sizeof(buf),"%Y-%m-%d %H:%M:%S",tm_c99_fmt);
    return env->NewStringUTF(buf);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_UpdateTimeByThread_MainActivity_GetDateTimeByChrono(JNIEnv *env, jobject thiz) {
    time_t t = std::chrono::system_clock::to_time_t(std::chrono::system_clock::now());
    struct tm *tm = localtime(&t);
    char buf[80]={0};
    strftime(buf,sizeof (buf),"%Y-%m-%d %H:%M:%S",tm);
    return env->NewStringUTF(buf);
}