package weblogic.cluster.migration.management;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.cluster.migration.RemoteMigratableServiceCoordinatorImpl;
import weblogic.cluster.migration.RemoteMigrationControl;
import weblogic.cluster.singleton.SingletonMonitorRemote;
import weblogic.jndi.Environment;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SingletonServiceMBean;
import weblogic.management.provider.ActivateTask;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.EditChangesValidationException;
import weblogic.management.provider.EditFailedException;
import weblogic.management.provider.EditNotEditorException;
import weblogic.management.provider.EditSaveChangesFailedException;
import weblogic.management.provider.EditWaitTimedOutException;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceRestricted;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.MigratableServiceCoordinatorRuntimeMBean;
import weblogic.management.runtime.MigrationException;
import weblogic.management.runtime.MigrationTaskRuntimeMBean;
import weblogic.protocol.URLManagerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.store.DefaultObjectHandler;
import weblogic.store.PersistentMap;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreManager;
import weblogic.utils.Debug;

public final class MigratableServiceCoordinatorRuntime extends DomainRuntimeMBeanDelegate implements MigratableServiceCoordinatorRuntimeMBean {
   private static final int TASK_AFTERLIFE_TIME_MILLIS = 1800000;
   private static final int POLLING_DELAY = 1000;
   public static final String STORE_NAME = "weblogic_migratable_services_store";
   private boolean sysTask = false;
   private DomainMBean domain;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static RemoteMigratableServiceCoordinatorImpl remoteCoordinator;
   Map taskMap = new ConcurrentHashMap();
   private static final AuthenticatedSubject KERNELID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public MigratableServiceCoordinatorRuntime() throws ManagementException {
      super("the-MigratableServiceCoordinator");

      try {
         this.domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         remoteCoordinator = new RemoteMigratableServiceCoordinatorImpl(this);
      } catch (NamingException var2) {
         throw new ManagementException("Failed to create remote migratable service coordinator", var2);
      }
   }

   public boolean isSystemTask() {
      return this.sysTask;
   }

   public void setSystemTask(boolean sys) {
      this.sysTask = sys;
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   public void migrateSingleton(SingletonServiceMBean service, ServerMBean destination) throws MigrationException {
      ServerMBean[] servers = service.getAllCandidateServers();
      SingletonMonitorRemote smr = null;
      DomainAccess runtime = ManagementService.getDomainAccess(kernelId);

      for(int i = 0; i < servers.length; ++i) {
         String state = runtime.lookupServerLifecycleRuntime(servers[i].getName()).getState();
         if ("RUNNING".equals(state)) {
            Context ctx = null;
            Environment env = new Environment();

            try {
               String url = getURLManagerService().findAdministrationURL(servers[i].getName());
               env.setProviderUrl(url);
               ctx = env.getInitialContext();
               smr = (SingletonMonitorRemote)ctx.lookup("weblogic.cluster.singleton.SingletonMonitorRemote");
            } catch (NamingException var13) {
            } catch (UnknownHostException var14) {
            }
         }
      }

      if (smr == null) {
         throw new MigrationException("No candidate server for " + service + " is reachable. The service cannot be migrated until at least one is up.");
      } else {
         try {
            if (!smr.migrate(service.getName(), destination.getName())) {
               throw new MigrationException("Failed to migrate " + service.getName());
            } else {
               ServerMBean ups = service.getUserPreferredServer();
               if (ups != null && !ups.getName().equals(destination.getName())) {
                  this.changeMigratableTargetsConfiguration(service.getName(), destination.getName());
               }

            }
         } catch (weblogic.cluster.migration.MigrationException var11) {
            throw new MigrationException(var11.getMessage());
         } catch (RemoteException var12) {
            throw new MigrationException("Error while communicating with the Singleton Monitor: " + var12, var12);
         }
      }
   }

   public void migrate(MigratableTargetMBean migratableTarget, ServerMBean destination) throws MigrationException {
      this.doMigrate(migratableTarget, destination, true, true, false);
   }

   public boolean migrate(String name, String destination) throws RemoteException {
      try {
         this.migrate(this.domain.lookupMigratableTarget(name), this.domain.lookupServer(destination));
         return true;
      } catch (MigrationException var4) {
         throw new RemoteException("Error: " + var4, var4);
      }
   }

   public void migrate(MigratableTargetMBean migratableTarget, ServerMBean destination, boolean sourceUp, boolean destinationUp) throws MigrationException {
      this.doMigrate(migratableTarget, destination, !sourceUp, !destinationUp, false);
   }

   public boolean migrate(String name, String destination, boolean sourceUp, boolean destinationUp) throws RemoteException {
      try {
         this.migrate(this.domain.lookupMigratableTarget(name), this.domain.lookupServer(destination), sourceUp, destinationUp);
         return true;
      } catch (MigrationException var6) {
         throw new RemoteException("Error: " + var6, var6);
      }
   }

   public void migrateJTA(MigratableTargetMBean migratableTarget, ServerMBean destination, boolean sourceUp, boolean destinationUp) throws MigrationException {
      this.doMigrate(migratableTarget, destination, !sourceUp, !destinationUp, true);
   }

   public boolean migrateJTA(String name, String destination, boolean sourceUp, boolean destinationUp) throws RemoteException {
      try {
         ServerMBean[] servers = this.domain.getServers();
         MigratableTargetMBean bean = null;

         for(int i = 0; i < servers.length; ++i) {
            if (servers[i].getJTAMigratableTarget() != null && name.equals(servers[i].getJTAMigratableTarget().getName())) {
               bean = servers[i].getJTAMigratableTarget();
               break;
            }
         }

         this.migrateJTA((MigratableTargetMBean)bean, (ServerMBean)this.domain.lookupServer(destination), sourceUp, destinationUp);
         return true;
      } catch (MigrationException var8) {
         throw new RemoteException("Error: " + var8, var8);
      }
   }

   public MigrationTaskRuntimeMBean startMigrateTask(MigratableTargetMBean migratableTarget, ServerMBean destination, boolean jta) throws ManagementException {
      MigrationTask task = new MigrationTask(migratableTarget, destination, jta, false, false, this);
      this.taskMap.put(task.getName(), task);
      task.run();
      return task;
   }

   public void deactivateJTATarget(MigratableTargetMBean migratableTarget, String host) throws MigrationException {
      try {
         PersistentMap map = this.getStoreMap();
         String currentHost = (String)map.get(migratableTarget.getName());
         if (currentHost != null && !currentHost.equals(host)) {
            RemoteMigrationControl control = this.getMigrationControl(host);
            control.deactivateTarget(migratableTarget.getName(), host);
         }
      } catch (PersistentStoreException var6) {
         throw new MigrationException("Unexpected exception opening store ", var6);
      } catch (RemoteException var7) {
         throw new MigrationException("Failed to deactivate " + migratableTarget.getName() + " on " + host, var7);
      } catch (weblogic.cluster.migration.MigrationException var8) {
         throw new MigrationException("Failed to deactivate " + migratableTarget.getName() + " on " + host, var8);
      }
   }

   private RemoteMigrationControl getMigrationControl(String server) throws MigrationException {
      Context ctx = null;

      try {
         Environment env = new Environment();
         env.setProviderUrl(getURLManagerService().findAdministrationURL(server));
         ctx = env.getInitialContext();
         return (RemoteMigrationControl)ctx.lookup("weblogic.cluster.migrationControl");
      } catch (UnknownHostException var4) {
         throw new MigrationException("Failed to reach " + server + " to deactivate JTAMigratableTarget", var4);
      } catch (NamingException var5) {
         throw new MigrationException("Unexpected naming exception", var5);
      }
   }

   public MigrationTaskRuntimeMBean startMigrateTask(MigratableTargetMBean migratableTarget, ServerMBean destination, boolean jta, boolean sourceDown, boolean destinationDown) throws ManagementException {
      MigrationTask task = new MigrationTask(migratableTarget, destination, jta, sourceDown, destinationDown, this);
      this.taskMap.put(task.getName(), task);
      task.run();
      return task;
   }

   public void driveMigrateTaskToEnd(String taskName, boolean sourceDown, boolean destinationDown) throws ManagementException {
      MigrationTaskRuntimeMBean task = this.getTaskRuntimeMBean(taskName);
      this.driveMigrateTaskToEnd(task, sourceDown, destinationDown);
   }

   private void doMigrate(MigratableTargetMBean migratableTarget, ServerMBean destination, boolean sourceDown, boolean destinationDown, boolean jta) throws MigrationException {
      try {
         MigrationTaskRuntimeMBean task = this.startMigrateTask(migratableTarget, destination, jta, sourceDown, destinationDown);
         this.driveMigrateTaskToEnd(task, sourceDown, destinationDown);
         if (task.getError() != null) {
            if (task.getError() instanceof MigrationException) {
               throw (MigrationException)task.getError();
            } else {
               throw new MigrationException(task.getError());
            }
         }
      } catch (ManagementException var7) {
         throw new MigrationException(var7);
      }
   }

   private void driveMigrateTaskToEnd(MigrationTaskRuntimeMBean task, boolean sourceDown, boolean destinationDown) throws ManagementException {
      while(task.isRunning()) {
         try {
            Thread.currentThread();
            Thread.sleep(1000L);
         } catch (InterruptedException var5) {
         }
      }

      if (!task.isTerminal()) {
         Debug.assertion(task.isWaitingForUser());
         if (task.getStatusCode() == 3) {
            task.continueWithSourceServerDown(sourceDown);
         } else if (task.getStatusCode() == 4) {
            task.continueWithDestinationServerDown(destinationDown);
         }

         this.driveMigrateTaskToEnd(task, sourceDown, destinationDown);
      }

   }

   void updateState(String targetName, String location) throws MigrationException {
      try {
         PersistentMap map = this.getStoreMap();
         map.put(targetName, location);
      } catch (PersistentStoreException var4) {
         throw new MigrationException("Failed to update store", var4);
      }
   }

   private PersistentMap getStoreMap() throws PersistentStoreException {
      PersistentStoreManager manager = PersistentStoreManager.getManager();
      PersistentStore store = manager.getDefaultStore();
      return store.createPersistentMap("weblogic_migratable_services_store", DefaultObjectHandler.THE_ONE);
   }

   public MigrationTaskRuntimeMBean[] getMigrationTaskRuntimes() {
      return (MigrationTaskRuntimeMBean[])((MigrationTaskRuntimeMBean[])this.taskMap.values().toArray(new MigrationTaskRuntimeMBean[this.taskMap.size()]));
   }

   public void clearOldMigrationTaskRuntimes() {
      Iterator iter = this.taskMap.values().iterator();

      while(iter.hasNext()) {
         MigrationTaskRuntimeMBean task = (MigrationTaskRuntimeMBean)iter.next();
         if (System.currentTimeMillis() - task.getEndTime() > 1800000L) {
            this.taskMap.remove(task.getName());
         }
      }

   }

   private MigrationTaskRuntimeMBean getTaskRuntimeMBean(String name) {
      return (MigrationTaskRuntimeMBean)this.taskMap.get(name);
   }

   synchronized void changeMigratableTargetsConfiguration(String source, String userPreferredServer) throws MigrationException {
      try {
         DomainMBean domain = null;
         EditAccess editAccess = ManagementServiceRestricted.getEditAccess(KERNELID);
         boolean saveChanges = false;
         if (editAccess.getEditor() != null && !editAccess.isEditorExclusive()) {
            domain = editAccess.getDomainBeanWithoutLock();
            saveChanges = false;
         } else {
            domain = editAccess.startEdit(120000, 120000);
            saveChanges = true;
         }

         if (userPreferredServer != null) {
            ServerMBean upsSrvr = getServer(domain, userPreferredServer);
            MigratableTargetMBean migTarget = getMigratableTarget(domain, source);
            if (migTarget != null) {
               migTarget.setUserPreferredServer(upsSrvr);
            } else {
               SingletonServiceMBean singletonService = domain.lookupSingletonService(source);
               if (singletonService != null) {
                  singletonService.setUserPreferredServer(upsSrvr);
               }
            }
         }

         if (saveChanges) {
            ActivateTask activateTask = null;
            boolean var17 = false;

            try {
               var17 = true;
               editAccess.saveChanges();
               activateTask = editAccess.activateChangesAndWaitForCompletion(120000L);
               var17 = false;
            } finally {
               if (var17) {
                  if (activateTask != null && activateTask.getState() != 4) {
                     editAccess.cancelEdit();
                     String error = activateTask.getError() != null ? activateTask.getError().toString() : "Failed to update config";
                     throw new MigrationException(error, activateTask.getError());
                  }

               }
            }

            if (activateTask != null && activateTask.getState() != 4) {
               editAccess.cancelEdit();
               String error = activateTask.getError() != null ? activateTask.getError().toString() : "Failed to update config";
               throw new MigrationException(error, activateTask.getError());
            }
         }

      } catch (EditNotEditorException var19) {
         throw new MigrationException("Failed to update config", var19);
      } catch (EditFailedException var20) {
         throw new MigrationException("Failed to update config", var20);
      } catch (EditChangesValidationException var21) {
         throw new MigrationException("Failed to update config", var21);
      } catch (EditSaveChangesFailedException var22) {
         throw new MigrationException("Failed to update config", var22);
      } catch (EditWaitTimedOutException var23) {
         throw new MigrationException("Failed to update config", var23);
      }
   }

   private static MigratableTargetMBean getMigratableTarget(DomainMBean domain, String name) {
      MigratableTargetMBean[] migratables = domain.getMigratableTargets();
      if (migratables != null) {
         for(int i = 0; i < migratables.length; ++i) {
            if (migratables[i].getName().equals(name)) {
               return migratables[i];
            }
         }
      }

      return null;
   }

   private static ServerMBean getServer(DomainMBean domain, String name) {
      ServerMBean[] servers = domain.getServers();

      for(int i = 0; i < servers.length; ++i) {
         if (servers[i].getName().equals(name)) {
            return servers[i];
         }
      }

      return null;
   }
}
