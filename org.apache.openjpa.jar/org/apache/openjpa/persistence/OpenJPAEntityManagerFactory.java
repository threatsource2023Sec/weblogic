package org.apache.openjpa.persistence;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import org.apache.openjpa.conf.OpenJPAConfiguration;

public interface OpenJPAEntityManagerFactory extends EntityManagerFactory, Serializable {
   /** @deprecated */
   @Deprecated
   int CONN_RETAIN_DEMAND = 0;
   /** @deprecated */
   @Deprecated
   int CONN_RETAIN_TRANS = 1;
   /** @deprecated */
   @Deprecated
   int CONN_RETAIN_ALWAYS = 2;

   Properties getPropertiesAsProperties();

   Map getProperties();

   Object putUserObject(Object var1, Object var2);

   Object getUserObject(Object var1);

   StoreCache getStoreCache();

   StoreCache getStoreCache(String var1);

   QueryResultCache getQueryResultCache();

   OpenJPAEntityManager createEntityManager();

   OpenJPAEntityManager createEntityManager(Map var1);

   /** @deprecated */
   @Deprecated
   OpenJPAConfiguration getConfiguration();

   /** @deprecated */
   @Deprecated
   void addLifecycleListener(Object var1, Class... var2);

   /** @deprecated */
   @Deprecated
   void removeLifecycleListener(Object var1);

   /** @deprecated */
   @Deprecated
   void addTransactionListener(Object var1);

   /** @deprecated */
   @Deprecated
   void removeTransactionListener(Object var1);
}
