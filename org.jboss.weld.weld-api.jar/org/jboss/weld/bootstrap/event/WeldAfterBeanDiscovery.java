package org.jboss.weld.bootstrap.event;

import javax.enterprise.inject.spi.AfterBeanDiscovery;

public interface WeldAfterBeanDiscovery extends AfterBeanDiscovery {
   InterceptorConfigurator addInterceptor();

   WeldBeanConfigurator addBean();
}
