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

    public AMLObject(String deviceId, String timeStamp) throws AMLException {
        this.mNativeHandle = constructAMLObject(deviceId, timeStamp);
        this.mNativeNeedsDelete = true;
    }

    public native long constructAMLObject(String deviceId, String timeStamp) throws AMLException;

    public AMLObject(String deviceId, String timeStamp, String id) throws AMLException {
        this.mNativeHandle = constructAMLObject(deviceId, timeStamp, id);
        this.mNativeNeedsDelete = true;
    }

    public native long constructAMLObject(String deviceId, String timeStamp, String id) throws AMLException;

    private AMLObject(long nativeHandle, boolean nativeNeedsDelete) {
        this.mNativeHandle = nativeHandle;
        this.mNativeNeedsDelete = nativeNeedsDelete;
    }

    public void addData(String name, AMLData data) throws AMLException {
        this.addDataImpl(name, data);
    }

    private native void addDataImpl(String name, AMLData data) throws AMLException;


    public AMLData getData(String name) throws AMLException {
        return this.getDataImpl(name);
    }

    private native AMLData getDataImpl(String name) throws AMLException;


    public List<String> getDataNames() throws AMLException {
        return this.getDataNamesImpl();
    }

    private native List<String> getDataNamesImpl() throws AMLException;


    public String getDeviceId() throws AMLException {
        return this.getDeviceIdImpl();
    }

    private native String getDeviceIdImpl() throws AMLException;


    public String getTimeStamp() throws AMLException {
        return this.getTimeStampImpl();
    }

    private native String getTimeStampImpl() throws AMLException;


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
