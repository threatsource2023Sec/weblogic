package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;

public interface CharsetMappingMBean extends WebElementMBean {
   String getIANACharsetName();

   void setIANACharsetName(String var1);

   String getJavaCharsetName();

   void setJavaCharsetName(String var1);
}
