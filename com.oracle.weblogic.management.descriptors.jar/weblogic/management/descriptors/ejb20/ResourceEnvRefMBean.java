package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBean;

public interface ResourceEnvRefMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getResourceEnvRefName();

   void setResourceEnvRefName(String var1);

   String getResourceEnvRefType();

   void setResourceEnvRefType(String var1);
}
