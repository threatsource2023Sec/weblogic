package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.aop.Pointcut;

public class NameMatchMethodPointcutAdvisor extends AbstractGenericPointcutAdvisor {
   private final NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();

   public NameMatchMethodPointcutAdvisor() {
   }

   public NameMatchMethodPointcutAdvisor(Advice advice) {
      this.setAdvice(advice);
   }

   public void setClassFilter(ClassFilter classFilter) {
      this.pointcut.setClassFilter(classFilter);
   }

   public void setMappedName(String mappedName) {
      this.pointcut.setMappedName(mappedName);
   }

   public void setMappedNames(String... mappedNames) {
      this.pointcut.setMappedNames(mappedNames);
   }

   public NameMatchMethodPointcut addMethodName(String name) {
      return this.pointcut.addMethodName(name);
   }

   public Pointcut getPointcut() {
      return this.pointcut;
   }
}
