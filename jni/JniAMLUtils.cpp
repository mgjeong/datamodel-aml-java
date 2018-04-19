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

#include <string>
#include <jni.h>
#include "JniAMLUtils.h"
#include "AMLException.h"

#define VERIFY_VARIABLE_NULL(arg) \
    if (NULL == (arg)) \
    { \
        printf("invalid input\n"); \
        return JNI_ERR; \
    } \

JavaVM* g_jvm = nullptr;

jclass g_cls_List = nullptr;
jclass g_cls_ArrayList = nullptr;
jclass g_cls_Byte = nullptr;
jclass g_cls_AMLObject = nullptr;
jclass g_cls_AMLData = nullptr;
jclass g_cls_AMLException = nullptr;

jmethodID g_mid_List_size = nullptr;
jmethodID g_mid_List_get_object = nullptr;
jmethodID g_mid_ArrayList_ctor = nullptr;
jmethodID g_mid_ArrayList_add_object = nullptr;
jmethodID g_mid_Byte_S_ctor = nullptr;
jmethodID g_mid_Byte_toString = nullptr;
jmethodID g_mid_AMLObject_N_ctor = nullptr;
jmethodID g_mid_AMLData_ctor = nullptr;
jmethodID g_mid_AMLData_N_ctor = nullptr;
jmethodID g_mid_AMLException_ctor = nullptr;
jmethodID g_mid_AMLException_setNativeExceptionLocation = nullptr;

jobject g_jobj_ValueType_String = nullptr;
jobject g_jobj_ValueType_StringList = nullptr;
jobject g_jobj_ValueType_AMLData = nullptr;

// JNI OnLoad
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved)
{
    printf("JNI_OnLoad\n");
    JNIEnv* env = nullptr;
    g_jvm = vm;

    if (g_jvm->GetEnv((void **)&env, JNI_CURRENT_VERSION) != JNI_OK)
    {
        printf("Failed to get the environment using Get(*env)()\n");
        return JNI_ERR;
    }
    VERIFY_VARIABLE_NULL(env);

    jclass clazz = nullptr;

    // List
    clazz = env->FindClass("java/util/List");
    VERIFY_VARIABLE_NULL(clazz);
    g_cls_List = (jclass)env->NewGlobalRef(clazz);
    env->DeleteLocalRef(clazz);

    g_mid_List_size = env->GetMethodID(g_cls_List, "size", "()I");
    VERIFY_VARIABLE_NULL(g_mid_List_size);

    g_mid_List_get_object = env->GetMethodID(g_cls_List, "get", "(I)Ljava/lang/Object;");
    VERIFY_VARIABLE_NULL(g_mid_List_get_object);

    // ArrayList
    clazz = env->FindClass("java/util/ArrayList");
    VERIFY_VARIABLE_NULL(clazz);
    g_cls_ArrayList = (jclass)env->NewGlobalRef(clazz);
    env->DeleteLocalRef(clazz);

    g_mid_ArrayList_ctor = env->GetMethodID(g_cls_ArrayList, "<init>", "()V");
    VERIFY_VARIABLE_NULL(g_mid_ArrayList_ctor);

    g_mid_ArrayList_add_object = env->GetMethodID(g_cls_ArrayList, "add", "(Ljava/lang/Object;)Z");
    VERIFY_VARIABLE_NULL(g_mid_ArrayList_add_object);

    // Byte
    clazz = env->FindClass("java/lang/Byte");
    VERIFY_VARIABLE_NULL(clazz);
    g_cls_Byte = (jclass)env->NewGlobalRef(clazz);
    env->DeleteLocalRef(clazz);

    g_mid_Byte_S_ctor = env->GetMethodID(g_cls_Byte, "<init>", "(Ljava/lang/String;)V");
    //g_mid_Byte_B_ctor = env->GetMethodID(g_cls_Byte, "<init>", "(B)V");
    VERIFY_VARIABLE_NULL(g_mid_Byte_S_ctor);

    g_mid_Byte_toString = env->GetMethodID(g_cls_Byte, "toString", "()Ljava/lang/String;");
    VERIFY_VARIABLE_NULL(g_mid_Byte_toString);

    // AMLObject
    clazz = env->FindClass("org/datamodel/aml/AMLObject");
    VERIFY_VARIABLE_NULL(clazz);
    g_cls_AMLObject = (jclass)env->NewGlobalRef(clazz);
    env->DeleteLocalRef(clazz);

    g_mid_AMLObject_N_ctor = env->GetMethodID(g_cls_AMLObject, "<init>", "(JZ)V");
    VERIFY_VARIABLE_NULL(g_mid_AMLObject_N_ctor);

    // AMLData
    clazz = env->FindClass("org/datamodel/aml/AMLData");
    VERIFY_VARIABLE_NULL(clazz);
    g_cls_AMLData = (jclass)env->NewGlobalRef(clazz);
    env->DeleteLocalRef(clazz);

    g_mid_AMLData_ctor = env->GetMethodID(g_cls_AMLData, "<init>", "()V");
    VERIFY_VARIABLE_NULL(g_mid_AMLData_ctor);

    g_mid_AMLData_N_ctor = env->GetMethodID(g_cls_AMLData, "<init>", "(JZ)V");
    VERIFY_VARIABLE_NULL(g_mid_AMLData_N_ctor);

    // AMLData.ValueType
    clazz = env->FindClass("org/datamodel/aml/AMLData$ValueType");
    VERIFY_VARIABLE_NULL(clazz);

    jfieldID fid = nullptr;
    fid = env->GetStaticFieldID(clazz, "STRING", "Lorg/datamodel/aml/AMLData$ValueType;");
    g_jobj_ValueType_String = env->GetStaticObjectField(clazz, fid);

    fid = env->GetStaticFieldID(clazz, "STRING_LIST", "Lorg/datamodel/aml/AMLData$ValueType;");
    g_jobj_ValueType_StringList = env->GetStaticObjectField(clazz, fid);

    fid = env->GetStaticFieldID(clazz, "AMLDATA", "Lorg/datamodel/aml/AMLData$ValueType;");
    g_jobj_ValueType_AMLData = env->GetStaticObjectField(clazz, fid);

    // AMLException
    clazz = env->FindClass("org/datamodel/aml/AMLException");
    VERIFY_VARIABLE_NULL(clazz);
    g_cls_AMLException = (jclass)env->NewGlobalRef(clazz);
    env->DeleteLocalRef(clazz);

    g_mid_AMLException_ctor = env->GetMethodID(g_cls_AMLException, "<init>", "(Ljava/lang/String;Ljava/lang/String;)V");
    VERIFY_VARIABLE_NULL(g_mid_AMLException_ctor);

    g_mid_AMLException_setNativeExceptionLocation = env->GetMethodID(g_cls_AMLException, "setNativeExceptionLocation",
        "(Ljava/lang/String;""Ljava/lang/String;""I)V");
    VERIFY_VARIABLE_NULL(g_mid_AMLException_setNativeExceptionLocation);


printf("JNI_OnLoad end\n");
    return JNI_CURRENT_VERSION;
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *vm, void *reserved)
{
    printf("JNI_OnUnload\n");
    JNIEnv* env = nullptr;

    if (vm->GetEnv((void **) &env, JNI_CURRENT_VERSION) != JNI_OK) {
        printf("Failed to get the environment using Getenv()\n");
        return;
    }

    if (env)
    {
        env->DeleteGlobalRef(g_cls_List);
        env->DeleteGlobalRef(g_cls_ArrayList);
        env->DeleteGlobalRef(g_cls_Byte);
        env->DeleteGlobalRef(g_cls_AMLObject);
        env->DeleteGlobalRef(g_cls_AMLData);
        env->DeleteGlobalRef(g_cls_AMLException);
    }
}


static std::string stackResultToStr(const int result)
{
    switch (result)
    {
        case AML::NO_ERROR :
            return "OK";
        case AML::INVALID_PARAM :
            return "INVALID_PARAM";
        case AML::INVALID_FILE_PATH :
            return "INVALID_FILE_PATH";
        case AML::INVALID_AML_SCHEMA :
            return "INVALID_AML_SCHEMA";
        case AML::INVALID_XML_STR :
            return "INVALID_XML_STR";
        case AML::NOT_MATCH_TO_AML_MODEL :
            return "NOT_MATCH_TO_AML_MODEL";
        case AML::INVALID_BYTE_STR :
            return "INVALID_BYTE_STR";
        case AML::SERIALIZE_FAIL :
            return "SERIALIZE_FAIL";
        case AML::NO_MEMORY :
            return "NO_MEMORY";
        case AML::KEY_NOT_EXIST :
            return "KEY_NOT_EXIST";
        case AML::KEY_ALREADY_EXIST :
            return "KEY_ALREADY_EXIST";
        case AML::WRONG_GETTER_TYPE :
            return "WRONG_GETTER_TYPE";
        case AML::API_NOT_ENABLED :
            return "API_NOT_ENABLED";

        case JNI_EXCEPTION:
            return "JNI_EXCEPTION";
        case JNI_NO_NATIVE_POINTER:
            return "JNI_NO_NATIVE_POINTER";
        case JNI_INVALID_VALUE:
            return "JNI_INVALID_VALUE";

        default:
            return "";
    }
}

jobject getAMLException(JNIEnv* env, const char* file, const char* functionName,
    const int line, const int code, const char* message)
{
    std::string codeStr = stackResultToStr(code);
    if (codeStr.empty())
    {
        codeStr = stackResultToStr(JNI_INVALID_VALUE);
    }
    jobject ex = env->NewObject(g_cls_AMLException,
                                g_mid_AMLException_ctor,
                                env->NewStringUTF(codeStr.c_str()),
                                env->NewStringUTF(message));
    if (!ex)
    {
        return nullptr;
    }
    env->CallVoidMethod(ex,
                        g_mid_AMLException_setNativeExceptionLocation,
                        env->NewStringUTF(file),
                        env->NewStringUTF(functionName),
                        line);
    if (env->ExceptionCheck())
    {
        return nullptr;
    }
    return ex;
}

void throwAMLException(JNIEnv* env, jobject ex)
{
    env->Throw((jthrowable)ex);
}

jobject convertStrVectorToJavaStrList(JNIEnv *env, std::vector<std::string> &vector)
{
    jobject jList = env->NewObject(g_cls_ArrayList, g_mid_ArrayList_ctor);
    if (!jList)
    {
        return nullptr;
    }
    for (size_t i = 0; i < vector.size(); ++i)
    {
        jstring jStr = env->NewStringUTF(vector[i].c_str());
        if (!jStr)
        {
            return nullptr;
        }
        env->CallBooleanMethod(jList, g_mid_ArrayList_add_object, jStr);
        if (env->ExceptionCheck())
        {
            return nullptr;
        }
        env->DeleteLocalRef(jStr);
    }
    return jList;
}

void convertJavaStrListToStrVector(JNIEnv* env, jobject jList, std::vector<std::string>& vector)
{
    jint size = env->CallIntMethod(jList, g_mid_List_size);

    for (jint i = 0; i < size; i++)
    {
        jstring strObj = (jstring)env->CallObjectMethod(jList, g_mid_List_get_object, i);
        const char * chr = env->GetStringUTFChars(strObj, NULL);
        vector.push_back(chr);
        env->ReleaseStringUTFChars(strObj, chr);
        env->DeleteLocalRef(strObj);
    }
}

jbyteArray convertStrToJavaByteArray(JNIEnv *env, std::string &str)
{
    size_t len = str.length();
    const jbyte* pNativeMessage = reinterpret_cast<const jbyte*>(str.c_str());
    jbyteArray byteArr = env->NewByteArray(len);
    env->SetByteArrayRegion(byteArr, 0, len, pNativeMessage);

    return byteArr;
}

void convertJavaByteArrayToStr(JNIEnv *env, jbyteArray jByteArr, std::string& str)
{
    jsize len = env->GetArrayLength(jByteArr);
    char* buf = new char[len];
    env->GetByteArrayRegion(jByteArr, 0, len, reinterpret_cast<jbyte*>(buf));

    str.assign(buf, len);

    delete[] buf;
}