package com.bea.core.repackaged.springframework.aop.config;

import com.bea.core.repackaged.springframework.aop.framework.ProxyFactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.support.ManagedList;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.xml.BeanDefinitionDecorator;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.List;
import org.w3c.dom.Node;

public abstract class AbstractInterceptorDrivenBeanDefinitionDecorator implements BeanDefinitionDecorator {
   public final BeanDefinitionHolder decorate(Node node, BeanDefinitionHolder definitionHolder, ParserContext parserContext) {
      BeanDefinitionRegistry registry = parserContext.getRegistry();
      String existingBeanName = definitionHolder.getBeanName();
      BeanDefinition targetDefinition = definitionHolder.getBeanDefinition();
      BeanDefinitionHolder targetHolder = new BeanDefinitionHolder(targetDefinition, existingBeanName + ".TARGET");
      BeanDefinition interceptorDefinition = this.createInterceptorDefinition(node);
      String interceptorName = existingBeanName + '.' + this.getInterceptorNameSuffix(interceptorDefinition);
      BeanDefinitionReaderUtils.registerBeanDefinition(new BeanDefinitionHolder(interceptorDefinition, interceptorName), registry);
      BeanDefinitionHolder result = definitionHolder;
      if (!this.isProxyFactoryBeanDefinition(targetDefinition)) {
         RootBeanDefinition proxyDefinition = new RootBeanDefinition();
         proxyDefinition.setBeanClass(ProxyFactoryBean.class);
         proxyDefinition.setScope(targetDefinition.getScope());
         proxyDefinition.setLazyInit(targetDefinition.isLazyInit());
         proxyDefinition.setDecoratedDefinition(targetHolder);
         proxyDefinition.getPropertyValues().add("target", targetHolder);
         proxyDefinition.getPropertyValues().add("interceptorNames", new ManagedList());
         proxyDefinition.setAutowireCandidate(targetDefinition.isAutowireCandidate());
         proxyDefinition.setPrimary(targetDefinition.isPrimary());
         if (targetDefinition instanceof AbstractBeanDefinition) {
            proxyDefinition.copyQualifiersFrom((AbstractBeanDefinition)targetDefinition);
         }

         result = new BeanDefinitionHolder(proxyDefinition, existingBeanName);
      }

      this.addInterceptorNameToList(interceptorName, result.getBeanDefinition());
      return result;
   }

   private void addInterceptorNameToList(String interceptorName, BeanDefinition beanDefinition) {
      List list = (List)beanDefinition.getPropertyValues().get("interceptorNames");
      Assert.state(list != null, "Missing 'interceptorNames' property");
      list.add(interceptorName);
   }

   private boolean isProxyFactoryBeanDefinition(BeanDefinition existingDefinition) {
      return ProxyFactoryBean.class.getName().equals(existingDefinition.getBeanClassName());
   }

   protected String getInterceptorNameSuffix(BeanDefinition interceptorDefinition) {
      String beanClassName = interceptorDefinition.getBeanClassName();
      return StringUtils.hasLength(beanClassName) ? StringUtils.uncapitalize(ClassUtils.getShortName(beanClassName)) : "";
   }

   protected abstract BeanDefinition createInterceptorDefinition(Node var1);
}
