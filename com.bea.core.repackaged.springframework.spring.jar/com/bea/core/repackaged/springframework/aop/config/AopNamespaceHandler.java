package com.bea.core.repackaged.springframework.aop.config;

import com.bea.core.repackaged.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class AopNamespaceHandler extends NamespaceHandlerSupport {
   public void init() {
      this.registerBeanDefinitionParser("config", new ConfigBeanDefinitionParser());
      this.registerBeanDefinitionParser("aspectj-autoproxy", new AspectJAutoProxyBeanDefinitionParser());
      this.registerBeanDefinitionDecorator("scoped-proxy", new ScopedProxyBeanDefinitionDecorator());
      this.registerBeanDefinitionParser("spring-configured", new SpringConfiguredBeanDefinitionParser());
   }
}
