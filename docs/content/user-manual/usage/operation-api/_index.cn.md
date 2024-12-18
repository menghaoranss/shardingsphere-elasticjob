+++
title = "操作 API"
weight = 4
chapter = true
+++

ElasticJob 提供了 Java API，可以通过直接对注册中心进行操作的方式控制作业在分布式环境下的生命周期。

该模块目前仍处于孵化状态。可能的依赖配置如下，

```xml
<dependency>
  <groupId>org.apache.shardingsphere.elasticjob</groupId>
  <artifactId>elasticjob-lifecycle</artifactId>
  <version>${elasticjob.version}</version>
</dependency>
```

## 配置类 API

类名称：`org.apache.shardingsphere.elasticjob.lifecycle.api.JobConfigurationAPI`

### 获取作业配置

方法签名：`JobConfigurationPOJO getJobConfiguration(String jobName)`

* **Parameters:** 
  * jobName — 作业名称

* **Returns:** 作业配置对象

### 更新作业配置

方法签名：`void updateJobConfiguration(JobConfigurationPOJO jobConfig)`

* **Parameters:** 
  * jobConfig — 作业配置对象

### 删除作业设置 

方法签名：`void removeJobConfiguration(String jobName)`

* **Parameters:** 
  * jobName — 作业名称

## 操作类 API

类名称：`org.apache.shardingsphere.elasticjob.lifecycle.api.JobOperateAPI`

### 触发作业执行

作业在不与当前运行中作业冲突的情况下才会触发执行，并在启动后自动清理此标记。

方法签名：`void trigger(String jobName)`

* **Parameters:**
  * jobName — 作业名称

### 禁用作业

禁用作业将会导致分布式的其他作业触发重新分片。

方法签名：`void disable(String jobName, String serverIp)`

* **Parameters:**
  * jobName — 作业名称
  * serverIp — 作业服务器 IP 地址

### 启用作业

方法签名：`void enable(String jobName, String serverIp)`

* **Parameters:**
  * jobName — 作业名称
  * serverIp — 作业服务器 IP 地址

### 停止调度作业

方法签名：`void shutdown(String jobName, String serverIp)`

* **Parameters:**
  * jobName — 作业名称
  * serverIp — 作业服务器IP地址

### 删除作业

方法签名：`void remove(String jobName, String serverIp)`

* **Parameters:**
  * jobName — 作业名称
  * serverIp — 作业服务器IP地址

### Dump 作业

方法签名：`String dump(String jobName, String instanceIp, int dumpPort) throws IOException`

* **Parameters:**
  * jobName — 作业名称
  * serverIp — 作业服务器IP地址
  * dumpPort — Dump port 

## 操作分片的 API

类名称：`org.apache.shardingsphere.elasticjob.lifecycle.api.ShardingOperateAPI`

### 禁用作业分片

方法签名：`void disable(String jobName, String item)`

* **Parameters:**
  * jobName — 作业名称
  * item — 作业分片项

### 启用作业分片

方法签名：`void enable(String jobName, String item)`

* **Parameters:**
  * jobName — 作业名称
  * item — 作业分片项

## 作业统计 API

类名称：`org.apache.shardingsphere.elasticjob.lifecycle.api.JobStatisticsAPI`

### 获取作业总数

方法签名：`int getJobsTotalCount()`

* **Returns:** 作业总数

### 获取作业简明信息

方法签名：`JobBriefInfo getJobBriefInfo(String jobName)`

* **Parameters:**
  * jobName — 作业名称
 
* **Returns:** 作业简明信息

### 获取所有作业简明信息

方法签名：`Collection<JobBriefInfo> getAllJobsBriefInfo()`

* **Returns:** 作业简明信息集合

### 获取该 IP 下所有作业简明信息

方法签名：`Collection<JobBriefInfo> getJobsBriefInfo(String ip)`

* **Parameters:**
  * ip — 服务器 IP
 
* **Returns:** 作业简明信息集合

## 作业服务器状态展示 API

类名称：`org.apache.shardingsphere.elasticjob.lifecycle.api.ServerStatisticsAPI`

### 获取作业服务器总数

方法签名：`int getServersTotalCount()`

* **Returns:** 作业服务器总数

### 获取所有作业服务器简明信息

方法签名：`Collection<ServerBriefInfo> getAllServersBriefInfo()`

* **Returns:** 作业服务器简明信息集合

## 作业分片状态展示 API

类名称：`org.apache.shardingsphere.elasticjob.lifecycle.api.ShardingStatisticsAPI`

### 获取作业分片信息集合

方法签名：`Collection<ShardingInfo> getShardingInfo(String jobName)`

* **Parameters:**
  * jobName — 作业名称
 
* **Returns:** 作业分片信息集合
