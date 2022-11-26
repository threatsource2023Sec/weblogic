package org.jboss.weld.bootstrap.events;

import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.enterprise.inject.spi.ProcessSyntheticObserverMethod;
import org.jboss.weld.manager.BeanManagerImpl;

public class ProcessSyntheticObserverMethodImpl extends ProcessObserverMethodImpl implements ProcessSyntheticObserverMethod {
   private final Extension source;

   public static ObserverMethod fire(BeanManagerImpl beanManager, AnnotatedMethod beanMethod, ObserverMethod observerMethod, Extension extension) {
      return fire(new ProcessSyntheticObserverMethodImpl(beanManager, beanMethod, observerMethod, extension));
   }

   private ProcessSyntheticObserverMethodImpl(BeanManagerImpl beanManager, AnnotatedMethod beanMethod, ObserverMethod observerMethod, Extension extension) {
      super(beanManager, beanMethod, observerMethod, ProcessSyntheticObserverMethod.class);
      this.source = extension;
   }

   public Extension getSource() {
      return this.source;
   }
}
