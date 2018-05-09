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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RepresentationTest {
    
    @Rule
    public ExpectedException thrown= ExpectedException.none();
    
    String amlModelFile                = "./src/test/resources/TEST_DataModel.aml";
    String amlModelFile_invalid_NoCAEX = "./src/test/resources/TEST_DataModel_Invalid_NoCAEX.aml";
    String amlModelFile_invalid_NoSUCL = "./src/test/resources/TEST_DataModel_Invalid_NoSUCL.aml";
    String amlDataFile                 = "./src/test/resources/TEST_Data.aml";
    String dataBinaryFile              = "./src/test/resources/TEST_DataBinary";

    String amlModelId                  = "GTC_Robot_0.0.1"; // from "TEST_DataModel.aml" file
    
    String TestAML() throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(amlDataFile));
        return new String(encoded, Charset.defaultCharset());
    }
    
    byte[] TestBinary() throws IOException
    {
        return Files.readAllBytes(Paths.get(dataBinaryFile));
    }
    
    @Test
    public void Representation_ConstructTest() throws AMLException {
        Representation rep = new Representation(amlModelFile);
    }
    
    @Test
    public void Representation_ConstructTest_InvalidPath() throws AMLException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.INVALID_FILE_PATH.toString());
        
        Representation rep = new Representation("NoeExist.aml");
    }
    
    @Test
    public void Representation_ConstructTest_withoutCAEX() throws AMLException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.INVALID_AML_SCHEMA.toString());
        
        Representation rep = new Representation(amlModelFile_invalid_NoCAEX);
    }
    
    @Test
    public void Representation_ConstructTest_withoutSUCL() throws AMLException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.INVALID_AML_SCHEMA.toString());
        
        Representation rep = new Representation(amlModelFile_invalid_NoSUCL);
    }
    
    @Test
    public void Representation_AmlToDataTest() throws AMLException, IOException {
        Representation rep = new Representation(amlModelFile);
        AMLObject amlObj;
        String amlStr = TestAML();
        amlObj = rep.AmlToData(amlStr);
        
        AMLObject varify = TestUtils.TestAMLObject();
        assertTrue(TestUtils.isEqual(amlObj, varify));
    }
    
    @Test
    public void Representation_AmlToDataTest_InvalidAml() throws AMLException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.INVALID_AML_SCHEMA.toString());
        
        Representation rep = new Representation(amlModelFile);
        AMLObject amlObj;
        String invalidAmlStr = "<invalid />";
                
        amlObj = rep.AmlToData(invalidAmlStr);
    }
    
    @Test
    public void Representation_DataToAmlTest() throws AMLException, IOException {
        Representation rep = new Representation(amlModelFile);
        
        AMLObject amlObj = TestUtils.TestAMLObject();
        String amlStr;
        amlStr = rep.DataToAml(amlObj);
        
        String varify = TestAML();
        assertTrue(varify.equals(amlStr));
    }
    
    @Test
    public void Representation_DataToAmlTest_InvalidDataToModel() throws AMLException, IOException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.NOT_MATCH_TO_AML_MODEL.toString());
        
        Representation rep = new Representation(amlModelFile);
        
        AMLObject notMatchToModel = new AMLObject("deviceId", "0");
        
        AMLData data = new AMLData();
        data.setValue("invalidKey", "invalidvalue");
        
        notMatchToModel.addData("invalidData", data);
        
        rep.DataToAml(notMatchToModel);
    }
    
    @Test
    public void Representation_DataToAmlTest_IgnoreDataNotInModel() throws AMLException, IOException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.KEY_NOT_EXIST.toString());
        
        Representation rep = new Representation(amlModelFile);
        
        AMLObject amlObj = TestUtils.TestAMLObject();
        
        AMLData sample = amlObj.getData("sample");
        sample.setValue("additionalKey", "additionalValue");
        
        String amlStr;
        amlStr = rep.DataToAml(amlObj);
        
        AMLObject resultObj;
        resultObj = rep.AmlToData(amlStr);
        
        AMLData sampleResult = resultObj.getData("sample");
        
        sampleResult.getValueToStr("additionalKey");
    }
    
    @Test
    public void Representation_ByteToDataTest() throws AMLException, IOException {
        Representation rep = new Representation(amlModelFile);
        AMLObject amlObj;
        byte[] binary = TestBinary();
        
        amlObj = rep.ByteToData(binary);
        
        AMLObject varify = TestUtils.TestAMLObject();
        
        assertTrue(TestUtils.isEqual(amlObj, varify));
    }
    
    @Test
    public void Representation_ByteToDataTest_InvalidByte() throws AMLException, IOException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.INVALID_BYTE_STR.toString());
        
        Representation rep = new Representation(amlModelFile);
        AMLObject amlObj;
        
        String temp = "invalidBianry";
        byte[] binary = temp.getBytes();
        
        amlObj = rep.ByteToData(binary);
    }
    
    @Test
    public void Representation_DataToByteTest() throws AMLException, IOException {
        Representation rep = new Representation(amlModelFile);
        AMLObject amlObj = TestUtils.TestAMLObject();
        
        byte[] amlbinary;
        amlbinary = rep.DataToByte(amlObj);
        
        byte[] varify = TestBinary();
                
        assertTrue(TestUtils.isEqual(amlbinary, varify));
    }
    
    @Test
    public void Representation_GetRepIdTest() throws AMLException, IOException {
        Representation rep = new Representation(amlModelFile);
        assertTrue(amlModelId.equals(rep.getRepresentationId()));
    }
}
