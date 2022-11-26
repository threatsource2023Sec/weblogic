package com.bea.core.repackaged.springframework.scheduling.config;

import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionBuilder;
import com.bea.core.repackaged.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import com.bea.core.repackaged.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class SchedulerBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
   protected String getBeanClassName(Element element) {
      return "com.bea.core.repackaged.springframework.scheduling.concurrent.ThreadPoolTaskScheduler";
   }

   protected void doParse(Element element, BeanDefinitionBuilder builder) {
      String poolSize = element.getAttribute("pool-size");
      if (StringUtils.hasText(poolSize)) {
         builder.addPropertyValue("poolSize", poolSize);
      }

   }
}
