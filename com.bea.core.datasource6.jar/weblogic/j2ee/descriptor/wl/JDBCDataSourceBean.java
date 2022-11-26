package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface JDBCDataSourceBean extends SettableBean {
   String getName();

   void setName(String var1);

   String getDatasourceType();

   void setDatasourceType(String var1);

   JDBCPropertiesBean getInternalProperties();

   JDBCDriverParamsBean getJDBCDriverParams();

   JDBCConnectionPoolParamsBean getJDBCConnectionPoolParams();

   JDBCDataSourceParamsBean getJDBCDataSourceParams();

   JDBCXAParamsBean getJDBCXAParams();

   JDBCOracleParamsBean getJDBCOracleParams();

   String getVersion();

   void setVersion(String var1);

   long getId();
}
