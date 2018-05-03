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

    /**
     * Constructor for Representation with aml file path.
     * 
     * @param       amlFilePath [in] File path of AML that contains a data model information.
     */
    public Representation(String amlFilePath) throws AMLException {
        this.mNativeHandle = constructRepresentation(amlFilePath);
    }

    private native long constructRepresentation(String amlFilePath) throws AMLException;

    /**
     * This function converts AMLObject to AML(XML) string to match the AML model information which is set by constructor.
     * 
     * @param       amlObject [in] AMLObject to be converted.
     * @return      AML(XML) string converted from amlObject.
     * @exception   AMLException If the schema of amlObject does not match to AML model information
     */
    public String DataToAml(AMLObject amlObject) throws AMLException {
        return this.DataToAmlImpl(amlObject);
    }

    private native String DataToAmlImpl(AMLObject amlObject) throws AMLException;

    /**
     * This function converts AML(XML) string to AMLObject to match the AML model information which is set by constructor.
     * 
     * @param       xmlStr [in] AML(XML) string to be converted.
     * @return      AMLObject instance converted from AML(XML) string.
     * @exception   AMLException If the schema of xmlStr does not match to AML model information
     */
    public AMLObject AmlToData(String xmlStr) throws AMLException {
        return this.AmlToDataImpl(xmlStr);
    }

    private native AMLObject AmlToDataImpl(String xmlStr) throws AMLException;

    /**
     * This function converts AMLObject to Protobuf byte data to match the AML model information which is set by constructor.
     * If 'disable_protobuf' build option is enabled, this API will be DISABLED and throw AMLException with code 'API_NOT_ENABLED'.
     * 
     * @param       amlObject [in] AMLObject to be converted.
     * @return      Protobuf byte data converted from amlObject.
     * @exception   AMLException If the schema of amlObject does not match to AML model information
     */
    public byte[] DataToByte(AMLObject amlObject) throws AMLException {
        return this.DataToByteImpl(amlObject);
    }

    private native byte[] DataToByteImpl(AMLObject amlObject) throws AMLException;

    /**
     * This function converts Protobuf byte data to AMLObject to match the AML model information which is set by constructor.
     * If 'disable_protobuf' build option is enabled, this API will be DISABLED and throw AMLException with code 'API_NOT_ENABLED'.
     * 
     * @param       amlByte [in] Protobuf byte data to be converted.
     * @return      AMLObject instance converted from amlObject.
     * @exception   AMLException If the schema of byte does not match to AML model information
     */
    public AMLObject ByteToData(byte[] amlByte) throws AMLException {
        return this.ByteToDataImpl(amlByte);
    }

    private native AMLObject ByteToDataImpl(byte[] amlByte) throws AMLException;

    /**
     * This function returns AutomationML SystemUnitClassLib's unique ID
     * 
     * @return      string value of AML SystemUnitClassLIb's ID
     */
    public String getRepresentationId() throws AMLException {
        return this.getRepresentationIdImpl();
    }

    private native String getRepresentationIdImpl() throws AMLException;

    /**
     * This function returns AMLObject that contains configuration data which is present in RoleClassLib.
     * 
     * @return      AMLObject that has configuration data.
     */
    public AMLObject getConfigInfo() throws AMLException {
        return this.getConfigInfoImpl();
    }

    private native AMLObject getConfigInfoImpl() throws AMLException;


    public long getHandle() {
        return this.mNativeHandle;
    }

    private long mNativeHandle;
}
