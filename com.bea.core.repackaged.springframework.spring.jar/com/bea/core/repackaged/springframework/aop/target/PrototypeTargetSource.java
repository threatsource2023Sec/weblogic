package com.bea.core.repackaged.springframework.aop.target;

import com.bea.core.repackaged.springframework.beans.BeansException;

public class PrototypeTargetSource extends AbstractPrototypeBasedTargetSource {
   public Object getTarget() throws BeansException {
      return this.newPrototypeInstance();
   }

   public void releaseTarget(Object target) {
      this.destroyPrototypeInstance(target);
   }

   public String toString() {
      return "PrototypeTargetSource for target bean with name '" + this.getTargetBeanName() + "'";
   }
}
