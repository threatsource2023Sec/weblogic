package org.jboss.weld.injection.spi;

import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.InjectionTarget;
import org.jboss.weld.bootstrap.api.Service;

public interface InjectionServices extends Service {
   void aroundInject(InjectionContext var1);

   void registerInjectionTarget(InjectionTarget var1, AnnotatedType var2);
}
