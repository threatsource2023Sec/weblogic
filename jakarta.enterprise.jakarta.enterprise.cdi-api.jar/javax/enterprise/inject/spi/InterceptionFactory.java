package javax.enterprise.inject.spi;

import javax.enterprise.inject.spi.configurator.AnnotatedTypeConfigurator;

public interface InterceptionFactory {
   InterceptionFactory ignoreFinalMethods();

   AnnotatedTypeConfigurator configure();

   Object createInterceptedInstance(Object var1);
}
