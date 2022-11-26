package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface ResourceEnvRefMBean extends WebElementMBean {
   String getRefName();

   void setRefName(String var1);

   String getRefType();

   void setRefType(String var1);

   String getDescription();

   void setDescription(String var1);
}
