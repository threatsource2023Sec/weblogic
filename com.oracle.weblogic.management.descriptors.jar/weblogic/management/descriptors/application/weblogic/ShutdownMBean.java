package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface ShutdownMBean extends XMLElementMBean {
   String getShutdownClass();

   void setShutdownClass(String var1);

   String getShutdownUri();

   void setShutdownUri(String var1);
}
