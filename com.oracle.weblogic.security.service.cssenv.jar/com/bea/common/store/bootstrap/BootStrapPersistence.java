package com.bea.common.store.bootstrap;

import java.util.Collection;

public interface BootStrapPersistence {
   String STORE_FILE = "file";
   String STORE_LDAP = "ldap";
   String STORE_RDBMS = "rdbms";
   String STORE_ID_DELIMITER = "_";

   boolean has(Object var1) throws Exception;

   void makePersistentAll(Collection var1) throws Exception;

   void close();

   String getStoreId();

   Collection postProcessObjects(Collection var1);
}
