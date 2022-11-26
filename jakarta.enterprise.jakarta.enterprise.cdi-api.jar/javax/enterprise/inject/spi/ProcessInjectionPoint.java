package javax.enterprise.inject.spi;

import javax.enterprise.inject.spi.configurator.InjectionPointConfigurator;

public interface ProcessInjectionPoint {
   InjectionPoint getInjectionPoint();

   void setInjectionPoint(InjectionPoint var1);

   InjectionPointConfigurator configureInjectionPoint();

   void addDefinitionError(Throwable var1);
}
