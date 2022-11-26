package weblogic.management.descriptors.cmp11;

import weblogic.management.descriptors.XMLElementMBean;

public interface BaseWeblogicRDBMSBeanMBean extends XMLElementMBean {
   String getEJBName();

   void setEJBName(String var1);

   String getDataSourceName();

   void setDataSourceName(String var1);

   String getTableName();

   void setTableName(String var1);
}
