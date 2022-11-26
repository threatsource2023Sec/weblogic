package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface StartupMBean extends XMLElementMBean {
   String getStartupClass();

   void setStartupClass(String var1);

   String getStartupUri();

   void setStartupUri(String var1);
}
