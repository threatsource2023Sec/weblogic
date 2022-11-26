package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface MimeMappingMBean extends WebElementMBean {
   String getMimeType();

   void setMimeType(String var1);

   String getExtension();

   void setExtension(String var1);
}
