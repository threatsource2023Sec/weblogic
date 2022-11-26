package weblogic.management.internal;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.glassfish.hk2.api.ServiceHandle;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.DescriptorUpdateEvent;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateListener;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.descriptor.LateDescriptorUpdateListener;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.invocation.PartitionTable;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementLogger;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ForeignJMSServerMBean;
import weblogic.management.configuration.JDBCStoreMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.JMSConnectionFactoryMBean;
import weblogic.management.configuration.JMSDistributedQueueMBean;
import weblogic.management.configuration.JMSDistributedTopicMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.MailSessionMBean;
import weblogic.management.configuration.MessagingBridgeMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.SNMPAgentDeploymentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.StartupClassMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.WTCServerMBean;
import weblogic.management.partition.admin.DomainLevelResourceGroupStateHelperImpl;
import weblogic.management.partition.admin.PartitionResourceGroupStateHelperImpl;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.management.utils.ApplicationVersionUtilsService;
import weblogic.management.utils.TargetingAnalyzer;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;

public final class DeploymentHandlerHome {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private static final int DEPLOYMENT_PREPARE = 0;
   private static final int DEPLOYMENT_UNPREPARE = 1;
   private static final int DEPLOYMENT_ACTIVATE = 2;
   private static final int DEPLOYMENT_DEACTIVATE = 3;
   private Set deploymentHandlers = new HashSet();
   private Set resourceDependentDeploymentHandlers = new HashSet();
   private final Set extendedHandlers = new HashSet();
   private final Set preparedExtenders = new HashSet();
   private final Map dynamicallyAdded = new HashMap();
   private final Map dynamicallyRemoved = new HashMap();
   private static boolean beanUpdateListenerAdded = false;
   private static boolean descriptorUpdateListenerAdded = false;
   private Set regularTargets = new HashSet();
   private List exceptionList = new ArrayList();
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ServiceHandle targetingAnalyzer = GlobalServiceLocator.getServiceLocator().getServiceHandle(TargetingAnalyzer.class, new Annotation[0]);
   private final ServerRuntimeMBean serverRuntime;
   private ServiceHandle domainRGStateHelper;
   private ServiceHandle partitionRGStateHelper;
   public static final boolean OLD_LIFECYCLE_MODEL = false;
   private final BeanUpdateListener deploymentListener;
   public static final Comparator deploymentComparator = new Comparator() {
      public int compare(DeploymentMBean o1, DeploymentMBean o2) {
         int retvalx = 0;

         int order1;
         for(order1 = 0; order1 < DeploymentHandlerHome.DEPLOYMENT_ORDER.length; ++order1) {
            boolean c1 = DeploymentHandlerHome.DEPLOYMENT_ORDER[order1].isAssignableFrom(o1.getClass());
            boolean c2 = DeploymentHandlerHome.DEPLOYMENT_ORDER[order1].isAssignableFrom(o2.getClass());
            if (c1 && c2) {
               break;
            }

            if (c1) {
               retvalx = -1;
               break;
            }

            if (c2) {
               retvalx = 1;
               break;
            }
         }

         if (retvalx == 0) {
            order1 = o1.getDeploymentOrder();
            int order2 = o2.getDeploymentOrder();
            boolean retval;
            if (order1 < order2) {
               retval = true;
            }

            if (order2 < order1) {
               retval = true;
            }

            return o1.getName().compareTo(o2.getName());
         } else {
            return retvalx;
         }
      }
   };
   public static final Comparator deploymentComparatorReverse = new Comparator() {
      public int compare(DeploymentMBean o1, DeploymentMBean o2) {
         return DeploymentHandlerHome.deploymentComparator.compare(o2, o1);
      }
   };
   private static final Class[] DEPLOYMENT_ORDER = new Class[]{StartupClassMBean.class, JDBCSystemResourceMBean.class, JDBCStoreMBean.class, PersistentStoreMBean.class, SAFAgentMBean.class, JMSConnectionFactoryMBean.class, JMSDistributedQueueMBean.class, JMSDistributedTopicMBean.class, JMSServerMBean.class, ForeignJMSServerMBean.class, MessagingBridgeMBean.class, MailSessionMBean.class, SNMPAgentDeploymentMBean.class};
   private static final Class[] RESOURCE_DEPENDENT_DEPLOYMENTS = new Class[]{WTCServerMBean.class};

   public DeploymentHandlerHome() {
      this.serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
      this.domainRGStateHelper = GlobalServiceLocator.getServiceLocator().getServiceHandle(DomainLevelResourceGroupStateHelperImpl.class, new Annotation[0]);
      this.partitionRGStateHelper = GlobalServiceLocator.getServiceLocator().getServiceHandle(PartitionResourceGroupStateHelperImpl.class, new Annotation[0]);
      this.deploymentListener = new DeploymentBeanUpdateListener();
   }

   public static DeploymentHandlerHome getInstance() {
      return DeploymentHandlerHome.Initializer.SINGLETON;
   }

   private LateDescriptorUpdateListener createDescriptorUpdateListener() {
      return new LateDescriptorUpdateListener() {
         public void prepareUpdate(DescriptorUpdateEvent event) throws DescriptorUpdateRejectedException {
            int addedSize = DeploymentHandlerHome.this.dynamicallyAdded.size();
            int removedSize = DeploymentHandlerHome.this.dynamicallyRemoved.size();
            if (addedSize > 0 || removedSize > 0) {
               Collection added = new HashSet(DeploymentHandlerHome.this.dynamicallyAdded.values());
               DeploymentHandlerHome.this.dynamicallyAdded.clear();
               DeploymentHandlerHome.this.preparedExtenders.clear();

               try {
                  Iterator var5 = DeploymentHandlerHome.this.extendedHandlers.iterator();

                  while(var5.hasNext()) {
                     DeploymentHandlerExtended extendedHandler = (DeploymentHandlerExtended)var5.next();
                     extendedHandler.prepareDeployments(added, false);
                     DeploymentHandlerHome.this.preparedExtenders.add(extendedHandler);
                  }

               } catch (BeanUpdateRejectedException var8) {
                  Iterator var6 = DeploymentHandlerHome.this.preparedExtenders.iterator();

                  while(var6.hasNext()) {
                     DeploymentHandlerExtended rollMeBack = (DeploymentHandlerExtended)var6.next();
                     rollMeBack.rollbackDeployments();
                  }

                  DeploymentHandlerHome.this.preparedExtenders.clear();
                  throw new DescriptorUpdateRejectedException(var8.getMessage(), var8);
               }
            }
         }

         public void activateUpdate(DescriptorUpdateEvent event) throws DescriptorUpdateFailedException {
            if (DeploymentHandlerHome.this.preparedExtenders.size() > 0 || DeploymentHandlerHome.this.dynamicallyRemoved.size() > 0) {
               String failureMessage = null;
               BeanUpdateFailedException failure = new BeanUpdateFailedException();
               boolean failed = false;
               Set localPreparedExtenders = new HashSet(DeploymentHandlerHome.this.preparedExtenders);
               DeploymentHandlerHome.this.preparedExtenders.clear();

               try {
                  Iterator var6 = localPreparedExtenders.iterator();

                  while(var6.hasNext()) {
                     DeploymentHandlerExtended extendedHandler = (DeploymentHandlerExtended)var6.next();
                     extendedHandler.activateDeployments();
                  }
               } catch (BeanUpdateFailedException var9) {
                  failed = true;
                  if (failureMessage == null) {
                     failureMessage = var9.getMessage();
                  }

                  failure.addException(var9);
               }

               if (DeploymentHandlerHome.this.dynamicallyRemoved.size() > 0) {
                  Collection removed = new HashSet(DeploymentHandlerHome.this.dynamicallyRemoved.values());
                  DeploymentHandlerHome.this.dynamicallyRemoved.clear();
                  Iterator var11 = DeploymentHandlerHome.this.extendedHandlers.iterator();

                  while(var11.hasNext()) {
                     DeploymentHandlerExtended extendedHandlerx = (DeploymentHandlerExtended)var11.next();
                     extendedHandlerx.destroyDeployments(removed);
                  }
               }

               if (failed) {
                  throw new DescriptorUpdateFailedException(failureMessage, failure);
               }
            }
         }

         public void rollbackUpdate(DescriptorUpdateEvent event) {
            DeploymentHandlerHome.this.dynamicallyRemoved.clear();
            if (DeploymentHandlerHome.this.preparedExtenders.size() > 0) {
               Set localPreparedExtenders = new HashSet(DeploymentHandlerHome.this.preparedExtenders);
               DeploymentHandlerHome.this.preparedExtenders.clear();
               Iterator var3 = localPreparedExtenders.iterator();

               while(var3.hasNext()) {
                  DeploymentHandlerExtended extendedHandler = (DeploymentHandlerExtended)var3.next();
                  extendedHandler.rollbackDeployments();
               }

            }
         }
      };
   }

   public synchronized Set prepareInitialDeployments(ConfigurationMBean scopeMBean, Set resourceGroups) throws DeploymentException {
      debug("Prepare initial deployments");
      RuntimeAccess configAccess = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domain = configAccess.getDomain();
      this.recalculateTargets(domain);
      Set deploymentSet = new TreeSet(deploymentComparator);
      Set fullDeploymentSet = new TreeSet(deploymentComparator);
      Set deplsToAddListener = new LinkedHashSet();

      try {
         DeploymentMBean[] deployments = this.getDeployments(scopeMBean, 0, resourceGroups);

         for(int i = 0; i < deployments.length; ++i) {
            DeploymentMBean deployment = deployments[i];
            if (isResourceDependent(deployment)) {
               if (this.isTargetedToThisServer(deployment)) {
                  fullDeploymentSet.add(deployment);
               }
            } else {
               if (this.isTargetedToThisServer(deployment)) {
                  deploymentSet.add(deployment);
                  fullDeploymentSet.add(deployment);
               }

               deplsToAddListener.add(deployment);
            }
         }
      } catch (ResourceGroupLifecycleException var12) {
         throw new DeploymentException(var12);
      }

      Iterator var13 = deploymentSet.iterator();

      DeploymentMBean deployment;
      while(var13.hasNext()) {
         deployment = (DeploymentMBean)var13.next();
         deployment.addBeanUpdateListener(this.deploymentListener);
         deplsToAddListener.remove(deployment);
         this.invokeHandlers(deployment, 0, (DeploymentHandlerContext)null);
      }

      var13 = deplsToAddListener.iterator();

      while(var13.hasNext()) {
         deployment = (DeploymentMBean)var13.next();
         deployment.addBeanUpdateListener(this.deploymentListener);
      }

      this.preparedExtenders.clear();

      try {
         var13 = this.extendedHandlers.iterator();

         while(var13.hasNext()) {
            DeploymentHandlerExtended extendedHandler = (DeploymentHandlerExtended)var13.next();
            extendedHandler.prepareDeployments(fullDeploymentSet, true);
            this.preparedExtenders.add(extendedHandler);
         }

         return deploymentSet;
      } catch (BeanUpdateRejectedException var11) {
         Iterator var15 = this.preparedExtenders.iterator();

         while(var15.hasNext()) {
            DeploymentHandlerExtended rollMeBack = (DeploymentHandlerExtended)var15.next();
            rollMeBack.rollbackDeployments();
         }

         this.preparedExtenders.clear();
         throw new DeploymentException(var11);
      }
   }

   public synchronized Set prepareResourceDependentInitialDeployments(ConfigurationMBean scopeMBean, Set resourceGroups) throws DeploymentException {
      debug("Prepare resource dependent initial deployments");
      RuntimeAccess configAccess = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domain = configAccess.getDomain();
      this.recalculateTargets(domain);
      Set deploymentSet = new TreeSet(deploymentComparator);

      try {
         DeploymentMBean[] deployments = this.getDeployments(scopeMBean, 0, resourceGroups);

         for(int i = 0; i < deployments.length; ++i) {
            DeploymentMBean deployment = deployments[i];
            if (isResourceDependent(deployment)) {
               if (this.isTargetedToThisServer(deployment)) {
                  deploymentSet.add(deployment);
               }

               deployment.addBeanUpdateListener(this.deploymentListener);
            }
         }
      } catch (ResourceGroupLifecycleException var9) {
         throw new DeploymentException(var9);
      }

      Iterator var10 = deploymentSet.iterator();

      while(var10.hasNext()) {
         DeploymentMBean deployment = (DeploymentMBean)var10.next();
         this.invokeHandlers(deployment, 0, (DeploymentHandlerContext)null);
      }

      return deploymentSet;
   }

   public synchronized void activateInitialDeployments(Set deploymentSet) throws DeploymentException {
      debug("Activate initial deployments");
      this.activateInitialDeployments(deploymentSet, false);
   }

   public synchronized void activateResourceDependentInitialDeployments(Set deploymentSet) throws DeploymentException {
      debug("Activate resource dependent initial deployments");
      this.activateInitialDeployments(deploymentSet, true);
      Set localPreparedExtenders = new HashSet(this.preparedExtenders);
      this.preparedExtenders.clear();
      BeanUpdateFailedException failure = new BeanUpdateFailedException();
      boolean failed = false;

      try {
         Iterator var5 = localPreparedExtenders.iterator();

         while(var5.hasNext()) {
            DeploymentHandlerExtended extendedHandler = (DeploymentHandlerExtended)var5.next();
            extendedHandler.activateDeployments();
         }
      } catch (BeanUpdateFailedException var7) {
         failed = true;
         failure.addException(var7);
      }

      if (failed) {
         throw new DeploymentException(failure);
      }
   }

   private synchronized void activateInitialDeployments(Set deploymentSet, boolean resourceDependent) throws DeploymentException {
      if (deploymentSet != null && deploymentSet.size() > 0) {
         Iterator var3 = deploymentSet.iterator();

         while(var3.hasNext()) {
            DeploymentMBean deployment = (DeploymentMBean)var3.next();
            debug("activateInitialDeployments " + deployment.getName());
            this.invokeHandlers(deployment, 2, (DeploymentHandlerContext)null);
         }
      }

      if (!resourceDependent) {
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         synchronized(domain) {
            if (!beanUpdateListenerAdded) {
               DescriptorUpdateListener listener = new DomainBeanUpdateListener();
               domain.getDescriptor().addUpdateListener(listener);
               beanUpdateListenerAdded = true;
            }

            if (!descriptorUpdateListenerAdded) {
               DescriptorUpdateListener listener = this.createDescriptorUpdateListener();
               domain.getDescriptor().addUpdateListener(listener);
               descriptorUpdateListenerAdded = true;
            }
         }
      }

   }

   public synchronized Set deactivateCurrentDeployments(ConfigurationMBean scopeMBean, Set resourceGroups) throws UndeploymentException {
      return this.deactivateCurrentDeployments(false, scopeMBean, resourceGroups);
   }

   public synchronized Set deactivateResourceDependentCurrentDeployments(ConfigurationMBean scopeMBean, Set resourceGroups) throws UndeploymentException {
      return this.deactivateCurrentDeployments(true, scopeMBean, resourceGroups);
   }

   private synchronized Set deactivateCurrentDeployments(boolean resourceDependent, ConfigurationMBean scopeMBean, Set resourceGroups) throws UndeploymentException {
      RuntimeAccess configAccess = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domain = configAccess.getDomain();
      this.recalculateTargets(domain);
      Set deploymentSet = new TreeSet(deploymentComparatorReverse);

      try {
         DeploymentMBean[] deployments = this.getDeployments(scopeMBean, 3, resourceGroups);

         for(int i = 0; i < deployments.length; ++i) {
            DeploymentMBean deployment = deployments[i];
            if (isResourceDependent(deployment) == resourceDependent && this.isTargetedToThisServer(deployment)) {
               deploymentSet.add(deployment);
            }
         }
      } catch (ResourceGroupLifecycleException var10) {
         throw new UndeploymentException(var10);
      }

      Iterator var11 = deploymentSet.iterator();

      while(var11.hasNext()) {
         DeploymentMBean deployment = (DeploymentMBean)var11.next();
         debug("deactivateCurrentDeployments " + deployment.getName());
         this.invokeHandlers(deployment, 3, (DeploymentHandlerContext)null);
      }

      return deploymentSet;
   }

   public synchronized void unprepareCurrentDeployments(Set deploymentSet) throws UndeploymentException {
      this.unprepareCurrentDeployments(deploymentSet, false);
   }

   public synchronized void unprepareResourceDependentCurrentDeployments(Set deploymentSet) throws UndeploymentException {
      this.unprepareCurrentDeployments(deploymentSet, true);
   }

   private synchronized void unprepareCurrentDeployments(Set deploymentSet, boolean resourceDependent) throws UndeploymentException {
      boolean forRollback = this.preparedExtenders.size() > 0;
      if (forRollback) {
         Set localPreparedExtenders = new HashSet(this.preparedExtenders);
         this.preparedExtenders.clear();
         Iterator var9 = localPreparedExtenders.iterator();

         while(var9.hasNext()) {
            DeploymentHandlerExtended extendedHandler = (DeploymentHandlerExtended)var9.next();
            extendedHandler.rollbackDeployments();
         }

      } else {
         if (deploymentSet != null) {
            Iterator var4 = deploymentSet.iterator();

            while(var4.hasNext()) {
               DeploymentMBean deployment = (DeploymentMBean)var4.next();
               debug("prepareInitialDeployments " + deployment.getName());
               this.invokeHandlers(deployment, 1, (DeploymentHandlerContext)null);
            }

            var4 = this.extendedHandlers.iterator();

            while(var4.hasNext()) {
               DeploymentHandlerExtended extendedHandler = (DeploymentHandlerExtended)var4.next();
               extendedHandler.destroyDeployments(deploymentSet);
            }
         }

      }
   }

   private static boolean isPartitionInRuntimeConfig(String partitionName) {
      RuntimeAccess ra = ManagementService.getRuntimeAccess(kernelId);
      if (ra == null) {
         return false;
      } else {
         return ra.getDomain() != null && ra.getDomain().lookupPartition(partitionName) != null;
      }
   }

   private void invokeHandlers(DeploymentMBean deployment, int phase, DeploymentHandlerContext context) {
      this.invokeHandlers(deployment, phase, context, (PartitionMBean[])null);
   }

   private void invokeHandlers(DeploymentMBean deployment, int phase, DeploymentHandlerContext context, PartitionMBean[] partitions) {
      this.exceptionList.clear();
      HashSet workingSet;
      if (!isResourceDependent(deployment)) {
         debug("Invoking resource independent handlers on " + deployment.getName());
         debug("Debug resource independent handler list for initializing workingSet");
         synchronized(this.deploymentHandlers) {
            workingSet = new HashSet(this.deploymentHandlers);
         }
      } else {
         debug("Invoking Resource dependent handlers on " + deployment.getName());
         debug("Debug resource dependent handler list for initializing workingSet");
         synchronized(this.resourceDependentDeploymentHandlers) {
            workingSet = new HashSet(this.resourceDependentDeploymentHandlers);
         }
      }

      debug("Debug workingSet handler list");
      debugHandlerList(workingSet);
      ComponentInvocationContextManager manager = null;

      try {
         ApplicationVersionUtilsService avus = (ApplicationVersionUtilsService)LocatorUtilities.getService(ApplicationVersionUtilsService.class);
         String partitionName = avus.getPartitionName(deployment.getName(), partitions);
         if (partitionName != null && !"DOMAIN".equals(partitionName) && phase == 1 && !isPartitionInRuntimeConfig(partitionName)) {
            this.invokeHandlers((Set)workingSet, phase, (DeploymentMBean)deployment, (DeploymentHandlerContext)context);
         } else {
            if (partitionName == null || "DOMAIN".equals(partitionName)) {
               partitionName = PartitionTable.getInstance().getGlobalPartitionName();
            }

            manager = ComponentInvocationContextManager.getInstance(kernelId);
            ComponentInvocationContext cic = manager.createComponentInvocationContext(partitionName, avus.getApplicationName(deployment.getName(), partitions), (String)null, (String)null, (String)null);
            ManagedInvocationContext mic = manager.setCurrentComponentInvocationContext(cic);
            Throwable var11 = null;

            try {
               this.invokeHandlers((Set)workingSet, phase, (DeploymentMBean)deployment, (DeploymentHandlerContext)context);
            } catch (Throwable var31) {
               var11 = var31;
               throw var31;
            } finally {
               if (mic != null) {
                  if (var11 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var30) {
                        var11.addSuppressed(var30);
                     }
                  } else {
                     mic.close();
                  }
               }

            }
         }
      } finally {
         debug("Call to Invocation Handle completed");
      }

   }

   private void invokeHandlers(Set workingSet, int phase, DeploymentMBean deployment, DeploymentHandlerContext context) {
      Iterator var5 = workingSet.iterator();

      while(var5.hasNext()) {
         DeploymentHandler handler = (DeploymentHandler)var5.next();

         try {
            switch (phase) {
               case 0:
                  debug("  Call prepare on " + handler + " with " + deployment.getName());
                  handler.prepareDeployment(deployment, context);
                  break;
               case 1:
                  debug("  Call unprepare on " + handler + " with " + deployment.getName());
                  handler.unprepareDeployment(deployment, context);
                  break;
               case 2:
                  debug("  Call activate on " + handler + " with " + deployment.getName());
                  handler.activateDeployment(deployment, context);
                  break;
               case 3:
                  debug("  Call deactivate on " + handler + " with " + deployment.getName());
                  handler.deactivateDeployment(deployment, context);
            }
         } catch (DeploymentException var8) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Exception in DeploymentHandlerHome: ", var8);
            }

            ManagementLogger.logDeploymentFailed(deployment.getName(), var8);
            this.exceptionList.add(var8);
         } catch (UndeploymentException var9) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Exception in DeploymentHandlerHome: ", var9);
            }

            ManagementLogger.logUndeploymentFailed(deployment.getName(), var9);
            this.exceptionList.add(var9);
         }
      }

   }

   private boolean isTargetedToThisServer(DeploymentMBean deployment) {
      TargetMBean[] targets = deployment.getTargets();

      for(int i = 0; i < targets.length; ++i) {
         if (this.regularTargets.contains(targets[i].getName())) {
            return true;
         }
      }

      return false;
   }

   private Object getChangedObject(BeanUpdateEvent.PropertyUpdate updateEvent) {
      int type = updateEvent.getUpdateType();
      return type == 2 ? updateEvent.getAddedObject() : updateEvent.getRemovedObject();
   }

   private void recalcTargetsIfNeeded(BeanUpdateEvent event) {
      boolean recalcNeeded = false;
      BeanUpdateEvent.PropertyUpdate[] updateEvents = event.getUpdateList();

      for(int i = 0; i < updateEvents.length; ++i) {
         BeanUpdateEvent.PropertyUpdate updateEvent = updateEvents[i];
         Object obj = this.getChangedObject(updateEvent);
         if (obj instanceof DeploymentMBean && obj instanceof TargetMBean) {
            recalcNeeded = true;
         }

         if (recalcNeeded) {
            this.recalculateTargets((DomainMBean)event.getProposedBean());
         }
      }

   }

   private void recalculateTargets(DomainMBean domain) {
      String serverName = ManagementService.getRuntimeAccess(kernelId).getServerName();
      ServerMBean server = domain.lookupServer(serverName);
      this.regularTargets.clear();
      this.regularTargets.add(serverName);
      String clusterName = null;
      ClusterMBean cluster = server.getCluster();
      if (cluster != null) {
         clusterName = cluster.getName();
         this.regularTargets.add(clusterName);
         MigratableTargetMBean[] migratableTargets = cluster.getMigratableTargets();

         for(int i = 0; i < migratableTargets.length; ++i) {
            MigratableTargetMBean migratableTarget = migratableTargets[i];
            ServerMBean[] candidates = migratableTarget.getAllCandidateServers();

            for(int c = 0; c < candidates.length; ++c) {
               if (serverName.equals(candidates[c].getName())) {
                  this.regularTargets.add(migratableTarget.getName());
                  break;
               }
            }
         }
      }

   }

   private static void debug(String msg) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(msg);
      }

   }

   private static boolean isResourceDependent(ConfigurationMBean bean) {
      for(int i = 0; i < RESOURCE_DEPENDENT_DEPLOYMENTS.length; ++i) {
         if (RESOURCE_DEPENDENT_DEPLOYMENTS[i].isAssignableFrom(bean.getClass())) {
            return true;
         }
      }

      return false;
   }

   public static boolean addDeploymentHandler(DeploymentHandler handler) {
      if (handler == null) {
         throw new NullPointerException("null handler");
      } else {
         boolean result = false;
         HashSet preWorkingSet;
         HashSet postWorkingSet;
         if (handler instanceof ResourceDependentDeploymentHandler) {
            debug("Add resource dependent deployment handler: " + handler);
            synchronized(getInstance().resourceDependentDeploymentHandlers) {
               preWorkingSet = new HashSet(getInstance().resourceDependentDeploymentHandlers);
               result = getInstance().resourceDependentDeploymentHandlers.add(handler);
               postWorkingSet = new HashSet(getInstance().resourceDependentDeploymentHandlers);
            }

            debug("Debug resource dependent handlers list before adding");
            debugHandlerList(preWorkingSet);
            debug("Debug resource dependent handlers list after adding");
            debugHandlerList(postWorkingSet);
         } else {
            debug("Add resource independent deployment handler: " + handler);
            synchronized(getInstance().deploymentHandlers) {
               preWorkingSet = new HashSet(getInstance().deploymentHandlers);
               result = getInstance().deploymentHandlers.add(handler);
               postWorkingSet = new HashSet(getInstance().deploymentHandlers);
            }

            debug("Debug resource independent deployment handlers list before adding");
            debugHandlerList(preWorkingSet);
            debug("Debug resource independent deployment handlers list after adding");
            debugHandlerList(postWorkingSet);
         }

         return result;
      }
   }

   public static boolean removeDeploymentHandler(DeploymentHandler handler) {
      if (handler == null) {
         return false;
      } else {
         boolean result = false;
         if (handler instanceof ResourceDependentDeploymentHandler) {
            debug("Remove resource dependent deployment handler: " + handler);
            synchronized(getInstance().resourceDependentDeploymentHandlers) {
               result = getInstance().resourceDependentDeploymentHandlers.remove(handler);
            }
         } else {
            debug("Remove resource independent deployment handler: " + handler);
            synchronized(getInstance().deploymentHandlers) {
               result = getInstance().deploymentHandlers.remove(handler);
            }
         }

         return result;
      }
   }

   public synchronized void addDeploymentHandlerExtended(DeploymentHandlerExtended handler) {
      if (handler != null) {
         this.extendedHandlers.add(handler);
      }
   }

   public synchronized void removedDeploymentHandlerExtended(DeploymentHandlerExtended handler) {
      if (handler != null) {
         this.extendedHandlers.remove(handler);
      }
   }

   private DeploymentMBean[] getDeployments(ConfigurationMBean scopeMBean, int phase, Set resourceGroups) throws ResourceGroupLifecycleException {
      Collection deployments = new ArrayList();
      if (scopeMBean instanceof DomainMBean) {
         deployments = this.getDeploymentsForDomain((DomainMBean)scopeMBean, phase);
      } else if (scopeMBean instanceof ResourceGroupMBean) {
         deployments = this.getDeploymentsForResourceGroup((ResourceGroupMBean)scopeMBean, phase);
      } else if (scopeMBean instanceof PartitionMBean) {
         deployments = this.getDeploymentsForPartition((PartitionMBean)scopeMBean, phase, resourceGroups);
      }

      return (DeploymentMBean[])((DeploymentMBean[])((Collection)deployments).toArray(new DeploymentMBean[((Collection)deployments).size()]));
   }

   private Collection getDeploymentsForDomain(DomainMBean domain, int phase) throws ResourceGroupLifecycleException {
      List domainDeployments = new ArrayList();
      DeploymentMBean[] deployments = domain.getDeployments();
      DeploymentMBean[] var5 = deployments;
      int var6 = deployments.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         DeploymentMBean dep = var5[var7];
         ResourceGroupMBean rgm = AppDeploymentHelper.getResourceGroupMBean(dep);
         if (rgm == null) {
            domainDeployments.add(dep);
         } else if (rgm.getParent() instanceof DomainMBean && this.isRGInRightState(rgm, phase)) {
            domainDeployments.add(dep);
         }
      }

      return domainDeployments;
   }

   private boolean isRGInRightState(ResourceGroupMBean rgm, int phase) throws ResourceGroupLifecycleException {
      if (phase != 0 && phase != 2) {
         if (phase != 1 && phase != 3) {
            return true;
         } else {
            return rgm.getParent() instanceof PartitionMBean ? ((PartitionResourceGroupStateHelperImpl)this.partitionRGStateHelper.getService()).isShuttingDown(rgm) || ((PartitionResourceGroupStateHelperImpl)this.partitionRGStateHelper.getService()).isSuspending(rgm) : ((DomainLevelResourceGroupStateHelperImpl)this.domainRGStateHelper.getService()).isShuttingDown(rgm) || ((DomainLevelResourceGroupStateHelperImpl)this.domainRGStateHelper.getService()).isSuspending(rgm);
         }
      } else {
         return rgm.getParent() instanceof DomainMBean ? ((DomainLevelResourceGroupStateHelperImpl)this.domainRGStateHelper.getService()).isDesiredStateAdmin(rgm) : true;
      }
   }

   private Collection getDeploymentsForResourceGroup(ResourceGroupMBean rgMBean, int phase) throws ResourceGroupLifecycleException {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      List rgDeployments = new ArrayList();
      DeploymentMBean[] var5 = domain.getDeployments();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         DeploymentMBean dep = var5[var7];
         ResourceGroupMBean rgm = AppDeploymentHelper.getResourceGroupMBean(dep);
         if (rgm != null && rgm.getName().equals(rgMBean.getName()) && rgm.getParent().getName().equals(rgMBean.getParent().getName()) && this.isRGInRightState(rgMBean, phase)) {
            rgDeployments.add(dep);
         }
      }

      return rgDeployments;
   }

   private Collection getDeploymentsForPartition(PartitionMBean parMBean, int phase, Set resourceGroups) throws ResourceGroupLifecycleException {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      List parDeployments = new ArrayList();
      DeploymentMBean[] var6 = domain.getDeployments();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         DeploymentMBean dep = var6[var8];
         ResourceGroupMBean rgm = AppDeploymentHelper.getResourceGroupMBean(dep);
         if (rgm != null && rgm.getParent() instanceof PartitionMBean && rgm.getParent().getName().equals(parMBean.getName()) && isRGAffectedByPartitionOperation(rgm, resourceGroups) && this.isRGInRightState(rgm, phase)) {
            parDeployments.add(dep);
         }
      }

      return parDeployments;
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

   private static void debugHandlerList(Set handlerList) {
      if (debugLogger.isDebugEnabled()) {
         StringBuffer handlers = new StringBuffer();
         Iterator var2 = handlerList.iterator();

         while(var2.hasNext()) {
            DeploymentHandler handler = (DeploymentHandler)var2.next();
            handlers.append(handler.toString());
            handlers.append(",");
         }

         debugLogger.debug("Deployment handlers list: " + handlers.toString());
      }

   }

   private class DeploymentBeanUpdateListener implements BeanUpdateListener {
      private DeploymentBeanUpdateListener() {
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         BeanUpdateEvent.PropertyUpdate[] updateEvents = event.getUpdateList();
         Object proposedBean = event.getProposedBean();
         if (proposedBean instanceof DeploymentMBean) {
            DeploymentMBean deployment = (DeploymentMBean)proposedBean;
            TargetMBean[] remove = this.getTargetUpdates(event, 3);

            for(int i = 0; i < remove.length; ++i) {
               TargetMBean target = remove[i];
               if (DeploymentHandlerHome.this.regularTargets.contains(target.getName())) {
                  DeploymentHandlerHome.debug("deploy listener de-activate " + deployment.getName());
                  DeploymentHandlerHome.this.invokeHandlers(deployment, 3, (DeploymentHandlerContext)null);
                  DeploymentHandlerHome.this.dynamicallyRemoved.put(deployment.getName(), deployment);
               }
            }

            TargetMBean[] add = this.getTargetUpdates(event, 2);

            for(int ix = 0; ix < add.length; ++ix) {
               TargetMBean targetx = add[ix];
               if (DeploymentHandlerHome.this.regularTargets.contains(targetx.getName())) {
                  DeploymentHandlerHome.debug("deploy listener prepare " + deployment.getName());
                  DeploymentHandlerHome.this.invokeHandlers(deployment, 0, (DeploymentHandlerContext)null);
                  DeploymentHandlerHome.this.dynamicallyAdded.put(deployment.getName(), deployment);
               }
            }

         }
      }

      public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
         BeanUpdateFailedException bufe = null;
         BeanUpdateEvent.PropertyUpdate[] updateEvents = event.getUpdateList();
         Object proposedBean = event.getSourceBean();
         if (proposedBean instanceof DeploymentMBean) {
            DeploymentMBean deployment = (DeploymentMBean)proposedBean;
            boolean isMBeanUnprepared = false;
            TargetMBean[] remove = this.getTargetUpdates(event, 3);

            for(int ix = 0; ix < remove.length; ++ix) {
               TargetMBean targetx = remove[ix];
               if (DeploymentHandlerHome.this.regularTargets.contains(targetx.getName())) {
                  DeploymentHandlerHome.debug("deploy listener un-prepare " + deployment.getName());
                  DeploymentHandlerHome.this.invokeHandlers(deployment, 1, (DeploymentHandlerContext)null);
                  isMBeanUnprepared = true;
                  if (!DeploymentHandlerHome.this.exceptionList.isEmpty()) {
                     if (bufe == null) {
                        bufe = new BeanUpdateFailedException();
                     }

                     Iterator var10 = DeploymentHandlerHome.this.exceptionList.iterator();

                     while(var10.hasNext()) {
                        Exception exception = (Exception)var10.next();
                        bufe.addException(exception);
                     }
                  }
               }
            }

            TargetMBean[] add = this.getTargetUpdates(event, 2);

            for(int i = 0; i < add.length; ++i) {
               TargetMBean target = add[i];
               if (DeploymentHandlerHome.this.regularTargets.contains(target.getName())) {
                  if (isMBeanUnprepared) {
                     DeploymentHandlerHome.debug("deploy listener prepare before activation " + deployment.getName());
                     DeploymentHandlerHome.this.invokeHandlers(deployment, 0, (DeploymentHandlerContext)null);
                     isMBeanUnprepared = false;
                  }

                  DeploymentHandlerHome.debug("deploy listener activate " + deployment.getName());
                  DeploymentHandlerHome.this.invokeHandlers(deployment, 2, (DeploymentHandlerContext)null);
                  if (!DeploymentHandlerHome.this.exceptionList.isEmpty()) {
                     if (bufe == null) {
                        bufe = new BeanUpdateFailedException();
                     }

                     Iterator var16 = DeploymentHandlerHome.this.exceptionList.iterator();

                     while(var16.hasNext()) {
                        Exception exceptionx = (Exception)var16.next();
                        bufe.addException(exceptionx);
                     }
                  }
               }
            }

            if (bufe != null) {
               throw bufe;
            }
         }
      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }

      private TargetMBean[] getTargetUpdates(BeanUpdateEvent event, int type) {
         Set targetSet = new HashSet();
         BeanUpdateEvent.PropertyUpdate[] updateEvents = event.getUpdateList();

         for(int i = 0; i < updateEvents.length; ++i) {
            BeanUpdateEvent.PropertyUpdate updateEvent = updateEvents[i];
            String propertyName = updateEvent.getPropertyName();
            Object obj = DeploymentHandlerHome.this.getChangedObject(updateEvent);
            if ("Targets".equals(propertyName) && type == updateEvent.getUpdateType()) {
               DeploymentHandlerHome.debug("ADD " + ((TargetMBean)obj).getName());
               targetSet.add((TargetMBean)obj);
            }
         }

         DeploymentHandlerHome.debug("Updated target set: " + targetSet);
         TargetMBean[] result = (TargetMBean[])((TargetMBean[])targetSet.toArray(new TargetMBean[targetSet.size()]));
         return result;
      }

      // $FF: synthetic method
      DeploymentBeanUpdateListener(Object x1) {
         this();
      }
   }

   private class DomainBeanUpdateListener implements DescriptorUpdateListener {
      final Map contextMap;

      private DomainBeanUpdateListener() {
         this.contextMap = new HashMap();
      }

      public void prepareUpdate(DescriptorUpdateEvent due) throws DescriptorUpdateRejectedException {
         DomainBeanUpdate domainEvent = DeploymentHandlerHome.this.new DomainBeanUpdate(due.getDiff());
         if (domainEvent.getEvent() != null) {
            DomainBeanUpdateContext ctx = null;
            if (DeploymentHandlerHome.this.targetingAnalyzer.getService() != null) {
               synchronized(this.contextMap) {
                  DomainMBean current = (DomainMBean)due.getSourceDescriptor().getRootBean();
                  DomainMBean proposed = (DomainMBean)due.getProposedDescriptor().getRootBean();
                  ((TargetingAnalyzer)DeploymentHandlerHome.this.targetingAnalyzer.getService()).init(current, proposed);
                  ctx = DeploymentHandlerHome.this.new DomainBeanUpdateContext(current, proposed, (TargetingAnalyzer)DeploymentHandlerHome.this.targetingAnalyzer.getService());
                  this.contextMap.put(due.getUpdateID(), ctx);
               }
            }

            DeploymentHandlerHome.this.recalcTargetsIfNeeded(domainEvent.getEvent());
            DeploymentMBean[] remove = domainEvent.getDeploymentUpdates(3, ctx);

            for(int i = remove.length - 1; i >= 0; --i) {
               DeploymentMBean deployment = remove[i];
               DeploymentHandlerHome.debug("Add listener to " + deployment.getName());
               deployment.addBeanUpdateListener(DeploymentHandlerHome.this.deploymentListener);
               if (DeploymentHandlerHome.this.isTargetedToThisServer(deployment)) {
                  DeploymentHandlerHome.debug("domain listener de-activate " + deployment.getName());
                  DeploymentHandlerHome.this.invokeHandlers((DeploymentMBean)deployment, 3, (DeploymentHandlerContext)null, (PartitionMBean[])null);
                  DeploymentHandlerHome.this.dynamicallyRemoved.put(deployment.getName(), deployment);
               }
            }

            DeploymentMBean[] add = domainEvent.getDeploymentUpdates(2, ctx);
            DeploymentMBean[] var14 = add;
            int var7 = add.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               DeploymentMBean deploymentx = var14[var8];
               DeploymentHandlerHome.debug("Add listener to " + deploymentx.getName());
               deploymentx.addBeanUpdateListener(DeploymentHandlerHome.this.deploymentListener);
               if (DeploymentHandlerHome.this.isTargetedToThisServer(deploymentx)) {
                  DeploymentHandlerHome.debug("domain listener prepare " + deploymentx.getName());
                  DeploymentHandlerHome.this.invokeHandlers((DeploymentMBean)deploymentx, 0, (DeploymentHandlerContext)null, (PartitionMBean[])null);
                  DeploymentHandlerHome.this.dynamicallyAdded.put(deploymentx.getName(), deploymentx);
               }
            }

         }
      }

      public void activateUpdate(DescriptorUpdateEvent due) throws DescriptorUpdateFailedException {
         boolean var21 = false;

         label220: {
            try {
               var21 = true;
               DomainBeanUpdate domainEvent = DeploymentHandlerHome.this.new DomainBeanUpdate(due.getDiff());
               if (domainEvent.getEvent() == null) {
                  var21 = false;
                  break label220;
               }

               DomainBeanUpdateContext ctx = (DomainBeanUpdateContext)this.contextMap.get(due.getUpdateID());
               BeanUpdateFailedException bufe = null;
               DeploymentMBean[] remove = domainEvent.getDeploymentUpdates(3, ctx);

               for(int i = remove.length - 1; i >= 0; --i) {
                  DeploymentMBean deployment = remove[i];
                  if (DeploymentHandlerHome.this.isTargetedToThisServer(deployment)) {
                     DeploymentHandlerHome.debug("domain listener un-prepare " + deployment.getName());
                     DeploymentHandlerHome.this.invokeHandlers((DeploymentMBean)deployment, 1, (DeploymentHandlerContext)null, (PartitionMBean[])null);
                     if (!DeploymentHandlerHome.this.exceptionList.isEmpty()) {
                        if (bufe == null) {
                           bufe = new BeanUpdateFailedException();
                        }

                        Iterator var8 = DeploymentHandlerHome.this.exceptionList.iterator();

                        while(var8.hasNext()) {
                           Exception exceptionx = (Exception)var8.next();
                           bufe.addException(exceptionx);
                        }
                     }
                  }
               }

               DeploymentMBean[] add = domainEvent.getDeploymentUpdates(2, ctx);
               if (add != null && add.length > 0) {
                  DeploymentMBean[] var27 = add;
                  int var28 = add.length;

                  for(int var29 = 0; var29 < var28; ++var29) {
                     DeploymentMBean deploymentx = var27[var29];
                     deploymentx.addBeanUpdateListener(DeploymentHandlerHome.this.deploymentListener);
                     if (DeploymentHandlerHome.this.isTargetedToThisServer(deploymentx)) {
                        DeploymentHandlerHome.debug("domain listener activate " + deploymentx.getName());
                        DeploymentHandlerHome.this.invokeHandlers((DeploymentMBean)deploymentx, 2, (DeploymentHandlerContext)null, (PartitionMBean[])null);
                        if (!DeploymentHandlerHome.this.exceptionList.isEmpty()) {
                           if (bufe == null) {
                              bufe = new BeanUpdateFailedException();
                           }

                           Iterator var11 = DeploymentHandlerHome.this.exceptionList.iterator();

                           while(var11.hasNext()) {
                              Exception exception = (Exception)var11.next();
                              bufe.addException(exception);
                           }
                        }
                     }
                  }
               }

               if (bufe != null) {
                  throw bufe;
               }

               var21 = false;
            } finally {
               if (var21) {
                  synchronized(this.contextMap) {
                     this.contextMap.remove(due.getUpdateID());
                  }
               }
            }

            synchronized(this.contextMap) {
               this.contextMap.remove(due.getUpdateID());
               return;
            }
         }

         synchronized(this.contextMap) {
            this.contextMap.remove(due.getUpdateID());
         }
      }

      public void rollbackUpdate(DescriptorUpdateEvent due) {
      }

      // $FF: synthetic method
      DomainBeanUpdateListener(Object x1) {
         this();
      }
   }

   private class DomainBeanUpdate {
      private BeanUpdateEvent event = null;

      DomainBeanUpdate(DescriptorDiff diff) {
         Iterator it = diff.iterator();

         while(it.hasNext()) {
            BeanUpdateEvent bue = (BeanUpdateEvent)it.next();
            if (bue.getSourceBean() instanceof DomainMBean) {
               this.event = bue;
               break;
            }
         }

      }

      DeploymentMBean[] getDeploymentUpdates(int type, DomainBeanUpdateContext ctx) {
         RuntimeAccess configAccess = ManagementService.getRuntimeAccess(DeploymentHandlerHome.kernelId);
         DomainMBean domain = configAccess.getDomain();
         Set deploymentSet = new TreeSet(DeploymentHandlerHome.deploymentComparator);
         BeanUpdateEvent.PropertyUpdate[] updateEvents = this.event.getUpdateList();
         BeanUpdateEvent.PropertyUpdate[] var7 = updateEvents;
         int var8 = updateEvents.length;
         int var9 = 0;

         while(var9 < var8) {
            BeanUpdateEvent.PropertyUpdate updateEvent = var7[var9];
            switch (updateEvent.getPropertyName()) {
               default:
                  Object obj = DeploymentHandlerHome.this.getChangedObject(updateEvent);
                  if (obj instanceof DeploymentMBean && type == updateEvent.getUpdateType()) {
                     DeploymentMBean depl = (DeploymentMBean)obj;
                     deploymentSet.add(depl);
                  }
               case "Targets":
               case "Deployments":
                  ++var9;
            }
         }

         return (DeploymentMBean[])deploymentSet.toArray(new DeploymentMBean[deploymentSet.size()]);
      }

      BeanUpdateEvent getEvent() {
         return this.event;
      }
   }

   private class DomainBeanUpdateContext {
      private final List partitionsToSkip = new ArrayList();
      private final Map partitionResourceGroupsToSkip = new HashMap();
      private final Map partitionResourceGroupsRemoved = new HashMap();
      private final List domainResourceGroupsToSkip = new ArrayList();
      private final List domainResourceGroupsRemoved = new ArrayList();
      private final DomainMBean proposed;
      private final DomainMBean current;
      private PartitionMBean[] partitions = null;

      public DomainBeanUpdateContext(DomainMBean _current, DomainMBean _proposed, TargetingAnalyzer analyzer) {
         this.current = _current;
         this.proposed = _proposed;
         this.partitions = null;
         this.processRemovedResourceGroups();
         this.processDeassignedPartitionsAndResourceGroups(analyzer);
         this.processNewlyAssignedPartitionsAndResourceGroups(analyzer);
      }

      final void processDeassignedPartitionsAndResourceGroups(TargetingAnalyzer analyzer) {
      }

      final void processRemovedResourceGroups() {
      }

      final void processNewlyAssignedPartitionsAndResourceGroups(TargetingAnalyzer analyzer) {
      }

      boolean isDomainRGroupMarkedToSkip(String resourceGroupName) {
         Iterator var2 = this.domainResourceGroupsRemoved.iterator();

         ResourceGroupMBean rg;
         do {
            if (!var2.hasNext()) {
               return this.domainResourceGroupsToSkip.contains(resourceGroupName);
            }

            rg = (ResourceGroupMBean)var2.next();
         } while(!rg.getName().equals(resourceGroupName));

         return true;
      }

      boolean isPartitionMarkedToSkip(String partitionName) {
         return this.partitionsToSkip.contains(partitionName);
      }

      boolean isPartitionRGroupMarkedToSkip(String partitionName, String resourceGroupName) {
         List resourceGroups = (List)this.partitionResourceGroupsToSkip.get(partitionName);
         return resourceGroups != null ? resourceGroups.contains(resourceGroupName) : false;
      }

      DomainMBean getProposedDomain() {
         return this.proposed;
      }

      DomainMBean getCurrentDomain() {
         return this.current;
      }

      PartitionMBean[] getPartitions() {
         return this.partitions;
      }
   }

   private static class Initializer {
      static final DeploymentHandlerHome SINGLETON = new DeploymentHandlerHome();
   }
}
