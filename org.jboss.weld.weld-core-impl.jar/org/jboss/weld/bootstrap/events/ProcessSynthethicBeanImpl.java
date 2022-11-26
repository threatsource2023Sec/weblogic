package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessSyntheticBean;
import org.jboss.weld.annotated.EmptyAnnotated;
import org.jboss.weld.manager.BeanManagerImpl;

public class ProcessSynthethicBeanImpl extends ProcessBeanImpl implements ProcessSyntheticBean {
   private final Extension source;

   protected static void fire(BeanManagerImpl beanManager, Bean bean, Extension extension) {
      fire(beanManager, bean, EmptyAnnotated.INSTANCE, extension);
   }

   private static void fire(BeanManagerImpl beanManager, Bean bean, Annotated annotated, Extension extension) {
      if (beanManager.isBeanEnabled(bean)) {
         (new ProcessSynthethicBeanImpl(beanManager, bean, annotated, extension) {
         }).fire();
      }

   }

   public ProcessSynthethicBeanImpl(BeanManagerImpl beanManager, Bean bean, Annotated annotated, Extension extension) {
      super(beanManager, bean, annotated);
      this.source = extension;
   }

   public Extension getSource() {
      return this.source;
   }

   protected Type getRawType() {
      return ProcessSyntheticBean.class;
   }
}
