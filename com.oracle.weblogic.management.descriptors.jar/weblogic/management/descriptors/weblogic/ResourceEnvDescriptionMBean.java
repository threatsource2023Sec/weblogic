package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface ResourceEnvDescriptionMBean extends XMLElementMBean {
   String getResEnvRefName();

   void setResEnvRefName(String var1);

   String getJNDIName();

   void setJNDIName(String var1);
}
