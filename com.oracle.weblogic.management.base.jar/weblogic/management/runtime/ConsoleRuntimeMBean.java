package weblogic.management.runtime;

public interface ConsoleRuntimeMBean extends RuntimeMBean {
   boolean isEnabled();

   String getHomePageURL();

   String getDefaultPageURL(String[] var1, String var2);

   String[] getDefaultPageURLs(String[][] var1, String var2);

   String[] getDefaultPageURLs(String[][] var1, String[] var2);

   String getSpecificPageURL(String var1, String[] var2);

   String[] getSpecificPageURLs(String var1, String[][] var2);

   String[] getSpecificPageURLs(String[] var1, String[][] var2);

   String[] getObjectNameContext(String var1);
}
