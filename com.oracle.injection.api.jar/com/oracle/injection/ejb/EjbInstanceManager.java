package com.oracle.injection.ejb;

public interface EjbInstanceManager {
   Object getEjbInstance(Class var1);

   void remove();

   boolean isRemoved();
}
