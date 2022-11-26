package org.jboss.weld.ejb.spi;

import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.ejb.api.SessionObjectReference;

public interface EjbServices extends Service {
   SessionObjectReference resolveEjb(EjbDescriptor var1);

   void registerInterceptors(EjbDescriptor var1, InterceptorBindings var2);
}
