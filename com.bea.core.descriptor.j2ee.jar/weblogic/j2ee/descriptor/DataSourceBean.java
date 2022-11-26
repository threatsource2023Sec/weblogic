package weblogic.j2ee.descriptor;

import weblogic.descriptor.SettableBean;

public interface DataSourceBean extends SettableBean, PropertyBean {
   String getDescription();

   void setDescription(String var1);

   String getName();

   void setName(String var1);

   String getClassName();

   void setClassName(String var1);

   String getServerName();

   void setServerName(String var1);

   int getPortNumber();

   void setPortNumber(int var1);

   String getDatabaseName();

   void setDatabaseName(String var1);

   String getUrl();

   void setUrl(String var1);

   String getUser();

   void setUser(String var1);

   String getPassword();

   void setPassword(String var1);

   JavaEEPropertyBean[] getProperties();

   JavaEEPropertyBean lookupProperty(String var1);

   JavaEEPropertyBean createProperty();

   void destroyProperty(JavaEEPropertyBean var1);

   int getLoginTimeout();

   void setLoginTimeout(int var1);

   boolean isTransactional();

   void setTransactional(boolean var1);

   String getIsolationLevel();

   void setIsolationLevel(String var1);

   int getInitialPoolSize();

   void setInitialPoolSize(int var1);

   int getMaxPoolSize();

   void setMaxPoolSize(int var1);

   int getMinPoolSize();

   void setMinPoolSize(int var1);

   int getMaxIdleTime();

   void setMaxIdleTime(int var1);

   int getMaxStatements();

   void setMaxStatements(int var1);

   String getId();

   void setId(String var1);
}
