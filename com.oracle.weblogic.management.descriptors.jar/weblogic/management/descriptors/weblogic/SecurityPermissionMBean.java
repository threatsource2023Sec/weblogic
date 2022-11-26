package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface SecurityPermissionMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getSecurityPermissionSpec();

   void setSecurityPermissionSpec(String var1);
}
