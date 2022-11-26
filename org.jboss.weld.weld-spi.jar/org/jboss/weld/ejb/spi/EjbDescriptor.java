package org.jboss.weld.ejb.spi;

import java.util.Collection;

public interface EjbDescriptor {
   Class getBeanClass();

   Collection getLocalBusinessInterfaces();

   Collection getRemoteBusinessInterfaces();

   String getEjbName();

   Collection getRemoveMethods();

   boolean isStateless();

   boolean isSingleton();

   boolean isStateful();

   boolean isMessageDriven();

   boolean isPassivationCapable();
}
