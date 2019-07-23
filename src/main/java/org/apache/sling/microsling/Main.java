/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.sling.microsling;

import org.apache.jackrabbit.j2ee.SimpleWebdavServlet;
import org.apache.jackrabbit.servlet.jackrabbit.JackrabbitRepositoryServlet;
import org.apache.sling.microsling.servlet.MicroslingMainServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class Main {
    public static void main(String [] args) throws Exception {
        final Server server = new Server(8080);
        final ServletHandler sh = new ServletHandler();
        server.setHandler(sh);
        sh.addServletWithMapping(MicroslingMainServlet.class, "/*");
        sh.addServletWithMapping(SimpleWebdavServlet.class, "/repository/*");
        sh.addServletWithMapping(JackrabbitRepositoryServlet.class, "/repository-info");
        server.start();
        server.join();
    }
}