package weblogic.servlet.internal;

public final class OnDemandContext {
   private final String contextPath;
   private final OnDemandListener listener;
   private final String appName;
   private final boolean displayRefresh;
   private int progressIndicator = 3;

   OnDemandContext(String cp, OnDemandListener listener, String appName, boolean displayRefresh) {
      this.contextPath = cp;
      this.listener = listener;
      this.appName = appName;
      this.displayRefresh = displayRefresh;
   }

   String getContextPath() {
      return this.contextPath;
   }

   OnDemandListener getListener() {
      return this.listener;
   }

   public String getAppName() {
      return this.appName;
   }

   public boolean isDisplayRefresh() {
      return this.displayRefresh;
   }

   public int updateProgressIndicator() {
      return this.progressIndicator++;
   }
}
