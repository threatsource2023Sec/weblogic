package com.oracle.injection.ejb;

import java.util.Collection;

public interface EjbDescriptor {
   Class getEJBClass();

   String getEjbName();

   Collection getLocalBusinessInterfaceClasses();

   EjbType getEjbType();

   Collection getRemoveMethods();

   EjbInstanceManager getEjbInstanceManager();

   Class getEJBGeneratedSubClass();

   boolean isPassivationCapable();

   Collection getRemoteBusinessInterfaceClasses();
}
