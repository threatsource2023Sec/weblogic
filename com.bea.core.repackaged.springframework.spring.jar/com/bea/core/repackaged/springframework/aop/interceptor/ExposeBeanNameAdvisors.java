package com.bea.core.repackaged.springframework.aop.interceptor;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.ProxyMethodInvocation;
import com.bea.core.repackaged.springframework.aop.support.DefaultIntroductionAdvisor;
import com.bea.core.repackaged.springframework.aop.support.DefaultPointcutAdvisor;
import com.bea.core.repackaged.springframework.aop.support.DelegatingIntroductionInterceptor;
import com.bea.core.repackaged.springframework.beans.factory.NamedBean;

public abstract class ExposeBeanNameAdvisors {
   private static final String BEAN_NAME_ATTRIBUTE = ExposeBeanNameAdvisors.class.getName() + ".BEAN_NAME";

   public static String getBeanName() throws IllegalStateException {
      return getBeanName(ExposeInvocationInterceptor.currentInvocation());
   }

   public static String getBeanName(MethodInvocation mi) throws IllegalStateException {
      if (!(mi instanceof ProxyMethodInvocation)) {
         throw new IllegalArgumentException("MethodInvocation is not a Spring ProxyMethodInvocation: " + mi);
      } else {
         ProxyMethodInvocation pmi = (ProxyMethodInvocation)mi;
         String beanName = (String)pmi.getUserAttribute(BEAN_NAME_ATTRIBUTE);
         if (beanName == null) {
            throw new IllegalStateException("Cannot get bean name; not set on MethodInvocation: " + mi);
         } else {
            return beanName;
         }
      }
   }

   public static Advisor createAdvisorWithoutIntroduction(String beanName) {
      return new DefaultPointcutAdvisor(new ExposeBeanNameInterceptor(beanName));
   }

   public static Advisor createAdvisorIntroducingNamedBean(String beanName) {
      return new DefaultIntroductionAdvisor(new ExposeBeanNameIntroduction(beanName));
   }

   private static class ExposeBeanNameIntroduction extends DelegatingIntroductionInterceptor implements NamedBean {
      private final String beanName;

      public ExposeBeanNameIntroduction(String beanName) {
         this.beanName = beanName;
      }

      public Object invoke(MethodInvocation mi) throws Throwable {
         if (!(mi instanceof ProxyMethodInvocation)) {
            throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation: " + mi);
         } else {
            ProxyMethodInvocation pmi = (ProxyMethodInvocation)mi;
            pmi.setUserAttribute(ExposeBeanNameAdvisors.BEAN_NAME_ATTRIBUTE, this.beanName);
            return super.invoke(mi);
         }
      }

      public String getBeanName() {
         return this.beanName;
      }
   }

   private static class ExposeBeanNameInterceptor implements MethodInterceptor {
      private final String beanName;

      public ExposeBeanNameInterceptor(String beanName) {
         this.beanName = beanName;
      }

      public Object invoke(MethodInvocation mi) throws Throwable {
         if (!(mi instanceof ProxyMethodInvocation)) {
            throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation: " + mi);
         } else {
            ProxyMethodInvocation pmi = (ProxyMethodInvocation)mi;
            pmi.setUserAttribute(ExposeBeanNameAdvisors.BEAN_NAME_ATTRIBUTE, this.beanName);
            return mi.proceed();
         }
      }
   }
}
