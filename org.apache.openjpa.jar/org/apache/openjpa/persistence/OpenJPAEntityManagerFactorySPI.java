package org.apache.openjpa.persistence;

import java.util.Map;
import org.apache.openjpa.conf.OpenJPAConfiguration;

public interface OpenJPAEntityManagerFactorySPI extends OpenJPAEntityManagerFactory {
   void addLifecycleListener(Object var1, Class... var2);

   void removeLifecycleListener(Object var1);

   void addTransactionListener(Object var1);

   void removeTransactionListener(Object var1);

   OpenJPAConfiguration getConfiguration();

   OpenJPAEntityManagerSPI createEntityManager();

   OpenJPAEntityManagerSPI createEntityManager(Map var1);
}
