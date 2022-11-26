package org.jboss.weld.injection.producer;

import java.util.List;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionTarget;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.manager.BeanManagerImpl;

public interface Injector {
   void inject(Object var1, CreationalContext var2, BeanManagerImpl var3, SlimAnnotatedType var4, InjectionTarget var5);

   void registerInjectionPoints(Set var1);

   List getInitializerMethods();

   List getInjectableFields();
}
