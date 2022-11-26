package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBean;

public interface ConnectionPropertiesMBean extends XMLElementMBean {
   String getUserName();

   void setUserName(String var1);

   String getPassword();

   void setPassword(String var1);

   String getUrl();

   void setUrl(String var1);

   String getDriverClassName();

   void setDriverClassName(String var1);

   ConnectionParamsMBean getConnectionParams();

   void setConnectionParams(ConnectionParamsMBean var1);
}
