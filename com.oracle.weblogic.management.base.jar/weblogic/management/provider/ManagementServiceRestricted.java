package weblogic.management.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.internal.SecurityHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.utils.collections.ConcurrentHashSet;

public class ManagementServiceRestricted {
   private static EditAccess editAccess;
   private static Map namedEditAccess;
   private static Set editSessionLifecycleListeners = new ConcurrentHashSet();
   private static final Set allowedClasses = new HashSet(Arrays.asList("com.oracle.weblogic.lifecycle.config.PropagationManagerImpl", "weblogic.deploy.api.internal.utils.JMXDeployerHelper", "weblogic.deploy.internal.adminserver.ConfigChangesHandler", "weblogic.deploy.internal.adminserver.EditAccessHelper", "weblogic.management.mbeanservers.domainruntime.internal.DomainRuntimeServerService", "weblogic.management.mbeanservers.domainruntime.internal.DomainRuntimeServiceMBeanImpl", "weblogic.management.mbeanservers.edit.internal.EditLockInterceptor", "weblogic.management.mbeanservers.edit.internal.EditServerService", "weblogic.management.mbeanservers.edit.internal.AppDeploymentConfigurationManagerMBeanImpl", "weblogic.cluster.migration.management.MigratableServiceCoordinatorRuntime", "weblogic.cluster.migration.management.MigrationTask", "weblogic.management.provider.internal.ConfigImageSource", "weblogic.management.provider.internal.TestEditAccess", "weblogic.management.provider.internal.TestLockManager", "weblogic.management.deploy.ApplicationsDirPoller", "weblogic.application.ComponentInvocationContextManagerImpl$EditConfigTree", "weblogic.application.utils.ApplicationVersionUtils", "weblogic.management.rest.lib.bean.utils.EditUtils", "weblogic.management.provider.internal.EditSessionConfigurationManagerService", "weblogic.management.provider.internal.EditAccessImpl", "weblogic.management.provider.internal.EditAccessImpl$ActivateTaskStartListener", "weblogic.management.provider.internal.EditAccessImpl$ActivateTaskCompletionListener", "weblogic.management.provider.internal.situationalconfig.XMLFileLoader", "weblogic.management.mbeanservers.edit.internal.BaseEditServerService"));

   public static EditAccess getEditAccess(AuthenticatedSubject sub) {
      assert editAccess != null : "EditAccess is not initialized";

      SecurityHelper.assertIfNotKernel(sub);
      checkAccess(new Throwable());
      return editAccess;
   }

   public static void setEditAccess(EditAccess ea) {
      if (editAccess != null) {
         throw new AssertionError("Edit Access Can only be initialized once.");
      } else {
         editAccess = ea;
      }
   }

   public static Map getNamedEditAccess(AuthenticatedSubject sub) {
      assert namedEditAccess != null : "Named EditAccess is not initialized";

      SecurityHelper.assertIfNotKernel(sub);
      checkAccess(new Throwable());
      return namedEditAccess;
   }

   public static void setNamedEditAccess(Map editAccess) {
      if (namedEditAccess != null) {
         throw new AssertionError("Named Edit Access can only be initialized once.");
      } else {
         namedEditAccess = (Map)(editAccess == null ? new HashMap() : editAccess);
      }
   }

   private static void checkAccess(Throwable t) {
      StackTraceElement[] elements = t.getStackTrace();
      if (elements != null && elements.length >= 2) {
         StackTraceElement e = elements[1];
         String name = e.getClassName();
         if (!allowedClasses.contains(name)) {
            throw new Error("Access to EditAccess is restricted.");
         }
      }
   }

   public static synchronized String[] getEditSessions() {
      String partitionName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      Map stringEditAccessMap = (Map)namedEditAccess.get(partitionName);
      if (stringEditAccessMap != null) {
         Set strings = stringEditAccessMap.keySet();
         return (String[])strings.toArray(new String[strings.size()]);
      } else {
         return new String[0];
      }
   }

   public static synchronized EditAccess getEditSession(String name) {
      String partitionName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      return getEditSession(partitionName, name);
   }

   public static synchronized EditAccess getEditSession(String partitionName, String name) {
      Map stringEditAccessMap = (Map)namedEditAccess.get(partitionName);
      return stringEditAccessMap != null ? (EditAccess)stringEditAccessMap.get(name == null ? "default" : name) : null;
   }

   public static synchronized EditAccess createEditSession(String name, String description) throws IllegalStateException {
      String partitionName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      return createEditSession(partitionName, name, description);
   }

   public static synchronized EditAccess createEditSession(String partitionName, String name, String description) throws IllegalStateException {
      Map stringEditAccessMap = (Map)namedEditAccess.get(partitionName);
      EditAccess created;
      if (stringEditAccessMap == null) {
         HashMap editAccessHashMap = new HashMap();
         created = editAccess.createEditAccess(partitionName, name, description);
         editAccessHashMap.put(name, created);
         namedEditAccess.put(partitionName, editAccessHashMap);
      } else {
         if (stringEditAccessMap.containsKey(name)) {
            throw new IllegalStateException("Named edit session with name \"" + name + "\" already registered in the current context.");
         }

         created = editAccess.createEditAccess(partitionName, name, description);
         stringEditAccessMap.put(name, created);
      }

      notifyEditSessionCreated(created);
      return created;
   }

   public static synchronized void destroyEditSession(String name) throws IllegalStateException {
      String partitionName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      Map stringEditAccessMap = (Map)namedEditAccess.get(partitionName);
      if (stringEditAccessMap != null && stringEditAccessMap.containsKey(name)) {
         EditAccess editAccess = (EditAccess)stringEditAccessMap.get(name);
         editAccess.destroy();
         stringEditAccessMap.remove(name);
      } else {
         throw new IllegalStateException("Named edit session with name \"" + name + "\" does not exist.");
      }
   }

   public static synchronized void destroyEditSession(EditAccess editAccess) {
      editAccess.destroy();
      Map map = (Map)namedEditAccess.get(editAccess.getPartitionName());
      if (map != null) {
         map.remove(editAccess.getEditSessionName());
      }

   }

   public static EditAccess createDefaultEditSession(String partitionName, AuthenticatedSubject sub) {
      SecurityHelper.assertIfNotKernel(sub);
      checkAccess(new Throwable());
      Map editAccessMap = (Map)namedEditAccess.get(partitionName);
      if (editAccessMap == null) {
         HashMap editAccessHashMap = new HashMap();
         EditAccess created = editAccess.createEditAccess(partitionName, "default", "");
         editAccessHashMap.put("default", created);
         namedEditAccess.put(partitionName, editAccessHashMap);
         notifyEditSessionCreated(created);
         return created;
      } else {
         throw new IllegalStateException("Uncleaned edit sessions for partition " + partitionName);
      }
   }

   public static synchronized void destroyEditSession(String name, String partitionName, AuthenticatedSubject sub) {
      SecurityHelper.assertIfNotKernel(sub);
      checkAccess(new Throwable());
      Map editAccessMap = (Map)namedEditAccess.get(partitionName);
      if (editAccessMap != null) {
         EditAccess editAccess = (EditAccess)editAccessMap.get(name);
         if (editAccess != null) {
            editAccess.forceDestroy();
            editAccessMap.remove(name);
         }

         if (editAccessMap.isEmpty()) {
            namedEditAccess.remove(partitionName);
         }
      }

   }

   public static synchronized void runOnAllEditSessions(RunnableWithParam runnable, AuthenticatedSubject sub) {
      SecurityHelper.assertIfNotKernel(sub);
      checkAccess(new Throwable());
      Iterator var2 = (new ArrayList(namedEditAccess.values())).iterator();

      while(var2.hasNext()) {
         Map m = (Map)var2.next();
         Iterator var4 = (new ArrayList(m.values())).iterator();

         while(var4.hasNext()) {
            EditAccess ea = (EditAccess)var4.next();
            runnable.run(ea);
         }
      }

   }

   public static void addEditSessionLifecycleListener(EditSessionLifecycleListener listener) {
      editSessionLifecycleListeners.add(listener);
   }

   public static void removeEditSessionLifecycleListener(EditSessionLifecycleListener listener) {
      editSessionLifecycleListeners.remove(listener);
   }

   public static void notifyEditSessionStarted(EditAccess ea) {
      Iterator var1 = editSessionLifecycleListeners.iterator();

      while(var1.hasNext()) {
         EditSessionLifecycleListener listener = (EditSessionLifecycleListener)var1.next();
         listener.onEditSessionStarted(ea);
      }

   }

   public static void notifyEditSessionDestroyed(EditAccess ea) {
      if (ea != null) {
         Iterator var1 = editSessionLifecycleListeners.iterator();

         while(var1.hasNext()) {
            EditSessionLifecycleListener listener = (EditSessionLifecycleListener)var1.next();
            listener.onEditSessionDestroyed(ea);
         }

      }
   }

   private static void notifyEditSessionCreated(EditAccess ea) {
      if (ea != null) {
         Iterator var1 = editSessionLifecycleListeners.iterator();

         while(var1.hasNext()) {
            EditSessionLifecycleListener listener = (EditSessionLifecycleListener)var1.next();
            listener.onEditSessionCreated(ea);
         }

      }
   }

   public static void notifyEditSessionActivateStarted(EditAccess ea, ActivateTask activateTask) {
      Iterator var2 = editSessionLifecycleListeners.iterator();

      while(var2.hasNext()) {
         EditSessionLifecycleListener listener = (EditSessionLifecycleListener)var2.next();
         listener.onActivateStarted(ea, activateTask);
      }

   }

   public static void notifyEditSessionActivateCompleted(EditAccess ea, ActivateTask activateTask) {
      Iterator var2 = editSessionLifecycleListeners.iterator();

      while(var2.hasNext()) {
         EditSessionLifecycleListener listener = (EditSessionLifecycleListener)var2.next();
         listener.onActivateCompleted(ea, activateTask);
      }

   }

   public static void notifyEditSessionUndidUnactivatedChanges(EditAccess ea) {
      Iterator var1 = editSessionLifecycleListeners.iterator();

      while(var1.hasNext()) {
         EditSessionLifecycleListener listener = (EditSessionLifecycleListener)var1.next();
         listener.onUndoUnactivatedChanges(ea);
      }

   }

   public static void notifyEditSessionUndidUnsavedChanges(EditAccess ea) {
      Iterator var1 = editSessionLifecycleListeners.iterator();

      while(var1.hasNext()) {
         EditSessionLifecycleListener listener = (EditSessionLifecycleListener)var1.next();
         listener.onUndoUnsavedChanges(ea);
      }

   }

   public static void notifyEditSessionReloaded(EditAccess ea) {
      Iterator var1 = editSessionLifecycleListeners.iterator();

      while(var1.hasNext()) {
         EditSessionLifecycleListener listener = (EditSessionLifecycleListener)var1.next();
         listener.onConfigurationReloaded(ea);
      }

   }

   public interface RunnableWithParam {
      void run(Object var1);
   }
}
