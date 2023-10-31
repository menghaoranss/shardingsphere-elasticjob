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

package org.apache.shardingsphere.elasticjob.kernel.internal.executor.error.handler;

import org.apache.shardingsphere.infra.spi.type.typed.TypedSPI;

import java.io.Closeable;

/**
 * Job error handler.
 */
public interface JobErrorHandler extends TypedSPI, Closeable {
    
    /**
     * Handle exception.
     * 
     * @param jobName job name
     * @param cause failure cause
     */
    void handleException(String jobName, Throwable cause);
    
    @Override
    String getType();
    
    @Override
    default void close() {
    }
}