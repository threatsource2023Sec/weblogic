package org.jboss.weld.bootstrap.events;

import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessSyntheticAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedTypeContext;
import org.jboss.weld.manager.BeanManagerImpl;

public class ProcessSyntheticAnnotatedTypeImpl extends ProcessAnnotatedTypeImpl implements ProcessSyntheticAnnotatedType {
   private Extension source;

   public ProcessSyntheticAnnotatedTypeImpl(BeanManagerImpl beanManager, SlimAnnotatedTypeContext annotatedTypeContext) {
      super(beanManager, annotatedTypeContext.getAnnotatedType(), ProcessSyntheticAnnotatedType.class);
      this.source = annotatedTypeContext.getExtension();
   }

   public Extension getSource() {
      this.checkWithinObserverNotification();
      return this.source;
   }
}
