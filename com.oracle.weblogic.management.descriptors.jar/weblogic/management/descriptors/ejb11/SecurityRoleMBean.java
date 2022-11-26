package weblogic.management.descriptors.ejb11;

import weblogic.management.descriptors.XMLElementMBean;

public interface SecurityRoleMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getRoleName();

   void setRoleName(String var1);
}
