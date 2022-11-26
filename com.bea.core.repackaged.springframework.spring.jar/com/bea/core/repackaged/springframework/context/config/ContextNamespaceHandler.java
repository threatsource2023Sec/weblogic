package com.bea.core.repackaged.springframework.context.config;

import com.bea.core.repackaged.springframework.beans.factory.xml.NamespaceHandlerSupport;
import com.bea.core.repackaged.springframework.context.annotation.AnnotationConfigBeanDefinitionParser;
import com.bea.core.repackaged.springframework.context.annotation.ComponentScanBeanDefinitionParser;

public class ContextNamespaceHandler extends NamespaceHandlerSupport {
   public void init() {
      this.registerBeanDefinitionParser("property-placeholder", new PropertyPlaceholderBeanDefinitionParser());
      this.registerBeanDefinitionParser("property-override", new PropertyOverrideBeanDefinitionParser());
      this.registerBeanDefinitionParser("annotation-config", new AnnotationConfigBeanDefinitionParser());
      this.registerBeanDefinitionParser("component-scan", new ComponentScanBeanDefinitionParser());
      this.registerBeanDefinitionParser("load-time-weaver", new LoadTimeWeaverBeanDefinitionParser());
      this.registerBeanDefinitionParser("spring-configured", new SpringConfiguredBeanDefinitionParser());
      this.registerBeanDefinitionParser("mbean-export", new MBeanExportBeanDefinitionParser());
      this.registerBeanDefinitionParser("mbean-server", new MBeanServerBeanDefinitionParser());
   }
}
