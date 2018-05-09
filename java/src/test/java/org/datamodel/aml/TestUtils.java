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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {
    
    public static boolean isEqual(AMLData data1, AMLData data2) throws AMLException 
    {
        List<String> keys1 = data1.getKeys();
        List<String> keys2 = data2.getKeys();
        
        if(false == keys1.equals(keys2))
            return false;
        
        for(String key : keys1)
        {
            AMLData.ValueType type1 = data1.getValueType(key);
            AMLData.ValueType type2 = data2.getValueType(key);
            if(type1 != type2) 
                return false;
            if(AMLData.ValueType.STRING == type1)
            {
                String valStr1 = data1.getValueToStr(key);
                String valStr2 = data1.getValueToStr(key);
                if(false == valStr1.equals(valStr2))
                    return false;
            }
            else if(AMLData.ValueType.STRING_LIST == type1)
            {
                List<String> valStrArr1 = data1.getValueToStrList(key);
                List<String> valStrArr2 = data2.getValueToStrList(key);
                
                if(false == valStrArr1.equals(valStrArr2))
                    return false;
            }
            else if(AMLData.ValueType.AMLDATA == type1)
            {
                AMLData valAMLData1 = data1.getValueToAMLData(key);
                AMLData valAMLData2 = data2.getValueToAMLData(key);
                
                if(false == isEqual(valAMLData1, valAMLData2))
                    return false;
            }
        }
        return true;
    }
    
    public static boolean isEqual(AMLObject obj1, AMLObject obj2) throws AMLException
    {
        if(false == obj1.getDeviceId().equals(obj2.getDeviceId())) 
            return false;
        if(false == obj1.getTimeStamp().equals(obj2.getTimeStamp())) 
            return false;
        if(false == obj1.getId().equals(obj2.getId())) 
            return false;
        
        List<String> dataNames1 = obj1.getDataNames();
        List<String> dataNames2 = obj2.getDataNames();
        
        if(false == dataNames1.equals(dataNames2)) 
            return false;
        
        for(String n : dataNames1)
        {
            AMLData data1 = obj1.getData(n);
            AMLData data2 = obj2.getData(n);
            
            if(false == isEqual(data1, data2))
                return false;
        }
        
        return true;
    }
    
    public static boolean isEqual(byte[] binary1, byte[] binary2)
    {
        if(binary1.length != binary2.length)
            return false;
        
        int i;
        for(i = 0; i < binary1.length; i++) 
        {
            if(binary1[i] != binary2[i])
                return false;
        }
        return true;
    }
    
    public static boolean isPresent(String str, List<String> strList)
    {
        if(strList.contains(str))
            return true;
        
        return false;
    }
    
    public static AMLObject TestAMLObject() throws AMLException
    {
        // create AMLObject
        String deviceId = "GTC001";
        String timeStamp = "123456789";

        AMLObject amlObj = new AMLObject(deviceId, timeStamp);


        // create "Model" data
        AMLData model = new AMLData();
        model.setValue("ctname", "Model_107.113.97.248");
        model.setValue("con", "SR-P7-970");

        // create "Sample" data
        AMLData axis = new AMLData();
        axis.setValue("x", "20");
        axis.setValue("y", "110");
        axis.setValue("z", "80");

        AMLData info = new AMLData();
        info.setValue("id", "f437da3b");
        info.setValue("axis", axis);

        ArrayList<String> appendix = new ArrayList<>();
        appendix.add("52303");
        appendix.add("935");
        appendix.add("1442");

        AMLData sample = new AMLData();
        sample.setValue("info", info);
        sample.setValue("appendix", appendix);


        // Add Datas to AMLObject
        amlObj.addData("Model", model);
        amlObj.addData("Sample", sample);

        return amlObj;
    }   
}

