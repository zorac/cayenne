/*****************************************************************
 *   Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 ****************************************************************/
package org.apache.cayenne.project;

import org.apache.cayenne.configuration.ConfigurationNameMapper;
import org.apache.cayenne.configuration.DataChannelDescriptor;
import org.apache.cayenne.configuration.DataChannelDescriptorLoader;
import org.apache.cayenne.configuration.DataMapLoader;
import org.apache.cayenne.configuration.DefaultConfigurationNameMapper;
import org.apache.cayenne.configuration.xml.DataChannelMetaData;
import org.apache.cayenne.configuration.xml.DefaultHandlerFactory;
import org.apache.cayenne.configuration.xml.HandlerFactory;
import org.apache.cayenne.configuration.xml.NoopDataChannelMetaData;
import org.apache.cayenne.configuration.xml.XMLDataChannelDescriptorLoader;
import org.apache.cayenne.configuration.xml.XMLDataMapLoader;
import org.apache.cayenne.configuration.xml.XMLReaderProvider;
import org.apache.cayenne.di.AdhocObjectFactory;
import org.apache.cayenne.di.Binder;
import org.apache.cayenne.di.ClassLoaderManager;
import org.apache.cayenne.di.DIBootstrap;
import org.apache.cayenne.di.Injector;
import org.apache.cayenne.di.Module;
import org.apache.cayenne.di.spi.DefaultAdhocObjectFactory;
import org.apache.cayenne.di.spi.DefaultClassLoaderManager;
import org.apache.cayenne.resource.Resource;
import org.apache.cayenne.resource.URLResource;
import org.junit.Test;
import org.xml.sax.XMLReader;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class DataChannelProjectLoaderTest {

    @Test
    public void testLoad() {

        DataChannelProjectLoader loader = new DataChannelProjectLoader();

        Module testModule = new Module() {

            @Override
            public void configure(Binder binder) {
                binder.bind(ClassLoaderManager.class).to(DefaultClassLoaderManager.class);
                binder.bind(AdhocObjectFactory.class).to(DefaultAdhocObjectFactory.class);

                binder.bind(DataMapLoader.class).to(XMLDataMapLoader.class);
                binder.bind(DataChannelDescriptorLoader.class).to(XMLDataChannelDescriptorLoader.class);
                binder.bind(ConfigurationNameMapper.class).to(DefaultConfigurationNameMapper.class);
                binder.bind(HandlerFactory.class).to(DefaultHandlerFactory.class);
                binder.bind(DataChannelMetaData.class).to(NoopDataChannelMetaData.class);
                binder.bind(XMLReader.class).toProviderInstance(new XMLReaderProvider(false)).withoutScope();
            }
        };

        Injector injector = DIBootstrap.createInjector(testModule);
        injector.injectMembers(loader);

        String testConfigName = "PROJECT1";
        String baseUrl = getClass().getPackage().getName().replace('.', '/');
        URL url = getClass().getClassLoader().getResource(baseUrl + "/cayenne-" + testConfigName + ".xml");

        Resource rootSource = new URLResource(url);

        Project project = loader.loadProject(rootSource);
        assertNotNull(project);

        DataChannelDescriptor rootNode = (DataChannelDescriptor) project.getRootNode();
        assertNotNull(rootNode);
        assertSame(rootSource, rootNode.getConfigurationSource());
        
        assertNotNull(project.getConfigurationResource());
        assertEquals(project.getConfigurationResource(), rootSource);
    }
}
