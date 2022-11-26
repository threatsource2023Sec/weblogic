package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface WelcomeFileListMBean extends WebElementMBean {
   String[] getWelcomeFiles();

   void setWelcomeFiles(String[] var1);

   void addWelcomeFile(String var1);

   void removeWelcomeFile(String var1);
}
