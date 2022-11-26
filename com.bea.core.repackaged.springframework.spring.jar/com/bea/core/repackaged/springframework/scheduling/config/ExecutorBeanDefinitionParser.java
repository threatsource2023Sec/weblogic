package com.bea.core.repackaged.springframework.scheduling.config;

import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionBuilder;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class ExecutorBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
   protected String getBeanClassName(Element element) {
      return "com.bea.core.repackaged.springframework.scheduling.config.TaskExecutorFactoryBean";
   }

   protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
      String keepAliveSeconds = element.getAttribute("keep-alive");
      if (StringUtils.hasText(keepAliveSeconds)) {
         builder.addPropertyValue("keepAliveSeconds", keepAliveSeconds);
      }

      String queueCapacity = element.getAttribute("queue-capacity");
      if (StringUtils.hasText(queueCapacity)) {
         builder.addPropertyValue("queueCapacity", queueCapacity);
      }

      this.configureRejectionPolicy(element, builder);
      String poolSize = element.getAttribute("pool-size");
      if (StringUtils.hasText(poolSize)) {
         builder.addPropertyValue("poolSize", poolSize);
      }

   }

   private void configureRejectionPolicy(Element element, BeanDefinitionBuilder builder) {
      String rejectionPolicy = element.getAttribute("rejection-policy");
      if (StringUtils.hasText(rejectionPolicy)) {
         String prefix = "java.util.concurrent.ThreadPoolExecutor.";
         String policyClassName;
         if (rejectionPolicy.equals("ABORT")) {
            policyClassName = prefix + "AbortPolicy";
         } else if (rejectionPolicy.equals("CALLER_RUNS")) {
            policyClassName = prefix + "CallerRunsPolicy";
         } else if (rejectionPolicy.equals("DISCARD")) {
            policyClassName = prefix + "DiscardPolicy";
         } else if (rejectionPolicy.equals("DISCARD_OLDEST")) {
            policyClassName = prefix + "DiscardOldestPolicy";
         } else {
            policyClassName = rejectionPolicy;
         }

         builder.addPropertyValue("rejectedExecutionHandler", new RootBeanDefinition(policyClassName));
      }
   }
}
