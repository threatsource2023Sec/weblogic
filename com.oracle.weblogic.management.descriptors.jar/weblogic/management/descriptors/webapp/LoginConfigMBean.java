package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface LoginConfigMBean extends WebElementMBean {
   String getAuthMethod();

   void setAuthMethod(String var1);

   String getRealmName();

   void setRealmName(String var1);

   String getLoginPage();

   void setLoginPage(String var1);

   String getErrorPage();

   void setErrorPage(String var1);
}
