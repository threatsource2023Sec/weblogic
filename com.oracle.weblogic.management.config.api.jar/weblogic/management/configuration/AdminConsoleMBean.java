package weblogic.management.configuration;

public interface AdminConsoleMBean extends ConfigurationMBean {
   String getCookieName();

   void setCookieName(String var1);

   boolean isProtectedCookieEnabled();

   void setProtectedCookieEnabled(boolean var1);

   int getSessionTimeout();

   void setSessionTimeout(int var1);

   String getSSOLogoutURL();

   void setSSOLogoutURL(String var1);

   int getMinThreads();

   void setMinThreads(int var1);
}
