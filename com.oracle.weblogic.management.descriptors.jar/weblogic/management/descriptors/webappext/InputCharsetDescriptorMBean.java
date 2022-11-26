package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;

public interface InputCharsetDescriptorMBean extends WebElementMBean {
   String getResourcePath();

   void setResourcePath(String var1);

   String getJavaCharsetName();

   void setJavaCharsetName(String var1);
}
