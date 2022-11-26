package com.oracle.injection.integration;

import com.oracle.injection.InjectionContainer;
import com.oracle.injection.provider.weld.WeldInjectionContainer;

public class DefaultInjectionContainerFactory implements InjectionContainerFactory {
   public InjectionContainer createInjectionContainer() {
      return new WeldInjectionContainer(new ApplicationSingletonProviderFactory());
   }
}
