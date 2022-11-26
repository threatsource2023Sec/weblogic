package weblogic.cluster.migration;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.naming.Context;
import javax.naming.NamingException;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterExtensionLogger;
import weblogic.cluster.ClusterService;
import weblogic.cluster.migration.DynamicLoadbalancer.State;
import weblogic.cluster.singleton.MemberLivenessDetector;
import weblogic.cluster.singleton.SingletonMonitorRemote;
import weblogic.cluster.singleton.SingletonServicesManagerService;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.jndi.Environment;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JTAMigratableTargetMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.internal.DynamicMBeanProcessor;
import weblogic.protocol.URLManagerService;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.Debug;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.utils.collections.FilteringIterator;
import weblogic.utils.collections.Iterators;
import weblogic.work.WorkManagerFactory;

@Service
@Named
@RunLevel(10)
public final class MigrationManagerService extends AbstractServerService implements RemoteMigrationControl, MigrationManager {
   private static DebugLogger dynLogger = DebugLogger.getDebugLogger("DebugDynamicSingletonServices");
   @Inject
   @Named("PrimordialClusterLeaderService")
   private ServerService dependencyOnPrimordialClusterLeaderService;
   @Inject
   @Named("MigratableServerService")
   private ServerService dependencyOnMigratableServerService;
   @Inject
   @Named("RemoteBinderFactoryService")
   private ServerService dependencyOnRemoteBinderFactoryService;
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnRemoteNamingService;
   @Inject
   @Named("ClusterServiceActivator")
   private ServerService dependencyOnClusterServiceActivator;
   @Inject
   private RuntimeAccess runtimeAccess;
   private MigratableGroupConfigManager configManager;
   private static MigrationManagerService singleton;
   private static final boolean GRACEFUL_SHUTDOWN = true;
   @Inject
   private Provider memberLivenessDetectorProvider;
   private Map groups;
   private Map migratableToGroup;
   private Map nameToGroup;
   private static int state = 0;
   private Map activeTargets;
   private static final long TIMEOUT = 300000L;
   private boolean migrating = false;
   private long timestamp = 0L;
   private boolean memberDeathDetectorHeartbeatReceiverEnabled = false;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private Map dlb2migratableGroups;
   private Map configName2PartitionMap;

   public static MigrationManagerService singleton() {
      return singleton;
   }

   public MigrationManagerService() {
      Debug.assertion(singleton == null);
      this.groups = new ConcurrentHashMap();
      this.migratableToGroup = new ConcurrentHashMap();
      this.nameToGroup = new ConcurrentHashMap();
      this.activeTargets = new ConcurrentHashMap();
      this.dlb2migratableGroups = new IdentityHashMap();
      this.configName2PartitionMap = new ConcurrentHashMap();
      singleton = this;
   }

   void initialize() {
      ServerMBean serverMBean = this.runtimeAccess.getServer();
      ClusterMBean clusterMBean = serverMBean.getCluster();
      if (clusterMBean != null) {
         Environment env = new Environment();
         env.setReplicateBindings(false);
         env.setCreateIntermediateContexts(true);
         env.setProperty("weblogic.jndi.createUnderSharable", "true");

         try {
            env.getInitialContext().bind("weblogic.cluster.migrationControl", singleton);
         } catch (NamingException var5) {
            throw new AssertionError("Unexpected exception: " + var5);
         }
      }

      this.configManager = new MigratableGroupConfigManager(this.runtimeAccess);
      DynamicMBeanProcessor.getInstance().register(this.configManager);
      state = 3;
   }

   public void start() throws ServiceFailureException {
      try {
         this.initialize();
         this.activateAllTargets();
         if (ClusterService.getClusterServiceInternal().isMemberDeathDetectorEnabled()) {
            ClusterExtensionLogger.logStartingMemberDeathDetectorReceiver();
            ((MemberLivenessDetector)this.memberLivenessDetectorProvider.get()).startDetector();
            this.memberDeathDetectorHeartbeatReceiverEnabled = true;
         }

         state = 2;
      } catch (MigrationException var2) {
         throw new ServiceFailureException(var2);
      }
   }

   public void halt() throws ServiceFailureException {
      try {
         this.deactivateAllTargets(false);
         DynamicMBeanProcessor.getInstance().unregister(this.configManager);
         state = 3;
      } catch (MigrationException var2) {
         throw new ServiceFailureException(var2);
      }
   }

   public void stop() throws ServiceFailureException {
      this.deactivateAllTargets(true);
      DynamicMBeanProcessor.getInstance().unregister(this.configManager);
      if (this.memberDeathDetectorHeartbeatReceiverEnabled) {
         ((MemberLivenessDetector)this.memberLivenessDetectorProvider.get()).stopDetector();
      }

      state = 3;
   }

   public synchronized void setMigrating(boolean b) {
      this.migrating = b;
      if (this.migrating) {
         this.timestamp = System.currentTimeMillis();
      }

   }

   public synchronized boolean isMigrating() {
      if (System.currentTimeMillis() - this.timestamp > 300000L) {
         this.setMigrating(false);
      }

      return this.migrating;
   }

   public MigratableGroup findGroup(String name) {
      return (MigratableGroup)this.groups.get(name);
   }

   public List getMigratableTargetsMarkedNotReadyToActivate() {
      List result = new ArrayList();
      Set keys = this.groups.keySet();
      Iterator itr = keys.iterator();

      while(itr.hasNext()) {
         String targetName = (String)itr.next();
         MigratableGroup group = (MigratableGroup)this.groups.get(targetName);
         if (group.isReady()) {
            result.add(targetName);
         }
      }

      return result;
   }

   public void markMigratableTargetReadyToActivate(String targetName) {
      MigratableGroup group = this.findGroup(targetName);
      if (group != null) {
         group.setReady(true);
      }

   }

   public void register(Migratable migratable, MigratableTargetMBean target) throws MigrationException {
      if (target.isCandidate(this.runtimeAccess.getServer())) {
         this.privateRegister(migratable, (String)null, target);
      }

   }

   public void register(Migratable migratable, MigratableTargetMBean target, boolean mgReady, ReadyListener readyListener) throws MigrationException {
      if (target.isCandidate(this.runtimeAccess.getServer())) {
         this.privateRegister(migratable, (String)null, target, mgReady, readyListener);
      }

   }

   public void register(MigratableRemote migratable, String jndiName, MigratableTargetMBean target) throws MigrationException {
      if (target.isCandidate(this.runtimeAccess.getServer())) {
         this.privateRegister(migratable, jndiName, target);
         this.nameToGroup.put(jndiName, this.migratableToGroup.get(migratable));
      }

   }

   private synchronized void privateRegister(Migratable migratable, String jndiName, MigratableTargetMBean target, boolean mgReady, ReadyListener readyListener) throws MigrationException {
      this.privateRegister((String)null, migratable, jndiName, target, mgReady, readyListener);
   }

   private synchronized void privateRegister(Migratable migratable, String jndiName, MigratableTargetMBean target) throws MigrationException {
      this.privateRegister((String)null, migratable, jndiName, target, true, (ReadyListener)null);
   }

   private synchronized void privateRegister(String partitionName, Migratable migratable, String jndiName, MigratableTargetMBean target) throws MigrationException {
      this.privateRegister(partitionName, migratable, jndiName, target, true, (ReadyListener)null);
   }

   private synchronized void privateRegister(String partitionName, final Migratable migratable, String jndiName, MigratableTargetMBean target, Boolean mgReady, ReadyListener readyListener) throws MigrationException {
      String name = target.getName();
      MigratableGroup group = null;
      if (target.isDynamicallyCreated() || target.isCandidate(this.runtimeAccess.getServer())) {
         PartitionAwareObject.runUnderPartition(partitionName, new Runnable() {
            public void run() {
               migratable.migratableInitialize();
            }
         });
      }

      synchronized(this) {
         group = (MigratableGroup)this.groups.get(name);
         if (group == null) {
            group = new MigratableGroup(target, this.configManager.findMigratableGroupConfig(name), partitionName);
            this.groups.put(name, group);
         }
      }

      if (group.add(migratable, jndiName)) {
         this.migratableToGroup.put(migratable, group);
      }

      group.setReady(mgReady);
      if (!mgReady) {
         group.setReadyListener(readyListener);
      }

      boolean manualPolicy = target.getMigrationPolicy().equals("manual");
      boolean jta = target instanceof JTAMigratableTargetMBean;
      if ((manualPolicy || jta) && target.isManualActiveOn(this.runtimeAccess.getServer()) && !group.isActive() && !group.isLocalRPTriggering()) {
         try {
            group.setReady(true);
            group.activate();
            this.activeTargets.put(name, name);
            if (jta) {
               SingletonServicesManagerService.getInstance().addActiveService(name);
            }
         } catch (MigrationException var12) {
            if (target.getMigrationPolicy() != null && target.getMigrationPolicy().equals("manual")) {
               throw var12;
            }

            if (var12.isFatal()) {
               throw var12;
            }
         }
      }

   }

   public void unregister(Migratable migratable, MigratableTargetMBean target) throws MigrationException {
      MigratableGroup group = (MigratableGroup)this.groups.get(target.getName());
      if (group != null) {
         synchronized(this) {
            if (group.remove(migratable)) {
               String jndiName = group.clearUpJNDIMap(migratable);
               this.migratableToGroup.remove(migratable);
               if (jndiName != null) {
                  this.nameToGroup.remove(jndiName);
               }

               this.activeTargets.remove(target.getName());
            }

         }
      }
   }

   public HostID[] getMigratableHostList(String migratableName) {
      MigratableGroup group = (MigratableGroup)this.nameToGroup.get(migratableName);
      return group != null ? group.getHostList() : null;
   }

   /** @deprecated */
   @Deprecated
   public String[] getMigratableGroupServerList(Migratable mig) {
      return null;
   }

   public int getMigratableState(Migratable migratable) {
      MigratableGroup group = (MigratableGroup)this.migratableToGroup.get(migratable);
      return group != null ? group.getMigratableState() : 0;
   }

   public int getMigratableState(String targetName) {
      MigratableGroup group = (MigratableGroup)this.groups.get(targetName);
      if (group != null) {
         return group.getMigratableState();
      } else {
         return this.activeTargets.get(targetName) != null ? 1 : 0;
      }
   }

   public int getMigratableTargetState(String targetName) {
      return this.getMigratableState(targetName);
   }

   public void restartTarget(String targetName) throws MigrationException {
      MigratableGroup group = (MigratableGroup)this.groups.get(targetName);
      if (group != null) {
         group.restart();
      } else {
         throw new MigrationException("Could not find a migratable target   named " + targetName);
      }
   }

   public void activateTarget(String targetName) throws MigrationException {
      MigratableGroup group = (MigratableGroup)this.groups.get(targetName);
      if (group != null) {
         group.activate();
      }

      this.activeTargets.put(targetName, targetName);
   }

   public void deactivateTarget(String targetName, String destinationName) throws MigrationException {
      MigratableGroup group = (MigratableGroup)this.groups.get(targetName);
      if (group != null) {
         group.deactivate();
      }

      this.activeTargets.remove(targetName);
   }

   public Collection activatedTargets() {
      Iterator i = new FilteringIterator(this.groups.values().iterator()) {
         protected boolean accept(Object o) {
            MigratableGroup g = (MigratableGroup)o;
            return g.getMigratableState() == 2 || g.getMigratableState() == 1;
         }
      };
      ArrayList l = new ArrayList();
      Iterators.addAll(l, i);
      return l;
   }

   private void activateAllTargets() throws MigrationException {
      Iterator it = this.groups.entrySet().iterator();
      MigratableGroup group = null;

      while(it.hasNext()) {
         group = (MigratableGroup)((Map.Entry)it.next()).getValue();
         MigratableTargetMBean target = group.getTarget();
         if (target.isManualActiveOn(this.runtimeAccess.getServer())) {
            group.activate();
            this.activeTargets.put(target.getName(), target.getName());
         }
      }

   }

   private void deactivateAllTargets(boolean gracefulShutdown) throws MigrationException {
      Iterator it = this.groups.entrySet().iterator();
      MigratableGroup group = null;

      while(it.hasNext()) {
         group = (MigratableGroup)((Map.Entry)it.next()).getValue();
         synchronized(group.getActivationLock()) {
            group.shutdown();
            this.activeTargets.remove(group.getTarget().getName());
            if (!group.getTarget().getMigrationPolicy().equals("manual")) {
               SingletonServicesManagerService.getInstance().removeActiveService(group.getName());
            }
         }
      }

   }

   public void handlePriorityShutDownTasks() {
      concurrentPriorityShutdownTasks(this.groups, (String)null);
   }

   public void handlePriorityShutDownTasks(String partitionName) {
      concurrentPriorityShutdownTasks(this.groups, partitionName);
   }

   private void serializePriorityShutdownTasks(Map migratableGroups, String partitionName) {
      Iterator it = migratableGroups.entrySet().iterator();

      while(true) {
         MigratableGroup group;
         do {
            if (!it.hasNext()) {
               return;
            }

            group = (MigratableGroup)((Map.Entry)it.next()).getValue();
         } while(partitionName != null && !partitionName.equals(group.getPartitionName()));

         synchronized(group.getActivationLock()) {
            group.handlePriorityShutDownTasks();
         }
      }
   }

   private static void concurrentPriorityShutdownTasks(Map migratableGroups, String partitionName) {
      if (migratableGroups != null && !migratableGroups.isEmpty()) {
         final CountDownLatch priorityShutdownTasks = new CountDownLatch(migratableGroups.size());
         Iterator it = migratableGroups.entrySet().iterator();

         while(true) {
            while(it.hasNext()) {
               final MigratableGroup group = (MigratableGroup)((Map.Entry)it.next()).getValue();
               if (partitionName != null && !partitionName.equals(group.getPartitionName())) {
                  priorityShutdownTasks.countDown();
               } else {
                  Runnable task = new Runnable() {
                     public void run() {
                        try {
                           synchronized(group.getActivationLock()) {
                              group.handlePriorityShutDownTasks();
                           }
                        } finally {
                           priorityShutdownTasks.countDown();
                        }

                     }
                  };
                  WorkManagerFactory.getInstance().getSystem().schedule(task);
               }
            }

            try {
               priorityShutdownTasks.await();
            } catch (InterruptedException var6) {
            }

            return;
         }
      }
   }

   public void register(MigratableGroupConfig config, DynamicLoadbalancer dlb, Migratable... migratables) throws MigrationException, IllegalArgumentException {
      this.register((String)null, config, dlb, migratables);
   }

   public synchronized void register(String partitionName, MigratableGroupConfig config, DynamicLoadbalancer dlb, Migratable... migratables) throws MigrationException, IllegalArgumentException {
      this.register((ReadyListener)null, true, partitionName, config, dlb, migratables);
   }

   public synchronized void register(ReadyListener readyListener, boolean mgReady, String partitionName, MigratableGroupConfig config, DynamicLoadbalancer dlb, Migratable... migratables) throws MigrationException, IllegalArgumentException {
      if (dynLogger.isDebugEnabled()) {
         p("register automatic service: " + config + PartitionAwareObject.getMessageInPartition(partitionName) + "; DLB:" + dlb + "; migratables number:" + migratables.length);
      }

      this.validateNotNull(config, "MigratableGroupConfig");
      this.validateNotNull(dlb, "DynamicLoadbalancer");
      if (config.isManualService()) {
         throw new IllegalArgumentException("This register method is only for automatic service!");
      } else {
         partitionName = PartitionAwareObject.ensureDomainPartition(partitionName);
         this.validatePartition(partitionName, config.getName(), "register");
         String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, config.getName());
         MigratableTargetMBean mtMBeanFound = this.findMigratableTargetMBean(targetName);
         MigratableTargetMBean mtMBean = null;
         if (mtMBeanFound == null) {
            mtMBean = this.configManager.findOrCreateMigratableTargetMBean(partitionName, config);
            if (dynLogger.isDebugEnabled()) {
               p("register automatic service: " + config.getName() + PartitionAwareObject.getMessageInPartition(partitionName) + ": created MTMBean: " + mtMBean);
            }
         } else {
            mtMBean = mtMBeanFound;
            if (dynLogger.isDebugEnabled()) {
               p("register automatic service: " + config.getName() + PartitionAwareObject.getMessageInPartition(partitionName) + ": found MTMBean: " + mtMBeanFound);
            }
         }

         synchronized(this.dlb2migratableGroups) {
            PartitionAwareDynamicLoadbalancer existPadlbKey = null;
            Iterator var12 = this.dlb2migratableGroups.keySet().iterator();

            while(true) {
               PartitionAwareDynamicLoadbalancer padlbKey;
               if (!var12.hasNext()) {
                  if (existPadlbKey == null) {
                     Map map = new HashMap();
                     padlbKey = new PartitionAwareDynamicLoadbalancer(partitionName, dlb);
                     this.dlb2migratableGroups.put(padlbKey, map);
                     existPadlbKey = padlbKey;
                  }

                  Map map = (Map)this.dlb2migratableGroups.get(existPadlbKey);
                  if (!map.containsKey(targetName)) {
                     map.put(targetName, config);
                  }
                  break;
               }

               padlbKey = (PartitionAwareDynamicLoadbalancer)var12.next();
               DynamicLoadbalancer dlbKey = (DynamicLoadbalancer)padlbKey.getDelegate();
               if (dlbKey == dlb) {
                  if (!padlbKey.isSamePartition(partitionName)) {
                     if (mtMBeanFound == null) {
                        this.configManager.unregisterMigratableGroupConfig(partitionName, config.getName());
                     }

                     throw new IllegalArgumentException("DynamicLoadbalancer " + dlbKey.getName() + "@" + dlbKey.hashCode() + " has been registred under partition " + padlbKey.getPartitionName() + ", therefore can not register again under another partition " + partitionName);
                  }

                  existPadlbKey = padlbKey;
               }

               if (dlbKey != dlb && ((Map)this.dlb2migratableGroups.get(padlbKey)).containsKey(targetName)) {
                  if (mtMBeanFound == null) {
                     this.configManager.unregisterMigratableGroupConfig(partitionName, config.getName());
                  }

                  throw new IllegalArgumentException("MigratableGroupConfig " + config.getName() + PartitionAwareObject.getMessageInPartition(partitionName) + " has been registered with DynamicLoadbalancer " + dlbKey.getName() + "@" + dlbKey.hashCode() + ", therefore can not register again with another DynamicLoadbalancer " + dlb.getName() + "@" + dlb.hashCode());
               }
            }
         }

         this.configName2PartitionMap.put(config.getName(), partitionName);

         try {
            Migratable[] var10 = migratables;
            int var19 = migratables.length;

            for(int var21 = 0; var21 < var19; ++var21) {
               Migratable migratable = var10[var21];
               MigratableGroup group = (MigratableGroup)this.groups.get(targetName);
               if (group == null || !group.contains(migratable)) {
                  if (dynLogger.isDebugEnabled()) {
                     p("registering automatic service: " + migratable + " with " + config.getName() + PartitionAwareObject.getMessageInPartition(partitionName));
                  }

                  this.privateRegister(partitionName, migratable, (String)null, mtMBean, mgReady, readyListener);
               }
            }
         } catch (Throwable var16) {
            this.unregister(partitionName, config, migratables);
            throw var16;
         }

         if (dynLogger.isDebugEnabled()) {
            p("register automatic service: done: " + config.getName() + PartitionAwareObject.getMessageInPartition(partitionName) + "; DLB:" + dlb + "; migratables number:" + migratables.length);
         }

      }
   }

   public void validatePartition(String partitionName, String migratableGroupConfigName, String hint) {
      String previousPartitionName = (String)this.configName2PartitionMap.get(migratableGroupConfigName);
      if (previousPartitionName != null && !PartitionAwareObject.isSamePartition(previousPartitionName, partitionName)) {
         throw new IllegalArgumentException("MigratableGroupConfig " + migratableGroupConfigName + " has been registered under partition " + previousPartitionName + ", therefore can not " + hint + " under another partition " + partitionName);
      }
   }

   public void register(MigratableGroupConfig config, Migratable... migratables) throws IllegalArgumentException, MigrationException {
      this.register((String)null, (MigratableGroupConfig)config, (Migratable[])migratables);
   }

   public synchronized void register(String partitionName, MigratableGroupConfig config, Migratable... migratables) throws IllegalArgumentException, MigrationException {
      if (dynLogger.isDebugEnabled()) {
         p("register manual service: " + config + PartitionAwareObject.getMessageInPartition(partitionName) + "; migratables number:" + migratables.length);
      }

      this.validateNotNull(config, "MigratableGroupConfig");
      if (!config.isManualService()) {
         throw new IllegalArgumentException("This register method is only for manual service!");
      } else {
         partitionName = PartitionAwareObject.ensureDomainPartition(partitionName);
         this.validatePartition(partitionName, config.getName(), "register");
         String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, config.getName());
         MigratableTargetMBean mtMBean = this.configManager.findOrCreateMigratableTargetMBean(partitionName, config);
         if (dynLogger.isDebugEnabled()) {
            p("register manual service: " + config.getName() + PartitionAwareObject.getMessageInPartition(partitionName) + ": found or created MTMBean: " + mtMBean);
         }

         this.configName2PartitionMap.put(config.getName(), partitionName);

         try {
            Migratable[] var6 = migratables;
            int var7 = migratables.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               Migratable migratable = var6[var8];
               MigratableGroup group = (MigratableGroup)this.groups.get(targetName);
               if (group == null || !group.contains(migratable)) {
                  if (dynLogger.isDebugEnabled()) {
                     p("registering manual service: " + migratable + " with " + config.getName() + PartitionAwareObject.getMessageInPartition(partitionName));
                  }

                  this.privateRegister(partitionName, migratable, (String)null, mtMBean);
               }
            }
         } catch (Throwable var11) {
            this.unregister(partitionName, config, migratables);
            throw var11;
         }

         if (dynLogger.isDebugEnabled()) {
            p("register manual service: done: " + config.getName() + PartitionAwareObject.getMessageInPartition(partitionName) + "; migratables number:" + migratables.length);
         }

      }
   }

   public void unregister(MigratableGroupConfig config, Migratable... migratables) throws IllegalArgumentException, MigrationException {
      this.unregister((String)null, config, migratables);
   }

   public synchronized void unregister(String partitionName, MigratableGroupConfig config, Migratable... migratables) throws IllegalArgumentException, MigrationException {
      if (dynLogger.isDebugEnabled()) {
         p("unregister service: " + config + "; migratables number:" + migratables.length);
      }

      this.validateNotNull(config, "MigratableGroupConfig");
      String configName = config.getName();
      partitionName = PartitionAwareObject.ensureDomainPartition(partitionName);
      this.validatePartition(partitionName, config.getName(), "unregister");
      String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, config.getName());
      MigratableTargetMBean mtMBean = this.findMigratableTargetMBean(targetName);
      if (mtMBean == null) {
         if (dynLogger.isDebugEnabled()) {
            p("return - could not find mtMBean; this may be duplicate unregistration; ignore");
         }

      } else {
         if (dynLogger.isDebugEnabled()) {
            p("unregister service: " + configName + ": found MTMBean: " + mtMBean);
         }

         MigratableGroup group = (MigratableGroup)this.groups.get(targetName);
         if (dynLogger.isDebugEnabled()) {
            p("find existing group: " + group);
         }

         if (migratables.length == 0 && group != null) {
            migratables = group.getAllMgratables();
         }

         Migratable[] var8 = migratables;
         int var9 = migratables.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            Migratable migratable = var8[var10];
            if (group != null && group.contains(migratable)) {
               if (dynLogger.isDebugEnabled()) {
                  p("unregistering migratable: " + migratable);
               }

               this.unregister(migratable, mtMBean);
            }
         }

         if (group == null || group.getAllMgratables().length == 0) {
            if (dynLogger.isDebugEnabled()) {
               p("no migratable in group. now cleaning up group: " + group);
            }

            if (group != null) {
               group.destroy();
               this.groups.remove(targetName);
            }

            if (!config.isManualService()) {
               synchronized(this.dlb2migratableGroups) {
                  Set padlbSet = new HashSet();
                  Iterator var17 = this.dlb2migratableGroups.entrySet().iterator();

                  while(var17.hasNext()) {
                     Map.Entry entry = (Map.Entry)var17.next();
                     PartitionAwareDynamicLoadbalancer padlbKey = (PartitionAwareDynamicLoadbalancer)entry.getKey();
                     Map map = (Map)entry.getValue();
                     if (map.containsKey(targetName)) {
                        map.remove(targetName);
                     }

                     if (map.isEmpty()) {
                        if (dynLogger.isDebugEnabled()) {
                           p("DLB " + padlbKey.getDelegate() + " has no group nor Migratable. now cleaning up DLB...");
                        }

                        padlbSet.add(padlbKey);
                     }
                  }

                  var17 = padlbSet.iterator();

                  while(var17.hasNext()) {
                     PartitionAwareDynamicLoadbalancer padlbKey = (PartitionAwareDynamicLoadbalancer)var17.next();
                     this.dlb2migratableGroups.remove(padlbKey);
                  }
               }
            }

            this.configName2PartitionMap.remove(config.getName());
            this.configManager.unregisterMigratableGroupConfig(partitionName, configName);
         }

      }
   }

   private static void p(Object msg) {
      dynLogger.debug("" + msg);
   }

   private static void p(Object msg, Throwable t) {
      dynLogger.debug("" + msg, t);
   }

   private MigratableTargetMBean findMigratableTargetMBean(String targetName) throws MigrationException {
      DomainMBean domain = this.runtimeAccess.getDomain();
      return domain.lookupMigratableTarget(targetName);
   }

   public Map getAllAutomaticDynamicServices() {
      synchronized(this.dlb2migratableGroups) {
         Map clone = new IdentityHashMap(this.dlb2migratableGroups.size());
         Iterator var3 = this.dlb2migratableGroups.keySet().iterator();

         while(var3.hasNext()) {
            PartitionAwareDynamicLoadbalancer padlbKey = (PartitionAwareDynamicLoadbalancer)var3.next();
            Map clonedConfigMap = new HashMap();
            clonedConfigMap.putAll((Map)this.dlb2migratableGroups.get(padlbKey));
            clone.put(padlbKey, clonedConfigMap);
         }

         return clone;
      }
   }

   public MigratableGroupConfigManager getMigratableGroupConfigManager() {
      return this.configManager;
   }

   public DynamicLoadbalancer.ServiceStatus getServiceStatus(String _partitionName, final String migratableGroupConfigName) throws RemoteException, MigrationException, IllegalArgumentException, IllegalStateException {
      this.validateOnClusteredServer();
      this.validateString(migratableGroupConfigName, "MigratableGroupConfig name");
      final String partitionName = PartitionAwareObject.ensureDomainPartition(_partitionName);
      this.validatePartition(partitionName, migratableGroupConfigName, "getServiceStatus");
      final String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, migratableGroupConfigName);
      final MigratableTargetMBean mtMBean = this.findMigratableTargetMBean(targetName);
      if (mtMBean == null) {
         throw new IllegalArgumentException("Could not find registered dynamic service with name " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName));
      } else {
         DomainMBean domain = this.runtimeAccess.getDomain();
         this.validateDynamicService(domain, partitionName, migratableGroupConfigName);
         final Throwable[] errorHolder = new Throwable[1];
         DynamicLoadbalancer.ServiceStatus result = (DynamicLoadbalancer.ServiceStatus)SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
            public DynamicLoadbalancer.ServiceStatus run() {
               try {
                  if ("manual".equals(mtMBean.getMigrationPolicy())) {
                     String ups = mtMBean.getUserPreferredServer().getName();
                     RemoteMigrationControl rmc = MigrationManagerService.this.getRemoteMigrationControl(ups, false, "source");
                     if (rmc == null) {
                        if (MigrationManagerService.dynLogger.isDebugEnabled()) {
                           MigrationManagerService.p("getServiceStatus: could not find RemoteMigrationControl for ups " + ups + ". Assuming it is down.");
                        }

                        return new DynamicLoadbalancer.ServiceStatus(State.INACTIVE_OTHERS, ups);
                     } else {
                        return new DynamicLoadbalancer.ServiceStatus(1 == rmc.getMigratableState(targetName) ? State.ACTIVE : State.INACTIVE_OTHERS, ups);
                     }
                  } else {
                     ClusterMBean cluster = MigrationManagerService.this.runtimeAccess.getServer().getCluster();
                     SingletonMonitorRemote smr = MigrationManagerService.this.getSingletonMonitor(cluster);
                     if (smr == null) {
                        if (MigrationManagerService.dynLogger.isDebugEnabled()) {
                           MigrationManagerService.p("getServiceStatus: Could not find SingletonMonitor for cluster " + cluster.getName());
                        }

                        throw new MigrationException("Failed to find SingletonMonitor for cluster " + cluster.getName() + "!");
                     } else {
                        return smr.getServiceStatus(partitionName, migratableGroupConfigName);
                     }
                  }
               } catch (Throwable var3) {
                  if (MigrationManagerService.dynLogger.isDebugEnabled()) {
                     MigrationManagerService.p("getServiceStatus failed: " + var3, var3);
                  }

                  errorHolder[0] = var3;
                  return null;
               }
            }
         });
         Throwable error = errorHolder[0];
         if (error == null) {
            return result;
         } else {
            this.reThrow("getServiceStatus", error);
            return null;
         }
      }
   }

   private void validateOnClusteredServer() {
      if (this.runtimeAccess.getServer().getCluster() == null) {
         throw new IllegalStateException("This method must be called on server within a cluster.");
      }
   }

   public boolean manualMigrate(String _partitionName, String clusterName, final String migratableGroupConfigName, String sourceServerName, final String destinationServerName, boolean sourceServerUp, boolean destinationServerUp) throws RemoteException, MigrationException, IllegalStateException, IllegalArgumentException {
      this.validateOnAdminServer();
      if (dynLogger.isDebugEnabled()) {
         p("manualMigrate for manual service: begin: clusterName:" + clusterName + "; partitionName:" + _partitionName + "; migratableGroupConfigName:" + migratableGroupConfigName + "; sourceServerName:" + sourceServerName + "; destinationServerName:" + destinationServerName + "; sourceServerUp:" + sourceServerUp + "; destinationServerUp:" + destinationServerUp);
      }

      final String partitionName = PartitionAwareObject.ensureDomainPartition(_partitionName);
      this.validateString(clusterName, "Cluster name");
      this.validateString(migratableGroupConfigName, "MigratableGroupConfig name");
      this.validateString(sourceServerName, "Source Server name");
      this.validateString(destinationServerName, "Destination Server name");
      DomainMBean domain = this.runtimeAccess.getDomain();
      this.validateDynamicService(domain, partitionName, migratableGroupConfigName);
      ClusterMBean cluster = this.getClusterMBean(domain, clusterName);
      this.validateServerInCluster(cluster, sourceServerName, "Source");
      this.validateServerInCluster(cluster, destinationServerName, "Destination");
      final RemoteMigrationControl srmc = this.getRemoteMigrationControl(sourceServerName, sourceServerUp, "source");
      final RemoteMigrationControl drmc = this.getRemoteMigrationControl(destinationServerName, destinationServerUp, "destination");
      Boolean result;
      if (srmc == null && drmc == null) {
         result = false;
      } else {
         result = this.doManualMigrationAction("manualMigrate for manual service " + migratableGroupConfigName, new Callable() {
            public Boolean call() throws Exception {
               if (srmc != null) {
                  if (MigrationManagerService.dynLogger.isDebugEnabled()) {
                     MigrationManagerService.p("manualMigrate for manual service: " + migratableGroupConfigName + " - will deactivating on source server using " + srmc);
                  }

                  srmc.manualDeactivateDynamicService(partitionName, migratableGroupConfigName, destinationServerName);
               }

               if (drmc != null) {
                  if (MigrationManagerService.dynLogger.isDebugEnabled()) {
                     MigrationManagerService.p("manualMigrate for manual service: " + migratableGroupConfigName + " - will activating on destination server using " + drmc);
                  }

                  drmc.manualActivateDynamicService(partitionName, migratableGroupConfigName);
                  return true;
               } else {
                  return false;
               }
            }
         });
      }

      if (dynLogger.isDebugEnabled()) {
         p("manualMigrate for manual service: done: activated:" + result + "; clusterName:" + clusterName + "; partitionName:" + _partitionName + "; migratableGroupConfigName:" + migratableGroupConfigName + "; sourceServerName:" + sourceServerName + "; destinationServerName:" + destinationServerName + "; sourceServerUp:" + sourceServerUp + "; destinationServerUp:" + destinationServerUp);
      }

      return result;
   }

   Boolean doManualMigrationAction(final String hint, final Callable action) throws RemoteException, MigrationException, IllegalStateException, IllegalArgumentException {
      final Throwable[] errorHolder = new Throwable[1];
      Boolean result = (Boolean)SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
         public Boolean run() {
            synchronized(this) {
               Object var3;
               try {
                  while(MigrationManagerService.this.isMigrating()) {
                     this.wait(1000L);
                  }

                  MigrationManagerService.this.setMigrating(true);
                  Boolean var2 = (Boolean)action.call();
                  return var2;
               } catch (Throwable var9) {
                  if (MigrationManagerService.dynLogger.isDebugEnabled()) {
                     MigrationManagerService.p(hint + " failed: " + var9, var9);
                  }

                  errorHolder[0] = var9;
                  var3 = null;
               } finally {
                  MigrationManagerService.this.setMigrating(false);
               }

               return (Boolean)var3;
            }
         }
      });
      Throwable error = errorHolder[0];
      if (error == null) {
         return result;
      } else {
         this.reThrow(hint, error);
         return null;
      }
   }

   private void reThrow(String hint, Throwable error) throws MigrationException, IllegalArgumentException, IllegalStateException, RemoteException, Error {
      if (error instanceof MigrationException) {
         throw (MigrationException)error;
      } else if (error instanceof IllegalArgumentException) {
         throw (IllegalArgumentException)error;
      } else if (error instanceof IllegalStateException) {
         throw (IllegalStateException)error;
      } else if (error instanceof RemoteException) {
         throw (RemoteException)error;
      } else if (error instanceof Exception) {
         throw new MigrationException(hint + " failed: " + error, (Exception)error);
      } else {
         throw (Error)error;
      }
   }

   private ClusterMBean getClusterMBean(DomainMBean domain, String clusterName) {
      ClusterMBean cluster = domain.lookupCluster(clusterName);
      if (cluster == null) {
         throw new IllegalArgumentException("Not a valid cluster: " + clusterName);
      } else {
         return cluster;
      }
   }

   private void validateOnAdminServer() {
      if (!this.runtimeAccess.isAdminServer()) {
         throw new IllegalStateException("This method is allowed on AdminSerer only.");
      }
   }

   private void validateString(String clusterName, String hint) {
      if (clusterName == null || clusterName.trim().isEmpty()) {
         throw new IllegalArgumentException(hint + " must not be null nor empty.");
      }
   }

   private void validateNotNull(Object obj, String hint) {
      if (obj == null) {
         throw new IllegalArgumentException(hint + " must not be null.");
      }
   }

   private void validateDynamicService(DomainMBean domain, String partitionName, String migratableGroupConfigName) {
      String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, migratableGroupConfigName);
      MigratableTargetMBean mtMBean = domain.lookupMigratableTarget(targetName);
      if (mtMBean != null && !mtMBean.isDynamicallyCreated()) {
         throw new IllegalArgumentException("Service " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName) + " must be a dynamic service, but it was found be a statistically configured MigratableTargetMBean.");
      }
   }

   private void validateServerInCluster(ClusterMBean cluster, String serverName, String hint) {
      if (!cluster.getServerNames().contains(serverName)) {
         throw new IllegalArgumentException(hint + " server " + serverName + " does not exist in cluster " + cluster.getName());
      }
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private RemoteMigrationControl getRemoteMigrationControl(String server, boolean serverUp, String hint) throws MigrationException {
      String url = null;

      try {
         url = getURLManagerService().findAdministrationURL(server);
         if (url == null) {
            if (dynLogger.isDebugEnabled()) {
               p("getRemoteMigrationControl: for " + hint + " server " + server + "; up:" + serverUp + " - failed: can not findAdministrationURL");
            }

            throw new MigrationException("Failed to find AdministrationURL of " + hint + " server " + server);
         }
      } catch (UnknownHostException var21) {
         if (dynLogger.isDebugEnabled()) {
            p("getRemoteMigrationControl: for " + hint + " server " + server + "; up:" + serverUp + " - failed:" + var21);
         }

         if (serverUp) {
            throw new MigrationException("Could not connect to " + hint + " server " + server + ". please ensure it is up and reachable: " + var21);
         }

         return null;
      }

      Environment env = new Environment();
      Context ctx = null;

      RemoteMigrationControl var8;
      try {
         try {
            env.setProviderUrl(url);
            ctx = env.getInitialContext();
            RemoteMigrationControl rmc = (RemoteMigrationControl)ctx.lookup("weblogic.cluster.migrationControl");
            if (dynLogger.isDebugEnabled()) {
               p("getRemoteMigrationControl: for " + hint + " server " + server + "; up:" + serverUp + " - found RemoteMigrationControl: " + rmc);
            }

            var8 = rmc;
            return var8;
         } catch (NamingException var19) {
            if (dynLogger.isDebugEnabled()) {
               p("getRemoteMigrationControl: for " + hint + " server " + server + "; up:" + serverUp + " - failed to find RemoteMigrationControl: " + var19);
            }
         }

         if (serverUp) {
            throw new MigrationException("Failed to look up RemoteMigrationControl on " + hint + " server " + server + ". please ensure it is up and reachable: " + var19);
         }

         var8 = null;
      } finally {
         if (ctx != null) {
            try {
               ctx.close();
            } catch (NamingException var18) {
            }
         }

      }

      return var8;
   }

   public void manualMigrate(String _partitionName, String clusterName, final String migratableGroupConfigName, final String destinationServerName) throws RemoteException, MigrationException, IllegalStateException, IllegalArgumentException {
      this.validateOnAdminServer();
      if (dynLogger.isDebugEnabled()) {
         p("manualMigrate for automatic service: begin: clusterName:" + clusterName + "; partitionName:" + _partitionName + "; migratableGroupConfigName:" + migratableGroupConfigName + "; destinationServerName:" + destinationServerName);
      }

      final String partitionName = PartitionAwareObject.ensureDomainPartition(_partitionName);
      this.validateString(clusterName, "Cluster name");
      this.validateString(migratableGroupConfigName, "MigratableGroupConfig name");
      this.validateString(destinationServerName, "Destination Server name");
      DomainMBean domain = this.runtimeAccess.getDomain();
      this.validateDynamicService(domain, partitionName, migratableGroupConfigName);
      ClusterMBean cluster = this.getClusterMBean(domain, clusterName);
      this.validateServerInCluster(cluster, destinationServerName, "Destination");
      final SingletonMonitorRemote smr = this.getSingletonMonitor(cluster);
      if (smr == null) {
         if (dynLogger.isDebugEnabled()) {
            p("manualMigrate: Could not find SingletonMonitor for cluster " + clusterName);
         }

         throw new MigrationException("Failed to find SingletonMonitor for cluster " + clusterName + "! please ensure at least one server in cluster is running and reachable.");
      } else {
         if (dynLogger.isDebugEnabled()) {
            p("manualMigrate: will do manual migration for automatic service " + migratableGroupConfigName + " in cluster " + clusterName + " using " + smr + " ...");
         }

         this.doManualMigrationAction("manualMigrate for automatic service " + migratableGroupConfigName, new Callable() {
            public Boolean call() throws Exception {
               smr.manualMigrateDynamicService(partitionName, migratableGroupConfigName, destinationServerName);
               return true;
            }
         });
         if (dynLogger.isDebugEnabled()) {
            p("manualMigrate for automatic service: done: clusterName:" + clusterName + "; partitionName:" + _partitionName + "; migratableGroupConfigName:" + migratableGroupConfigName + "; destinationServerName:" + destinationServerName);
         }

      }
   }

   private SingletonMonitorRemote getSingletonMonitor(ClusterMBean cluster) {
      int timeout = 0;
      ServerMBean[] servers = new ServerMBean[0];
      if (cluster != null) {
         timeout = cluster.getSingletonServiceRequestTimeout();
         servers = cluster.getServers();
      }

      if (dynLogger.isDebugEnabled()) {
         p("getSingletonMonitor: JNDI request timeout: " + timeout);
      }

      SingletonMonitorRemote smr = null;

      for(int i = 0; i < servers.length; ++i) {
         smr = this.getSingletonMonitorRemote(servers[i].getName(), (long)timeout);
         if (smr != null) {
            return smr;
         }
      }

      return null;
   }

   private SingletonMonitorRemote getSingletonMonitorRemote(String server, long timeout) {
      Environment env = null;
      Context ctx = null;

      SingletonMonitorRemote var7;
      try {
         String url = getURLManagerService().findAdministrationURL(server);
         if (url != null) {
            if (dynLogger.isDebugEnabled()) {
               p("getSingletonMonitorRemote:" + server + ": findAdministrationURL: " + url);
            }

            env = createJNDIEnvironment(url, timeout);
            if (dynLogger.isDebugEnabled()) {
               p("getSingletonMonitorRemote:" + server + ": JNDI env: " + env);
            }

            ctx = env.getInitialContext();
            var7 = (SingletonMonitorRemote)ctx.lookup("weblogic/cluster/singleton/SingletonMonitorRemote");
            return var7;
         }

         if (dynLogger.isDebugEnabled()) {
            p("getSingletonMonitorRemote:" + server + ": failed to findAdministrationURL");
         }

         var7 = null;
         return var7;
      } catch (NamingException var20) {
         if (dynLogger.isDebugEnabled()) {
            p("getSingletonMonitorRemote:" + server + ": failed:" + var20);
         }

         var7 = null;
      } catch (UnknownHostException var21) {
         if (dynLogger.isDebugEnabled()) {
            p("getSingletonMonitorRemote:" + server + ": failed:" + var21);
         }

         var7 = null;
         return var7;
      } finally {
         if (ctx != null) {
            try {
               ctx.close();
            } catch (NamingException var19) {
            }
         }

      }

      return var7;
   }

   private static Environment createJNDIEnvironment(String url, long timeout) {
      Environment env = new Environment();
      env.setProviderUrl(url);
      if (timeout >= 0L) {
         env.setConnectionTimeout(timeout);
         env.setResponseReadTimeout(timeout);
      }

      return env;
   }

   public void manualActivateDynamicService(String partitionName, String migratableGroupConfigName) throws RemoteException, MigrationException, IllegalArgumentException {
      if (dynLogger.isDebugEnabled()) {
         p("manualActivateDynamicTarget: begin: service: " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName));
      }

      this.validateManualDynamicService(partitionName, migratableGroupConfigName, "manualActivateDynamicTarget");
      this.validatePartition(partitionName, migratableGroupConfigName, "manually activate");
      String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, migratableGroupConfigName);
      this.activateTarget(targetName);
      if (dynLogger.isDebugEnabled()) {
         p("manualActivateDynamicTarget: done: service: " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName));
      }

   }

   public void manualDeactivateDynamicService(String partitionName, String migratableGroupConfigName, String destinationServer) throws RemoteException, MigrationException, IllegalArgumentException {
      if (dynLogger.isDebugEnabled()) {
         p("manualDeactivateDynamicService: begin service: " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName) + "; destinationServer: " + destinationServer);
      }

      this.validateManualDynamicService(partitionName, migratableGroupConfigName, "manualDeactivateDynamicService");
      this.validatePartition(partitionName, migratableGroupConfigName, "manually deactivate");
      String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, migratableGroupConfigName);
      this.deactivateTarget(targetName, destinationServer);
      if (dynLogger.isDebugEnabled()) {
         p("manualDeactivateDynamicService: done: service: " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName) + "; destinationServer: " + destinationServer);
      }

   }

   private void validateManualDynamicService(String partitionName, String migratableGroupConfigName, String hint) throws IllegalArgumentException {
      String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, migratableGroupConfigName);
      MigratableTargetMBean mtMBean = this.findMigratableTargetMBean(targetName);
      String msg;
      if (mtMBean == null) {
         msg = "MigratableGroupConfig " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName) + " does not registered on server " + this.runtimeAccess.getServerName() + ".";
         if (dynLogger.isDebugEnabled()) {
            p(hint + ": error: " + msg);
         }

         throw new IllegalArgumentException(msg);
      } else if (!mtMBean.isDynamicallyCreated()) {
         msg = "Service " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName) + " must be registered as a dynamic service and must not be statistically configured MigratableTargetMBeanon on server " + this.runtimeAccess.getServerName() + ".";
         if (dynLogger.isDebugEnabled()) {
            p(hint + ": error: " + msg);
         }

         throw new IllegalArgumentException(msg);
      } else if (!"manual".equals(mtMBean.getMigrationPolicy())) {
         msg = "MigratableGroupConfig " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName) + " must be manual service on server " + this.runtimeAccess.getServerName() + ".";
         if (dynLogger.isDebugEnabled()) {
            p(hint + ": error: " + msg);
         }

         throw new IllegalArgumentException(msg);
      } else {
         MigratableGroup group = (MigratableGroup)this.groups.get(targetName);
         if (group == null) {
            String msg = "MigratableGroupConfig " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName) + " does not registered correctly on server " + this.runtimeAccess.getServerName() + ".";
            if (dynLogger.isDebugEnabled()) {
               p(hint + ": error: " + msg);
            }

            throw new IllegalArgumentException(msg);
         }
      }
   }

   public static int getState() {
      return state;
   }

   public boolean isDynamicService(String targetName) {
      if (this.configManager.findMigratableGroupConfig(targetName) != null) {
         return true;
      } else {
         MigratableTargetMBean mtMBean = this.findMigratableTargetMBean(targetName);
         return isDynamicService(mtMBean);
      }
   }

   public static boolean isDynamicService(MigratableTargetMBean mtMBean) {
      return mtMBean != null && mtMBean.isDynamicallyCreated();
   }
}
