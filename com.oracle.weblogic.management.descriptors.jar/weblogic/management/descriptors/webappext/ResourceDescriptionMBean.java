package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;
import weblogic.management.descriptors.webapp.ResourceRefMBean;

public interface ResourceDescriptionMBean extends WebElementMBean {
   void setResourceReference(ResourceRefMBean var1);

   ResourceRefMBean getResourceReference();

   void setJndiName(String var1);

   String getJndiName();
}
