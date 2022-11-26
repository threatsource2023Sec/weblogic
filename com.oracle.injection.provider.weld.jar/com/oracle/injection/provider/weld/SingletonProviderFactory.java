package com.oracle.injection.provider.weld;

import org.jboss.weld.bootstrap.api.SingletonProvider;

public interface SingletonProviderFactory {
   SingletonProvider createSingletonProvider();
}
