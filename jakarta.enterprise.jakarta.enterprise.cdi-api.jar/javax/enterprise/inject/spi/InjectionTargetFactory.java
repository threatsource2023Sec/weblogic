package javax.enterprise.inject.spi;

import javax.enterprise.inject.spi.configurator.AnnotatedTypeConfigurator;

public interface InjectionTargetFactory {
   InjectionTarget createInjectionTarget(Bean var1);

   default AnnotatedTypeConfigurator configure() {
      throw new UnsupportedOperationException("Configuration not supported here");
   }
}
