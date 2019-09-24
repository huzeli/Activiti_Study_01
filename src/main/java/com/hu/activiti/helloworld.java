package com.hu.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class helloworld {
    private static final Logger LOGGER=LoggerFactory.getLogger(helloworld.class);
    public static void main(String[] args) {
        LOGGER.info("启动我们的程序");
        //创建流程模板
        ProcessEngineConfiguration cfg=ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
       ProcessEngine processEngine=cfg.buildProcessEngine();
      String name= processEngine.getName();
      String version=ProcessEngine.VERSION;
        System.out.println("流程模板名称---"+name+"-----流程模板版本---"+version);
        //部署流程定义文件
       RepositoryService repositoryServier =processEngine.getRepositoryService();
       DeploymentBuilder deploymentBuilder= repositoryServier.createDeployment();

        deploymentBuilder.addClasspathResource("bpmn/towshenpi.xml");
       Deployment deployment= deploymentBuilder.deploy();
        String deploymentId=deployment.getId();
        ProcessDefinitionQuery dd=repositoryServier.createProcessDefinitionQuery()
                .deploymentId(deploymentId);

        ProcessDefinition processDefinition=dd
                .singleResult();
        LOGGER.info("流程定义文件按{}","流程定义ID{}",processDefinition.getName(),processDefinition.getId());
        //启动运行流程
        RuntimeService runtimeService=processEngine.getRuntimeService();
        ProcessInstance processInstance=runtimeService.startProcessInstanceById(processDefinition.getId());
        LOGGER.info("启动流程{}",processInstance.getProcessDefinitionKey());
        //处理流程任务
        LOGGER.info("关闭我们的程序");
    }
}
