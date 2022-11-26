package weblogic.j2ee.descriptor.wl;

public interface TableMapBean {
   String getTableName();

   void setTableName(String var1);

   FieldMapBean[] getFieldMaps();

   FieldMapBean createFieldMap();

   void destroyFieldMap(FieldMapBean var1);

   String getVerifyRows();

   void setVerifyRows(String var1);

   String getVerifyColumns();

   void setVerifyColumns(String var1);

   String getOptimisticColumn();

   void setOptimisticColumn(String var1);

   boolean isTriggerUpdatesOptimisticColumn();

   void setTriggerUpdatesOptimisticColumn(boolean var1);

   int getVersionColumnInitialValue();

   void setVersionColumnInitialValue(int var1);

   String getId();

   void setId(String var1);
}
