package org.apache.openjpa.persistence;

import java.util.EnumSet;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.ee.ManagedRuntime;

public interface OpenJPAEntityManagerSPI extends OpenJPAEntityManager {
   OpenJPAConfiguration getConfiguration();

   ManagedRuntime getManagedRuntime();

   void addTransactionListener(Object var1);

   void removeTransactionListener(Object var1);

   EnumSet getTransactionListenerCallbackModes();

   void setTransactionListenerCallbackMode(CallbackMode var1);

   void setTransactionListenerCallbackMode(EnumSet var1);

   void addLifecycleListener(Object var1, Class... var2);

   void removeLifecycleListener(Object var1);

   EnumSet getLifecycleListenerCallbackModes();

   void setLifecycleListenerCallbackMode(CallbackMode var1);

   void setLifecycleListenerCallbackMode(EnumSet var1);
}
