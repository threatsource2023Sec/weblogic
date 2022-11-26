package weblogic.jms;

import weblogic.management.WebLogicMBean;

public interface ConnectionFactoryListener {
   void onConnectionFactoryRemoval(String var1, WebLogicMBean var2);
}
