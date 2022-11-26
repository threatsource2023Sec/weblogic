package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLDeclarationMBean;
import weblogic.management.descriptors.XMLElementMBean;

public interface WeblogicRDBMSJarMBean extends XMLElementMBean, XMLDeclarationMBean {
   WeblogicRDBMSBeanMBean[] getWeblogicRDBMSBeans();

   void setWeblogicRDBMSBeans(WeblogicRDBMSBeanMBean[] var1);

   void addWeblogicRDBMSBean(WeblogicRDBMSBeanMBean var1);

   void removeWeblogicRDBMSBean(WeblogicRDBMSBeanMBean var1);

   WeblogicRDBMSRelationMBean[] getWeblogicRDBMSRelations();

   void setWeblogicRDBMSRelations(WeblogicRDBMSRelationMBean[] var1);

   void addWeblogicRDBMSRelation(WeblogicRDBMSRelationMBean var1);

   void removeWeblogicRDBMSRelation(WeblogicRDBMSRelationMBean var1);

   boolean getOrderDatabaseOperations();

   void setOrderDatabaseOperations(boolean var1);

   boolean getEnableBatchOperations();

   void setEnableBatchOperations(boolean var1);

   String getCreateDefaultDBMSTables();

   void setCreateDefaultDBMSTables(String var1);

   String getValidateDbSchemaWith();

   void setValidateDbSchemaWith(String var1);

   String getDatabaseType();

   void setDatabaseType(String var1);

   String getDefaultDbmsTablesDdl();

   void setDefaultDbmsTablesDdl(String var1);

   CompatibilityMBean getCompatibilityBean();

   void setCompatibilityBean(CompatibilityMBean var1);
}
