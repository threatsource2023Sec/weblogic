package weblogic.j2ee.descriptor.wl;

public interface WeblogicRdbmsJarBean {
   WeblogicRdbmsBeanBean[] getWeblogicRdbmsBeans();

   WeblogicRdbmsBeanBean createWeblogicRdbmsBean();

   void destroyWeblogicRdbmsBean(WeblogicRdbmsBeanBean var1);

   WeblogicRdbmsRelationBean[] getWeblogicRdbmsRelations();

   WeblogicRdbmsRelationBean createWeblogicRdbmsRelation();

   void destroyWeblogicRdbmsRelation(WeblogicRdbmsRelationBean var1);

   boolean isOrderDatabaseOperations();

   void setOrderDatabaseOperations(boolean var1);

   boolean isEnableBatchOperations();

   void setEnableBatchOperations(boolean var1);

   String getCreateDefaultDbmsTables();

   void setCreateDefaultDbmsTables(String var1);

   String getValidateDbSchemaWith();

   void setValidateDbSchemaWith(String var1);

   String getDatabaseType();

   void setDatabaseType(String var1);

   String getDefaultDbmsTablesDdl();

   void setDefaultDbmsTablesDdl(String var1);

   CompatibilityBean getCompatibility();

   CompatibilityBean createCompatibility();

   void destroyCompatibility(CompatibilityBean var1);

   String getId();

   void setId(String var1);

   String getVersion();

   void setVersion(String var1);
}
