package weblogic.j2ee.descriptor;

public interface SessionConfigBean {
   int getSessionTimeout();

   void setSessionTimeout(int var1);

   String getId();

   void setId(String var1);

   CookieConfigBean getCookieConfig();

   CookieConfigBean createCookieConfig();

   String[] getTrackingModes();

   void addTrackingMode(String var1);

   void removeTrackingMode(String var1);

   void setTrackingModes(String[] var1);
}
