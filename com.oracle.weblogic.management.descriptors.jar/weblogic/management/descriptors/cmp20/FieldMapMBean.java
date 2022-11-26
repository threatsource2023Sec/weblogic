package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBean;

public interface FieldMapMBean extends XMLElementMBean {
   String getCMPField();

   void setCMPField(String var1);

   String getDBMSColumn();

   void setDBMSColumn(String var1);

   String getDBMSColumnType();

   void setDBMSColumnType(String var1);

   String getGroupName();

   void setGroupName(String var1);
}
