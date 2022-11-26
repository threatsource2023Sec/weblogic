package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface ListenerMBean extends WebElementMBean {
   String getListenerClassName();

   void setListenerClassName(String var1);
}
