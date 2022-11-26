package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface ListenerMBean extends XMLElementMBean {
   String getListenerClass();

   void setListenerClass(String var1);

   String getListenerUri();

   void setListenerUri(String var1);

   String getRunAsPrincipalName();

   void setRunAsPrincipalName(String var1);
}
