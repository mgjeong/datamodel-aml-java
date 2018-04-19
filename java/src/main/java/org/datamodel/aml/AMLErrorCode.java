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

public enum AMLErrorCode {
    OK("OK", ""),
    INVALID_PARAM("INVALID_PARAM", ""),
    INVALID_FILE_PATH("INVALID_FILE_PATH", ""),
    INVALID_AML_SCHEMA("INVALID_AML_SCHEMA", ""),
    INVALID_XML_STR("INVALID_XML_STR", ""),
    NOT_MATCH_TO_AML_MODEL("NOT_MATCH_TO_AML_MODEL", ""),
    INVALID_BYTE_STR("INVALID_BYTE_STR", ""),
    SERIALIZE_FAIL("SERIALIZE_FAIL", ""),
    NO_MEMORY("NO_MEMORY", ""),
    KEY_NOT_EXIST("KEY_NOT_EXIST", ""),
    KEY_ALREADY_EXIST("KEY_ALREADY_EXIST", ""),
    WRONG_GETTER_TYPE("WRONG_GETTER_TYPE", ""),
    API_NOT_ENABLED("API_NOT_ENABLED", ""),

    JNI_EXCEPTION("JNI_EXCEPTION", "Generic Java binder error"),
    JNI_NO_NATIVE_OBJECT("JNI_NO_NATIVE_OBJECT", ""),
    JNI_INVALID_VALUE("JNI_INVALID_VALUE", ""),
    INVALID_CLASS_CAST("INVALID_CLASS_CAST", ""),;

    private String error;
    private String description;

    private AMLErrorCode(String error, String description) {
        this.error = error;
        this.description = description;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }

    public static AMLErrorCode get(String errorCode) {
        for (AMLErrorCode eCode : AMLErrorCode.values()) {
            if (eCode.getError().equals(errorCode)) {
                return eCode;
            }
        }
        throw new IllegalArgumentException("Unexpected AMLErrorCode value");
    }

    @Override
    public String toString() {
        return error + (description.isEmpty() ? "" : " : " + description);
    }
}
