package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface RunAsMBean extends WebElementMBean {
   String getRoleName();

   void setRoleName(String var1);

   String getDescription();

   void setDescription(String var1);
}
