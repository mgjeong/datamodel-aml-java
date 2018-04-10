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

#include "JniRepresentation.h"
#include "JniAMLUtils.h"
#include "Representation.h"
#include "AMLInterface.h"
#include "AMLException.h"

JNIEXPORT jlong JNICALL
Java_org_datamodel_aml_Representation_constructRepresentation(JNIEnv *env, jobject thiz, jstring jAmlFilePath)
{
    const char *charAmlFilePath = env->GetStringUTFChars(jAmlFilePath, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charAmlFilePath, "charAmlFilePath is null", JNI_EXCEPTION);
    std::string amlFilePath(charAmlFilePath);
    env->ReleaseStringUTFChars(jAmlFilePath, charAmlFilePath);

    AML::Representation* rep;
    try
    {
        rep = new AML::Representation(amlFilePath);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    return reinterpret_cast<jlong>(rep);
}

JNIEXPORT jstring JNICALL
Java_org_datamodel_aml_Representation_DataToAmlImpl(JNIEnv *env, jobject thiz, jobject jAmlObject)
{
    AML::Representation* rep = GetHandle<AML::Representation>(env, thiz);
    AML::AMLObject* amlObj = GetHandle<AML::AMLObject>(env, jAmlObject);

    std::string amlStr;
    try
    {
        amlStr = rep->DataToAml(*amlObj);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    return env->NewStringUTF(amlStr.c_str());
}

JNIEXPORT jobject JNICALL
Java_org_datamodel_aml_Representation_AmlToDataImpl(JNIEnv *env, jobject thiz, jstring jXmlStr)
{
    AML::Representation* rep = GetHandle<AML::Representation>(env, thiz);

    const char *charXmlStr = env->GetStringUTFChars(jXmlStr, nullptr);
    VERIFY_NON_NULL_THROW_EXCEPTION(charXmlStr, "charXmlStr is null", JNI_EXCEPTION);
    std::string xmlStr(charXmlStr);
    env->ReleaseStringUTFChars(jXmlStr, charXmlStr);

    jobject jAmlObject;
    try
    {
        AML::AMLObject* amlObj = rep->AmlToData(xmlStr);
        jlong handle = reinterpret_cast<jlong>(amlObj);
        jAmlObject = env->NewObject(g_cls_AMLObject, g_mid_AMLObject_N_ctor, handle, true);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    return jAmlObject;
}

JNIEXPORT jbyteArray JNICALL
Java_org_datamodel_aml_Representation_DataToByteImpl(JNIEnv *env, jobject thiz, jobject jAmlObject)
{
    AML::Representation* rep = GetHandle<AML::Representation>(env, thiz);
    AML::AMLObject* amlObj = GetHandle<AML::AMLObject>(env, jAmlObject);

    std::string byteStr;
    try
    {
        byteStr = rep->DataToByte(*amlObj);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    return convertStrToJavaByteArray(env, byteStr);
}

JNIEXPORT jobject JNICALL
Java_org_datamodel_aml_Representation_ByteToDataImpl(JNIEnv *env, jobject thiz, jbyteArray jAmlByte)
{
    AML::Representation* rep = GetHandle<AML::Representation>(env, thiz);

    std::string byteStr;
    convertJavaByteArrayToStr(env, jAmlByte, byteStr);

    jobject jAmlObject;
    try
    {
        AML::AMLObject* amlObj = rep->ByteToData(byteStr);
        jlong handle = reinterpret_cast<jlong>(amlObj);
        jAmlObject = env->NewObject(g_cls_AMLObject, g_mid_AMLObject_N_ctor, handle, true);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    return jAmlObject;
}

JNIEXPORT jstring JNICALL
Java_org_datamodel_aml_Representation_getRepresentationIdImpl(JNIEnv *env, jobject thiz)
{
    AML::Representation* rep = GetHandle<AML::Representation>(env, thiz);

    std::string repId;
    try
    {
        repId = rep->getRepresentationId();
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    return env->NewStringUTF(repId.c_str());
}

JNIEXPORT jobject JNICALL
Java_org_datamodel_aml_Representation_getConfigInfoImpl(JNIEnv *env, jobject thiz)
{
    AML::Representation* rep = GetHandle<AML::Representation>(env, thiz);

    jobject jAmlObject;
    try
    {
        AML::AMLObject* amlObj = rep->getConfigInfo();
        jlong handle = reinterpret_cast<jlong>(amlObj);
        jAmlObject = env->NewObject(g_cls_AMLObject, g_mid_AMLObject_N_ctor, handle, true);
    }
    catch (const AML::AMLException& e)
    {
        ThrowAMLException(e.code(), e.reason().c_str());
    }

    return jAmlObject;
}
