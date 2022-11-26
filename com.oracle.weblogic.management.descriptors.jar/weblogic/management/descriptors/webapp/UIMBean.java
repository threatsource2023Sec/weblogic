package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface UIMBean extends WebElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getDisplayName();

   void setDisplayName(String var1);

   String getLargeIconFileName();

   void setLargeIconFileName(String var1);

   String getSmallIconFileName();

   void setSmallIconFileName(String var1);
}
