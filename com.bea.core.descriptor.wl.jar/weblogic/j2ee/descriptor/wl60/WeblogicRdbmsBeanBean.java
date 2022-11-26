package weblogic.j2ee.descriptor.wl60;

public interface WeblogicRdbmsBeanBean extends BaseWeblogicRdbmsBeanBean {
   String getEjbName();

   void setEjbName(String var1);

   String getPoolName();

   void setPoolName(String var1);

   String getDataSourceJndiName();

   void setDataSourceJndiName(String var1);

   String getTableName();

   void setTableName(String var1);

   FieldMapBean[] getFieldMaps();

   FieldMapBean createFieldMap();

   void destroyFieldMap(FieldMapBean var1);

   FinderBean[] getFinders();

   FinderBean createFinder();

   void destroyFinder(FinderBean var1);

   boolean isEnableTunedUpdates();

   void setEnableTunedUpdates(boolean var1);

   String getId();

   void setId(String var1);
}
