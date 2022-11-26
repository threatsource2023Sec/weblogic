package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBean;

public interface ConnectionCheckParamsMBean extends weblogic.management.descriptors.resourcepool.ConnectionCheckParamsMBean, XMLElementMBean {
   String getTableName();

   void setTableName(String var1);

   int getRefreshMinutes();

   void setRefreshMinutes(int var1);

   String getInitSQL();

   void setInitSQL(String var1);
}
