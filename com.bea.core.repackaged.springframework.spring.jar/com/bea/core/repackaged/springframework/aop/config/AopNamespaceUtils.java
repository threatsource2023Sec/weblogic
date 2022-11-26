package com.bea.core.repackaged.springframework.aop.config;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.parsing.BeanComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import org.w3c.dom.Element;

public abstract class AopNamespaceUtils {
   public static final String PROXY_TARGET_CLASS_ATTRIBUTE = "proxy-target-class";
   private static final String EXPOSE_PROXY_ATTRIBUTE = "expose-proxy";

   public static void registerAutoProxyCreatorIfNecessary(ParserContext parserContext, Element sourceElement) {
      BeanDefinition beanDefinition = AopConfigUtils.registerAutoProxyCreatorIfNecessary(parserContext.getRegistry(), parserContext.extractSource(sourceElement));
      useClassProxyingIfNecessary(parserContext.getRegistry(), sourceElement);
      registerComponentIfNecessary(beanDefinition, parserContext);
   }

   public static void registerAspectJAutoProxyCreatorIfNecessary(ParserContext parserContext, Element sourceElement) {
      BeanDefinition beanDefinition = AopConfigUtils.registerAspectJAutoProxyCreatorIfNecessary(parserContext.getRegistry(), parserContext.extractSource(sourceElement));
      useClassProxyingIfNecessary(parserContext.getRegistry(), sourceElement);
      registerComponentIfNecessary(beanDefinition, parserContext);
   }

   public static void registerAspectJAnnotationAutoProxyCreatorIfNecessary(ParserContext parserContext, Element sourceElement) {
      BeanDefinition beanDefinition = AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(parserContext.getRegistry(), parserContext.extractSource(sourceElement));
      useClassProxyingIfNecessary(parserContext.getRegistry(), sourceElement);
      registerComponentIfNecessary(beanDefinition, parserContext);
   }

   private static void useClassProxyingIfNecessary(BeanDefinitionRegistry registry, @Nullable Element sourceElement) {
      if (sourceElement != null) {
         boolean proxyTargetClass = Boolean.parseBoolean(sourceElement.getAttribute("proxy-target-class"));
         if (proxyTargetClass) {
            AopConfigUtils.forceAutoProxyCreatorToUseClassProxying(registry);
         }

         boolean exposeProxy = Boolean.parseBoolean(sourceElement.getAttribute("expose-proxy"));
         if (exposeProxy) {
            AopConfigUtils.forceAutoProxyCreatorToExposeProxy(registry);
         }
      }

   }

   private static void registerComponentIfNecessary(@Nullable BeanDefinition beanDefinition, ParserContext parserContext) {
      if (beanDefinition != null) {
         parserContext.registerComponent(new BeanComponentDefinition(beanDefinition, "com.bea.core.repackaged.springframework.aop.config.internalAutoProxyCreator"));
      }

   }
}
