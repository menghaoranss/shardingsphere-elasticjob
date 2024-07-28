/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.elasticjob.test.e2e.raw.enable;

import org.apache.shardingsphere.elasticjob.api.ElasticJob;
import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.kernel.infra.env.IpUtils;
import org.apache.shardingsphere.elasticjob.kernel.internal.config.JobConfigurationPOJO;
import org.apache.shardingsphere.elasticjob.kernel.infra.yaml.YamlEngine;
import org.apache.shardingsphere.elasticjob.bootstrap.type.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.test.e2e.raw.BaseE2ETest;
import org.apache.shardingsphere.elasticjob.kernel.internal.schedule.JobRegistry;
import org.apache.shardingsphere.elasticjob.kernel.internal.server.ServerStatus;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class EnabledJobE2ETest extends BaseE2ETest {
    
    protected EnabledJobE2ETest(final TestType type, final ElasticJob elasticJob) {
        super(type, elasticJob);
    }
    
    @BeforeEach
    void assertEnabledRegCenterInfo() {
        assertThat(JobRegistry.getInstance().getCurrentShardingTotalCount(getJobName()), is(3));
        assertThat(JobRegistry.getInstance().getJobInstance(getJobName()).getServerIp(), is(IpUtils.getIp()));
        JobConfiguration jobConfig = YamlEngine.unmarshal(getRegistryCenter().get("/" + getJobName() + "/config"), JobConfigurationPOJO.class).toJobConfiguration();
        assertThat(jobConfig.getShardingTotalCount(), is(3));
        if (getJobBootstrap() instanceof ScheduleJobBootstrap) {
            assertThat(jobConfig.getCron(), is("0/1 * * * * ?"));
        } else {
            assertNull(jobConfig.getCron());
        }
        assertThat(jobConfig.getShardingItemParameters(), is("0=A,1=B,2=C"));
        assertThat(getRegistryCenter().get("/" + getJobName() + "/servers/" + JobRegistry.getInstance().getJobInstance(getJobName()).getServerIp()), is(ServerStatus.ENABLED.name()));
        assertThat(getRegistryCenter().get("/" + getJobName() + "/leader/election/instance"), is(JobRegistry.getInstance().getJobInstance(getJobName()).getJobInstanceId()));
        assertTrue(getRegistryCenter().isExisted("/" + getJobName() + "/instances/" + JobRegistry.getInstance().getJobInstance(getJobName()).getJobInstanceId()));
        getRegistryCenter().remove("/" + getJobName() + "/leader/election");
        assertTrue(getLeaderService().isLeaderUntilBlock());
    }
}
