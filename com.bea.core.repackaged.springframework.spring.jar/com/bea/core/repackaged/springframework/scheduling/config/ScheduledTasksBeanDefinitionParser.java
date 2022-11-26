package com.bea.core.repackaged.springframework.scheduling.config;

import com.bea.core.repackaged.springframework.beans.factory.config.RuntimeBeanReference;
import com.bea.core.repackaged.springframework.beans.factory.parsing.BeanComponentDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionBuilder;
import com.bea.core.repackaged.springframework.beans.factory.support.ManagedList;
import com.bea.core.repackaged.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ScheduledTasksBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
   private static final String ELEMENT_SCHEDULED = "scheduled";
   private static final long ZERO_INITIAL_DELAY = 0L;

   protected boolean shouldGenerateId() {
      return true;
   }

   protected String getBeanClassName(Element element) {
      return "com.bea.core.repackaged.springframework.scheduling.config.ContextLifecycleScheduledTaskRegistrar";
   }

   protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
      builder.setLazyInit(false);
      ManagedList cronTaskList = new ManagedList();
      ManagedList fixedDelayTaskList = new ManagedList();
      ManagedList fixedRateTaskList = new ManagedList();
      ManagedList triggerTaskList = new ManagedList();
      NodeList childNodes = element.getChildNodes();

      for(int i = 0; i < childNodes.getLength(); ++i) {
         Node child = childNodes.item(i);
         if (this.isScheduledElement(child, parserContext)) {
            Element taskElement = (Element)child;
            String ref = taskElement.getAttribute("ref");
            String method = taskElement.getAttribute("method");
            if (StringUtils.hasText(ref) && StringUtils.hasText(method)) {
               String cronAttribute = taskElement.getAttribute("cron");
               String fixedDelayAttribute = taskElement.getAttribute("fixed-delay");
               String fixedRateAttribute = taskElement.getAttribute("fixed-rate");
               String triggerAttribute = taskElement.getAttribute("trigger");
               String initialDelayAttribute = taskElement.getAttribute("initial-delay");
               boolean hasCronAttribute = StringUtils.hasText(cronAttribute);
               boolean hasFixedDelayAttribute = StringUtils.hasText(fixedDelayAttribute);
               boolean hasFixedRateAttribute = StringUtils.hasText(fixedRateAttribute);
               boolean hasTriggerAttribute = StringUtils.hasText(triggerAttribute);
               boolean hasInitialDelayAttribute = StringUtils.hasText(initialDelayAttribute);
               if (!hasCronAttribute && !hasFixedDelayAttribute && !hasFixedRateAttribute && !hasTriggerAttribute) {
                  parserContext.getReaderContext().error("one of the 'cron', 'fixed-delay', 'fixed-rate', or 'trigger' attributes is required", taskElement);
               } else if (!hasInitialDelayAttribute || !hasCronAttribute && !hasTriggerAttribute) {
                  String runnableName = this.runnableReference(ref, method, taskElement, parserContext).getBeanName();
                  if (hasFixedDelayAttribute) {
                     fixedDelayTaskList.add(this.intervalTaskReference(runnableName, initialDelayAttribute, fixedDelayAttribute, taskElement, parserContext));
                  }

                  if (hasFixedRateAttribute) {
                     fixedRateTaskList.add(this.intervalTaskReference(runnableName, initialDelayAttribute, fixedRateAttribute, taskElement, parserContext));
                  }

                  if (hasCronAttribute) {
                     cronTaskList.add(this.cronTaskReference(runnableName, cronAttribute, taskElement, parserContext));
                  }

                  if (hasTriggerAttribute) {
                     String triggerName = (new RuntimeBeanReference(triggerAttribute)).getBeanName();
                     triggerTaskList.add(this.triggerTaskReference(runnableName, triggerName, taskElement, parserContext));
                  }
               } else {
                  parserContext.getReaderContext().error("the 'initial-delay' attribute may not be used with cron and trigger tasks", taskElement);
               }
            } else {
               parserContext.getReaderContext().error("Both 'ref' and 'method' are required", taskElement);
            }
         }
      }

      String schedulerRef = element.getAttribute("scheduler");
      if (StringUtils.hasText(schedulerRef)) {
         builder.addPropertyReference("taskScheduler", schedulerRef);
      }

      builder.addPropertyValue("cronTasksList", cronTaskList);
      builder.addPropertyValue("fixedDelayTasksList", fixedDelayTaskList);
      builder.addPropertyValue("fixedRateTasksList", fixedRateTaskList);
      builder.addPropertyValue("triggerTasksList", triggerTaskList);
   }

   private boolean isScheduledElement(Node node, ParserContext parserContext) {
      return node.getNodeType() == 1 && "scheduled".equals(parserContext.getDelegate().getLocalName(node));
   }

   private RuntimeBeanReference runnableReference(String ref, String method, Element taskElement, ParserContext parserContext) {
      BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition("com.bea.core.repackaged.springframework.scheduling.support.ScheduledMethodRunnable");
      builder.addConstructorArgReference(ref);
      builder.addConstructorArgValue(method);
      return this.beanReference(taskElement, parserContext, builder);
   }

   private RuntimeBeanReference intervalTaskReference(String runnableBeanName, String initialDelay, String interval, Element taskElement, ParserContext parserContext) {
      BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition("com.bea.core.repackaged.springframework.scheduling.config.IntervalTask");
      builder.addConstructorArgReference(runnableBeanName);
      builder.addConstructorArgValue(interval);
      builder.addConstructorArgValue(StringUtils.hasLength(initialDelay) ? initialDelay : 0L);
      return this.beanReference(taskElement, parserContext, builder);
   }

   private RuntimeBeanReference cronTaskReference(String runnableBeanName, String cronExpression, Element taskElement, ParserContext parserContext) {
      BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition("com.bea.core.repackaged.springframework.scheduling.config.CronTask");
      builder.addConstructorArgReference(runnableBeanName);
      builder.addConstructorArgValue(cronExpression);
      return this.beanReference(taskElement, parserContext, builder);
   }

   private RuntimeBeanReference triggerTaskReference(String runnableBeanName, String triggerBeanName, Element taskElement, ParserContext parserContext) {
      BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition("com.bea.core.repackaged.springframework.scheduling.config.TriggerTask");
      builder.addConstructorArgReference(runnableBeanName);
      builder.addConstructorArgReference(triggerBeanName);
      return this.beanReference(taskElement, parserContext, builder);
   }

   private RuntimeBeanReference beanReference(Element taskElement, ParserContext parserContext, BeanDefinitionBuilder builder) {
      builder.getRawBeanDefinition().setSource(parserContext.extractSource(taskElement));
      String generatedName = parserContext.getReaderContext().generateBeanName(builder.getRawBeanDefinition());
      parserContext.registerBeanComponent(new BeanComponentDefinition(builder.getBeanDefinition(), generatedName));
      return new RuntimeBeanReference(generatedName);
   }
}
