package com.bea.core.repackaged.springframework.aop.target;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class LazyInitTargetSource extends AbstractBeanFactoryBasedTargetSource {
   @Nullable
   private Object target;

   @Nullable
   public synchronized Object getTarget() throws BeansException {
      if (this.target == null) {
         this.target = this.getBeanFactory().getBean(this.getTargetBeanName());
         this.postProcessTargetObject(this.target);
      }

      return this.target;
   }

   protected void postProcessTargetObject(Object targetObject) {
   }
}
