package org.apache.openjpa.jdbc.kernel;

import java.sql.Connection;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.SQLFactory;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.util.Id;

public interface JDBCStore {
   StoreContext getContext();

   JDBCConfiguration getConfiguration();

   DBDictionary getDBDictionary();

   SQLFactory getSQLFactory();

   JDBCLockManager getLockManager();

   Connection getConnection();

   JDBCFetchConfiguration getFetchConfiguration();

   Id newDataStoreId(long var1, ClassMapping var3, boolean var4);

   Object find(Object var1, ValueMapping var2, JDBCFetchConfiguration var3);

   void loadSubclasses(ClassMapping var1);
}
