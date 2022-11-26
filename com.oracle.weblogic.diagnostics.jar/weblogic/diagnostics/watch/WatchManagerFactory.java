package weblogic.diagnostics.watch;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.module.WLDFModuleException;
import weblogic.diagnostics.utils.LogEventRulesEvaluator;
import weblogic.management.ManagementException;

public class WatchManagerFactory {
   private WatchNotificationRuntimeMBeanImpl watchNotificationRuntime;
   private String partitionName;
   private static Map watchManagerFactories = new ConcurrentHashMap();
   private Map activeWatchManagers = new ConcurrentHashMap();

   private WatchManagerFactory(String partitionName) throws ManagementException {
      this.partitionName = partitionName;
   }

   public static synchronized WatchManagerFactory getFactoryInstance(String name) throws ManagementException {
      if (!watchManagerFactories.containsKey(name)) {
         watchManagerFactories.put(name, new WatchManagerFactory(name));
      }

      return (WatchManagerFactory)watchManagerFactories.get(name);
   }

   public static synchronized WatchManagerFactory removeFactoryInstance(String name) {
      return (WatchManagerFactory)watchManagerFactories.remove(name);
   }

   public static boolean isImageNotificationActive() {
      Iterator var0 = watchManagerFactories.values().iterator();

      while(var0.hasNext()) {
         WatchManagerFactory watchManagerFactory = (WatchManagerFactory)var0.next();
         WatchManager[] watchManagers = watchManagerFactory.listActiveWatchManagers();
         WatchManager[] var3 = watchManagers;
         int var4 = watchManagers.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            WatchManager wm = var3[var5];
            if (wm.getNumActiveImageNotifications() > 0) {
               return true;
            }
         }
      }

      return false;
   }

   public synchronized WatchManager createWatchManager(WLDFResourceBean bean) throws WLDFModuleException {
      WatchManager wm = new WatchManager(this, bean);
      this.activeWatchManagers.put(bean, wm);
      return wm;
   }

   public synchronized void destroyWatchManager(WLDFResourceBean bean) throws WLDFModuleException {
      WatchManager removedWm = (WatchManager)this.activeWatchManagers.remove(bean);
      if (removedWm != null) {
         removedWm.destroy();
      }

   }

   public int numActiveWatchManagers() {
      return this.activeWatchManagers.size();
   }

   public WatchManager[] listActiveWatchManagers() {
      WatchManager[] activeWMList = new WatchManager[0];
      if (this.activeWatchManagers != null) {
         activeWMList = (WatchManager[])this.activeWatchManagers.values().toArray(new WatchManager[this.activeWatchManagers.size()]);
      }

      return activeWMList;
   }

   public LogEventRulesEvaluator getLogEventRulesEvaluator() {
      return WatchEventListener.getInstance();
   }

   public int getEffectiveLogEventRulesEvaluationSeverity() {
      int severity = 0;
      WatchManager[] watchManagers = this.listActiveWatchManagers();
      WatchManager[] var3 = watchManagers;
      int var4 = watchManagers.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         WatchManager watchManager = var3[var5];
         int watchManagerLogSeverity = watchManager.getWatchConfiguration().getEventHandlerSeverity();
         if (watchManagerLogSeverity > severity) {
            severity = watchManagerLogSeverity;
         }
      }

      return severity;
   }

   public WatchManager lookupWatchManager(WLDFResourceBean wldfResource) {
      return (WatchManager)this.activeWatchManagers.get(wldfResource);
   }

   public WatchNotificationRuntimeMBeanImpl getWatchNotificationRuntime() {
      return this.watchNotificationRuntime;
   }

   public void setWatchNotificationRuntime(WatchNotificationRuntimeMBeanImpl watchNotificationRuntime) {
      this.watchNotificationRuntime = watchNotificationRuntime;
      Iterator var2 = this.activeWatchManagers.values().iterator();

      while(var2.hasNext()) {
         WatchManager wm = (WatchManager)var2.next();
         wm.initializeJMXNotificationListeners(watchNotificationRuntime);
      }

   }

   public String getPartitionName() {
      return this.partitionName;
   }
}
