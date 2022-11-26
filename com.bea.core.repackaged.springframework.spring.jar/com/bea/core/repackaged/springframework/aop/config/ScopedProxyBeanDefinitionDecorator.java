package com.bea.core.repackaged.springframework.aop.config;

import com.bea.core.repackaged.springframework.aop.scope.ScopedProxyUtils;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.parsing.BeanComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.xml.BeanDefinitionDecorator;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class ScopedProxyBeanDefinitionDecorator implements BeanDefinitionDecorator {
   private static final String PROXY_TARGET_CLASS = "proxy-target-class";

   public BeanDefinitionHolder decorate(Node node, BeanDefinitionHolder definition, ParserContext parserContext) {
      boolean proxyTargetClass = true;
      if (node instanceof Element) {
         Element ele = (Element)node;
         if (ele.hasAttribute("proxy-target-class")) {
            proxyTargetClass = Boolean.valueOf(ele.getAttribute("proxy-target-class"));
         }
      }

      BeanDefinitionHolder holder = ScopedProxyUtils.createScopedProxy(definition, parserContext.getRegistry(), proxyTargetClass);
      String targetBeanName = ScopedProxyUtils.getTargetBeanName(definition.getBeanName());
      parserContext.getReaderContext().fireComponentRegistered(new BeanComponentDefinition(definition.getBeanDefinition(), targetBeanName));
      return holder;
   }
}
