package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;

public interface VirtualDirectoryMappingMBean extends WebElementMBean {
   String getLocalPath();

   void setLocalPath(String var1);

   String[] getURLPatterns();

   void setURLPatterns(String[] var1);
}
