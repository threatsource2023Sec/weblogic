package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class DefaultBeanFactoryPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
   private Pointcut pointcut;

   public DefaultBeanFactoryPointcutAdvisor() {
      this.pointcut = Pointcut.TRUE;
   }

   public void setPointcut(@Nullable Pointcut pointcut) {
      this.pointcut = pointcut != null ? pointcut : Pointcut.TRUE;
   }

   public Pointcut getPointcut() {
      return this.pointcut;
   }

   public String toString() {
      return this.getClass().getName() + ": pointcut [" + this.getPointcut() + "]; advice bean '" + this.getAdviceBeanName() + "'";
   }
}
