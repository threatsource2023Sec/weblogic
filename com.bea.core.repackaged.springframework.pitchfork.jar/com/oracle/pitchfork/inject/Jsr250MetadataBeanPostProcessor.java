package com.oracle.pitchfork.inject;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.oracle.pitchfork.config.MetadataDrivenBeanPostProcessorSupport;

public class Jsr250MetadataBeanPostProcessor extends MetadataDrivenBeanPostProcessorSupport {
   public Jsr250MetadataBeanPostProcessor() {
      super(Jsr250Metadata.KEY);
   }

   public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
      Jsr250Metadata jsr250 = (Jsr250Metadata)this.getMetadata(beanName);
      if (jsr250 != null) {
         jsr250.injectAndPostConstruct(bean);
      }

      return true;
   }
}
