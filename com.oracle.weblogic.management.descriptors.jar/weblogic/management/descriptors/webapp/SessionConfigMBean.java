package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface SessionConfigMBean extends WebElementMBean {
   int getSessionTimeout();

   void setSessionTimeout(int var1);
}
