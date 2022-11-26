package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface ResourceDescriptionMBean extends XMLElementMBean {
   String getResRefName();

   void setResRefName(String var1);

   String getJNDIName();

   void setJNDIName(String var1);
}
