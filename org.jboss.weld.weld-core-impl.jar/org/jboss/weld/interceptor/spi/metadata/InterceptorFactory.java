package org.jboss.weld.interceptor.spi.metadata;

import javax.enterprise.context.spi.CreationalContext;
import org.jboss.weld.manager.BeanManagerImpl;

public interface InterceptorFactory {
   Object create(CreationalContext var1, BeanManagerImpl var2);
}
