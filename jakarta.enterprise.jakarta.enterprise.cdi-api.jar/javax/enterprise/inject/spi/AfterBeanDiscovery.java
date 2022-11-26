package javax.enterprise.inject.spi;

import javax.enterprise.context.spi.Context;
import javax.enterprise.inject.spi.configurator.BeanConfigurator;
import javax.enterprise.inject.spi.configurator.ObserverMethodConfigurator;

public interface AfterBeanDiscovery {
   void addDefinitionError(Throwable var1);

   void addBean(Bean var1);

   BeanConfigurator addBean();

   void addObserverMethod(ObserverMethod var1);

   ObserverMethodConfigurator addObserverMethod();

   void addContext(Context var1);

   AnnotatedType getAnnotatedType(Class var1, String var2);

   Iterable getAnnotatedTypes(Class var1);
}
