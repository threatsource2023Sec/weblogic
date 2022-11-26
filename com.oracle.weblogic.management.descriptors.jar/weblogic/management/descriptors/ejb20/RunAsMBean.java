package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBean;

public interface RunAsMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getRoleName();

   void setRoleName(String var1);
}
