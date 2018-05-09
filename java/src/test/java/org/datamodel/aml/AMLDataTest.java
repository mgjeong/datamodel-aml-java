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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AMLDataTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void AMLData_setValueStrTest() throws AMLException {
        AMLData amlData = new AMLData();

        String key = "key";
        String value = "value";
        
        amlData.setValue(key, value);
    }

    @Test
    public void AMLData_setValueStrTest_DupKey() throws AMLException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.KEY_ALREADY_EXIST.toString());
        AMLData amlData = new AMLData();

        String key = "key";
        String value = "value";

        amlData.setValue(key, value);
        amlData.setValue(key, value);
    }

    @Test
    public void AMLData_setValueStrArrTest() throws AMLException {
        AMLData amlData = new AMLData();

        String key = "key";
        ArrayList<String> value = new ArrayList<>();
        value.add("value");
                
        amlData.setValue(key, value);
    }

    @Test
    public void AMLData_setValueStrArrTest_DupKey() throws AMLException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.KEY_ALREADY_EXIST.toString());
        AMLData amlData = new AMLData();

        String key = "key";
        ArrayList<String> value = new ArrayList<>();
        value.add("value");

        amlData.setValue(key, value);
        amlData.setValue(key, value);
    }

    @Test
    public void AMLData_setValueAMLDataTest() throws AMLException {
        AMLData amlData = new AMLData();

        String key = "key";
        AMLData value = new AMLData();

        amlData.setValue(key, value);
    }

    @Test
    public void AMLData_setValueAMLDataTest_DupKey() throws AMLException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.KEY_ALREADY_EXIST.toString());
        AMLData amlData = new AMLData();

        String key = "key";
        AMLData value = new AMLData();

        amlData.setValue(key, value);
        amlData.setValue(key, value);
    }

    @Test
    public void AMLData_getValueStrTest() throws AMLException {
        AMLData amlData = new AMLData();

        String key = "key";
        String value = "value";

        amlData.setValue(key, value);
        assertEquals(value, amlData.getValueToStr(key));
    }

    @Test
    public void AMLData_getValueStrTest_WrongKey() throws AMLException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.WRONG_GETTER_TYPE.toString());
        AMLData amlData = new AMLData();

        String key = "key";
        ArrayList<String> value = new ArrayList<>();
        value.add("value");

        amlData.setValue(key, value);
        assertEquals(value, amlData.getValueToStr(key));
    }

    @Test
    public void AMLData_getValueStrArrTest() throws AMLException {
        AMLData amlData = new AMLData();

        String key = "key";
        ArrayList<String> value = new ArrayList<>();
        value.add("value");

        amlData.setValue(key, value);
        assertEquals(value, amlData.getValueToStrList(key));
    }

    @Test
    public void AMLData_getValueStrArrTest_WrongKey() throws AMLException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.WRONG_GETTER_TYPE.toString());
        AMLData amlData = new AMLData();

        String key = "key";
        String value = "value";

        amlData.setValue(key, value);
        assertEquals(value, amlData.getValueToStrList(key));
    }

    @Test
    public void AMLData_getValueAMLDataTest() throws AMLException {
        AMLData amlData = new AMLData();

        String key = "key";
        AMLData value = new AMLData();

        amlData.setValue(key, value);

        assertTrue(TestUtils.isEqual(value, amlData.getValueToAMLData(key)));
    }

    @Test
    public void AMLData_getValueAMLDataTest_WrongKey() throws AMLException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.WRONG_GETTER_TYPE.toString());
        AMLData amlData = new AMLData();

        String key = "key";
        String value = "value";

        amlData.setValue(key, value);
        assertEquals(value, amlData.getValueToAMLData(key));
    }

    @Test
    public void AMLData_getKeys() throws AMLException {
        AMLData amlData = new AMLData();

        String key1 = "key1";
        String value1 = "value1";

        String key2 = "key2";
        ArrayList<String> value2 = new ArrayList<>();
        value2.add("value2");

        String key3 = "key3";
        AMLData value3 = new AMLData();

        amlData.setValue(key1, value1);
        amlData.setValue(key2, value2);
        amlData.setValue(key3, value3);

        List<String> keys = amlData.getKeys();
        assertTrue(TestUtils.isPresent(key1, keys));
    }

    @Test
    public void AMLData_getValueType() throws AMLException {
        AMLData amlData = new AMLData();

        String key1 = "key1";
        String value1 = "value1";

        String key2 = "key2";
        ArrayList<String> value2 = new ArrayList<>();
        value2.add("value2");

        String key3 = "key3";
        AMLData value3 = new AMLData();

        amlData.setValue(key1, value1);
        amlData.setValue(key2, value2);
        amlData.setValue(key3, value3);

        assertTrue(amlData.getValueType(key1) == AMLData.ValueType.STRING);
        assertTrue(amlData.getValueType(key2) == AMLData.ValueType.STRING_LIST);
        assertTrue(amlData.getValueType(key3) == AMLData.ValueType.AMLDATA);
    }

    @Test
    public void AMLData_getValueType_InvalidKey() throws AMLException {
        thrown.expect(AMLException.class);
        thrown.expectMessage(AMLErrorCode.KEY_NOT_EXIST.toString());
        AMLData amlData = new AMLData();

        String key1 = "key1";
        String value1 = "value1";

        String key2 = "key2";
        ArrayList<String> value2 = new ArrayList<>();
        value2.add("value2");

        String key3 = "key3";
        AMLData value3 = new AMLData();

        amlData.setValue(key1, value1);
        amlData.setValue(key2, value2);
        amlData.setValue(key3, value3);

        assertTrue(amlData.getValueType("key4") == AMLData.ValueType.STRING);
    }
}
