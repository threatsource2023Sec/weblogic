package weblogic.management.deploy.internal;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.FatalModuleException;
import weblogic.application.metadatacache.ClassesMetadataEntry;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.DeploymentType;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.internal.targetserver.AppDeployment;
import weblogic.deploy.internal.targetserver.BasicDeployment;
import weblogic.deploy.internal.targetserver.OrderedDeployments;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.deploy.internal.parallel.Bucket;
import weblogic.management.deploy.internal.parallel.BucketInvoker;
import weblogic.management.deploy.internal.parallel.BucketSorter;
import weblogic.management.partition.admin.DomainLevelResourceGroupStateHelperImpl;
import weblogic.management.partition.admin.PartitionResourceGroupStateHelperImpl;
import weblogic.management.partition.admin.ResourceGroupStateHelper;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.management.utils.PartitionUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServerLifecycleException;
import weblogic.server.ServiceFailureException;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.progress.ProgressTrackerRegistrarFactory;
import weblogic.utils.progress.ProgressTrackerService;
import weblogic.utils.progress.ProgressWorkHandle;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

@Service
public class ConfiguredDeployments {
   static final String PSEUDO_DEPLOYMENT_HANDLER = "PseudoDeploymentHandler";
   static final String PSEUDO_RESOURCE_DEPENDENT_DEPLOYMENT_HANDLER = "PseudoResourceDependentDeploymentHandler";
   static final String PSEUDO_STARTUP_CLASS = "PseudoStartupClass";
   private static final String TUNNELING_WEBAPP = "bea_wls_internal";
   private static final AppRuntimeStateManager appRTStateMgr = AppRuntimeStateManager.getManager();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DomainMBean domain;
   private static MultiVersionDeployments multiVersionDeployments;
   private final ResourceGroupStateHelper domainRGStateHelper;
   private final ResourceGroupStateHelper partitionRGStateHelper;
   private static final boolean IS_START_ALL = false;
   @Inject
   private RuntimeAccess runtimeAccess;
   private static Map deploymentWorkHandles;
   private final Set administrativeInternalApps = new HashSet();
   private boolean isParallelPrepareAcrossApplications = true;
   private boolean isParallelActivateAcrossApplications = false;
   private ProgressTrackerService depProgress = null;

   @Inject
   private ConfiguredDeployments(DomainLevelResourceGroupStateHelperImpl domainRGStateHelper, PartitionResourceGroupStateHelperImpl partitionRGStateHelper) {
      this.domainRGStateHelper = domainRGStateHelper;
      this.partitionRGStateHelper = partitionRGStateHelper;
      multiVersionDeployments = new MultiVersionDeployments(domain);
   }

   /** @deprecated */
   @Deprecated
   public static ConfiguredDeployments getConfigureDeploymentsHandler() {
      if (multiVersionDeployments == null) {
         multiVersionDeployments = new MultiVersionDeployments(domain);
      }

      return ConfiguredDeployments.ConfiguredDeploymentsInitializer.INSTANCE;
   }

   private boolean isInternalAppIncluded(BasicDeploymentMBean bdm, String partitionName, InternalAppScope scope, AppTransition transition) {
      if (!PartitionUtils.isPartitionInternalApp(bdm, partitionName)) {
         return false;
      } else if (!PartitionUtils.isEffective(bdm, partitionName)) {
         return false;
      } else {
         boolean admin;
         if (transition == AppTransition.PREPARE) {
            admin = PartitionUtils.isAdministrative(bdm, partitionName);
            if (admin) {
               this.administrativeInternalApps.add(bdm);
            }
         } else {
            admin = this.administrativeInternalApps.contains(bdm);
         }

         if (scope == ConfiguredDeployments.InternalAppScope.ANY || scope == ConfiguredDeployments.InternalAppScope.ADMIN && admin || scope == ConfiguredDeployments.InternalAppScope.NON_ADMIN && !admin) {
            if (transition == AppTransition.UNPREPARE) {
               this.administrativeInternalApps.remove(bdm);
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public MultiVersionDeployments getMultiVersionDeployments() {
      return multiVersionDeployments;
   }

   void deployPreStandbyInternalApps() throws ServiceFailureException {
      try {
         DomainMBean domain = this.runtimeAccess.getDomain();
         AppDeploymentMBean mbean = domain.lookupInternalAppDeployment("bea_wls_internal");
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Deploy preStandby internal app bea_wls_internal, mbean=" + mbean);
         }

         if (mbean != null) {
            BasicDeployment app = OrderedDeployments.getOrCreateBasicDeployment(mbean);
            app.prepare();
            app.activateFromServerLifecycle();
            app.adminToProductionFromServerLifecycle();
         }
      } catch (Exception var4) {
         throw new ServiceFailureException("Cannot deploy internal app bea_wls_internal", var4);
      }
   }

   void undeployPreStandbyInternalApps() throws ServiceFailureException {
      try {
         DomainMBean domain = this.runtimeAccess.getDomain();
         AppDeploymentMBean mbean = domain.lookupInternalAppDeployment("bea_wls_internal");
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("undeploy preStandby internal app bea_wls_internal, mbean=" + mbean);
         }

         if (mbean == null) {
            return;
         }

         BasicDeployment app = OrderedDeployments.getOrCreateBasicDeployment(mbean);
         app.productionToAdminFromServerLifecycle(false);
         app.deactivateFromServerLifecycle();
         app.unprepareFromServerLifecycle();
      } catch (Exception var4) {
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Cannot deploy internal app bea_wls_internal", var4);
         }
      }

   }

   void deploy() throws DeploymentException, ServiceFailureException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.deploy()");
      }

      SlaveDeployerLogger.logSlaveResumeStart();
      this.init();
      DomainMBean domain = this.runtimeAccess.getDomain();
      this.prepare(domain, (Set)null, ConfiguredDeployments.InternalAppScope.ANY);
      this.activate(domain, (Set)null, ConfiguredDeployments.InternalAppScope.ANY);

      try {
         this.domainRGStateHelper.markShutdownAsAdmin();
      } catch (ResourceGroupLifecycleException var3) {
         throw new DeploymentException(var3);
      }
   }

   private void init() throws DeploymentException, ServiceFailureException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.init");
      }

      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.init before processing");
      }

      this.debugOrderedDeploymentList();
      DomainMBean domain = this.runtimeAccess.getDomain();
      this.isParallelPrepareAcrossApplications = Boolean.valueOf(System.getProperty("weblogic.application.ParallelPrepare", "true"));
      this.isParallelActivateAcrossApplications = domain.isParallelDeployApplications();
      BasicDeploymentMBean[] basicDepMBeans = domain.getBasicDeployments();
      Map apps = new HashMap();

      for(int i = 0; i < basicDepMBeans.length; ++i) {
         BasicDeploymentMBean basicDepMBean = basicDepMBeans[i];
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Processing BasicDeploymentMBean: " + basicDepMBean.getName());
         }

         if (this.isRetiredApp(basicDepMBean)) {
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Skipping retired app: " + basicDepMBean);
            }
         } else {
            basicDepMBean = multiVersionDeployments.processStaticDeployment(basicDepMBean);
            BasicDeployment deployment;
            if (TargetHelper.isTargetedLocaly(basicDepMBean)) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("+++ " + basicDepMBean.getName() + " is locally targeted");
               }

               deployment = OrderedDeployments.getOrCreateBasicDeployment(basicDepMBean);
               this.recordIfApp(apps, basicDepMBean, deployment);
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("Added targeted locally BasicDeploymentMBean: " + basicDepMBean.getName());
               }
            } else if (TargetHelper.isPinnedToServerInCluster(basicDepMBean) && !(basicDepMBean instanceof SystemResourceMBean)) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("+++ " + basicDepMBean.getName() + " is pinned to other servers in cluster");
               }

               deployment = OrderedDeployments.getOrCreateBasicDeployment(basicDepMBean);
               this.recordIfApp(apps, basicDepMBean, deployment);
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("Added pinned to server BasicDeploymentMBean: " + basicDepMBean.getName());
               }
            }
         }
      }

      this.checkAndImplicitlyRetireApps(apps);
      OrderedDeployments.addDeployment(DeploymentType.PSEUDO_DEPLOYMENT_HANDLER_MBEAN, "PseudoDeploymentHandler");
      OrderedDeployments.addDeployment(DeploymentType.PSEUDO_RESOURCE_DEPENDENT_DEP_HANDLER_MBEAN, "PseudoResourceDependentDeploymentHandler");
      OrderedDeployments.addDeployment(DeploymentType.PSEUDO_STARTUP_CLASS_MBEAN, "PseudoStartupClass");
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.init after processing");
      }

      this.debugOrderedDeploymentList();
   }

   public void updateMultiVersionConfiguration(DomainMBean proposedDomain) {
      multiVersionDeployments.processDynamicDeployment(proposedDomain);
   }

   private String getAppIdSansVersion(AppDeploymentMBean mbean) {
      String applicationName = mbean.getApplicationName();
      String partitionName = ApplicationVersionUtils.getPartitionName(mbean.getApplicationIdentifier());
      return ApplicationVersionUtils.getApplicationId(applicationName, (String)null, partitionName);
   }

   private void recordIfApp(Map apps, BasicDeploymentMBean basicDepMBean, BasicDeployment basicDeployment) {
      if (basicDeployment instanceof AppDeployment && basicDepMBean instanceof AppDeploymentMBean && !(basicDepMBean instanceof LibraryMBean)) {
         AppDeploymentMBean appDepMBean = (AppDeploymentMBean)basicDepMBean;
         AppDeployment appDeployment = (AppDeployment)basicDeployment;
         String key = this.getAppIdSansVersion(appDepMBean);
         List internalList = (List)apps.get(key);
         if (internalList == null) {
            internalList = new ArrayList();
            apps.put(key, internalList);
         }

         ((List)internalList).add(appDeployment);
      }

   }

   private void resetCaches() {
      ClassesMetadataEntry.clearCheckedStaleCache();
   }

   void prepare(ConfigurationMBean scopeMBean, Set resourceGroups, InternalAppScope internalAppScope) throws DeploymentException, ServiceFailureException {
      long startTime = System.currentTimeMillis();
      boolean var12 = false;

      try {
         var12 = true;
         this.resetCaches();
         this.progressTrackerStartWork(scopeMBean);
         if (this.isParallelPrepareAcrossApplications) {
            this.transitionAppsParallel(AppTransition.PREPARE, scopeMBean, resourceGroups, internalAppScope, false);
            var12 = false;
         } else {
            this.transitionApps(AppTransition.PREPARE, scopeMBean, resourceGroups, internalAppScope);
            var12 = false;
         }
      } finally {
         if (var12) {
            long endTime = System.currentTimeMillis();
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Total PREPARE deployments: " + (endTime - startTime));
            }

         }
      }

      long endTime = System.currentTimeMillis();
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Total PREPARE deployments: " + (endTime - startTime));
      }

   }

   void activate(ConfigurationMBean scopeMBean, Set resourceGroups, InternalAppScope internalAppScope) throws DeploymentException, ServiceFailureException {
      long startTime = System.currentTimeMillis();
      boolean var12 = false;

      try {
         var12 = true;
         if (this.isParallelActivateAcrossApplications) {
            this.transitionAppsParallel(AppTransition.ACTIVATE, scopeMBean, resourceGroups, internalAppScope);
         } else {
            this.transitionApps(AppTransition.ACTIVATE, scopeMBean, resourceGroups, internalAppScope);
         }

         this.progressTrackerCompleteWork(scopeMBean);
         var12 = false;
      } finally {
         if (var12) {
            long endTime = System.currentTimeMillis();
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Total ACTIVATE deployments: " + (endTime - startTime));
            }

         }
      }

      long endTime = System.currentTimeMillis();
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("Total ACTIVATE deployments: " + (endTime - startTime));
      }

   }

   void adminToProduction() throws DeploymentException, ServiceFailureException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.adminToProduction()");
      }

      DomainMBean domain = this.runtimeAccess.getDomain();
      this.transitionApps(AppTransition.ADMIN_TO_PRODUCTION, domain, (Set)null, ConfiguredDeployments.InternalAppScope.ANY);
      if (multiVersionDeployments.isEnabled()) {
         multiVersionDeployments.startPolling();
      }

      try {
         this.domainRGStateHelper.markAdminAsRunning();
      } catch (ResourceGroupLifecycleException var3) {
         throw new DeploymentException(var3);
      }
   }

   void productionToAdmin(boolean graceful) throws DeploymentException, ServiceFailureException {
      if (multiVersionDeployments.isEnabled()) {
         multiVersionDeployments.stopPolling();
      }

      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.productionToAdmin()");
      }

      DomainMBean domain = this.runtimeAccess.getDomain();
      if (graceful) {
         this.transitionApps(AppTransition.GRACEFUL_PRODUCTION_TO_ADMIN, domain, (Set)null, ConfiguredDeployments.InternalAppScope.ANY);
      } else {
         this.transitionApps(AppTransition.FORCE_PRODUCTION_TO_ADMIN, domain, (Set)null, ConfiguredDeployments.InternalAppScope.ANY);
      }

      try {
         this.domainRGStateHelper.markRunningAsAdmin();
      } catch (ResourceGroupLifecycleException var4) {
         throw new DeploymentException(var4);
      }
   }

   void undeploy() throws DeploymentException, ServiceFailureException {
      if (multiVersionDeployments.isEnabled()) {
         multiVersionDeployments.teardown();
      }

      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.undeploy()");
      }

      DomainMBean domain = this.runtimeAccess.getDomain();
      this.deactivate(domain, (Set)null, ConfiguredDeployments.InternalAppScope.ANY);
      this.unprepare(domain, (Set)null, ConfiguredDeployments.InternalAppScope.ANY);

      try {
         this.domainRGStateHelper.markAdminAsShutdown();
      } catch (ResourceGroupLifecycleException var3) {
         throw new DeploymentException(var3);
      }
   }

   private void deactivate(ConfigurationMBean scopeMBean, Set resourceGroups, InternalAppScope internalAppScope) throws DeploymentException, ServiceFailureException {
      this.transitionApps(AppTransition.DEACTIVATE, scopeMBean, resourceGroups, internalAppScope);
   }

   private void unprepare(ConfigurationMBean scopeMBean, Set resourceGroups, InternalAppScope internalAppScope) throws DeploymentException, ServiceFailureException {
      this.transitionApps(AppTransition.UNPREPARE, scopeMBean, resourceGroups, internalAppScope);
   }

   private void removeDeployment(ConfigurationMBean scopeMBean, Set resourceGroups) throws DeploymentException, ServiceFailureException {
      this.transitionApps(AppTransition.REMOVE, scopeMBean, resourceGroups, ConfiguredDeployments.InternalAppScope.ANY);
   }

   public void deploy(PartitionMBean partition, Set resourceGroups, InternalAppScope internalAppScope) throws DeploymentException, ServiceFailureException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.deploy PartitionMBean");
      }

      this.init();
      this.prepare(partition, resourceGroups, internalAppScope);
      this.activate(partition, resourceGroups, internalAppScope);
   }

   public void deploy(ResourceGroupMBean resourceGroup) throws DeploymentException, ServiceFailureException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.deploy ResourceGroupMBean");
      }

      this.init();
      this.prepare(resourceGroup, (Set)null, ConfiguredDeployments.InternalAppScope.ANY);
      this.activate(resourceGroup, (Set)null, ConfiguredDeployments.InternalAppScope.ANY);
   }

   public void adminToProduction(PartitionMBean partition, Set resourceGroups, InternalAppScope internalAppScope) throws DeploymentException, ServiceFailureException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.adminToProduction PartitionMBean");
      }

      this.transitionApps(AppTransition.ADMIN_TO_PRODUCTION, partition, resourceGroups, internalAppScope);
   }

   public void adminToProduction(ResourceGroupMBean resourceGroup) throws DeploymentException, ServiceFailureException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.adminToProduction ResourcegroupMBean");
      }

      this.transitionApps(AppTransition.ADMIN_TO_PRODUCTION, resourceGroup, (Set)null, ConfiguredDeployments.InternalAppScope.ANY);
   }

   public void productionToAdmin(PartitionMBean partition, boolean isGraceful, Set resourceGroups, InternalAppScope internalAppScope) throws DeploymentException, ServiceFailureException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.productionToAdmin PartitionMBean");
      }

      if (isGraceful) {
         this.transitionApps(AppTransition.GRACEFUL_PRODUCTION_TO_ADMIN, partition, resourceGroups, internalAppScope);
      } else {
         this.transitionApps(AppTransition.FORCE_PRODUCTION_TO_ADMIN, partition, resourceGroups, internalAppScope);
      }

   }

   public void productionToAdmin(ResourceGroupMBean resourceGroup, boolean isGraceful) throws DeploymentException, ServiceFailureException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.productionToAdmin ResourcegroupMBean");
      }

      if (isGraceful) {
         this.transitionApps(AppTransition.GRACEFUL_PRODUCTION_TO_ADMIN, resourceGroup, (Set)null, ConfiguredDeployments.InternalAppScope.ANY);
      } else {
         this.transitionApps(AppTransition.FORCE_PRODUCTION_TO_ADMIN, resourceGroup, (Set)null, ConfiguredDeployments.InternalAppScope.ANY);
      }

   }

   public void undeploy(PartitionMBean partition, Set resourceGroups, InternalAppScope internalAppScope) throws DeploymentException, ServiceFailureException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.undeploy PartitionMBean");
      }

      this.deactivate(partition, resourceGroups, internalAppScope);
      this.unprepare(partition, resourceGroups, internalAppScope);
   }

   public void undeploy(ResourceGroupMBean resourceGroup) throws DeploymentException, ServiceFailureException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.undeploy ResourcegroupMBean");
      }

      this.deactivate(resourceGroup, (Set)null, ConfiguredDeployments.InternalAppScope.ANY);
      this.unprepare(resourceGroup, (Set)null, ConfiguredDeployments.InternalAppScope.ANY);
   }

   public void remove(PartitionMBean partition) throws DeploymentException, ServiceFailureException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.remove PartitionMBean");
      }

      this.removeDeployment(partition, (Set)null);
   }

   public void remove(ResourceGroupMBean resourceGroup) throws DeploymentException, ServiceFailureException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In ConfiguredDeployments.remove ResourcegroupMBean");
      }

      this.removeDeployment(resourceGroup, (Set)null);
   }

   Collection sortForParallelTransition(ConfigurationMBean scopeMBean, AppTransition transition, Set resourceGroups, InternalAppScope internalAppScope) throws DeploymentException {
      boolean isTransitionStartup = transition.isStartup();
      return sortForParallelTransition(isTransitionStartup, this.getDeployments(scopeMBean, transition, resourceGroups, internalAppScope), transition == AppTransition.PREPARE);
   }

   static Collection sortForParallelTransition(final boolean isTransitionStartup, Collection deployments, final boolean isPrepareTransition) {
      BucketSorter sorter = new BucketSorter() {
         protected boolean preSort(Object dep, BucketSorter.SortState state, Deque delayedApps) {
            if (ConfiguredDeployments.isPreStandbyApp(dep)) {
               return true;
            } else {
               DeploymentAdapter adapter = ConfiguredDeployments.getDeploymentAdapter(dep);
               if (adapter == null) {
                  return true;
               } else {
                  Object delayed = delayedApps.peekFirst();
                  if (delayed != null && !ConfiguredDeployments.isSameApp(dep, delayed)) {
                     Iterator var6 = delayedApps.iterator();

                     while(var6.hasNext()) {
                        Object d = var6.next();
                        this.sort(d, state);
                     }

                     delayedApps = this.recreateSaved();
                  }

                  if (ConfiguredDeployments.isAppVersion(dep)) {
                     boolean isActiveAppVersion = ConfiguredDeployments.isActiveAppVersion(dep);
                     if (isTransitionStartup && isActiveAppVersion || !isTransitionStartup && !isActiveAppVersion) {
                        if (!ConfiguredDeployments.isAdminMode(dep)) {
                           delayedApps.addFirst(dep);
                        } else {
                           delayedApps.add(dep);
                        }

                        return true;
                     }
                  }

                  return false;
               }
            }
         }

         protected boolean isDeploymentTypeParallelForTransition(DeploymentType type) {
            if (type != null) {
               return isPrepareTransition ? type.isDefaultParallelPrepare() : type.isDefaultParallelActivate();
            } else {
               return false;
            }
         }

         protected boolean usesDeploymentOrderForTransition(DeploymentType type) {
            if (type != null) {
               return isPrepareTransition ? type.usesDeploymentOrderPrepare() : type.usesDeploymentOrderActivate();
            } else {
               return false;
            }
         }

         protected BasicDeploymentMBean getDeploymentMBean(Object dep) {
            return dep instanceof AppDeployment ? ((AppDeployment)dep).getAppDeploymentMBean() : null;
         }
      };
      return sorter.sortForParallelTransition(deployments);
   }

   private String scopeName(ConfigurationMBean scopeMBean) {
      if (scopeMBean instanceof PartitionMBean) {
         return "partition-" + ((PartitionMBean)scopeMBean).getName() + "-";
      } else {
         return scopeMBean instanceof ResourceGroupMBean ? "rg-" + ((ResourceGroupMBean)scopeMBean).getName() + "-" : "domain-";
      }
   }

   private void transitionAppsParallel(AppTransition transition, ConfigurationMBean scopeMBean, Set resourceGroups, InternalAppScope internalAppScope) throws DeploymentException, ServiceFailureException {
      this.transitionAppsParallel(transition, scopeMBean, resourceGroups, internalAppScope, true);
   }

   private void transitionAppsParallel(final AppTransition transition, final ConfigurationMBean scopeMBean, final Set resourceGroups, InternalAppScope internalAppScope, boolean isFailForCluster) throws DeploymentException, ServiceFailureException {
      final Deque failedApps = new ConcurrentLinkedDeque();
      WorkManagerFactory fact = WorkManagerFactory.getInstance();
      String wmName = "wls-internal-parallel-configured-deployments-" + this.scopeName(scopeMBean) + transition.toString().toLowerCase();
      boolean wmCreated = false;

      try {
         Collection buckets = this.sortForParallelTransition(scopeMBean, transition, resourceGroups, internalAppScope);
         int maxParallelBucketItems = 0;
         Iterator var12 = buckets.iterator();

         while(var12.hasNext()) {
            Bucket b = (Bucket)var12.next();
            if (b.isParallel) {
               int count = b.contents.size();
               if (count > maxParallelBucketItems) {
                  maxParallelBucketItems = count;
               }
            }
         }

         WorkManager wm = null;
         if (maxParallelBucketItems > 0) {
            wm = fact.findOrCreate(wmName, maxParallelBucketItems, -1);
            wmCreated = true;
         }

         BucketInvoker invoker = new BucketInvoker() {
            protected void doItem(Object item) throws Exception {
               DeploymentAdapter adapter = ConfiguredDeployments.getDeploymentAdapter(item);
               transition.transitionApp(adapter, item, scopeMBean, resourceGroups);
            }

            protected Throwable handleThrowable(Throwable t, Object item) {
               return ConfiguredDeployments.this.handleAppException(transition, item, t, failedApps);
            }
         };
         invoker.invoke(buckets, wm);
         this.handleFailedApps(failedApps, transition, isFailForCluster);
      } finally {
         if (wmCreated) {
            fact.remove(wmName);
         }

      }

   }

   private void transitionApps(AppTransition transition, ConfigurationMBean scopeMBean, Set resourceGroups, InternalAppScope internalAppScope) throws DeploymentException, ServiceFailureException {
      ArrayList failedApps = new ArrayList();
      ArrayList delayedApps = new ArrayList();
      Iterator iter = this.getAppsIterator(scopeMBean, transition, resourceGroups, internalAppScope);

      while(true) {
         while(true) {
            Object dep;
            DeploymentAdapter adapter;
            do {
               do {
                  if (!iter.hasNext()) {
                     if (!delayedApps.isEmpty()) {
                        this.transitionApps(transition, delayedApps, failedApps, scopeMBean, resourceGroups);
                        delayedApps.clear();
                     }

                     this.handleFailedApps(failedApps, transition);
                     return;
                  }

                  dep = iter.next();
               } while(isPreStandbyApp(dep));

               adapter = getDeploymentAdapter(dep);
            } while(adapter == null);

            if (!delayedApps.isEmpty() && !isSameApp(dep, delayedApps.get(0))) {
               this.transitionApps(transition, delayedApps, failedApps, scopeMBean, resourceGroups);
               delayedApps.clear();
            }

            if (isAppVersion(dep)) {
               boolean isActiveAppVersion = isActiveAppVersion(dep);
               if (transition.isStartup() && isActiveAppVersion || !transition.isStartup() && !isActiveAppVersion) {
                  if (!isAdminMode(dep)) {
                     delayedApps.add(0, dep);
                  } else {
                     delayedApps.add(dep);
                  }
                  continue;
               }
            }

            try {
               transition.transitionApp(adapter, dep, scopeMBean, resourceGroups);
            } catch (Throwable var12) {
               Throwable x = this.handleAppException(transition, dep, var12, failedApps);
               if (x != null) {
                  if (x instanceof DeploymentException) {
                     throw (DeploymentException)x;
                  }

                  if (x instanceof ServiceFailureException) {
                     throw (ServiceFailureException)x;
                  }

                  throw new ServiceFailureException(x);
               }
            }
         }
      }
   }

   private void transitionApps(AppTransition transition, Collection apps, Collection failedApps, ConfigurationMBean scopeMBean, Set resourceGroups) throws DeploymentException, ServiceFailureException {
      Iterator iter = apps.iterator();

      while(true) {
         Object dep;
         DeploymentAdapter adapter;
         do {
            if (!iter.hasNext()) {
               return;
            }

            dep = iter.next();
            adapter = getDeploymentAdapter(dep);
         } while(adapter == null);

         try {
            transition.transitionApp(adapter, dep, scopeMBean, resourceGroups);
         } catch (Throwable var11) {
            Throwable x = this.handleAppException(transition, dep, var11, failedApps);
            if (x != null) {
               if (x instanceof DeploymentException) {
                  throw (DeploymentException)x;
               }

               if (x instanceof ServiceFailureException) {
                  throw (ServiceFailureException)x;
               }

               throw new ServiceFailureException(x);
            }
         }
      }
   }

   private Throwable handleAppException(AppTransition transition, Object dep, Throwable t, Collection failedApps) {
      if (t instanceof ServiceFailureException) {
         return (ServiceFailureException)t;
      } else {
         if (t instanceof FatalModuleException) {
            try {
               this.runtimeAccess.getServerRuntime().abortStartupAfterAdminState();
            } catch (ServerLifecycleException var6) {
               return (FatalModuleException)t;
            }
         }

         if (!(dep instanceof BasicDeployment)) {
            SlaveDeployerLogger.logAppStartupFailed(t);
         }

         if (transition.isStartup()) {
            failedApps.add(dep);
         }

         return null;
      }
   }

   private Iterator getAppsIterator(ConfigurationMBean scopeMBean, AppTransition transition, Set resourceGroups, InternalAppScope internalAppScope) throws DeploymentException {
      boolean isStartup = transition.isStartup();
      final Collection deps = this.getDeployments(scopeMBean, transition, resourceGroups, internalAppScope);
      return isStartup ? deps.iterator() : new Iterator() {
         private ListIterator listIter = (new ArrayList(deps)).listIterator(deps.size());

         public boolean hasNext() {
            return this.listIter.hasPrevious();
         }

         public Object next() {
            return this.listIter.previous();
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   private Collection getDeployments(ConfigurationMBean scopeMBean, AppTransition transition, Set resourceGroups, InternalAppScope internalAppScope) throws DeploymentException {
      Collection deployments = new ArrayList();
      if (scopeMBean instanceof DomainMBean) {
         deployments = this.getDeploymentsForDomain(transition);
      } else if (scopeMBean instanceof ResourceGroupMBean) {
         deployments = getDeploymentsForResourceGroup((ResourceGroupMBean)scopeMBean);
      } else if (scopeMBean instanceof PartitionMBean) {
         deployments = this.getDeploymentsForPartition((PartitionMBean)scopeMBean, transition, resourceGroups, internalAppScope);
      }

      return (Collection)deployments;
   }

   private Collection getDeploymentsForDomain(AppTransition transition) throws DeploymentException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In getDeploymentsForDomain");
      }

      Collection deployments = OrderedDeployments.getDeployments();
      List domainDeployments = new ArrayList();
      Iterator var4 = deployments.iterator();

      while(var4.hasNext()) {
         Object dep = var4.next();
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("Process deployment of OrderededDeployments in getDeploymentsForDomain: " + dep);
         }

         if (dep instanceof BasicDeployment) {
            BasicDeploymentMBean bdm = ((BasicDeployment)dep).getDeploymentMBean();
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Process BasicDeploymentMBean in getDeploymentsForDomain: " + bdm.getName());
            }

            if (!PartitionUtils.isPartitionInternalApp(bdm)) {
               ResourceGroupMBean rgm = AppDeploymentHelper.getResourceGroupMBean(bdm);
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("The parent resource group of the BasicDeploymentMBean: " + bdm.getName() + " is: " + rgm);
               }

               if (rgm == null) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("Adding domain level BasicDeploymentMBean: " + bdm.getName() + " to the domain deployments list");
                  }

                  domainDeployments.add(dep);
               } else if (rgm.getParent() instanceof DomainMBean && this.isRGInRightState(rgm, transition)) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("Adding domain RG level BasicDeploymentMBean: " + bdm.getName() + " to the domain deployments list");
                  }

                  domainDeployments.add(dep);
               }
            }
         } else {
            domainDeployments.add(dep);
         }
      }

      return domainDeployments;
   }

   private boolean isRGInRightState(ResourceGroupMBean rgm, AppTransition transition) throws DeploymentException {
      try {
         if (transition != AppTransition.PREPARE && transition != AppTransition.ACTIVATE) {
            if (transition == AppTransition.ADMIN_TO_PRODUCTION) {
               return rgm.getParent() instanceof DomainMBean ? this.domainRGStateHelper.isDesiredStateRunning(rgm) : true;
            } else if (transition != AppTransition.FORCE_PRODUCTION_TO_ADMIN && transition != AppTransition.GRACEFUL_PRODUCTION_TO_ADMIN) {
               if (transition != AppTransition.UNPREPARE && transition != AppTransition.DEACTIVATE) {
                  return true;
               } else {
                  return rgm.getParent() instanceof PartitionMBean ? this.partitionRGStateHelper.isShuttingDown(rgm) : true;
               }
            } else {
               return rgm.getParent() instanceof PartitionMBean ? this.partitionRGStateHelper.isShuttingDown(rgm) || this.partitionRGStateHelper.isSuspending(rgm) : true;
            }
         } else {
            return rgm.getParent() instanceof DomainMBean ? this.domainRGStateHelper.isDesiredStateAdmin(rgm) : true;
         }
      } catch (ResourceGroupLifecycleException var4) {
         throw new DeploymentException(var4);
      }
   }

   private static Collection getDeploymentsForResourceGroup(ResourceGroupMBean rgMBean) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In getDeploymentsForResourceGroup for resource group: " + rgMBean.getName());
      }

      Collection deployments = OrderedDeployments.getDeployments();
      List rgDeployments = new ArrayList();
      Iterator var3 = deployments.iterator();

      while(true) {
         while(var3.hasNext()) {
            Object dep = var3.next();
            if (Debug.isDeploymentDebugEnabled()) {
               Debug.deploymentDebug("Process deployment of OrderededDeployments in getDeploymentsForResourceGroup: " + dep);
            }

            if (dep instanceof BasicDeployment) {
               BasicDeploymentMBean bdm = ((BasicDeployment)dep).getDeploymentMBean();
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("Process BasicDeploymentMBean in getDeploymentsForResourceGroup: " + bdm.getName());
               }

               String bdmPartName = bdm != null && bdm.getPartitionName() != null ? bdm.getPartitionName() : "";
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("BasicDeploymentMBean partition name: " + bdmPartName);
               }

               if (!PartitionUtils.isPartitionInternalApp(bdm)) {
                  ResourceGroupMBean rgm = AppDeploymentHelper.getResourceGroupMBean(bdm);
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("The parent resource group of the BasicDeploymentMBean: " + bdm.getName() + " is: " + rgm);
                  }

                  if (rgm != null && rgm.getName().equals(rgMBean.getName()) && rgm.getParent().getName().equals(rgMBean.getParent().getName())) {
                     if (Debug.isDeploymentDebugEnabled()) {
                        Debug.deploymentDebug("Adding basic dep: " + bdm.getName() + " to the resource group deployment list");
                     }

                     rgDeployments.add(dep);
                  }
               }
            } else {
               rgDeployments.add(dep);
            }
         }

         return rgDeployments;
      }
   }

   private Collection getDeploymentsForPartition(PartitionMBean parMBean, AppTransition transition, Set resourceGroups, InternalAppScope internalAppScope) throws DeploymentException {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In getDeploymentsForPartition for partition: " + parMBean.getName());
      }

      Collection deployments = OrderedDeployments.getDeployments();
      List parDeployments = new ArrayList();
      boolean hasInternalApps = parMBean.getInternalAppDeployments().length > 0;
      Iterator var8 = deployments.iterator();

      while(true) {
         while(true) {
            Object dep;
            BasicDeploymentMBean bdm;
            String bdmPartName;
            label81:
            do {
               while(var8.hasNext()) {
                  dep = var8.next();
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("Process deployment of OrderededDeployments in getDeploymentsForPartition: " + dep);
                  }

                  if (dep instanceof BasicDeployment) {
                     bdm = ((BasicDeployment)dep).getDeploymentMBean();
                     if (Debug.isDeploymentDebugEnabled()) {
                        Debug.deploymentDebug("Process BasicDeploymentMBean in getDeploymentsForPartition: " + bdm.getName());
                     }

                     bdmPartName = bdm != null && bdm.getPartitionName() != null ? bdm.getPartitionName() : "";
                     if (Debug.isDeploymentDebugEnabled()) {
                        Debug.deploymentDebug("BasicDeploymentMBean partition name: " + bdmPartName);
                     }
                     continue label81;
                  }

                  parDeployments.add(dep);
               }

               return parDeployments;
            } while(!bdmPartName.isEmpty() && !bdmPartName.equals(parMBean.getName()));

            if (hasInternalApps && this.isInternalAppIncluded(bdm, bdmPartName, internalAppScope, transition)) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("Adding internal basic dep: " + bdm.getName() + " to the partition deployment list");
               }

               parDeployments.add(dep);
            } else {
               ResourceGroupMBean rgm = AppDeploymentHelper.getResourceGroupMBean(bdm);
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("The parent resource group of the BasicDeploymentMBean: " + bdm.getName() + " is: " + rgm);
               }

               if (rgm != null && rgm.getParent() instanceof PartitionMBean && rgm.getParent().getName().equals(parMBean.getName()) && isRGAffectedByPartitionOperation(rgm, resourceGroups) && this.isRGInRightState(rgm, transition)) {
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("Adding basic dep: " + bdm.getName() + " to the partition deployment list");
                  }

                  parDeployments.add(dep);
               }
            }
         }
      }
   }

   private static boolean isRGAffectedByPartitionOperation(ResourceGroupMBean rgm, Set resourceGroups) {
      if (resourceGroups == null) {
         return true;
      } else {
         Iterator var2 = resourceGroups.iterator();

         ResourceGroupMBean resourceGroup;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            resourceGroup = (ResourceGroupMBean)var2.next();
         } while(!resourceGroup.getName().equals(rgm.getName()));

         return true;
      }
   }

   void handleFailedApps(Collection failedApps, AppTransition transition) throws DeploymentException {
      this.handleFailedApps(failedApps, transition, true);
   }

   void handleFailedApps(Collection failedApps, AppTransition transition, boolean isFailForCluster) throws DeploymentException {
      if (failedApps.size() != 0) {
         Iterator iter = failedApps.iterator();

         while(iter.hasNext()) {
            Object dep = iter.next();
            if (this.isProgressTransition(transition) && dep instanceof BasicDeploymentMBean) {
               this.failMatchingDeploymentHandle((BasicDeploymentMBean)dep);
            }

            DeploymentAdapter adapter = getDeploymentAdapter(dep);
            if (adapter != null) {
               try {
                  adapter.remove(dep, false);
               } catch (Throwable var8) {
                  Debug.deploymentDebug("Error in removing deployment", var8);
               }
            }
         }

         ServerMBean server = this.runtimeAccess.getServer();
         ClusterMBean cluster = server.getCluster();
         if (cluster != null && isFailForCluster) {
            if (transition != AppTransition.ACTIVATE) {
               if (!Boolean.getBoolean("weblogic.deployment.IgnorePrepareStateFailures")) {
                  try {
                     this.runtimeAccess.getServerRuntime().abortStartupAfterAdminState();
                     SlaveDeployerLogger.logStartupFailedTransitionToAdmin(server.getName(), cluster.getName());
                  } catch (ServerLifecycleException var9) {
                     if (transition == AppTransition.PREPARE) {
                        this.failForErrorsInCluster(server.getName(), cluster.getName());
                     }
                  }

               }
            }
         }
      }
   }

   private void failForErrorsInCluster(String serverName, String clusterName) throws DeploymentException {
      if (this.runtimeAccess.isAdminServer()) {
         SlaveDeployerLogger.logFailedDeployClusterAS(serverName, clusterName);
      } else {
         Loggable l = SlaveDeployerLogger.logStartupFailedLoggable(serverName, clusterName);
         l.log();
         throw new DeploymentException(l.getMessage());
      }
   }

   private static DeploymentAdapter getDeploymentAdapter(Object obj) {
      if (obj instanceof BasicDeployment) {
         return DeploymentAdapter.BASIC_DEP_ADAPTER;
      } else if (obj == "PseudoDeploymentHandler") {
         return DeploymentAdapter.DEPLOYMENT_HANDLERS_ADAPTER;
      } else if (obj == "PseudoResourceDependentDeploymentHandler") {
         return DeploymentAdapter.RESOURCE_DEPENDENT_DEPLOYMENT_HANDLERS_ADAPTER;
      } else {
         return obj == "PseudoStartupClass" ? DeploymentAdapter.STARTUP_CLASSES_ADAPTER : null;
      }
   }

   private static boolean isPreStandbyApp(Object dep) {
      return dep instanceof AppDeployment && ((AppDeployment)dep).getDeploymentMBean().getPartitionName() == null ? "bea_wls_internal".equals(((AppDeployment)dep).getName()) : false;
   }

   private static boolean isAppVersion(Object dep) {
      if (!(dep instanceof AppDeployment)) {
         return false;
      } else {
         AppDeploymentMBean mbean = (AppDeploymentMBean)((AppDeployment)dep).getDeploymentMBean();
         return mbean.getVersionIdentifier() != null;
      }
   }

   private static boolean isActiveAppVersion(Object dep) {
      return dep instanceof AppDeployment && appRTStateMgr.isActiveVersion(((AppDeployment)dep).getDeploymentMBean().getName());
   }

   private static boolean isAdminMode(Object dep) {
      String appId = null;
      if (dep instanceof AppDeployment) {
         appId = ((AppDeployment)dep).getDeploymentMBean().getName();
      } else if (dep instanceof AppDeploymentMBean) {
         appId = ((AppDeploymentMBean)dep).getName();
      }

      return appRTStateMgr.isAdminMode(appId);
   }

   private static boolean isSameApp(Object dep1, Object dep2) {
      if (dep1 instanceof AppDeployment && dep2 instanceof AppDeployment) {
         AppDeploymentMBean mbean1 = (AppDeploymentMBean)((AppDeployment)dep1).getDeploymentMBean();
         AppDeploymentMBean mbean2 = (AppDeploymentMBean)((AppDeployment)dep2).getDeploymentMBean();
         return mbean1.getApplicationName().equals(mbean2.getApplicationName());
      } else {
         return false;
      }
   }

   private boolean isRetiredApp(BasicDeploymentMBean bean) {
      return bean instanceof AppDeploymentMBean && appRTStateMgr.isRetiredVersion((AppDeploymentMBean)bean);
   }

   private void debugOrderedDeploymentList() {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("In debugOrderedDeploymentList");
      }

      Collection deployments = OrderedDeployments.getDeployments();
      StringBuffer sb = new StringBuffer();
      Iterator var3 = deployments.iterator();

      while(var3.hasNext()) {
         Object dep = var3.next();
         if (dep instanceof BasicDeployment) {
            BasicDeploymentMBean bdm = ((BasicDeployment)dep).getDeploymentMBean();
            sb.append(bdm.getName());
            sb.append(", ");
         }
      }

      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("BasicDeploymentMBean in list: " + sb.toString());
      }

   }

   private void checkAndImplicitlyRetireApps(Map apps) throws DeploymentException {
      Iterator var2 = apps.keySet().iterator();

      while(var2.hasNext()) {
         String key = (String)var2.next();
         List retiringList = new ArrayList();
         List internalList = (List)apps.get(key);
         if (internalList == null || internalList.size() == 0) {
            throw new IllegalStateException("Value found to be null or empty for key " + key + " value " + internalList);
         }

         if (internalList.size() != 1) {
            Iterator var6 = internalList.iterator();

            while(var6.hasNext()) {
               AppDeployment appDeployment = (AppDeployment)var6.next();
               if (isAppVersion(appDeployment)) {
                  ApplicationRuntimeState state = appRTStateMgr.get(appDeployment.getDeploymentMBean().getName());
                  if (state != null && state.markedForRetirement()) {
                     long currentTimeMillis = System.currentTimeMillis();
                     if (currentTimeMillis >= state.getRetireTimeMillis()) {
                        retiringList.add(appDeployment);
                     } else {
                        DeployerRuntimeExtendedLogger.logAppMarkedForFutureRetirement(appDeployment.getDeploymentMBean().getName(), state.getRetireTimeMillis(), currentTimeMillis);
                     }
                  }
               }
            }

            if (retiringList.size() == 0) {
               DeployerRuntimeExtendedLogger.logMoreThanOneNonRetiringVersionsFoundOnStartup(this.format(internalList));
            } else if (retiringList.size() == 1) {
               AppDeployment deployment = (AppDeployment)retiringList.get(0);
               deployment.retireFromServerLifecycle();
               OrderedDeployments.removeDeployment(deployment.getDeploymentMBean());
               DeployerRuntimeExtendedLogger.logImplicitlyRetiringOnStartup(deployment.getDeploymentMBean().getName());
            } else {
               if (retiringList.size() <= 1) {
                  throw new IllegalStateException("Retiring List cannot have negative size");
               }

               DeployerRuntimeExtendedLogger.logMoreThanOneRetiringVersionsFoundOnStartup(this.format(retiringList));
            }
         }
      }

   }

   private String format(List listOfApps) {
      StringBuilder sb = null;
      if (listOfApps != null) {
         Iterator var3 = listOfApps.iterator();

         while(var3.hasNext()) {
            AppDeployment deployment = (AppDeployment)var3.next();
            if (sb == null) {
               sb = new StringBuilder();
               sb.append(deployment.getDeploymentMBean().getName());
            } else {
               sb.append(", ").append(deployment.getDeploymentMBean().getName());
            }
         }
      }

      return sb == null ? "" : sb.toString();
   }

   private boolean isProgressTransition(AppTransition transition) {
      return transition == AppTransition.PREPARE || transition == AppTransition.ACTIVATE;
   }

   private void failMatchingDeploymentHandle(BasicDeploymentMBean deployment) {
      ProgressWorkHandle deploymentWorkHandle = (ProgressWorkHandle)deploymentWorkHandles.get(deployment.getName());
      if (deploymentWorkHandle != null) {
         deploymentWorkHandle.fail();
      }

   }

   private ProgressTrackerService findDeploymentProgressTrackerSubsystem() {
      if (this.depProgress != null) {
         return this.depProgress;
      } else {
         this.depProgress = ProgressTrackerRegistrarFactory.getInstance().getProgressTrackerRegistrar().findProgressTrackerSubsystem("Deployments");
         return this.depProgress;
      }
   }

   private void progressTrackerStartWork(ConfigurationMBean scopeMBean) {
      this.depProgress = this.findDeploymentProgressTrackerSubsystem();
      if (scopeMBean instanceof DomainMBean) {
         AppDeploymentMBean[] var2 = ((DomainMBean)scopeMBean).getAppDeployments();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            AppDeploymentMBean app = var2[var4];
            this.deployProgressTrackerStartWork(app);
            if (isAdminMode(app)) {
               this.deployProgressTrackerCompleteWork(app);
            }
         }
      } else {
         this.deployProgressTrackerStartWork(scopeMBean);
      }

   }

   private void deployProgressTrackerStartWork(ConfigurationMBean scopeMBean) {
      deploymentWorkHandles.put(scopeMBean.getName(), this.depProgress.startWork(scopeMBean.getName()));
   }

   private void progressTrackerCompleteWork(ConfigurationMBean scopeMBean) {
      if (scopeMBean instanceof DomainMBean) {
         AppDeploymentMBean[] var2 = ((DomainMBean)scopeMBean).getAppDeployments();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            AppDeploymentMBean app = var2[var4];
            this.deployProgressTrackerCompleteWork(app);
         }
      } else {
         this.deployProgressTrackerCompleteWork(scopeMBean);
      }

   }

   private void deployProgressTrackerCompleteWork(ConfigurationMBean scopeMBean) {
      ProgressWorkHandle deploymentWorkHandle = (ProgressWorkHandle)deploymentWorkHandles.get(scopeMBean.getName());
      if (deploymentWorkHandle != null) {
         deploymentWorkHandle.complete();
         deploymentWorkHandles.remove(scopeMBean.getName());
      }

   }

   static {
      domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      deploymentWorkHandles = new HashMap();
   }

   public static enum InternalAppScope {
      ADMIN,
      NON_ADMIN,
      ANY;
   }

   private static class ConfiguredDeploymentsInitializer {
      private static final ConfiguredDeployments INSTANCE = (ConfiguredDeployments)LocatorUtilities.getService(ConfiguredDeployments.class);
   }
}
