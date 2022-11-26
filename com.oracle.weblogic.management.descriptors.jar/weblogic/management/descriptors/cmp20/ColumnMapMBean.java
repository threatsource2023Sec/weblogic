package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBean;

public interface ColumnMapMBean extends XMLElementMBean {
   String getForeignKeyColumn();

   void setForeignKeyColumn(String var1);

   String getKeyColumn();

   void setKeyColumn(String var1);
}
