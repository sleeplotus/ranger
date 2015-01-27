/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.ranger.plugin.store;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ranger.plugin.store.file.ServiceFileStore;
import org.apache.ranger.plugin.store.rest.ServiceRESTStore;
import org.apache.ranger.plugin.util.RangerRESTClient;


public class ServiceStoreFactory {
	private static final Log LOG = LogFactory.getLog(ServiceStoreFactory.class);

	private static ServiceStoreFactory sInstance = null;

	private ServiceStore serviceStore = null;


	public static ServiceStoreFactory instance() {
		if(sInstance == null) {
			sInstance = new ServiceStoreFactory();
		}

		return sInstance;
	}

	public ServiceStore getServiceStore() {
		return serviceStore;
	}

	private ServiceStoreFactory() {
		if(LOG.isDebugEnabled()) {
			LOG.debug("==> ServiceStoreFactory.ServiceStoreFactory()");
		}

		init();

		if(LOG.isDebugEnabled()) {
			LOG.debug("<== ServiceStoreFactory.ServiceStoreFactory()");
		}
	}

	private void init() {
		if(LOG.isDebugEnabled()) {
			LOG.debug("==> ServiceStoreFactory.init()");
		}
		
		boolean useFileStore = true;

		if(useFileStore) {
			serviceStore = new ServiceFileStore(); // TODO: configurable store implementation
		} else {
			RangerRESTClient restClient = new RangerRESTClient("http://localhost:6080", "");
			restClient.setBasicAuthInfo("admin", "admin");
	
			serviceStore = new ServiceRESTStore(restClient);
		}


		if(LOG.isDebugEnabled()) {
			LOG.debug("<== ServiceStoreFactory.init()");
		}
	}
}