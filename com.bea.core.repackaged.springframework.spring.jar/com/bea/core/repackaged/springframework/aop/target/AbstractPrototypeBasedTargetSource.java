package com.bea.core.repackaged.springframework.aop.target;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;

public abstract class AbstractPrototypeBasedTargetSource extends AbstractBeanFactoryBasedTargetSource {
   public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
      super.setBeanFactory(beanFactory);
      if (!beanFactory.isPrototype(this.getTargetBeanName())) {
         throw new BeanDefinitionStoreException("Cannot use prototype-based TargetSource against non-prototype bean with name '" + this.getTargetBeanName() + "': instances would not be independent");
      }
   }

   protected Object newPrototypeInstance() throws BeansException {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Creating new instance of bean '" + this.getTargetBeanName() + "'");
      }

      return this.getBeanFactory().getBean(this.getTargetBeanName());
   }

   protected void destroyPrototypeInstance(Object target) {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Destroying instance of bean '" + this.getTargetBeanName() + "'");
      }

      if (this.getBeanFactory() instanceof ConfigurableBeanFactory) {
         ((ConfigurableBeanFactory)this.getBeanFactory()).destroyBean(this.getTargetBeanName(), target);
      } else if (target instanceof DisposableBean) {
         try {
            ((DisposableBean)target).destroy();
         } catch (Throwable var3) {
            this.logger.warn("Destroy method on bean with name '" + this.getTargetBeanName() + "' threw an exception", var3);
         }
      }

   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      throw new NotSerializableException("A prototype-based TargetSource itself is not deserializable - just a disconnected SingletonTargetSource or EmptyTargetSource is");
   }

   protected Object writeReplace() throws ObjectStreamException {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Disconnecting TargetSource [" + this + "]");
      }

      try {
         Object target = this.getTarget();
         return target != null ? new SingletonTargetSource(target) : EmptyTargetSource.forClass(this.getTargetClass());
      } catch (Exception var3) {
         String msg = "Cannot get target for disconnecting TargetSource [" + this + "]";
         this.logger.error(msg, var3);
         throw new NotSerializableException(msg + ": " + var3);
      }
   }
}
