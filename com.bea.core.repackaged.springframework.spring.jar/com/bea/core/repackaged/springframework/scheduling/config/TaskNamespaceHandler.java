package com.bea.core.repackaged.springframework.scheduling.config;

import com.bea.core.repackaged.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class TaskNamespaceHandler extends NamespaceHandlerSupport {
   public void init() {
      this.registerBeanDefinitionParser("annotation-driven", new AnnotationDrivenBeanDefinitionParser());
      this.registerBeanDefinitionParser("executor", new ExecutorBeanDefinitionParser());
      this.registerBeanDefinitionParser("scheduled-tasks", new ScheduledTasksBeanDefinitionParser());
      this.registerBeanDefinitionParser("scheduler", new SchedulerBeanDefinitionParser());
   }
}
