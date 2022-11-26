package com.bea.core.repackaged.springframework.ejb.config;

import com.bea.core.repackaged.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class JeeNamespaceHandler extends NamespaceHandlerSupport {
   public void init() {
      this.registerBeanDefinitionParser("jndi-lookup", new JndiLookupBeanDefinitionParser());
      this.registerBeanDefinitionParser("local-slsb", new LocalStatelessSessionBeanDefinitionParser());
      this.registerBeanDefinitionParser("remote-slsb", new RemoteStatelessSessionBeanDefinitionParser());
   }
}
