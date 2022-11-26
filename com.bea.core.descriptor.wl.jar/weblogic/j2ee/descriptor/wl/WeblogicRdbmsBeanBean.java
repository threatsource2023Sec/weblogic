package weblogic.j2ee.descriptor.wl;

import weblogic.j2ee.descriptor.wl60.BaseWeblogicRdbmsBeanBean;

public interface WeblogicRdbmsBeanBean extends BaseWeblogicRdbmsBeanBean {
   String getEjbName();

   void setEjbName(String var1);

   String getDataSourceJNDIName();

   void setDataSourceJNDIName(String var1);

   UnknownPrimaryKeyFieldBean getUnknownPrimaryKeyField();

   UnknownPrimaryKeyFieldBean createUnknownPrimaryKeyField();

   void destroyUnknownPrimaryKeyField(UnknownPrimaryKeyFieldBean var1);

   TableMapBean[] getTableMaps();

   TableMapBean createTableMap();

   void destroyTableMap(TableMapBean var1);

   FieldGroupBean[] getFieldGroups();

   FieldGroupBean createFieldGroup();

   void destroyFieldGroup(FieldGroupBean var1);

   RelationshipCachingBean[] getRelationshipCachings();

   RelationshipCachingBean createRelationshipCaching();

   void destroyRelationshipCaching(RelationshipCachingBean var1);

   SqlShapeBean[] getSqlShapes();

   SqlShapeBean createSqlShape();

   void destroySqlShape(SqlShapeBean var1);

   WeblogicQueryBean[] getWeblogicQueries();

   WeblogicQueryBean createWeblogicQuery();

   void destroyWeblogicQuery(WeblogicQueryBean var1);

   String getDelayDatabaseInsertUntil();

   void setDelayDatabaseInsertUntil(String var1);

   boolean isUseSelectForUpdate();

   void setUseSelectForUpdate(boolean var1);

   int getLockOrder();

   void setLockOrder(int var1);

   String getInstanceLockOrder();

   void setInstanceLockOrder(String var1);

   AutomaticKeyGenerationBean getAutomaticKeyGeneration();

   AutomaticKeyGenerationBean createAutomaticKeyGeneration();

   void destroyAutomaticKeyGeneration(AutomaticKeyGenerationBean var1);

   boolean isCheckExistsOnMethod();

   void setCheckExistsOnMethod(boolean var1);

   String getId();

   void setId(String var1);

   boolean isClusterInvalidationDisabled();

   void setClusterInvalidationDisabled(boolean var1);

   boolean isUseInnerJoin();

   void setUseInnerJoin(boolean var1);

   String getCategoryCmpField();

   void setCategoryCmpField(String var1);
}
