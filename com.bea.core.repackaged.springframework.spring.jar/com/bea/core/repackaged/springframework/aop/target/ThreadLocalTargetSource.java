package com.bea.core.repackaged.springframework.aop.target;

import com.bea.core.repackaged.springframework.aop.IntroductionAdvisor;
import com.bea.core.repackaged.springframework.aop.support.DefaultIntroductionAdvisor;
import com.bea.core.repackaged.springframework.aop.support.DelegatingIntroductionInterceptor;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.core.NamedThreadLocal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ThreadLocalTargetSource extends AbstractPrototypeBasedTargetSource implements ThreadLocalTargetSourceStats, DisposableBean {
   private final ThreadLocal targetInThread = new NamedThreadLocal("Thread-local instance of bean '" + this.getTargetBeanName() + "'");
   private final Set targetSet = new HashSet();
   private int invocationCount;
   private int hitCount;

   public Object getTarget() throws BeansException {
      ++this.invocationCount;
      Object target = this.targetInThread.get();
      if (target == null) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("No target for prototype '" + this.getTargetBeanName() + "' bound to thread: creating one and binding it to thread '" + Thread.currentThread().getName() + "'");
         }

         target = this.newPrototypeInstance();
         this.targetInThread.set(target);
         synchronized(this.targetSet) {
            this.targetSet.add(target);
         }
      } else {
         ++this.hitCount;
      }

      return target;
   }

   public void destroy() {
      this.logger.debug("Destroying ThreadLocalTargetSource bindings");
      synchronized(this.targetSet) {
         Iterator var2 = this.targetSet.iterator();

         while(true) {
            if (!var2.hasNext()) {
               this.targetSet.clear();
               break;
            }

            Object target = var2.next();
            this.destroyPrototypeInstance(target);
         }
      }

      this.targetInThread.remove();
   }

   public int getInvocationCount() {
      return this.invocationCount;
   }

   public int getHitCount() {
      return this.hitCount;
   }

   public int getObjectCount() {
      synchronized(this.targetSet) {
         return this.targetSet.size();
      }
   }

   public IntroductionAdvisor getStatsMixin() {
      DelegatingIntroductionInterceptor dii = new DelegatingIntroductionInterceptor(this);
      return new DefaultIntroductionAdvisor(dii, ThreadLocalTargetSourceStats.class);
   }
}
