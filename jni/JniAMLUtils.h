/*******************************************************************************
 * Copyright 2018 Samsung Electronics All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *******************************************************************************/

#include <vector>
#include <string>
#include <jni.h>

#ifndef _JNI_AML_UTILS_H_
#define _JNI_AML_UTILS_H_

#define JNI_CURRENT_VERSION JNI_VERSION_1_6
#define JNI_EXCEPTION (1000)
#define JNI_NO_NATIVE_POINTER (1001)
#define JNI_INVALID_VALUE (1002)
#define JNI_NO_SUCH_KEY (1003)
#define JNI_NO_SUPPORT (1004)

#define VERIFY_NON_NULL_THROW_EXCEPTION(arg, log_message, exc) \
    if (!(arg)) \
    { \
        ThrowAMLException(exc, log_message); \
    } \

extern JavaVM* g_jvm;

extern jclass g_cls_List;
extern jclass g_cls_ArrayList;
extern jclass g_cls_Byte;
extern jclass g_cls_AMLObject;
extern jclass g_cls_AMLData;
extern jclass g_cls_AMLDataValueType;
extern jclass g_cls_AMLException;

extern jmethodID g_mid_ArrayList_ctor;
extern jmethodID g_mid_ArrayList_add_object;
extern jmethodID g_mid_Byte_S_ctor;
extern jmethodID g_mid_Byte_toString;
extern jmethodID g_mid_AMLObject_N_ctor;
extern jmethodID g_mid_AMLData_ctor;
extern jmethodID g_mid_AMLData_N_ctor;
extern jmethodID g_mid_AMLException_ctor;
extern jmethodID g_mid_AMLException_setNativeExceptionLocation;

extern jfieldID g_fid_ValueType_String;
extern jfieldID g_fid_ValueType_StringList;
extern jfieldID g_fid_ValueType_AMLData;

static jfieldID GetHandleField(JNIEnv *env, jobject jobj)
{
    jclass cls = env->GetObjectClass(jobj);
    return env->GetFieldID(cls, "mNativeHandle", "J");
}

template <typename T>
static T *GetHandle(JNIEnv *env, jobject jobj)
{
    jlong handle = env->GetLongField(jobj, GetHandleField(env, jobj));
    return reinterpret_cast<T *>(handle);
}

/*                      @TODO may be used..                    */
// template <typename T>
// static void SetHandle(JNIEnv *env, jobject jobj, T *type)
// {
//     jlong handle = reinterpret_cast<jlong>(type);
//     env->SetLongField(jobj, GetHandleField(env, jobj), handle);
// }

jobject getAMLException(JNIEnv* env, const char* file, const char* functionName, const int line,
                        const int code, const char* message);
void throwAMLException(JNIEnv* env, jobject ex);
#define GetAMLException(code, message) getAMLException(env,__FILE__,__func__,__LINE__,code,message)
#define ThrowAMLException(code, message) throwAMLException(env, GetAMLException(code, message))

void convertJavaStrListToStrVector(JNIEnv* env, jobject jList, std::vector<std::string>& vector);
jobject convertStrVectorToJavaStrList(JNIEnv *env, std::vector<std::string> &vector);
jbyteArray convertStrToJavaByteArray(JNIEnv *env, std::string &str);
void convertJavaByteArrayToStr(JNIEnv *env, jbyteArray jByteArr, std::string& str);

#endif // _JNI_AML_UTILS_H_
