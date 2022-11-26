package com.bea.core.repackaged.springframework.aop.target;

import com.bea.core.repackaged.springframework.aop.support.DefaultIntroductionAdvisor;
import com.bea.core.repackaged.springframework.aop.support.DelegatingIntroductionInterceptor;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanInitializationException;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.lang.Nullable;

public abstract class AbstractPoolingTargetSource extends AbstractPrototypeBasedTargetSource implements PoolingConfig, DisposableBean {
   private int maxSize = -1;

   public void setMaxSize(int maxSize) {
      this.maxSize = maxSize;
   }

   public int getMaxSize() {
      return this.maxSize;
   }

   public final void setBeanFactory(BeanFactory beanFactory) throws BeansException {
      super.setBeanFactory(beanFactory);

      try {
         this.createPool();
      } catch (Throwable var3) {
         throw new BeanInitializationException("Could not create instance pool for TargetSource", var3);
      }
   }

   protected abstract void createPool() throws Exception;

   @Nullable
   public abstract Object getTarget() throws Exception;

   public abstract void releaseTarget(Object var1) throws Exception;

   public DefaultIntroductionAdvisor getPoolingConfigMixin() {
      DelegatingIntroductionInterceptor dii = new DelegatingIntroductionInterceptor(this);
      return new DefaultIntroductionAdvisor(dii, PoolingConfig.class);
   }
}
