package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface TagLibMBean extends WebElementMBean {
   String getURI();

   void setURI(String var1);

   String getLocation();

   void setLocation(String var1);

   TLDMBean getTLD();

   void setTLD(TLDMBean var1);
}
