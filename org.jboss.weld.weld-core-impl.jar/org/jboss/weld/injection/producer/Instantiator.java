package org.jboss.weld.injection.producer;

import java.lang.reflect.Constructor;
import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.manager.BeanManagerImpl;

public interface Instantiator {
   Object newInstance(CreationalContext var1, BeanManagerImpl var2);

   boolean hasInterceptorSupport();

   boolean hasDecoratorSupport();

   Constructor getConstructor();
}
