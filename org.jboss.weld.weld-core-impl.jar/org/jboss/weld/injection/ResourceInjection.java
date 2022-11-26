package org.jboss.weld.injection;

import javax.enterprise.context.spi.CreationalContext;

public interface ResourceInjection {
   Object getResourceReference(CreationalContext var1);

   void injectResourceReference(Object var1, CreationalContext var2);
}
