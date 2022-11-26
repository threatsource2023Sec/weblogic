package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBean;

public interface RelationshipRoleMapMBean extends XMLElementMBean {
   String getForeignKeyTable();

   void setForeignKeyTable(String var1);

   String getPrimaryKeyTable();

   void setPrimaryKeyTable(String var1);

   ColumnMapMBean[] getColumnMaps();

   void setColumnMaps(ColumnMapMBean[] var1);

   void addColumnMap(ColumnMapMBean var1);

   void removeColumnMap(ColumnMapMBean var1);
}
