package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBean;

public interface FieldGroupMBean extends XMLElementMBean {
   String getGroupName();

   void setGroupName(String var1);

   String[] getCMPFields();

   void setCMPFields(String[] var1);

   void addCMPField(String var1);

   void removeCMPField(String var1);

   String[] getCMRFields();

   void setCMRFields(String[] var1);

   void addCMRField(String var1);

   void removeCMRField(String var1);
}
