package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface ResourceRefMBean extends WebElementMBean {
   String getRefName();

   void setRefName(String var1);

   String getRefType();

   void setRefType(String var1);

   String getAuth();

   void setAuth(String var1);

   String getSharingScope();

   void setSharingScope(String var1);

   String getDescription();

   void setDescription(String var1);
}
