package weblogic.management.descriptors.cmp11;

import weblogic.management.descriptors.XMLElementMBean;

public interface FieldMapMBean extends XMLElementMBean {
   String getCMPField();

   void setCMPField(String var1);

   String getDBMSColumn();

   void setDBMSColumn(String var1);
}
