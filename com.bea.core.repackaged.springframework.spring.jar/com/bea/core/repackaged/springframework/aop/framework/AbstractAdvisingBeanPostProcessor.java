package com.bea.core.repackaged.springframework.aop.framework;

import com.bea.core.repackaged.springframework.aop.Advisor;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanPostProcessor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractAdvisingBeanPostProcessor extends ProxyProcessorSupport implements BeanPostProcessor {
   @Nullable
   protected Advisor advisor;
   protected boolean beforeExistingAdvisors = false;
   private final Map eligibleBeans = new ConcurrentHashMap(256);

   public void setBeforeExistingAdvisors(boolean beforeExistingAdvisors) {
      this.beforeExistingAdvisors = beforeExistingAdvisors;
   }

   public Object postProcessBeforeInitialization(Object bean, String beanName) {
      return bean;
   }

   public Object postProcessAfterInitialization(Object bean, String beanName) {
      if (this.advisor != null && !(bean instanceof AopInfrastructureBean)) {
         if (bean instanceof Advised) {
            Advised advised = (Advised)bean;
            if (!advised.isFrozen() && this.isEligible(AopUtils.getTargetClass(bean))) {
               if (this.beforeExistingAdvisors) {
                  advised.addAdvisor(0, this.advisor);
               } else {
                  advised.addAdvisor(this.advisor);
               }

               return bean;
            }
         }

         if (this.isEligible(bean, beanName)) {
            ProxyFactory proxyFactory = this.prepareProxyFactory(bean, beanName);
            if (!proxyFactory.isProxyTargetClass()) {
               this.evaluateProxyInterfaces(bean.getClass(), proxyFactory);
            }

            proxyFactory.addAdvisor(this.advisor);
            this.customizeProxyFactory(proxyFactory);
            return proxyFactory.getProxy(this.getProxyClassLoader());
         } else {
            return bean;
         }
      } else {
         return bean;
      }
   }

   protected boolean isEligible(Object bean, String beanName) {
      return this.isEligible(bean.getClass());
   }

   protected boolean isEligible(Class targetClass) {
      Boolean eligible = (Boolean)this.eligibleBeans.get(targetClass);
      if (eligible != null) {
         return eligible;
      } else if (this.advisor == null) {
         return false;
      } else {
         eligible = AopUtils.canApply(this.advisor, targetClass);
         this.eligibleBeans.put(targetClass, eligible);
         return eligible;
      }
   }

   protected ProxyFactory prepareProxyFactory(Object bean, String beanName) {
      ProxyFactory proxyFactory = new ProxyFactory();
      proxyFactory.copyFrom(this);
      proxyFactory.setTarget(bean);
      return proxyFactory;
   }

   protected void customizeProxyFactory(ProxyFactory proxyFactory) {
   }
}
