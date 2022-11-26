package org.jboss.weld.injection.spi;

import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.InjectionTarget;

public interface InjectionContext {
   void proceed();

   Object getTarget();

   InjectionTarget getInjectionTarget();

   AnnotatedType getAnnotatedType();
}
