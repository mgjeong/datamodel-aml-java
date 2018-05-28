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

#include "JniAMLData.h"
#include "JniAMLUtils.h"
#include "AMLInterface.h"
#include "AMLException.h"

JNIEXPORT jlong JNICALL
Java_org_datamodel_aml_AMLData_constructAMLData(JNIEnv *env, jobject thiz)
{
    // printf("constructAMLData s\n");
    AML::AMLData* amlData = new AML::AMLData();
    if (!amlData)
    {
        ThrowAMLException(AML::NO_MEMORY, "Failed to create AMLData");
        return 0;
    }

    // printf("constructAMLData e (amlData : %p)\n", amlData);
    return reinterpret_cast<jlong>(amlData);
}

JNIEXPORT void JNICALL
Java_org_datamodel_aml_AMLData_setValueImpl__Ljava_lang_String_2Ljava_lang_String_2(JNIEnv *env, jobject thiz, jstring jKey, jstring jValue)
{
    // printf("setValueImpl s\n");
    AML::AMLData* amlData = GetHandle<AML::AMLData>(env, thiz);

    const char *charKey = env->GetStringUTFChars(jKey, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charKey, "charKey is null", JNI_EXCEPTION);
    std::string key(charKey);
    env->ReleaseStringUTFChars(jKey, charKey);

    const char *charValue = env->GetStringUTFChars(jValue, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charValue, "charValue is null", JNI_EXCEPTION);
    std::string value(charValue);
    env->ReleaseStringUTFChars(jValue, charValue);

    try
    {
        amlData->setValue(key, value);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }
}

JNIEXPORT void JNICALL
Java_org_datamodel_aml_AMLData_setValueImpl__Ljava_lang_String_2Ljava_util_List_2(JNIEnv *env, jobject thiz, jstring jKey, jobject jValue)
{
    // printf("setValueImpl s\n");
    AML::AMLData* amlData = GetHandle<AML::AMLData>(env, thiz);

    const char *charKey = env->GetStringUTFChars(jKey, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charKey, "charKey is null", JNI_EXCEPTION);
    std::string key(charKey);
    env->ReleaseStringUTFChars(jKey, charKey);

    std::vector<std::string> value;
    convertJavaStrListToStrVector(env, jValue, value);

    try
    {
        amlData->setValue(key, value);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }
}

JNIEXPORT void JNICALL
Java_org_datamodel_aml_AMLData_setValueImpl__Ljava_lang_String_2Lorg_datamodel_aml_AMLData_2(JNIEnv *env, jobject thiz, jstring jKey, jobject jValue)
{
    // printf("setValueImpl s\n");
    AML::AMLData* amlData = GetHandle<AML::AMLData>(env, thiz);

    const char *charKey = env->GetStringUTFChars(jKey, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charKey, "charKey is null", JNI_EXCEPTION);
    std::string key(charKey);
    env->ReleaseStringUTFChars(jKey, charKey);

    AML::AMLData* value = GetHandle<AML::AMLData>(env, jValue);

    try
    {
        amlData->setValue(key, *value);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }
}

JNIEXPORT jobject JNICALL
Java_org_datamodel_aml_AMLData_getKeysImpl(JNIEnv *env, jobject thiz)
{
    // printf("getKeysImpl s\n");
    AML::AMLData* amlData = GetHandle<AML::AMLData>(env, thiz);

    std::vector<std::string> keys;
    try
    {
        keys = amlData->getKeys();
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    return convertStrVectorToJavaStrList(env, keys);
}

JNIEXPORT jobject JNICALL
Java_org_datamodel_aml_AMLData_getValueTypeImpl(JNIEnv *env, jobject thiz, jstring jKey)
{
    AML::AMLData* amlData = GetHandle<AML::AMLData>(env, thiz);

    const char *charKey = env->GetStringUTFChars(jKey, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charKey, "charKey is null", JNI_EXCEPTION);
    std::string key(charKey);
    env->ReleaseStringUTFChars(jKey, charKey);

    AML::AMLValueType type;
    try
    {
        type = amlData->getValueType(key);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    jobject jtype;
    switch (type)
    {
        case AML::AMLValueType::String :
            jtype = env->GetStaticObjectField(g_cls_AMLDataValueType, g_fid_ValueType_String);
            break;
        case AML::AMLValueType::StringArray :
            jtype = env->GetStaticObjectField(g_cls_AMLDataValueType, g_fid_ValueType_StringList);
            break;
        case AML::AMLValueType::AMLData :
            jtype = env->GetStaticObjectField(g_cls_AMLDataValueType, g_fid_ValueType_AMLData);
            break;
        default :
            //@TODO 
            //ThrowAMLException( , );
            break;
    }

    return jtype;
}

JNIEXPORT jstring JNICALL
Java_org_datamodel_aml_AMLData_getValueToStrImpl(JNIEnv *env, jobject thiz, jstring jKey)
{
    AML::AMLData* amlData = GetHandle<AML::AMLData>(env, thiz);

    const char *charKey = env->GetStringUTFChars(jKey, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charKey, "charKey is null", JNI_EXCEPTION);
    std::string key(charKey);
    env->ReleaseStringUTFChars(jKey, charKey);

    std::string value;
    try
    {
        value = amlData->getValueToStr(key);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    return env->NewStringUTF(value.c_str());
}

JNIEXPORT jobject JNICALL
Java_org_datamodel_aml_AMLData_getValueToStrListImpl(JNIEnv *env, jobject thiz, jstring jKey)
{
    AML::AMLData* amlData = GetHandle<AML::AMLData>(env, thiz);

    const char *charKey = env->GetStringUTFChars(jKey, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charKey, "charKey is null", JNI_EXCEPTION);
    std::string key(charKey);
    env->ReleaseStringUTFChars(jKey, charKey);

    std::vector<std::string> value;
    try
    {
        value = amlData->getValueToStrArr(key);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    return convertStrVectorToJavaStrList(env, value);
}

JNIEXPORT jobject JNICALL
Java_org_datamodel_aml_AMLData_getValueToAMLDataImpl(JNIEnv *env, jobject thiz, jstring jKey)
{
    // printf("getValueToAMLDataImpl s\n");
    AML::AMLData* amlData = GetHandle<AML::AMLData>(env, thiz);

    const char *charKey = env->GetStringUTFChars(jKey, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charKey, "charKey is null", JNI_EXCEPTION);
    std::string key(charKey);
    env->ReleaseStringUTFChars(jKey, charKey);

    jobject jData;
    try
    {
        const AML::AMLData& value = amlData->getValueToAMLData(key);
        jlong handle = reinterpret_cast<jlong>(&value);
        jData = env->NewObject(g_cls_AMLData, g_mid_AMLData_N_ctor, handle, false);

        // object = env->NewObject(g_cls_AMLData, g_mid_AMLData_ctor);
        // jfieldID fieldNativeHandle = env->GetFieldID(g_cls_AMLData, "mNativeHandle", "J");
        // env->SetLongField(object, fieldNativeHandle, handle);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    return jData;
}

JNIEXPORT void JNICALL
Java_org_datamodel_aml_AMLData_destructAMLData(JNIEnv *env, jobject thiz)
{
    AML::AMLData* amlData = GetHandle<AML::AMLData>(env, thiz);
    delete amlData;
}