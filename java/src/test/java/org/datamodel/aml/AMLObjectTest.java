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

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AMLObjectTest {
    
    @Rule
    public ExpectedException thrown= ExpectedException.none();
    
    @Test
    public void AMLObject_ConstructWithIdTest() throws AMLException {
        String deviceId  = "deviceId";
        String timeStamp = "timeStamp";
        String id = "id";

        AMLObject amlObj = new AMLObject(deviceId, timeStamp, id);

        assertTrue(deviceId.equals(amlObj.getDeviceId()));
        assertTrue(timeStamp.equals(amlObj.getTimeStamp()));
        assertTrue(id.equals(amlObj.getId()));
    }
    
    @Test
    public void AMLObject_ConstructWithoutID() throws AMLException {
        String deviceId  = "deviceId";
        String timeStamp = "timeStamp";

        AMLObject amlObj = new AMLObject(deviceId, timeStamp);
        
        String id = deviceId + "_" + timeStamp;
        
        assertTrue(deviceId.equals(amlObj.getDeviceId()));
        assertTrue(timeStamp.equals(amlObj.getTimeStamp()));
        assertTrue(id.equals(amlObj.getId()));
    }
    
    @Test
    public void AMLObject_ConstructWithNoDeviceId() throws AMLException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.INVALID_PARAM.toString());
        
        AMLObject amlObj = new AMLObject("", "0");
    }
    
    @Test
    public void AMLObject_ConstructWithNoId() throws AMLException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.INVALID_PARAM.toString());
        
        String deviceId  = "deviceId";
        String timeStamp = "timeStamp";
        String id = "";
        
        AMLObject amlObj = new AMLObject(deviceId, timeStamp, id);
    }
    
    @Test
    public void AMLObject_addDataTest() throws AMLException {
        AMLData amlData = new AMLData();
        amlData.setValue("key", "value");
        
        AMLObject amlObj = new AMLObject("deviceId", "timeStamp");
        
        String dataName = "dataName";
        
        amlObj.addData(dataName, amlData);
    }
    
    @Test
    public void AMLObject_addDataTest_DupKey() throws AMLException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.KEY_ALREADY_EXIST.toString());
        
        AMLData amlData1 = new AMLData();
        amlData1.setValue("key1", "value1");
        
        AMLData amlData2 = new AMLData();
        amlData2.setValue("key2", "value2");
        
        AMLObject amlObj = new AMLObject("deviceId", "timeStamp");
        
        String dataName = "dataName";
        
        amlObj.addData(dataName, amlData1);
        amlObj.addData(dataName, amlData2);
    }
        
    @Test
    public void AMLObject_getDataTest() throws AMLException {
        AMLData amlData = new AMLData();
        String key = "key";
        String value = "value";
        
        amlData.setValue(key, value);
        
        AMLObject amlObj = new AMLObject("deviceId", "timeStamp");
        
        String dataName = "dataName";
        amlObj.addData(dataName, amlData);
        
        AMLData amlData_get;
        amlData_get = amlObj.getData(dataName);
        assertTrue(value.equals(amlData_get.getValueToStr(key)));
    }

    @Test
    public void AMLObject_getDataTest_InvalidKey() throws AMLException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.KEY_NOT_EXIST.toString());
        
        AMLData amlData = new AMLData();
        String key = "key";
        String value = "value";
        
        amlData.setValue(key, value);
        
        AMLObject amlObj = new AMLObject("deviceId", "timeStamp");
        
        String dataName = "dataName";
        amlObj.addData(dataName, amlData);
        
        AMLData amlData_get;
        amlData_get = amlObj.getData("invalid_dataName");
    }
}
