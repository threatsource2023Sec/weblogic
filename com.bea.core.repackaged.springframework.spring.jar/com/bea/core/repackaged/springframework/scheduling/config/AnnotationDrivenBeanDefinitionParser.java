package com.bea.core.repackaged.springframework.scheduling.config;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.parsing.BeanComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.parsing.CompositeComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionBuilder;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.xml.BeanDefinitionParser;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class AnnotationDrivenBeanDefinitionParser implements BeanDefinitionParser {
   private static final String ASYNC_EXECUTION_ASPECT_CLASS_NAME = "com.bea.core.repackaged.springframework.scheduling.aspectj.AnnotationAsyncExecutionAspect";

   @Nullable
   public BeanDefinition parse(Element element, ParserContext parserContext) {
      Object source = parserContext.extractSource(element);
      CompositeComponentDefinition compDefinition = new CompositeComponentDefinition(element.getTagName(), source);
      parserContext.pushContainingComponent(compDefinition);
      BeanDefinitionRegistry registry = parserContext.getRegistry();
      String mode = element.getAttribute("mode");
      BeanDefinitionBuilder builder;
      String scheduler;
      if ("aspectj".equals(mode)) {
         this.registerAsyncExecutionAspect(element, parserContext);
      } else if (registry.containsBeanDefinition("com.bea.core.repackaged.springframework.context.annotation.internalAsyncAnnotationProcessor")) {
         parserContext.getReaderContext().error("Only one AsyncAnnotationBeanPostProcessor may exist within the context.", source);
      } else {
         builder = BeanDefinitionBuilder.genericBeanDefinition("com.bea.core.repackaged.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor");
         builder.getRawBeanDefinition().setSource(source);
         scheduler = element.getAttribute("executor");
         if (StringUtils.hasText(scheduler)) {
            builder.addPropertyReference("executor", scheduler);
         }

         String exceptionHandler = element.getAttribute("exception-handler");
         if (StringUtils.hasText(exceptionHandler)) {
            builder.addPropertyReference("exceptionHandler", exceptionHandler);
         }

         if (Boolean.valueOf(element.getAttribute("proxy-target-class"))) {
            builder.addPropertyValue("proxyTargetClass", true);
         }

         registerPostProcessor(parserContext, builder, "com.bea.core.repackaged.springframework.context.annotation.internalAsyncAnnotationProcessor");
      }

      if (registry.containsBeanDefinition("com.bea.core.repackaged.springframework.context.annotation.internalScheduledAnnotationProcessor")) {
         parserContext.getReaderContext().error("Only one ScheduledAnnotationBeanPostProcessor may exist within the context.", source);
      } else {
         builder = BeanDefinitionBuilder.genericBeanDefinition("com.bea.core.repackaged.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor");
         builder.getRawBeanDefinition().setSource(source);
         scheduler = element.getAttribute("scheduler");
         if (StringUtils.hasText(scheduler)) {
            builder.addPropertyReference("scheduler", scheduler);
         }

         registerPostProcessor(parserContext, builder, "com.bea.core.repackaged.springframework.context.annotation.internalScheduledAnnotationProcessor");
      }

      parserContext.popAndRegisterContainingComponent();
      return null;
   }

   private void registerAsyncExecutionAspect(Element element, ParserContext parserContext) {
      if (!parserContext.getRegistry().containsBeanDefinition("com.bea.core.repackaged.springframework.scheduling.config.internalAsyncExecutionAspect")) {
         BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition("com.bea.core.repackaged.springframework.scheduling.aspectj.AnnotationAsyncExecutionAspect");
         builder.setFactoryMethod("aspectOf");
         String executor = element.getAttribute("executor");
         if (StringUtils.hasText(executor)) {
            builder.addPropertyReference("executor", executor);
         }

         String exceptionHandler = element.getAttribute("exception-handler");
         if (StringUtils.hasText(exceptionHandler)) {
            builder.addPropertyReference("exceptionHandler", exceptionHandler);
         }

         parserContext.registerBeanComponent(new BeanComponentDefinition(builder.getBeanDefinition(), "com.bea.core.repackaged.springframework.scheduling.config.internalAsyncExecutionAspect"));
      }

   }

   private static void registerPostProcessor(ParserContext parserContext, BeanDefinitionBuilder builder, String beanName) {
      builder.setRole(2);
      parserContext.getRegistry().registerBeanDefinition(beanName, builder.getBeanDefinition());
      BeanDefinitionHolder holder = new BeanDefinitionHolder(builder.getBeanDefinition(), beanName);
      parserContext.registerComponent(new BeanComponentDefinition(holder));
   }
}
