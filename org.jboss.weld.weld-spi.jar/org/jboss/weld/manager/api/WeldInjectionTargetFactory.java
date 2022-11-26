package org.jboss.weld.manager.api;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionTargetFactory;

public interface WeldInjectionTargetFactory extends InjectionTargetFactory {
   WeldInjectionTarget createInjectionTarget(Bean var1);

   WeldInjectionTarget createNonProducibleInjectionTarget();

   WeldInjectionTarget createInterceptorInjectionTarget();
}
