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

import java.util.EnumSet;
import java.util.List;

public final class AMLData {

    static {
        System.loadLibrary("jniaml");
    }

    public enum ValueType {
        STRING,
        STRING_LIST,
        AMLDATA
    }

    /**
     * Constructor for AMLData.
     */
    public AMLData() {
        this.mNativeHandle = constructAMLData();
        this.mNativeNeedsDelete = true;
    }

    private AMLData(long nativeHandle, boolean nativeNeedsDelete) {
        this.mNativeHandle = nativeHandle;
        this.mNativeNeedsDelete = nativeNeedsDelete;
    }

    private native long constructAMLData();

    /**
     * This function set key and string type value pair on AMLData.
     * 
     * @param       key     [in] AMLData key.
     * @param       value   [in] AMLData value.
     */
    public void setValue(String key, String value) throws AMLException {
        this.setValueImpl(key, value);
    }

    private native void setValueImpl(String key, String value) throws AMLException;

    /**
     * This function set key and string list type value pair on AMLData.
     * 
     * @param       key     [in] AMLData key.
     * @param       value   [in] AMLData value.
     */
    public void setValue(String key,  List<String> value) throws AMLException {
        this.setValueImpl(key, value);
    }

    private native void setValueImpl(String key,  List<String> value) throws AMLException;

    /**
     * This function set key and AMLData type value pair on AMLData.
     * 
     * @param       key     [in] AMLData key.
     * @param       value   [in] AMLData value.
     */
    public void setValue(String key, AMLData value) throws AMLException {
        this.setValueImpl(key, value);
    }

    private native void setValueImpl(String key, AMLData value) throws AMLException;

    /**
     * This function returns string list about AMLData's AMLMap keys string list.
     * 
     * @return      list of string data's keys value list.
     */
    public List<String> getKeys() throws AMLException {
        return this.getKeysImpl();
    }

    private native List<String> getKeysImpl() throws AMLException;

    /**
     * This function returns string list about AMLData's AMLMap keys string list.
     * 
     * @param       key     [in] string of the AMLData value to check.
     * @return      ValueType that represents the type of value matched to the key.
     */
    public ValueType getValueType(String key) throws AMLException {
        return this.getValueTypeImpl(key);
    }

    private native ValueType getValueTypeImpl(String key) throws AMLException;

    /**
     * This function returns string which matched key in a AMLData's AMLMap.
     * 
     * @param       key     [in] pair's which has string value, key.
     * @return      String value which matched using key on AMLMap.
     * @exception   AMLException If input key is not matching on AMLMap.
     */
    public String getValueToStr(String key) throws AMLException {
        return this.getValueToStrImpl(key);
    }

    private native String getValueToStrImpl(String key) throws AMLException;

    /**
     * This function returns string list which matched key in a AMLData's AMLMap.
     * 
     * @param       key     [in] pair's which has string list value, key.
     * @return      String list value which matched using key on AMLMap.
     * @exception   AMLException If input key is not matching on AMLMap.
     */
    public List<String> getValueToStrList(String key) throws AMLException {
        return this.getValueToStrListImpl(key);
    }

    private native List<String> getValueToStrListImpl(String key) throws AMLException;

    /**
     * This function returns AMLData which matched key in a AMLData's AMLMap.
     * 
     * @param       key     [in] pair's which has AMLData, key.
     * @return      AMLData value which matched using key on AMLMap.
     * @exception   AMLException If input key is not matching on AMLMap.
     */
    public AMLData getValueToAMLData(String key) throws AMLException {
        return this.getValueToAMLDataImpl(key);
    }

    private native AMLData getValueToAMLDataImpl(String key) throws AMLException;

    public long getHandle() {
        return this.mNativeHandle;
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();

        if (this.mNativeNeedsDelete) {
            destructAMLData();
        }
    }

    private native void destructAMLData();

    private long mNativeHandle;
    private boolean mNativeNeedsDelete;
}
