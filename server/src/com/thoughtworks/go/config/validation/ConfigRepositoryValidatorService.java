/*************************GO-LICENSE-START*********************************
 * Copyright 2014 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *************************GO-LICENSE-END***********************************/

package com.thoughtworks.go.config.validation;

import com.thoughtworks.go.service.ConfigRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({"unchecked"})
public class ConfigRepositoryValidatorService implements InitializingBean {
    private static final Logger LOG = Logger.getLogger(ConfigRepositoryValidatorService.class);
    private ConfigRepository configRepository;

    @Autowired
    public ConfigRepositoryValidatorService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public void afterPropertiesSet() throws Exception {
        if (configRepository.isRepositoryCorrupted()) {
            LOG.error("[FAILURE] Go Server failed to start as its configuration history store is corrupt. Please contact support@thoughtworks.com");
            shutDownServer();
        }
    }

    void shutDownServer() {
        new Thread(new Runnable() {
            public void run() {
                System.exit(1);
            }
        }).start();
    }

    public void destroy() throws Exception {

    }
}
