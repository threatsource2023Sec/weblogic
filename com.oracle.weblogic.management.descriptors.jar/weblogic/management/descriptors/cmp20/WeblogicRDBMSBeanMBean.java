package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.cmp11.BaseWeblogicRDBMSBeanMBean;

public interface WeblogicRDBMSBeanMBean extends BaseWeblogicRDBMSBeanMBean {
   String getEJBName();

   void setEJBName(String var1);

   String getDataSourceName();

   void setDataSourceName(String var1);

   TableMapMBean[] getTableMaps();

   void setTableMaps(TableMapMBean[] var1);

   void addTableMap(TableMapMBean var1);

   void removeTableMap(TableMapMBean var1);

   FieldGroupMBean[] getFieldGroups();

   void setFieldGroups(FieldGroupMBean[] var1);

   void addFieldGroup(FieldGroupMBean var1);

   void removeFieldGroup(FieldGroupMBean var1);

   RelationshipCachingMBean[] getRelationshipCachings();

   void setRelationshipCachings(RelationshipCachingMBean[] var1);

   void addRelationshipCaching(RelationshipCachingMBean var1);

   void removeRelationshipCaching(RelationshipCachingMBean var1);

   WeblogicQueryMBean[] getWeblogicQueries();

   void setWeblogicQueries(WeblogicQueryMBean[] var1);

   void addWeblogicQuery(WeblogicQueryMBean var1);

   void removeWeblogicQuery(WeblogicQueryMBean var1);

   String getDelayDatabaseInsertUntil();

   void setDelayDatabaseInsertUntil(String var1);

   boolean getUseSelectForUpdate();

   void setUseSelectForUpdate(boolean var1);

   int getLockOrder();

   void setLockOrder(int var1);

   String getInstanceLockOrder();

   void setInstanceLockOrder(String var1);

   AutomaticKeyGenerationMBean getAutomaticKeyGeneration();

   void setAutomaticKeyGeneration(AutomaticKeyGenerationMBean var1);

   boolean getCheckExistsOnMethod();

   void setCheckExistsOnMethod(boolean var1);
}
