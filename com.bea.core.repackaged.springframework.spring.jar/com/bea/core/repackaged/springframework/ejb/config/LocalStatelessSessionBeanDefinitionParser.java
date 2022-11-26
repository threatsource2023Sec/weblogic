package com.bea.core.repackaged.springframework.ejb.config;

import org.w3c.dom.Element;

class LocalStatelessSessionBeanDefinitionParser extends AbstractJndiLocatingBeanDefinitionParser {
   protected String getBeanClassName(Element element) {
      return "com.bea.core.repackaged.springframework.ejb.access.LocalStatelessSessionProxyFactoryBean";
   }
}
