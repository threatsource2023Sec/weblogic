package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;

public interface SecurityPermissionMBean extends WebElementMBean {
   void setDescription(String var1);

   String getDescription();

   void setSecurityPermissionSpec(String var1);

   String getSecurityPermissionSpec();
}
