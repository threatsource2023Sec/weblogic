package com.bea.core.repackaged.springframework.aop.target;

public class SimpleBeanTargetSource extends AbstractBeanFactoryBasedTargetSource {
   public Object getTarget() throws Exception {
      return this.getBeanFactory().getBean(this.getTargetBeanName());
   }
}
