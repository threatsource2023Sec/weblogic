package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.IOException;
import java.io.ObjectInputStream;

public abstract class AbstractBeanFactoryPointcutAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
   @Nullable
   private String adviceBeanName;
   @Nullable
   private BeanFactory beanFactory;
   @Nullable
   private transient volatile Advice advice;
   private transient volatile Object adviceMonitor = new Object();

   public void setAdviceBeanName(@Nullable String adviceBeanName) {
      this.adviceBeanName = adviceBeanName;
   }

   @Nullable
   public String getAdviceBeanName() {
      return this.adviceBeanName;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
      this.resetAdviceMonitor();
   }

   private void resetAdviceMonitor() {
      if (this.beanFactory instanceof ConfigurableBeanFactory) {
         this.adviceMonitor = ((ConfigurableBeanFactory)this.beanFactory).getSingletonMutex();
      } else {
         this.adviceMonitor = new Object();
      }

   }

   public void setAdvice(Advice advice) {
      synchronized(this.adviceMonitor) {
         this.advice = advice;
      }
   }

   public Advice getAdvice() {
      Advice advice = this.advice;
      if (advice != null) {
         return advice;
      } else {
         Assert.state(this.adviceBeanName != null, "'adviceBeanName' must be specified");
         Assert.state(this.beanFactory != null, "BeanFactory must be set to resolve 'adviceBeanName'");
         if (this.beanFactory.isSingleton(this.adviceBeanName)) {
            advice = (Advice)this.beanFactory.getBean(this.adviceBeanName, Advice.class);
            this.advice = advice;
            return advice;
         } else {
            synchronized(this.adviceMonitor) {
               advice = this.advice;
               if (advice == null) {
                  advice = (Advice)this.beanFactory.getBean(this.adviceBeanName, Advice.class);
                  this.advice = advice;
               }

               return advice;
            }
         }
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder(this.getClass().getName());
      sb.append(": advice ");
      if (this.adviceBeanName != null) {
         sb.append("bean '").append(this.adviceBeanName).append("'");
      } else {
         sb.append(this.advice);
      }

      return sb.toString();
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      ois.defaultReadObject();
      this.resetAdviceMonitor();
   }
}
