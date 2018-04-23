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

import java.util.List;

public final class AMLObject {

    static {
        System.loadLibrary("jniaml");
    }

    /**
     * @brief       Constructor.
     * @param       deviceId    [in] Device id that source device of AMLObject.
     * @param       timestamp   [in] timestamp value of AMLObject delibered by device.
     */
    public AMLObject(String deviceId, String timeStamp) throws AMLException {
        this.mNativeHandle = constructAMLObject(deviceId, timeStamp);
        this.mNativeNeedsDelete = true;
    }

    public native long constructAMLObject(String deviceId, String timeStamp) throws AMLException;

    /**
     * @brief       Constructor.
     * @param       deviceId    [in] Device id that source device of AMLObject.
     * @param       timestamp   [in] timestamp value of AMLObject delibered by device.
     * @param       id          [in] id of AMLObject.
     */
    public AMLObject(String deviceId, String timeStamp, String id) throws AMLException {
        this.mNativeHandle = constructAMLObject(deviceId, timeStamp, id);
        this.mNativeNeedsDelete = true;
    }

    public native long constructAMLObject(String deviceId, String timeStamp, String id) throws AMLException;

    private AMLObject(long nativeHandle, boolean nativeNeedsDelete) {
        this.mNativeHandle = nativeHandle;
        this.mNativeNeedsDelete = nativeNeedsDelete;
    }

    /**
     * @brief       This function adds AMLData to AMLObject using AMLData key that to match AMLData value.
     * @param       name    [in] AMLData key.
     * @param       data    [in] AMLData value.
     * @exception   AMLException If AMLData key is duplicated on AMLObject or if name is a invalid key.
     */
    public void addData(String name, AMLData data) throws AMLException {
        this.addDataImpl(name, data);
    }

    private native void addDataImpl(String name, AMLData data) throws AMLException;

    /**
     * @brief       This function returns AMLData which matched input name string with AMLObject's amlDatas key.
     * @param       name    [in] String value to use matching with key.
     * @return      AMLData that have sub key value fair.
     * @exception   AMLException If the input name does not exist in amlDatas.
     */
    public AMLData getData(String name) throws AMLException {
        return this.getDataImpl(name);
    }

    private native AMLData getDataImpl(String name) throws AMLException;

    /**
     * @brief       This function returns string list about AMLObject's amlDatas keys string array.
     * @return      vector of string data's keys value list.
     */
    public List<String> getDataNames() throws AMLException {
        return this.getDataNamesImpl();
    }

    private native List<String> getDataNamesImpl() throws AMLException;

    /**
     * @brief       This function returns Device's ID saved on AMLObject.
     * @return      String value of device's Id.
     */   
    public String getDeviceId() throws AMLException {
        return this.getDeviceIdImpl();
    }

    private native String getDeviceIdImpl() throws AMLException;

    /**
     * @brief       This function returns timestamp that deliveried device.
     * @return      String value of timestamp.
     */
    public String getTimeStamp() throws AMLException {
        return this.getTimeStampImpl();
    }

    private native String getTimeStampImpl() throws AMLException;

    /**
     * @brief       This function returns ID that AMLObject.
     * @return      String value of AMLObject Id.
     */
    public String getId() throws AMLException {
        return this.getIdImpl();
    }

    private native String getIdImpl() throws AMLException;

    public long getHandle() {
        return this.mNativeHandle;
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();

        if (this.mNativeNeedsDelete) {
            destructAMLObject();
        }
    }

    private native void destructAMLObject();

    private long mNativeHandle;
    private boolean mNativeNeedsDelete;
}
