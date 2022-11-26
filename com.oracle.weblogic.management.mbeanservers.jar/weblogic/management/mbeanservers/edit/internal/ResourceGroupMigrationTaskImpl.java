package weblogic.management.mbeanservers.edit.internal;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import javax.management.InvalidAttributeValueException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.ResourceGroupMultitargetingUtils;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.mbeanservers.edit.EditTimedOutException;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGState;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ResourceGroupMigrationTask;
import weblogic.management.runtime.ClusterRuntimeMBean;
import weblogic.management.runtime.PartitionLifeCycleRuntimeMBean;
import weblogic.management.runtime.ResourceGroupLifeCycleRuntimeMBean;
import weblogic.management.runtime.ResourceGroupLifeCycleTaskRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.LCMHelper;
import weblogic.server.ServiceFailureException;
import weblogic.server.ResourceGroupLifeCycleTaskRuntime.Status;

public final class ResourceGroupMigrationTaskImpl implements ResourceGroupMigrationTask, Runnable {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugMigrationInfo");
   private static final String GLOBAL_PARTITION = "DOMAIN";
   private static final String RG_STARTED_STATE = RGState.runningState().name();
   private int state = -1;
   private final long timeout;
   private final Class targetType;
   private final String targetName;
   private final Class currentTargetType;
   private final String currentTargetName;
   private final Class newTargetType;
   private final String newTargetName;
   private final String partitionName;
   private final Set resourceGroups;
   private final DomainRuntimeServiceMBean domainRuntimeService;
   private Exception error;
   private static int instCount = 0;
   private EditAccess editAccess;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private static void logDebug(String s, Exception e) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("<ResourceGroupMigrationTaskImpl> " + s, e);
      }

   }

   private static void logDebug(String s) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("<ResourceGroupMigrationTaskImpl> " + s);
      }

   }

   private static Set findResourceGroups(ResourceGroupMBean[] rgs, String targetName, Class targetType) {
      Set targetedRGSet = new HashSet();
      ResourceGroupMBean[] var4 = rgs;
      int var5 = rgs.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ResourceGroupMBean rg = var4[var6];
         TargetMBean[] targets = rg.findEffectiveTargets();
         TargetMBean[] var9 = targets;
         int var10 = targets.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            TargetMBean targetMBean = var9[var11];
            if (targetMBean.getName().equals(targetName) && targetMBean.getClass().equals(targetType)) {
               targetedRGSet.add(rg);
            }
         }
      }

      return targetedRGSet;
   }

   private static TargetMBean lookupTarget(DomainMBean domain, String name, Class type) {
      if (domain == null) {
         throw new IllegalArgumentException("Supplied domain is null");
      } else if (VirtualTargetMBean.class.isAssignableFrom(type)) {
         return domain.lookupVirtualTarget(name);
      } else if (VirtualHostMBean.class.isAssignableFrom(type)) {
         return domain.lookupVirtualHost(name);
      } else if (ServerMBean.class.isAssignableFrom(type)) {
         return domain.lookupServer(name);
      } else {
         return ClusterMBean.class.isAssignableFrom(type) ? domain.lookupCluster(name) : null;
      }
   }

   private static ClusterRuntimeMBean findClusterRuntime(DomainRuntimeServiceMBean drs, String clusterName) {
      ClusterMBean cluster = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupCluster(clusterName);
      if (cluster == null) {
         throw new IllegalArgumentException(String.format("Cluster %s does not exit in the domain", clusterName));
      } else {
         ServerMBean[] servers = cluster.getServers();
         if (servers != null) {
            ServerMBean[] var4 = servers;
            int var5 = servers.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               ServerMBean server = var4[var6];
               ServerRuntimeMBean serverRuntime = drs.lookupServerRuntime(server.getName());
               if (serverRuntime != null) {
                  return serverRuntime.getClusterRuntime();
               }
            }
         }

         return null;
      }
   }

   public ResourceGroupMigrationTaskImpl(TargetMBean target, TargetMBean currentTarget, TargetMBean newTarget, long timeout, DomainRuntimeServiceMBean drs) {
      this.domainRuntimeService = drs;
      DomainMBean domain = drs.getDomainConfiguration();
      if (target == null) {
         throw new IllegalArgumentException("target is null");
      } else if (!(target instanceof VirtualTargetMBean) && !(target instanceof VirtualHostMBean)) {
         throw new IllegalArgumentException(String.format("Target %s is not a supported target for partitions", target.getName()));
      } else {
         this.targetName = target.getName();
         this.targetType = target.getClass();
         if (currentTarget == null) {
            throw new IllegalArgumentException("currentTarget is null");
         } else {
            this.currentTargetName = currentTarget.getName();
            this.currentTargetType = currentTarget.getClass();
            if (newTarget == null) {
               throw new IllegalArgumentException("newTarget is null");
            } else {
               this.newTargetName = newTarget.getName();
               this.newTargetType = newTarget.getClass();
               TargetMBean _targetFromDomainTree = lookupTarget(domain, this.targetName, this.targetType);
               boolean match = false;
               TargetMBean[] _partitionName = ((DeploymentMBean)_targetFromDomainTree).getTargets();
               int var11 = _partitionName.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  TargetMBean tgt = _partitionName[var12];

                  assert tgt != null;

                  if (tgt.getName().equals(this.currentTargetName)) {
                     match = true;
                     break;
                  }
               }

               if (!match) {
                  throw new IllegalArgumentException(String.format("Target %s does not have the %s %s as a target", target.getName(), currentTarget.getType(), currentTarget.getName()));
               } else if (drs == null) {
                  throw new IllegalArgumentException("drs is null");
               } else {
                  this.timeout = timeout;
                  _partitionName = null;
                  Set _resourceGroups = null;
                  if (_partitionName == null) {
                     throw new IllegalArgumentException(String.format("Target %s of type %s does not have any associated partition or domain resource group", this.targetName, this.targetType.getSimpleName()));
                  } else {
                     this.partitionName = _partitionName;
                     if (_resourceGroups != null && !((Set)_resourceGroups).isEmpty()) {
                        this.resourceGroups = (Set)_resourceGroups;
                     } else {
                        throw new IllegalArgumentException(String.format("Target %s does not have any associated resource group", this.targetName));
                     }
                  }
               }
            }
         }
      }
   }

   public int getState() {
      return this.state;
   }

   public Exception getError() {
      return this.error;
   }

   public final void setError(Exception ex) {
      this.error = ex;
   }

   public void run() {
      logDebug("ResourceGroupMigrationTaskImpl: run");
      this.state = 1;
      MigrationOption migrationOption;
      if (this.isSeamlessMigration() && RG_STARTED_STATE.equals(this.getResourceGroupsState())) {
         this.validateRGs();
         migrationOption = ResourceGroupMigrationTaskImpl.MigrationOption.SEAMLESS;
      } else {
         migrationOption = ResourceGroupMigrationTaskImpl.MigrationOption.NON_SEAMLESS;
      }

      try {
         this.editAccess = this.initEditSession(this.getClass().getName() + instCount++ + "_" + System.currentTimeMillis());
         JSONArray propArray = this.createPropArray(this.partitionName, this.resourceGroups, this.targetName, this.currentTargetName, this.newTargetName);
         switch (migrationOption) {
            case SEAMLESS:
               this.addNewTarget(migrationOption);
               LCMHelper.otdUpdateStartMigrate(this.partitionName, propArray);
               this.doSessionReplication();
               LCMHelper.otdUpdateEndMigrate(this.partitionName, propArray);
               this.gracefulShutdownRGs();
               this.removeOldTarget(migrationOption);
               break;
            case NON_SEAMLESS:
               this.gracefulShutdownRGs();
               this.removeOldTarget(migrationOption);
               this.addNewTarget(migrationOption);
               LCMHelper.otdUpdate(this.partitionName, propArray);
         }

         this.state = 2;
         logDebug("ResourceGroup migration completed. State of ResourceGroup migration task is: " + this.state);
      } catch (Exception var10) {
         logDebug("Exception Occurred during migration. Rolling back to previous configuration. State of ResourceGroup migration task is: " + this.state);
         this.rollback(migrationOption);
         this.setError(var10);
         this.state = 3;
         throw new RuntimeException(var10);
      } finally {
         try {
            this.endEditSession(this.editAccess, true);
         } catch (ManagementException | ServiceFailureException var9) {
            throw new RuntimeException(var9);
         }
      }

   }

   private JSONArray createPropArray(String partitionName, Set resourceGroups, String targetName, String currentTargetName, String newTargetName) throws JSONException {
      StringBuilder sb = null;
      if (resourceGroups != null) {
         sb = new StringBuilder();
         Iterator it = resourceGroups.iterator();

         for(int i = 0; it.hasNext(); ++i) {
            sb.append(((ResourceGroupMBean)it.next()).getName());
            if (i < resourceGroups.size() - 1) {
               sb.append(",");
            }
         }
      }

      JSONArray propArray = new JSONArray();
      JSONObject propObject = new JSONObject();
      if (sb != null) {
         propObject.put("name", "ResourceGroupName");
         propObject.put("value", sb.toString());
      }

      propArray.put(propObject);
      propObject = new JSONObject();
      propObject.put("name", "CurrentCluster");
      propObject.put("value", currentTargetName);
      propArray.put(propObject);
      propObject = new JSONObject();
      propObject.put("name", "PartitionName");
      propObject.put("value", partitionName);
      propArray.put(propObject);
      propObject = new JSONObject();
      propObject.put("name", "TargetCluster");
      propObject.put("value", newTargetName);
      propArray.put(propObject);
      propObject = new JSONObject();
      propObject.put("name", "VirtualHost");
      propObject.put("value", targetName);
      propArray.put(propObject);
      return propArray;
   }

   private void validateRGs() {
      new ArrayList();
      Iterator var2 = this.resourceGroups.iterator();

      while(var2.hasNext()) {
         ResourceGroupMBean rg = (ResourceGroupMBean)var2.next();

         List blResources;
         try {
            blResources = ResourceGroupMultitargetingUtils.findBlacklistedResources(rg);
         } catch (InvocationTargetException | IllegalAccessException | IOException var5) {
            logDebug(var5.getLocalizedMessage());
            throw new RuntimeException(var5);
         }

         if (blResources != null && blResources.size() > 0) {
            throw new RuntimeException(String.format("ResourceGroup %s contains resources %s that do not support multi-targets. ResourceGroup %s must be shutdown before migration.", rg.getName(), blResources.toString(), rg.getName()));
         }
      }

   }

   private void addNewTarget(MigrationOption migrationOption) {
      try {
         DomainMBean domain = this.startEdit(this.editAccess);
         TargetMBean target = lookupTarget(domain, this.targetName, this.targetType);
         if (target instanceof VirtualTargetMBean && migrationOption == ResourceGroupMigrationTaskImpl.MigrationOption.SEAMLESS) {
            ((VirtualTargetMBean)target).setMoreThanOneTargetAllowed(true);
         }

         TargetMBean newTarget = lookupTarget(domain, this.newTargetName, this.newTargetType);
         ((DeploymentMBean)target).addTarget(newTarget);
         this.activate(this.editAccess);
         logDebug("New target added " + newTarget);
      } catch (Exception var5) {
         if (var5 instanceof EditTimedOutException) {
            logDebug("Unable to start an edit session", var5);
         } else if (!(var5 instanceof InvalidAttributeValueException) && !(var5 instanceof DistributedManagementException)) {
            logDebug("Unable to activate changes", var5);
         } else {
            logDebug("Unable to add the new target", var5);
         }

         throw new RuntimeException(var5);
      }
   }

   private void removeOldTarget(MigrationOption migrationOption) {
      TargetMBean target = null;

      try {
         DomainMBean domain = this.startEdit(this.editAccess);
         target = lookupTarget(domain, this.targetName, this.targetType);
         TargetMBean currentTarget = lookupTarget(domain, this.currentTargetName, this.currentTargetType);
         ((DeploymentMBean)target).removeTarget(currentTarget);
         if (target instanceof VirtualTargetMBean && migrationOption == ResourceGroupMigrationTaskImpl.MigrationOption.SEAMLESS) {
            ((VirtualTargetMBean)target).setMoreThanOneTargetAllowed(false);
            target.unSet("MoreThanOneTargetAllowed");
         }

         this.activate(this.editAccess);
      } catch (Exception var5) {
         if (target != null && target instanceof VirtualTargetMBean && !((VirtualTargetMBean)target).isMoreThanOneTargetAllowed()) {
            ((VirtualTargetMBean)target).setMoreThanOneTargetAllowed(true);
         }

         if (var5 instanceof EditTimedOutException) {
            logDebug("Unable to start an edit session", var5);
         } else if (!(var5 instanceof InvalidAttributeValueException) && !(var5 instanceof DistributedManagementException)) {
            logDebug("Unable to activate changes", var5);
         } else {
            logDebug("Unable to remove the old target", var5);
         }

         throw new RuntimeException(var5);
      }
   }

   private boolean isSeamlessMigration() {
      return ClusterMBean.class.isAssignableFrom(this.currentTargetType) && ClusterMBean.class.isAssignableFrom(this.newTargetType);
   }

   private void doSessionReplication() throws TimeoutException {
      logDebug("Initiating session replication on the RGs : " + this.resourceGroups);
      if (this.isSeamlessMigration()) {
         ClusterRuntimeMBean clusterRuntimeMBean = findClusterRuntime(this.domainRuntimeService, this.currentTargetName);
         if (clusterRuntimeMBean == null) {
            throw new IllegalStateException(String.format("Unable to get clusterRuntime for cluster: %s", this.currentTargetName));
         }

         Iterator var2 = this.resourceGroups.iterator();

         while(var2.hasNext()) {
            ResourceGroupMBean rg = (ResourceGroupMBean)var2.next();
            clusterRuntimeMBean.initiateResourceGroupMigration(this.partitionName, rg.getName(), this.newTargetName, (int)this.timeout);
         }
      }

   }

   private String getResourceGroupsState() {
      String _state = null;
      if ("DOMAIN".equals(this.partitionName)) {
         Iterator var2 = this.resourceGroups.iterator();

         while(var2.hasNext()) {
            ResourceGroupMBean rg = (ResourceGroupMBean)var2.next();
            ResourceGroupLifeCycleRuntimeMBean rLCR = this.domainRuntimeService.getDomainRuntime().lookupResourceGroupLifeCycleRuntime(rg.getName());
            if (_state == null) {
               _state = rLCR.getState();
            } else if (!_state.equals(rLCR.getState())) {
               throw new IllegalStateException(String.format("All the RGs in the Target: %s need to be in same state", this.targetName));
            }
         }
      } else {
         PartitionLifeCycleRuntimeMBean partitionLifeCycleRuntime = this.domainRuntimeService.getDomainRuntime().lookupDomainPartitionRuntime(this.partitionName).getPartitionLifeCycleRuntime();
         Iterator var7 = this.resourceGroups.iterator();

         while(var7.hasNext()) {
            ResourceGroupMBean rg = (ResourceGroupMBean)var7.next();
            ResourceGroupLifeCycleRuntimeMBean rLCR = partitionLifeCycleRuntime.lookupResourceGroupLifeCycleRuntime(rg.getName());
            if (_state == null) {
               _state = rLCR.getState();
            } else if (!_state.equals(rLCR.getState())) {
               throw new IllegalStateException(String.format("All the RGs in the Target: %s need to be in same state", this.targetName));
            }
         }
      }

      return RGState.normalize(_state).name();
   }

   private void gracefulShutdownRGs() throws ResourceGroupLifecycleException {
      logDebug("Initiating graceful shutdown on the ResourceGroups: " + this.resourceGroups);
      if ("DOMAIN".equals(this.partitionName)) {
         Iterator var1 = this.resourceGroups.iterator();

         while(var1.hasNext()) {
            ResourceGroupMBean rg = (ResourceGroupMBean)var1.next();
            ResourceGroupLifeCycleRuntimeMBean resourceGroupLifeCycleRuntime = this.domainRuntimeService.getDomainRuntime().lookupResourceGroupLifeCycleRuntime(rg.getName());
            if (resourceGroupLifeCycleRuntime == null) {
               throw new RuntimeException(String.format("Unable to get ResourceGroupLifeCycleRuntimeMBean for ResourceGroup: %s", rg.getName()));
            }

            this.shutdownRG(resourceGroupLifeCycleRuntime);
         }
      } else {
         PartitionLifeCycleRuntimeMBean partitionLifeCycleRuntime = this.domainRuntimeService.getDomainRuntime().lookupDomainPartitionRuntime(this.partitionName).getPartitionLifeCycleRuntime();
         Iterator var6 = this.resourceGroups.iterator();

         while(var6.hasNext()) {
            ResourceGroupMBean rg = (ResourceGroupMBean)var6.next();
            ResourceGroupLifeCycleRuntimeMBean resourceGroupLifeCycleRuntime = partitionLifeCycleRuntime.lookupResourceGroupLifeCycleRuntime(rg.getName());
            if (resourceGroupLifeCycleRuntime == null) {
               throw new IllegalStateException(String.format("Unable to get ResourceGroupLifeCycleRuntimeMBean for %s", rg));
            }

            this.shutdownRG(resourceGroupLifeCycleRuntime);
         }
      }

   }

   private void shutdownRG(ResourceGroupLifeCycleRuntimeMBean resourceGroupLifeCycleRuntime) throws ResourceGroupLifecycleException {
      if (!resourceGroupLifeCycleRuntime.getState().equals(RGState.SHUTDOWN.name()) || !resourceGroupLifeCycleRuntime.getState().equals(RGState.SHUTTING_DOWN.name()) || !resourceGroupLifeCycleRuntime.getState().equals(RGState.FORCE_SHUTTING_DOWN.name())) {
         TargetMBean currentTarget = lookupTarget(this.domainRuntimeService.getDomainConfiguration(), this.currentTargetName, this.currentTargetType);
         if (currentTarget == null) {
            throw new IllegalStateException(String.format("Target %s of type %s does not exist in the domain", this.currentTargetName, this.currentTargetType));
         }

         ResourceGroupLifeCycleTaskRuntimeMBean task = resourceGroupLifeCycleRuntime.forceShutdown(new TargetMBean[]{currentTarget});
         long sleepInterval = 5000L;

         while(!task.getStatus().equals(Status.SUCCEEDED.toString())) {
            try {
               if (this.timeout > 0L && sleepInterval >= this.timeout) {
                  throw new ResourceGroupLifecycleException(String.format("ResourceGroup %s can not be shutdown within the specified timeout %dms", resourceGroupLifeCycleRuntime.getName(), this.timeout));
               }

               if (task.getStatus().equals(Status.FAILED.toString())) {
                  throw new ResourceGroupLifecycleException(String.format("ResourceGroup %s can not be shutdown within the specified timeout %dms because of exception %s", resourceGroupLifeCycleRuntime.getName(), this.timeout, task.getError()));
               }

               Thread.sleep(sleepInterval);
            } catch (InterruptedException var7) {
               logDebug(String.format("Shutdown of ResourceGroup failed: %s - %s", resourceGroupLifeCycleRuntime, var7.getLocalizedMessage()), var7);
            }
         }
      }

   }

   private void rollback(MigrationOption migrationOption) {
      TargetMBean target = null;

      try {
         DomainMBean domain = this.startEdit(this.editAccess);
         target = lookupTarget(domain, this.targetName, this.targetType);
         if (target instanceof VirtualTargetMBean) {
            TargetMBean oldTarget = lookupTarget(domain, this.currentTargetName, this.currentTargetType);
            ((DeploymentMBean)target).setTargets(new TargetMBean[]{oldTarget});
         }

         switch (migrationOption) {
            case SEAMLESS:
               ((VirtualTargetMBean)target).setMoreThanOneTargetAllowed(false);
               target.unSet("MoreThanOneTargetAllowed");
               this.activate(this.editAccess);
               JSONArray propArray = this.createPropArray(this.partitionName, this.resourceGroups, this.targetName, this.currentTargetName, this.newTargetName);
               LCMHelper.otdUpdate(this.partitionName, propArray);
               break;
            case NON_SEAMLESS:
               this.activate(this.editAccess);
         }

      } catch (Exception var5) {
         logDebug("Exception Occurred during rollback.");
         throw new RuntimeException(var5);
      }
   }

   EditAccess initEditSession(String editSessionName) throws IllegalArgumentException, ManagementException, ServiceFailureException {
      return PortablePartitionUtils.initEditSession(editSessionName);
   }

   DomainMBean startEdit(EditAccess editAccess) throws IllegalArgumentException, ManagementException, ServiceFailureException {
      return PortablePartitionUtils.startEdit(editAccess);
   }

   boolean activate(EditAccess editAccess) throws Exception {
      return PortablePartitionUtils.activate(editAccess);
   }

   void endEditSession(EditAccess editAccess, Boolean force) throws ServiceFailureException, ManagementException {
      PortablePartitionUtils.endEditSession(editAccess, force);
   }

   public static enum MigrationOption {
      SEAMLESS,
      NON_SEAMLESS;
   }
}
