package weblogic.management.descriptors.cmp11;

import weblogic.management.descriptors.XMLDeclarationMBean;
import weblogic.management.descriptors.XMLElementMBean;

public interface WeblogicRDBMSJarMBean extends XMLElementMBean, XMLDeclarationMBean {
   WeblogicRDBMSBeanMBean[] getWeblogicRDBMSBeans();

   void setWeblogicRDBMSBeans(WeblogicRDBMSBeanMBean[] var1);

   void addWeblogicRDBMSBean(WeblogicRDBMSBeanMBean var1);

   void removeWeblogicRDBMSBean(WeblogicRDBMSBeanMBean var1);

   String getCreateDefaultDBMSTables();

   void setCreateDefaultDBMSTables(String var1);

   String getValidateDbSchemaWith();

   void setValidateDbSchemaWith(String var1);

   String getDatabaseType();

   void setDatabaseType(String var1);
}
