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

#include "JniAMLObject.h"
#include "JniAMLData.h"
#include "JniAMLUtils.h"
#include "AMLInterface.h"
#include "AMLException.h"

JNIEXPORT jlong JNICALL
Java_org_datamodel_aml_AMLObject_constructAMLObject__Ljava_lang_String_2Ljava_lang_String_2(JNIEnv *env, jobject thiz, jstring jDeviceId, jstring jTimeStamp)
{
    // printf("constructAMLObject s\n");

    const char *charDeviceId = env->GetStringUTFChars(jDeviceId, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charDeviceId, "charDeviceId is null", JNI_EXCEPTION);
    std::string deviceId(charDeviceId);
    env->ReleaseStringUTFChars(jDeviceId, charDeviceId);

    const char *charTimeStamp = env->GetStringUTFChars(jTimeStamp, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charTimeStamp, "charTimeStamp is null", JNI_EXCEPTION);
    std::string timeStamp(charTimeStamp);
    env->ReleaseStringUTFChars(jTimeStamp, charTimeStamp);

    try
    {
        AML::AMLObject* amlObj = new AML::AMLObject(deviceId, timeStamp);
        
        if (!amlObj)
        {
            ThrowAMLException(AML::NO_MEMORY, "Failed to create AMLObject");
            return 0;
        }

        // printf("constructAMLObject e (amlObj : %p)\n", amlObj);
        return reinterpret_cast<jlong>(amlObj);
        //SetHandle<AML::AMLObject>(env, thiz, amlObj);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }
}

JNIEXPORT jlong JNICALL
Java_org_datamodel_aml_AMLObject_constructAMLObject__Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2(JNIEnv *env, jobject thiz, jstring jDeviceId, jstring jTimeStamp, jstring jId)
{
    // printf("constructAMLObject s\n");

    const char *charDeviceId = env->GetStringUTFChars(jDeviceId, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charDeviceId, "charDeviceId is null", JNI_EXCEPTION);
    std::string deviceId(charDeviceId);
    env->ReleaseStringUTFChars(jDeviceId, charDeviceId);

    const char *charTimeStamp = env->GetStringUTFChars(jTimeStamp, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charTimeStamp, "charTimeStamp is null", JNI_EXCEPTION);
    std::string timeStamp(charTimeStamp);
    env->ReleaseStringUTFChars(jTimeStamp, charTimeStamp);

    const char *charId = env->GetStringUTFChars(jId, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charId, "charId is null", JNI_EXCEPTION);
    std::string id(charId);
    env->ReleaseStringUTFChars(jId, charId);
    
    try
    {
        AML::AMLObject* amlObj = new AML::AMLObject(deviceId, timeStamp, id);
        
        if (!amlObj)
        {
            ThrowAMLException(AML::NO_MEMORY, "Failed to create AMLObject");
            return 0;
        }
        
        // printf("constructAMLObject e (amlObj : %p)\n", amlObj);
        return reinterpret_cast<jlong>(amlObj);
        //SetHandle<AML::AMLObject>(env, thiz, amlObj);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }
}

JNIEXPORT void JNICALL
Java_org_datamodel_aml_AMLObject_addDataImpl(JNIEnv *env, jobject thiz, jstring jName, jobject jData)
{
    // printf("addDataImpl s\n");
    AML::AMLObject* amlObj = GetHandle<AML::AMLObject>(env, thiz);

    const char *charName = env->GetStringUTFChars(jName, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charName, "charName is null", JNI_EXCEPTION);
    std::string name(charName);
    env->ReleaseStringUTFChars(jName, charName);

    AML::AMLData* amlData = GetHandle<AML::AMLData>(env, jData);

    try
    {
        amlObj->addData(name, *amlData);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }
}

JNIEXPORT jobject JNICALL
Java_org_datamodel_aml_AMLObject_getDataImpl(JNIEnv *env, jobject thiz, jstring jName)
{
    // printf("getDataImpl s\n");
    AML::AMLObject* amlObj = GetHandle<AML::AMLObject>(env, thiz);

    const char *charName = env->GetStringUTFChars(jName, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charName, "charName is null", JNI_EXCEPTION);
    std::string name(charName);
    env->ReleaseStringUTFChars(jName, charName);

    jobject jData;
    try
    {
        const AML::AMLData& data = amlObj->getData(name);
        jlong handle = reinterpret_cast<jlong>(&data);
        jData = env->NewObject(g_cls_AMLData, g_mid_AMLData_N_ctor, handle, false);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    return jData;
}

JNIEXPORT jobject JNICALL
Java_org_datamodel_aml_AMLObject_getDataNamesImpl(JNIEnv *env, jobject thiz)
{
    // printf("getDataNamesImpl s\n");
    AML::AMLObject* amlObj = GetHandle<AML::AMLObject>(env, thiz);

    std::vector<std::string> dataNames;
    try
    {
        dataNames = amlObj->getDataNames();
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    return convertStrVectorToJavaStrList(env, dataNames);
}

JNIEXPORT jstring JNICALL
Java_org_datamodel_aml_AMLObject_getDeviceIdImpl(JNIEnv *env, jobject thiz)
{
    // printf("getDeviceIdImpl s\n");
    AML::AMLObject* amlObj = GetHandle<AML::AMLObject>(env, thiz);

    std::string deviceId;
    try
    {
        deviceId = amlObj->getDeviceId();
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    return env->NewStringUTF(deviceId.c_str());
}

JNIEXPORT jstring JNICALL
Java_org_datamodel_aml_AMLObject_getTimeStampImpl(JNIEnv *env, jobject thiz)
{
    // printf("getTimeStampImpl s\n");
    AML::AMLObject* amlObj = GetHandle<AML::AMLObject>(env, thiz);

    std::string timeStamp;
    try
    {
        timeStamp = amlObj->getTimeStamp();
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    return env->NewStringUTF(timeStamp.c_str());
}

JNIEXPORT jstring JNICALL
Java_org_datamodel_aml_AMLObject_getIdImpl(JNIEnv *env, jobject thiz)
{
    // printf("getIdImpl s\n");
    AML::AMLObject* amlObj = GetHandle<AML::AMLObject>(env, thiz);

    std::string id;
    try
    {
        id = amlObj->getId();
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    return env->NewStringUTF(id.c_str());
}

JNIEXPORT void JNICALL
Java_org_datamodel_aml_AMLObject_destructAMLObject(JNIEnv *env, jobject thiz)
{
    AML::AMLObject* amlObj = GetHandle<AML::AMLObject>(env, thiz);
    delete amlObj;
}