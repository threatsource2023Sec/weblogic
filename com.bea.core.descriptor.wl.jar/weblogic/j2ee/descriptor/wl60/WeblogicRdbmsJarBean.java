package weblogic.j2ee.descriptor.wl60;

public interface WeblogicRdbmsJarBean {
   WeblogicRdbmsBeanBean[] getWeblogicRdbmsBeans();

   WeblogicRdbmsBeanBean createWeblogicRdbmsBean();

   void destroyWeblogicRdbmsBean(WeblogicRdbmsBeanBean var1);

   boolean isCreateDefaultDbmsTables();

   void setCreateDefaultDbmsTables(boolean var1);

   String getValidateDbSchemaWith();

   void setValidateDbSchemaWith(String var1);

   String getDatabaseType();

   void setDatabaseType(String var1);
}
