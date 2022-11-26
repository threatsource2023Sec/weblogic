package com.bea.core.repackaged.springframework.transaction.config;

import com.bea.core.repackaged.springframework.aop.config.AopNamespaceUtils;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.RuntimeBeanReference;
import com.bea.core.repackaged.springframework.beans.factory.parsing.BeanComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.parsing.CompositeComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.xml.BeanDefinitionParser;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.event.TransactionalEventListenerFactory;
import com.bea.core.repackaged.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import com.bea.core.repackaged.springframework.transaction.interceptor.TransactionInterceptor;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import org.w3c.dom.Element;

class AnnotationDrivenBeanDefinitionParser implements BeanDefinitionParser {
   @Nullable
   public BeanDefinition parse(Element element, ParserContext parserContext) {
      this.registerTransactionalEventListenerFactory(parserContext);
      String mode = element.getAttribute("mode");
      if ("aspectj".equals(mode)) {
         this.registerTransactionAspect(element, parserContext);
         if (ClassUtils.isPresent("javax.transaction.Transactional", this.getClass().getClassLoader())) {
            this.registerJtaTransactionAspect(element, parserContext);
         }
      } else {
         AnnotationDrivenBeanDefinitionParser.AopAutoProxyConfigurer.configureAutoProxyCreator(element, parserContext);
      }

      return null;
   }

   private void registerTransactionAspect(Element element, ParserContext parserContext) {
      String txAspectBeanName = "com.bea.core.repackaged.springframework.transaction.config.internalTransactionAspect";
      String txAspectClassName = "com.bea.core.repackaged.springframework.transaction.aspectj.AnnotationTransactionAspect";
      if (!parserContext.getRegistry().containsBeanDefinition(txAspectBeanName)) {
         RootBeanDefinition def = new RootBeanDefinition();
         def.setBeanClassName(txAspectClassName);
         def.setFactoryMethodName("aspectOf");
         registerTransactionManager(element, def);
         parserContext.registerBeanComponent(new BeanComponentDefinition(def, txAspectBeanName));
      }

   }

   private void registerJtaTransactionAspect(Element element, ParserContext parserContext) {
      String txAspectBeanName = "com.bea.core.repackaged.springframework.transaction.config.internalJtaTransactionAspect";
      String txAspectClassName = "com.bea.core.repackaged.springframework.transaction.aspectj.JtaAnnotationTransactionAspect";
      if (!parserContext.getRegistry().containsBeanDefinition(txAspectBeanName)) {
         RootBeanDefinition def = new RootBeanDefinition();
         def.setBeanClassName(txAspectClassName);
         def.setFactoryMethodName("aspectOf");
         registerTransactionManager(element, def);
         parserContext.registerBeanComponent(new BeanComponentDefinition(def, txAspectBeanName));
      }

   }

   private static void registerTransactionManager(Element element, BeanDefinition def) {
      def.getPropertyValues().add("transactionManagerBeanName", TxNamespaceHandler.getTransactionManagerName(element));
   }

   private void registerTransactionalEventListenerFactory(ParserContext parserContext) {
      RootBeanDefinition def = new RootBeanDefinition();
      def.setBeanClass(TransactionalEventListenerFactory.class);
      parserContext.registerBeanComponent(new BeanComponentDefinition(def, "com.bea.core.repackaged.springframework.transaction.config.internalTransactionalEventListenerFactory"));
   }

   private static class AopAutoProxyConfigurer {
      public static void configureAutoProxyCreator(Element element, ParserContext parserContext) {
         AopNamespaceUtils.registerAutoProxyCreatorIfNecessary(parserContext, element);
         String txAdvisorBeanName = "com.bea.core.repackaged.springframework.transaction.config.internalTransactionAdvisor";
         if (!parserContext.getRegistry().containsBeanDefinition(txAdvisorBeanName)) {
            Object eleSource = parserContext.extractSource(element);
            RootBeanDefinition sourceDef = new RootBeanDefinition("com.bea.core.repackaged.springframework.transaction.annotation.AnnotationTransactionAttributeSource");
            sourceDef.setSource(eleSource);
            sourceDef.setRole(2);
            String sourceName = parserContext.getReaderContext().registerWithGeneratedName(sourceDef);
            RootBeanDefinition interceptorDef = new RootBeanDefinition(TransactionInterceptor.class);
            interceptorDef.setSource(eleSource);
            interceptorDef.setRole(2);
            AnnotationDrivenBeanDefinitionParser.registerTransactionManager(element, interceptorDef);
            interceptorDef.getPropertyValues().add("transactionAttributeSource", new RuntimeBeanReference(sourceName));
            String interceptorName = parserContext.getReaderContext().registerWithGeneratedName(interceptorDef);
            RootBeanDefinition advisorDef = new RootBeanDefinition(BeanFactoryTransactionAttributeSourceAdvisor.class);
            advisorDef.setSource(eleSource);
            advisorDef.setRole(2);
            advisorDef.getPropertyValues().add("transactionAttributeSource", new RuntimeBeanReference(sourceName));
            advisorDef.getPropertyValues().add("adviceBeanName", interceptorName);
            if (element.hasAttribute("order")) {
               advisorDef.getPropertyValues().add("order", element.getAttribute("order"));
            }

            parserContext.getRegistry().registerBeanDefinition(txAdvisorBeanName, advisorDef);
            CompositeComponentDefinition compositeDef = new CompositeComponentDefinition(element.getTagName(), eleSource);
            compositeDef.addNestedComponent(new BeanComponentDefinition(sourceDef, sourceName));
            compositeDef.addNestedComponent(new BeanComponentDefinition(interceptorDef, interceptorName));
            compositeDef.addNestedComponent(new BeanComponentDefinition(advisorDef, txAdvisorBeanName));
            parserContext.registerComponent(compositeDef);
         }

      }
   }
}
