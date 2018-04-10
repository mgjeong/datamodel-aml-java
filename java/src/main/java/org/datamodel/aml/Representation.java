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

package org.datamodel.aml;

import java.lang.Byte;

public final class Representation {

    static {
        System.loadLibrary("jniaml");
    }

    public Representation(String amlFilePath) throws AMLException {
        this.mNativeHandle = constructRepresentation(amlFilePath);
    }

    public native long constructRepresentation(String amlFilePath) throws AMLException;


    public String DataToAml(AMLObject amlObject) throws AMLException {
        return this.DataToAmlImpl(amlObject);
    }

    private native String DataToAmlImpl(AMLObject amlObject) throws AMLException;


    public AMLObject AmlToData(String xmlStr) throws AMLException {
        return this.AmlToDataImpl(xmlStr);
    }

    private native AMLObject AmlToDataImpl(String xmlStr) throws AMLException;


    public byte[] DataToByte(AMLObject amlObject) throws AMLException {
        return this.DataToByteImpl(amlObject);
    }

    private native byte[] DataToByteImpl(AMLObject amlObject) throws AMLException;


    public AMLObject ByteToData(byte[] amlByte) throws AMLException {
        return this.ByteToDataImpl(amlByte);
    }

    private native AMLObject ByteToDataImpl(byte[] amlByte) throws AMLException;


    public String getRepresentationId() throws AMLException {
        return this.getRepresentationIdImpl();
    }

    private native String getRepresentationIdImpl() throws AMLException;


    public AMLObject getConfigInfo() throws AMLException {
        return this.getConfigInfoImpl();
    }

    private native AMLObject getConfigInfoImpl() throws AMLException;


    public long getHandle() {
        return this.mNativeHandle;
    }

    private long mNativeHandle;
}
