package weblogic.work;

import com.oracle.core.registryhelper.RegistryListener;
import com.oracle.core.registryhelper.utils.MonitorableMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WorkManagerFactory {
   public static final int UNSPECIFIED = -1;
   public static final int HIGH_FAIR_SHARE = 100;
   protected static WorkManagerFactory SINGLETON;
   protected WorkManager DEFAULT;
   protected WorkManager SYSTEM;
   protected WorkManager REJECTOR;
   protected final MonitorableMap byName = new MonitorableMap(Collections.synchronizedMap(new HashMap()));

   protected WorkManagerFactory() {
   }

   public static synchronized boolean isInitialized() {
      return SINGLETON != null;
   }

   protected static synchronized void set(WorkManagerFactory factory) {
      if (SINGLETON != null) {
         throw new AssertionError("Duplicate initialization of WorkManager");
      } else {
         SINGLETON = factory;
      }
   }

   public static WorkManagerFactory getInstance() {
      if (SINGLETON != null) {
         return SINGLETON;
      } else {
         initDelegate();
         return SINGLETON;
      }
   }

   protected void addByName(String name, WorkManager manager) {
      this.getDomainWorkManagersMap().put(name, manager);
   }

   public WorkManager getDefault() {
      return this.DEFAULT;
   }

   public WorkManager getSystem() {
      return this.SYSTEM;
   }

   WorkManager getRejector() {
      return this.REJECTOR;
   }

   public final WorkManager findOrCreate(String workManagerName, int fairshare, int minThreadsConstraint, int maxThreadsConstraint) {
      if (SINGLETON == null) {
         initDelegate();
      }

      Map byName = this.getDomainWorkManagersMap();
      WorkManager manager = (WorkManager)byName.get(workManagerName);
      if (manager != null) {
         return manager;
      } else {
         manager = this.findAppScoped(workManagerName, (String)null, (String)null, false);
         if (manager != null && manager.getName().equals(workManagerName)) {
            return manager;
         } else {
            manager = this.create(workManagerName, fairshare, minThreadsConstraint, maxThreadsConstraint);
            byName.put(workManagerName, manager);
            return manager;
         }
      }
   }

   public final WorkManager findOrCreateResponseTime(String workManagerName, int responseTime, int minThreadsConstraint, int maxThreadsConstraint) {
      Map byName = this.getDomainWorkManagersMap();
      WorkManager manager = (WorkManager)byName.get(workManagerName);
      if (manager != null) {
         return manager;
      } else {
         manager = this.createResponseTime(workManagerName, responseTime, minThreadsConstraint, maxThreadsConstraint);
         byName.put(workManagerName, manager);
         return manager;
      }
   }

   public final WorkManager findOrCreate(String workManagerName, int minThreadsConstraint, int maxThreadsConstraint) {
      return this.findOrCreate(workManagerName, -1, minThreadsConstraint, maxThreadsConstraint);
   }

   public final WorkManager find(String workManagerName) {
      Map byName = this.getDomainWorkManagersMap();
      WorkManager manager = (WorkManager)byName.get(workManagerName);
      if (manager != null) {
         return manager;
      } else {
         manager = this.findAppScoped(workManagerName, (String)null, (String)null);
         return manager != null ? manager : this.getDefault();
      }
   }

   public final WorkManager find(String workManagerName, String appName, String moduleName) {
      Map byName = this.getDomainWorkManagersMap();
      WorkManager manager = (WorkManager)byName.get(workManagerName);
      if (manager != null) {
         return manager;
      } else {
         manager = this.findAppScoped(workManagerName, appName, moduleName);
         return manager != null ? manager : this.getDefault();
      }
   }

   public final WorkManager find(String workManagerName, String appName, String moduleName, boolean lookupModuleName) {
      Map byName = this.getDomainWorkManagersMap();
      WorkManager manager = (WorkManager)byName.get(workManagerName);
      if (manager != null) {
         return manager;
      } else {
         manager = this.findAppScoped(workManagerName, appName, moduleName, true, lookupModuleName);
         return manager != null ? manager : this.getDefault();
      }
   }

   private static synchronized void initDelegate() {
      if (SINGLETON == null) {
         WorkManagerFactory factory = new WorkManagerFactory();
         factory.initialize();
         SINGLETON = factory;
      }
   }

   private void initialize() {
      this.DEFAULT = new WorkManagerLite("default");
      this.SYSTEM = this.DEFAULT;
      this.byName.put("weblogic.kernel.Default", this.DEFAULT);
      this.byName.put("default", this.DEFAULT);
      this.byName.put("weblogic.kernel.System", this.SYSTEM);
      WorkManagerLite direct = new WorkManagerLite();
      this.byName.put("direct", direct);
   }

   protected WorkManager create(String workManagerName, int fairshare, int minThreadsConstraint, int maxThreadsConstraint) {
      return new WorkManagerLite(workManagerName, Math.max(minThreadsConstraint, maxThreadsConstraint));
   }

   protected WorkManager createResponseTime(String workManagerName, int responseTime, int minThreadsConstraint, int maxThreadsConstraint) {
      return this.create(workManagerName, -1, minThreadsConstraint, maxThreadsConstraint);
   }

   protected WorkManager findAppScoped(String workManagerName, String appName, String moduleName) {
      return this.findAppScoped(workManagerName, appName, moduleName, true);
   }

   protected WorkManager findAppScoped(String workManagerName, String appName, String moduleName, boolean warnIfNotFound) {
      return this.findAppScoped(workManagerName, appName, moduleName, warnIfNotFound, true);
   }

   protected WorkManager findAppScoped(String workManagerName, String appName, String moduleName, boolean warnIfNotFound, boolean lookupModuleName) {
      return null;
   }

   protected void cleanupWorkManager(WorkManager manager) {
   }

   public final void remove(String workManagerName) {
      Map byName = this.getDomainWorkManagersMap();
      WorkManager manager = (WorkManager)byName.get(workManagerName);
      if (manager != null) {
         byName.remove(workManagerName);
         this.cleanupWorkManager(manager);
      }

   }

   public void addWorkManagerListener(RegistryListener listener) {
      this.getDomainWorkManagersMap().addListener(listener);
   }

   public void removeWorkManagerListener(RegistryListener listener) {
      this.getDomainWorkManagersMap().removeListener(listener);
   }

   protected MonitorableMap getDomainWorkManagersMap() {
      return this.byName;
   }

   public String toString() {
      return "WorkManagerFactory{DEFAULT=" + this.DEFAULT + ", SYSTEM=" + this.SYSTEM + '}';
   }
}
