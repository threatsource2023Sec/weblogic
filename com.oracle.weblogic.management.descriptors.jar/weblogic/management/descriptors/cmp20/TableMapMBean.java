package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBean;

public interface TableMapMBean extends XMLElementMBean {
   String getTableName();

   void setTableName(String var1);

   FieldMapMBean[] getFieldMaps();

   void setFieldMaps(FieldMapMBean[] var1);

   void addFieldMap(FieldMapMBean var1);

   void removeFieldMap(FieldMapMBean var1);

   String getVerifyRows();

   void setVerifyRows(String var1);

   String getVerifyColumns();

   void setVerifyColumns(String var1);

   String getOptimisticColumn();

   void setOptimisticColumn(String var1);
}
