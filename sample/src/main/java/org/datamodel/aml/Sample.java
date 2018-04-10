/******************************************************************
 *
 * Copyright 2018 Samsung Electronics All Rights Reserved.
 *
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 ******************************************************************/

package org.datamodel.aml;

import java.util.ArrayList;
import java.util.List;
import java.lang.Byte;
import java.util.Scanner;

import org.datamodel.aml.*;

public class Sample {
    public static void main(String[] args) throws Exception {

        System.out.println("Sample start");

        try
        {
            // construct Representation object
            Representation rep = new Representation("../src/main/resources/sample_data_model.aml");
            System.out.println("RepresentationId is " + rep.getRepresentationId());
            System.out.println("----------------------------------------------------------------------");

            AMLObject config_amlObj = rep.getConfigInfo();
            printAMLObject(config_amlObj);
            System.out.println("----------------------------------------------------------------------");


            // create AMLObject
            String deviceId = "GTC001";
            String timeStamp = "123456789";

            AMLObject amlObj = new AMLObject(deviceId, timeStamp);

            // create "Model" data
            AMLData model = new AMLData();
            model.setValue("ctname", "Model_107.113.97.248");
            model.setValue("con", "SR-P7-970");

            // create "Sample" data
            AMLData axis  = new AMLData();
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

            printAMLObject(amlObj);
            System.out.println("----------------------------------------------------------------------");

            // Convert AMLObject to AMLstring(XML)
            String aml_string = rep.DataToAml(amlObj);
            System.out.println(aml_string.length());
            System.out.println(aml_string);
            System.out.println("----------------------------------------------------------------------");

            // Convert AMLstring(XML) to AMLObject
            AMLObject data_from_aml = rep.AmlToData(aml_string);
            printAMLObject(data_from_aml);
            System.out.println("----------------------------------------------------------------------");

            // Convert AMLObject to Bytes
            byte[] bytes = rep.DataToByte(data_from_aml);
            System.out.println("bytes length : " + bytes.length);
            System.out.println(new String(bytes, 0));
            System.out.println("----------------------------------------------------------------------");

            // Convert Bytes to AMLObject
            AMLObject data_from_byte = rep.ByteToData(bytes);
            printAMLObject(data_from_byte);
            System.out.println("----------------------------------------------------------------------");

        } catch (AMLException e) {
            System.out.println(e.toString());
        }

        System.gc();

        System.out.println("Sample end");
    }

    public static void printAMLObject(AMLObject amlObj)
    {
        try
        {
            System.out.println("{");
            System.out.println("    \"device\" : " + amlObj.getDeviceId() + ",");
            System.out.println("    \"timestamp\" : " + amlObj.getTimeStamp() + ",");
            System.out.println("    \"id\" : " + amlObj.getId() + ",");

            List<String> dataNames = amlObj.getDataNames();

            for (String n : dataNames)
            {
                AMLData data = amlObj.getData(n);
        
                System.out.println("    \"" + n + "\" : ");
                printAMLData(data, 1);
                //if (n != dataNames.back()) System.out.println(",");
                if (dataNames.indexOf(n) != dataNames.size() - 1) System.out.println(",");
            }

            System.out.println("\n}");

        } catch (AMLException e) {
            System.out.println(e.toString());
        }
    }

    public static void printAMLData(AMLData amlData, int depth)
    {
        try
        {
            String indent = new String("");
            for (int i = 0; i < depth; i++) indent += "    ";

            System.out.println(indent + "{");

            List<String> keys = amlData.getKeys();
            for (String key : keys)
            {
                System.out.print(indent + "    \"" + key + "\" : ");
        
                AMLData.ValueType type = amlData.getValueType(key);
                if (AMLData.ValueType.STRING == type)
                {
                    String valStr = amlData.getValueToStr(key);
                    System.out.print(valStr);
                }
                else if (AMLData.ValueType.STRING_LIST == type)
                {
                    List<String> valStrList = amlData.getValueToStrList(key);
                    System.out.print("[");
                    for (String val : valStrList)
                    {
                        System.out.print(val);
                        //if (val != valStrList.back()) System.out.print(", ");
                        if (valStrList.indexOf(val) != valStrList.size() - 1) System.out.print(", ");
                    }
                    System.out.print("]");
                }
                else if (AMLData.ValueType.AMLDATA == type)
                {
                    AMLData valAMLData = amlData.getValueToAMLData(key);
                    System.out.print("\n");
                    printAMLData(valAMLData, depth + 1);
                }

                //if (key != keys.back()) System.out.print(",");
                if (keys.indexOf(key) != keys.size() - 1) System.out.print(",");
                System.out.print("\n");
            }
            System.out.print(indent + "}");

        } catch (AMLException e) {
            System.out.println(e.toString());
        }
    }
}
