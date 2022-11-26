package org.apache.openjpa.jdbc.conf;

import javax.sql.DataSource;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.jdbc.kernel.UpdateManager;
import org.apache.openjpa.jdbc.meta.MappingDefaults;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.jdbc.schema.DriverDataSource;
import org.apache.openjpa.jdbc.schema.SchemaFactory;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.SQLFactory;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.jdbc.ConnectionDecorator;
import org.apache.openjpa.lib.jdbc.JDBCListener;

public interface JDBCConfiguration extends OpenJPAConfiguration {
   String LOG_SQL = "openjpa.jdbc.SQL";
   String LOG_JDBC = "openjpa.jdbc.JDBC";
   String LOG_SCHEMA = "openjpa.jdbc.Schema";

   String getSchema();

   void setSchema(String var1);

   String getSchemas();

   void setSchemas(String var1);

   String[] getSchemasList();

   void setSchemas(String[] var1);

   String getTransactionIsolation();

   void setTransactionIsolation(String var1);

   int getTransactionIsolationConstant();

   void setTransactionIsolation(int var1);

   String getResultSetType();

   int getResultSetTypeConstant();

   void setResultSetType(String var1);

   void setResultSetType(int var1);

   String getFetchDirection();

   int getFetchDirectionConstant();

   void setFetchDirection(String var1);

   void setFetchDirection(int var1);

   String getEagerFetchMode();

   void setEagerFetchMode(String var1);

   int getEagerFetchModeConstant();

   void setEagerFetchMode(int var1);

   String getSubclassFetchMode();

   void setSubclassFetchMode(String var1);

   int getSubclassFetchModeConstant();

   void setSubclassFetchMode(int var1);

   String getLRSSize();

   int getLRSSizeConstant();

   void setLRSSize(String var1);

   void setLRSSize(int var1);

   String getSynchronizeMappings();

   void setSynchronizeMappings(String var1);

   String getJDBCListeners();

   void setJDBCListeners(String var1);

   JDBCListener[] getJDBCListenerInstances();

   void setJDBCListeners(JDBCListener[] var1);

   String getConnectionDecorators();

   void setConnectionDecorators(String var1);

   ConnectionDecorator[] getConnectionDecoratorInstances();

   void setConnectionDecorators(ConnectionDecorator[] var1);

   String getDBDictionary();

   void setDBDictionary(String var1);

   DBDictionary getDBDictionaryInstance();

   void setDBDictionary(DBDictionary var1);

   String getUpdateManager();

   void setUpdateManager(String var1);

   UpdateManager getUpdateManagerInstance();

   void setUpdateManager(UpdateManager var1);

   String getDriverDataSource();

   void setDriverDataSource(String var1);

   DriverDataSource newDriverDataSourceInstance();

   String getSchemaFactory();

   void setSchemaFactory(String var1);

   SchemaFactory getSchemaFactoryInstance();

   void setSchemaFactory(SchemaFactory var1);

   String getSQLFactory();

   SQLFactory getSQLFactoryInstance();

   void setSQLFactory(String var1);

   void setSQLFactory(SQLFactory var1);

   String getMappingFactory();

   void setMappingFactory(String var1);

   String getMappingDefaults();

   void setMappingDefaults(String var1);

   MappingDefaults getMappingDefaultsInstance();

   void setMappingDefaults(MappingDefaults var1);

   MappingRepository getMappingRepositoryInstance();

   MappingRepository newMappingRepositoryInstance();

   DataSource getDataSource(StoreContext var1);

   DataSource getDataSource2(StoreContext var1);
}
