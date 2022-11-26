package com.oracle.injection.integration;

import com.oracle.injection.provider.weld.SingletonProviderFactory;
import org.jboss.weld.bootstrap.api.SingletonProvider;

public class ApplicationSingletonProviderFactory implements SingletonProviderFactory {
   public SingletonProvider createSingletonProvider() {
      return new ApplicationSingletonProvider();
   }
}
