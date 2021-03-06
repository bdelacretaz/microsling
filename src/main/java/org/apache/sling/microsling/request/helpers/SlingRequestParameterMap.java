/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sling.microsling.request.helpers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.microsling.api.requestparams.RequestParameter;
import org.apache.sling.microsling.api.requestparams.RequestParameterMap;

/**
 * The <code>SlingRequestParameterMap</code> implements the
 * <code>RequestParameterMap</code> map interface simply containing all
 * request parameters as <code>RequestParameter</code> instances.
 */
public class SlingRequestParameterMap extends
        HashMap<String, RequestParameter[]> implements RequestParameterMap {

    /** Create from the HTTP request parameters */
    public SlingRequestParameterMap(HttpServletRequest request) {
        Map<?, ?> parameters = request.getParameterMap();
        for (Map.Entry<?, ?> entry : parameters.entrySet()) {
            String[] values = (String[]) entry.getValue();
            RequestParameter[] rpValues = new RequestParameter[values.length];
            for (int i = 0; i < values.length; i++) {
                rpValues[i] = new SimpleRequestParameter(values[i]);
            }
            put((String) entry.getKey(), rpValues);
        }
    }

    public RequestParameter[] getValues(String name) {
        return get(name);
    }

    public RequestParameter getValue(String name) {
        RequestParameter[] values = get(name);
        return (values != null && values.length > 0) ? values[0] : null;
    }

    /** Simple implementation of the RequestParameter interface */
    private static class SimpleRequestParameter implements RequestParameter {

        private String value;

        private byte[] cachedBytes;

        SimpleRequestParameter(String value) {
            this.value = value;
        }

        /**
         * Convert the parameter string value to a byte[] using ISO-8859-1
         * encoding, which is assumed to be the default for parameters
         */
        public byte[] get() {
            if (cachedBytes == null) {
                try {
                    cachedBytes = getString().getBytes("ISO-88591-1");
                } catch (UnsupportedEncodingException uee) {
                    // don't care, fall back to platform default
                    // actually, this is not expected as ISO-8859-1 is required
                    cachedBytes = getString().getBytes();
                }
            }
            return cachedBytes;
        }

        public String getContentType() {
            return null;
        }

        public String getFileName() {
            return null;
        }

        public InputStream getInputStream() {
            return new ByteArrayInputStream(get());
        }

        public long getSize() {
            return get().length;
        }

        public String getString() {
            return value;
        }

        public String getString(String encoding) {
            return value;
        }

        public boolean isFormField() {
            return true;
        }

    }
}
