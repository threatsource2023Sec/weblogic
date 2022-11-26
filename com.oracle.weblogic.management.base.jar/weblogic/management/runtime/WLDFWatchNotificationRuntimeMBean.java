package weblogic.management.runtime;

public interface WLDFWatchNotificationRuntimeMBean extends WLDFWatchManagerRuntimeMBean {
   /** @deprecated */
   @Deprecated
   WLDFWatchJMXNotificationRuntimeMBean getWLDFWatchJMXNotificationRuntime();

   WLDFWatchNotificationSourceRuntimeMBean getWLDFWatchJMXNotificationSource();

   String execute(String var1, String... var2);

   String getBeanInfo(String var1, String... var2);
}
