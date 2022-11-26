package weblogic.work;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.invocation.PartitionTable;
import weblogic.management.ManagementException;
import weblogic.management.configuration.CapacityMBean;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.configuration.ContextCaseMBean;
import weblogic.management.configuration.ContextRequestClassMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.FairShareRequestClassMBean;
import weblogic.management.configuration.MaxThreadsConstraintMBean;
import weblogic.management.configuration.MinThreadsConstraintMBean;
import weblogic.management.configuration.OverloadProtectionMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PartitionWorkManagerMBean;
import weblogic.management.configuration.ResponseTimeRequestClassMBean;
import weblogic.management.configuration.SelfTuningMBean;
import weblogic.management.configuration.ServerFailureTriggerMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.WorkManagerMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ClusterRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.utils.PartitionUtils;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

final class GlobalWorkManagerComponentsFactory {
   private static final DebugCategory debugWM = Debug.getCategory("weblogic.globalworkmanagercomponents");
   private static boolean initialized;
   private SelfTuningMBean stmb;
   private ServerRuntimeMBean serverRuntime;
   private PartitionRuntimeMBean partitionRuntime;
   private GlobalWorkManagerComponentsFactory parent;
   private final HashMap maxMap;
   private final HashMap minMap;
   private final HashMap overloadMap;
   private final HashMap requestClassMap;
   private final HashMap workManagerTemplateMap;
   private final HashMap nonAppScopedWorkManagerTemplateMap;
   private OverloadManager partitionOverloadManager;
   private PartitionFairShare partitionFairShare;
   private PartitionMinThreadsConstraint partitionMinThreadsConstraint;
   private PartitionMaxThreadsConstraint partitionMaxThreadsConstraint;
   private PartitionWorkManagerMBean partitionWorkManagerMBean;
   private PartitionFairShareBeanUpdateListener partitionFairShareBeanUpdateListener;
   private PartitionMinThreadsConstraintBeanUpdateListener partitionMinThreadsConstraintBeanUpdateListener;
   private PartitionMaxThreadsConstraintBeanUpdateListener partitionMaxThreadsConstraintBeanUpdateListener;
   private PartitionOverloadBeanUpdateListener partitionOverloadBeanUpdateListener;
   private PartitionWorkManagerRuntimeMBeanImpl partitionWorkManagerRuntimeMBean;
   private final PartitionBeanUpdateListener partitionBeanUpdateListener;
   private PartitionMBean partition;
   private ServerFailureAction serverFailureAction;
   private static String thisServer;
   private static String thisCluster;
   static boolean OLD_LIFECYCLE_MODEL = false;
   private static volatile RuntimeAccess runtimeAccess;
   private static final HashMap partitionWorkManagerComponents = new HashMap();

   private static RuntimeAccess getRuntimeAccess() {
      if (runtimeAccess != null) {
         return runtimeAccess;
      } else {
         Class var0 = ServerWorkManagerImpl.class;
         synchronized(ServerWorkManagerImpl.class) {
            if (runtimeAccess != null) {
               return runtimeAccess;
            } else {
               runtimeAccess = (RuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
                  public RuntimeAccess run() {
                     return (RuntimeAccess)GlobalServiceLocator.getServiceLocator().getService(RuntimeAccess.class, new Annotation[0]);
                  }
               });
               return runtimeAccess;
            }
         }
      }
   }

   public static void reset() {
      Class var0 = ServerWorkManagerImpl.class;
      synchronized(ServerWorkManagerImpl.class) {
         runtimeAccess = null;
      }
   }

   static GlobalWorkManagerComponentsFactory getInstance() {
      return GlobalWorkManagerComponentsFactory.Factory.THE_ONE;
   }

   static GlobalWorkManagerComponentsFactory getInstance(String partitionName) {
      return getInstance(partitionName, true);
   }

   static GlobalWorkManagerComponentsFactory getInstance(String partitionName, boolean createIfNotFound) {
      if (isGlobalPartition(partitionName)) {
         return getInstance();
      } else {
         synchronized(partitionWorkManagerComponents) {
            if (createIfNotFound && !partitionWorkManagerComponents.containsKey(partitionName)) {
               PartitionMBean partitionMBean = getRuntimeAccess().getDomain().lookupPartition(partitionName);

               try {
                  ensureInitialized(partitionName, partitionMBean);
               } catch (ManagementException var6) {
                  debug(var6.getMessage());
               }
            }

            return (GlobalWorkManagerComponentsFactory)partitionWorkManagerComponents.get(partitionName);
         }
      }
   }

   private static GlobalWorkManagerComponentsFactory createForPartition(String partitionName) {
      synchronized(partitionWorkManagerComponents) {
         GlobalWorkManagerComponentsFactory factory = new GlobalWorkManagerComponentsFactory(getInstance());
         partitionWorkManagerComponents.put(partitionName, factory);
         return factory;
      }
   }

   private GlobalWorkManagerComponentsFactory() {
      this.maxMap = new HashMap();
      this.minMap = new HashMap();
      this.overloadMap = new HashMap();
      this.requestClassMap = new HashMap();
      this.workManagerTemplateMap = new HashMap();
      this.nonAppScopedWorkManagerTemplateMap = new HashMap();
      this.partitionBeanUpdateListener = new PartitionBeanUpdateListener();
   }

   private GlobalWorkManagerComponentsFactory(GlobalWorkManagerComponentsFactory parent) {
      this.maxMap = new HashMap();
      this.minMap = new HashMap();
      this.overloadMap = new HashMap();
      this.requestClassMap = new HashMap();
      this.workManagerTemplateMap = new HashMap();
      this.nonAppScopedWorkManagerTemplateMap = new HashMap();
      this.partitionBeanUpdateListener = new PartitionBeanUpdateListener();
      this.parent = parent;
   }

   static synchronized void initialize() throws ManagementException {
      if (!initialized) {
         DomainMBean dom = getRuntimeAccess().getDomain();
         ServerRuntimeMBean serverRuntimeMBean = getRuntimeAccess().getServerRuntime();
         ServerMBean serverMBean = getRuntimeAccess().getServer();
         thisServer = serverMBean.getName();
         ClusterRuntimeMBean clusterRuntime = serverRuntimeMBean.getClusterRuntime();
         if (clusterRuntime != null) {
            thisCluster = clusterRuntime.getName();
         }

         getInstance().initialize(dom.getSelfTuning(), serverRuntimeMBean, serverMBean.getOverloadProtection());
         initialized = true;
      }
   }

   static boolean factoryExists(String partitionName) {
      return partitionWorkManagerComponents.containsKey(partitionName);
   }

   static void ensureInitialized(String partitionName, PartitionMBean partitionMBean) throws ManagementException {
      synchronized(partitionWorkManagerComponents) {
         if (!partitionWorkManagerComponents.containsKey(partitionName)) {
            GlobalWorkManagerComponentsFactory partitionComponentFactory;
            if (partitionMBean == null) {
               partitionComponentFactory = createForPartition(partitionName);
               partitionComponentFactory.initializePartition(partitionName);
               WorkManagerLogger.logPartitionConfigNotFound(partitionName);
            } else {
               if (!isPartitionDeployedToThisServer(partitionMBean)) {
                  WorkManagerLogger.logPartitionNotDeployedToThisServer(partitionName);
               }

               partitionComponentFactory = createForPartition(partitionName);
               partitionComponentFactory.initializePartition(partitionMBean, partitionName);
            }

         }
      }
   }

   static void shutdownPartition(String partitionName) {
      synchronized(partitionWorkManagerComponents) {
         GlobalWorkManagerComponentsFactory factory = (GlobalWorkManagerComponentsFactory)partitionWorkManagerComponents.remove(partitionName);
         if (factory != null) {
            factory.shutdownPartition();
         }

      }
   }

   synchronized void initialize(SelfTuningMBean selfTuningMBean, ServerRuntimeMBean serverRuntimeMBean, OverloadProtectionMBean overloadProtectionMBean) throws ManagementException {
      this.stmb = selfTuningMBean;
      this.serverRuntime = serverRuntimeMBean;
      if (overloadProtectionMBean != null) {
         ServerFailureTriggerMBean triggerMBean = overloadProtectionMBean.getServerFailureTrigger();
         if (triggerMBean != null && triggerMBean.getStuckThreadCount() > 0) {
            debug("creating ServerFailureAction with " + triggerMBean.getMaxStuckThreadTime() + " seconds and count of " + triggerMBean.getStuckThreadCount());
            this.serverFailureAction = new ServerFailureAction(triggerMBean);
         }
      }

      this.partitionFairShare = PartitionFairShare.createPartitionFairShare((String)null, selfTuningMBean.getPartitionFairShare());
      selfTuningMBean.addBeanUpdateListener(new PartitionFairShareBeanUpdateListener(this.partitionFairShare));
      this.createSelfTuningComponents(this.stmb);
   }

   synchronized void initializePartition(PartitionMBean partitionMBean, String partitionName) throws ManagementException {
      SelfTuningMBean selfTuningMBean = partitionMBean.getSelfTuning();
      this.stmb = selfTuningMBean;
      this.partitionRuntime = this.lookupPartitionRuntime(partitionName);
      PartitionWorkManagerMBean partitionWorkManagerMBean = this.getPartitionWorkManagerMBeanFromPartition(partitionMBean);
      if (partitionWorkManagerMBean == null) {
         this.createDefaultPartitionWorkManagerArtifacts(partitionName);
         this.createSelfTuningComponents(this.stmb);
      } else {
         this.partitionFairShare = PartitionFairShare.createPartitionFairShare(partitionName, partitionWorkManagerMBean.getFairShare());
         this.partitionMinThreadsConstraint = new PartitionMinThreadsConstraint(partitionName, partitionWorkManagerMBean.getMinThreadsConstraintCap());
         this.partitionMaxThreadsConstraint = new PartitionMaxThreadsConstraint(partitionName, partitionWorkManagerMBean.getMaxThreadsConstraint(), partitionWorkManagerMBean.getMaxThreadsConstraintQueueSize());
         this.createSelfTuningComponents(this.stmb);
         int partitionSharedCapacityPercent = partitionWorkManagerMBean.getSharedCapacityPercent();
         if (partitionSharedCapacityPercent >= 100) {
            partitionSharedCapacityPercent = 0;
         }

         this.partitionOverloadManager = OverloadManager.createPartitionOverloadManager(partitionName, ServerWorkManagerImpl.SHARED_OVERLOAD_MANAGER, partitionSharedCapacityPercent);
         this.createAndRegisterBeanUpdateListeners(partitionWorkManagerMBean);
      }

      this.createRuntimeMBeans(partitionName);
      partitionMBean.removeBeanUpdateListener(this.partitionBeanUpdateListener);
      partitionMBean.addBeanUpdateListener(this.partitionBeanUpdateListener);
      this.partition = partitionMBean;
   }

   synchronized void initializePartition(String partitionName) throws ManagementException {
      this.createDefaultPartitionWorkManagerArtifacts(partitionName);
      DomainMBean dom = getRuntimeAccess().getDomain();
      dom.addBeanUpdateListener(new PartitionAddBeanUpdateListener(partitionName));
   }

   synchronized void updatePartition(PartitionMBean partitionMBean, boolean newPartition) throws ManagementException {
      PartitionWorkManagerMBean partitionWorkManagerMBean = this.getPartitionWorkManagerMBeanFromPartition(partitionMBean);
      if (partitionWorkManagerMBean == null) {
         this.updatePartitionWorkManagerArtifactsWithDefaults();
         this.createAndRegisterBeanUpdateListeners((PartitionWorkManagerMBean)null);
      } else {
         this.partitionFairShare.setFairShare(partitionWorkManagerMBean.getFairShare());
         this.partitionMinThreadsConstraint.setCount(partitionWorkManagerMBean.getMinThreadsConstraintCap());
         this.partitionMaxThreadsConstraint.setCount(partitionWorkManagerMBean.getMaxThreadsConstraint());
         this.partitionOverloadManager.setPercentage(partitionWorkManagerMBean.getSharedCapacityPercent());
         this.createAndRegisterBeanUpdateListeners(partitionWorkManagerMBean);
      }

      if (newPartition) {
         SelfTuningMBean selfTuningMBean = partitionMBean.getSelfTuning();
         this.stmb = selfTuningMBean;
         this.partitionRuntime = this.lookupPartitionRuntime(partitionMBean.getName());
         this.createRuntimeMBeans(partitionMBean.getName());
         this.createSelfTuningComponents(this.stmb);
         this.partition = partitionMBean;
         partitionMBean.removeBeanUpdateListener(this.partitionBeanUpdateListener);
         partitionMBean.addBeanUpdateListener(this.partitionBeanUpdateListener);
      }

   }

   void shutdownPartition() {
      this.partitionRuntime = null;
      if (this.partitionFairShare != null) {
         PartitionFairShare.deregister(this.partitionFairShare);
      }

      if (this.partitionWorkManagerRuntimeMBean != null) {
         try {
            this.partitionWorkManagerRuntimeMBean.unregister();
         } catch (ManagementException var2) {
            debug("Exception occurred while unregistering PartitionWorkManagerRuntimeMBean " + var2);
         }

         this.partitionWorkManagerRuntimeMBean = null;
      }

      if (this.partition != null) {
         this.partition.removeBeanUpdateListener(this.partitionBeanUpdateListener);
      }

   }

   private PartitionWorkManagerMBean getPartitionWorkManagerMBeanFromPartition(PartitionMBean partitionMBean) {
      PartitionWorkManagerMBean partitionWorkManagerMBean = partitionMBean.getPartitionWorkManagerRef();
      if (partitionWorkManagerMBean == null) {
         partitionWorkManagerMBean = partitionMBean.getPartitionWorkManager();
      }

      return partitionWorkManagerMBean;
   }

   private void createDefaultPartitionWorkManagerArtifacts(String partitionName) {
      this.partitionFairShare = PartitionFairShare.createPartitionFairShare(partitionName);
      this.partitionMinThreadsConstraint = new PartitionMinThreadsConstraint(partitionName, 0);
      this.partitionMaxThreadsConstraint = new PartitionMaxThreadsConstraint(partitionName);
      this.partitionOverloadManager = OverloadManager.createPartitionOverloadManager(partitionName, ServerWorkManagerImpl.SHARED_OVERLOAD_MANAGER, 0);
   }

   private void updatePartitionWorkManagerArtifactsWithDefaults() {
      this.partitionFairShare.setFairShare(50);
      this.partitionMinThreadsConstraint.setCount(0);
      this.partitionMaxThreadsConstraint.setCount(0);
      this.partitionOverloadManager.setPercentage(0);
   }

   private void createAndRegisterBeanUpdateListeners(PartitionWorkManagerMBean mBean) {
      if (mBean != this.partitionWorkManagerMBean) {
         if (this.partitionWorkManagerMBean != null) {
            this.partitionWorkManagerMBean.removeBeanUpdateListener(this.partitionFairShareBeanUpdateListener);
            this.partitionWorkManagerMBean.removeBeanUpdateListener(this.partitionMinThreadsConstraintBeanUpdateListener);
            this.partitionWorkManagerMBean.removeBeanUpdateListener(this.partitionMaxThreadsConstraintBeanUpdateListener);
            this.partitionWorkManagerMBean.removeBeanUpdateListener(this.partitionOverloadBeanUpdateListener);
         }

         if (mBean != null) {
            if (this.partitionFairShareBeanUpdateListener == null) {
               this.partitionFairShareBeanUpdateListener = new PartitionFairShareBeanUpdateListener(this.partitionFairShare);
            }

            if (this.partitionMinThreadsConstraintBeanUpdateListener == null) {
               this.partitionMinThreadsConstraintBeanUpdateListener = new PartitionMinThreadsConstraintBeanUpdateListener(this.partitionMinThreadsConstraint);
            }

            if (this.partitionMaxThreadsConstraintBeanUpdateListener == null) {
               this.partitionMaxThreadsConstraintBeanUpdateListener = new PartitionMaxThreadsConstraintBeanUpdateListener(this.partitionMaxThreadsConstraint);
            }

            if (this.partitionOverloadBeanUpdateListener == null) {
               this.partitionOverloadBeanUpdateListener = new PartitionOverloadBeanUpdateListener(this.partitionOverloadManager);
            }

            mBean.addBeanUpdateListener(this.partitionFairShareBeanUpdateListener);
            mBean.addBeanUpdateListener(this.partitionMinThreadsConstraintBeanUpdateListener);
            mBean.addBeanUpdateListener(this.partitionMaxThreadsConstraintBeanUpdateListener);
            mBean.addBeanUpdateListener(this.partitionOverloadBeanUpdateListener);
         }

         this.partitionWorkManagerMBean = mBean;
      }

   }

   private synchronized void createRuntimeMBeans(String partitionName) throws ManagementException {
      if (this.partitionWorkManagerRuntimeMBean == null && this.partitionRuntime != null) {
         this.partitionWorkManagerRuntimeMBean = new PartitionWorkManagerRuntimeMBeanImpl(partitionName, this.partitionOverloadManager, this.partitionRuntime);
         this.partitionWorkManagerRuntimeMBean.setFairShareRuntime(new PartitionFairShareRuntimeMBeanImpl(this.partitionFairShare, this.partitionWorkManagerRuntimeMBean));
         this.partitionWorkManagerRuntimeMBean.setMaxThreadsConstraintRuntime(new MaxThreadsConstraintRuntimeMBeanImpl(this.partitionMaxThreadsConstraint, this.partitionWorkManagerRuntimeMBean));
         this.partitionWorkManagerRuntimeMBean.setMinThreadsConstraintCapRuntime(new PartitionMinThreadsConstraintCapRuntimeMBeanImpl(this.partitionMinThreadsConstraint, this.partitionWorkManagerRuntimeMBean));
         this.partitionRuntime.setPartitionWorkManagerRuntime(this.partitionWorkManagerRuntimeMBean);
      }

   }

   private void createSelfTuningComponents(SelfTuningMBean stmb) throws ManagementException {
      this.create(stmb.getFairShareRequestClasses());
      this.create(stmb.getResponseTimeRequestClasses());
      this.create(stmb.getContextRequestClasses());
      this.create(stmb.getMaxThreadsConstraints());
      this.create(stmb.getMinThreadsConstraints());
      this.create(stmb.getCapacities());
      this.create(stmb.getWorkManagers());
   }

   private static boolean isGlobalPartition(String partitionName) {
      return partitionName == null || partitionName.equals(PartitionTable.getInstance().getGlobalPartitionName());
   }

   void create(WorkManagerMBean[] workManagers) throws ConfigurationException {
      if (workManagers != null) {
         for(int count = 0; count < workManagers.length; ++count) {
            WorkManagerMBean workManagerMBean = workManagers[count];
            if (isDeployedToThisServer(workManagerMBean.getTargets())) {
               String name = workManagerMBean.getName();
               WorkManagerTemplate template = new WorkManagerTemplate(workManagerMBean);
               if (workManagerMBean.isApplicationScope()) {
                  if (this.workManagerTemplateMap.get(name) == null) {
                     this.workManagerTemplateMap.put(name, template);
                  }
               } else if (this.nonAppScopedWorkManagerTemplateMap.get(name) == null) {
                  this.nonAppScopedWorkManagerTemplateMap.put(name, template);
               }
            }
         }

      }
   }

   void create(FairShareRequestClassMBean[] shares) throws ManagementException {
      if (shares != null) {
         for(int count = 0; count < shares.length; ++count) {
            FairShareRequestClassMBean share = shares[count];
            if (isDeployedToThisServer(share.getTargets())) {
               FairShareRequestClass fsrc = (FairShareRequestClass)this.getRequestClass(share.getName(), FairShareRequestClass.class);
               if (fsrc == null) {
                  this.create(share);
               }
            }
         }

      }
   }

   private RequestClass create(FairShareRequestClassMBean share) throws ManagementException {
      FairShareRequestClass fsrc = new FairShareRequestClass(share.getName(), share.getFairShare(), this.partitionFairShare);
      debug("created global fair share RC '" + share.getName() + "'");
      share.addBeanUpdateListener(new FairShareRequestClassBeanUpdateListener(fsrc));
      fsrc.setShared(true);
      this.requestClassMap.put(share.getName(), fsrc);
      if (this.serverRuntime != null) {
         this.serverRuntime.addRequestClassRuntime(new RequestClassRuntimeMBeanImpl(fsrc));
      } else if (this.partitionRuntime != null) {
         this.partitionRuntime.addRequestClassRuntime(new RequestClassRuntimeMBeanImpl(fsrc));
      }

      return fsrc;
   }

   void create(ResponseTimeRequestClassMBean[] responses) throws ManagementException {
      if (responses != null) {
         for(int count = 0; count < responses.length; ++count) {
            ResponseTimeRequestClassMBean response = responses[count];
            if (isDeployedToThisServer(response.getTargets())) {
               ResponseTimeRequestClass rtrc = (ResponseTimeRequestClass)this.getRequestClass(response.getName(), ResponseTimeRequestClass.class);
               if (rtrc == null) {
                  this.create(response);
               }
            }
         }

      }
   }

   private RequestClass create(ResponseTimeRequestClassMBean response) throws ManagementException {
      ResponseTimeRequestClass rtrc = new ResponseTimeRequestClass(response.getName(), response.getGoalMs(), this.partitionFairShare);
      debug("created global response time RC '" + response.getName() + "'");
      response.addBeanUpdateListener(new ResponseTimeRequestClassBeanUpdateListener(rtrc));
      rtrc.setShared(true);
      this.requestClassMap.put(response.getName(), rtrc);
      if (this.serverRuntime != null) {
         this.serverRuntime.addRequestClassRuntime(new RequestClassRuntimeMBeanImpl(rtrc));
      } else if (this.partitionRuntime != null) {
         this.partitionRuntime.addRequestClassRuntime(new RequestClassRuntimeMBeanImpl(rtrc));
      }

      return rtrc;
   }

   void create(ContextRequestClassMBean[] contexts) throws ConfigurationException {
      if (contexts != null) {
         for(int count = 0; count < contexts.length; ++count) {
            ContextRequestClassMBean context = contexts[count];
            if (isDeployedToThisServer(context.getTargets())) {
               ContextRequestClass crc = (ContextRequestClass)this.getRequestClass(context.getName(), ContextRequestClass.class);
               if (crc == null) {
                  this.create(context);
               }
            }
         }

      }
   }

   private ContextRequestClass create(ContextRequestClassMBean context) throws ConfigurationException {
      ContextRequestClass crc = new ContextRequestClass(context.getName(), this.partitionFairShare);
      ContextCaseMBean[] cases = context.getContextCases();
      if (cases != null) {
         for(int count = 0; count < cases.length; ++count) {
            ContextCaseMBean casee = cases[count];
            RequestClass requestClass = this.findPrimitiveRequestClass(casee);
            if (requestClass == null) {
               throw new ConfigurationException("request class for group " + casee.getGroupName() + " not found");
            }

            if (casee.getUserName() != null) {
               crc.addUser(casee.getUserName(), requestClass);
            } else if (casee.getGroupName() != null) {
               crc.addGroup(casee.getGroupName(), requestClass);
            }
         }
      }

      debug("created global context RC '" + context.getName() + "'");
      crc.setShared(true);
      this.requestClassMap.put(context.getName(), crc);
      return crc;
   }

   private Object getRequestClass(String name, Class type) throws ConfigurationException {
      Object obj = this.requestClassMap.get(name);
      if (type.isInstance(obj)) {
         return obj;
      } else if (obj != null) {
         throw new ConfigurationException(name + " request class cannot be created. A request class of a different type already exists with that name");
      } else {
         return null;
      }
   }

   void create(MaxThreadsConstraintMBean[] maxs) throws ManagementException {
      if (maxs != null) {
         for(int count = 0; count < maxs.length; ++count) {
            MaxThreadsConstraintMBean max = maxs[count];
            if (isDeployedToThisServer(max.getTargets())) {
               MaxThreadsConstraint mtc = (MaxThreadsConstraint)this.maxMap.get(max.getName());
               if (mtc == null) {
                  String poolName = max.getConnectionPoolName();
                  if (poolName == null || poolName.trim().length() == 0) {
                     this.create(max);
                  }
               }
            }
         }

      }
   }

   private MaxThreadsConstraint create(MaxThreadsConstraintMBean max) throws ManagementException {
      String poolName = max.getConnectionPoolName();
      Object mtc;
      if (poolName != null && poolName.trim().length() != 0) {
         mtc = new PoolBasedMaxThreadsConstraint(max.getName(), max.getConnectionPoolName(), this.partitionMaxThreadsConstraint);
      } else {
         mtc = new MaxThreadsConstraint(max.getName(), max.getCount(), max.getQueueSize(), this.partitionMaxThreadsConstraint);
         max.addBeanUpdateListener(new MaxThreadsConstraintBeanUpdateListener((MaxThreadsConstraint)mtc));
      }

      debug("created global max threads constraint '" + max.getName() + "'");
      this.maxMap.put(max.getName(), mtc);
      if (this.serverRuntime != null) {
         this.serverRuntime.addMaxThreadsConstraintRuntime(new MaxThreadsConstraintRuntimeMBeanImpl((MaxThreadsConstraint)mtc));
      } else if (this.partitionRuntime != null) {
         this.partitionRuntime.addMaxThreadsConstraintRuntime(new MaxThreadsConstraintRuntimeMBeanImpl((MaxThreadsConstraint)mtc));
      }

      return (MaxThreadsConstraint)mtc;
   }

   void create(MinThreadsConstraintMBean[] mins) throws ManagementException {
      if (mins != null) {
         for(int count = 0; count < mins.length; ++count) {
            MinThreadsConstraintMBean min = mins[count];
            if (isDeployedToThisServer(min.getTargets())) {
               MinThreadsConstraint mtc = (MinThreadsConstraint)this.minMap.get(min.getName());
               if (mtc == null) {
                  this.create(min);
               }
            }
         }

      }
   }

   private MinThreadsConstraint create(MinThreadsConstraintMBean min) throws ManagementException {
      MinThreadsConstraint mtc = new MinThreadsConstraint(min.getName(), min.getCount(), this.partitionMinThreadsConstraint);
      min.addBeanUpdateListener(new MinThreadsConstraintBeanUpdateListener(mtc));
      debug("created global min threads constraint '" + min.getName() + "'");
      this.minMap.put(min.getName(), mtc);
      if (this.serverRuntime != null) {
         this.serverRuntime.addMinThreadsConstraintRuntime(new MinThreadsConstraintRuntimeMBeanImpl(mtc));
      } else if (this.partitionRuntime != null) {
         this.partitionRuntime.addMinThreadsConstraintRuntime(new MinThreadsConstraintRuntimeMBeanImpl(mtc));
      }

      return mtc;
   }

   void create(CapacityMBean[] caps) {
      if (caps != null) {
         for(int count = 0; count < caps.length; ++count) {
            CapacityMBean cap = caps[count];
            if (isDeployedToThisServer(cap.getTargets()) && this.overloadMap.get(cap.getName()) == null) {
               this.create(cap);
            }
         }

      }
   }

   private OverloadManager create(CapacityMBean cap) {
      OverloadManager om = new OverloadManager(cap.getName(), cap.getCount());
      cap.addBeanUpdateListener(new OverloadManagerBeanUpdateListener(om));
      this.overloadMap.put(cap.getName(), om);
      debug("created global capacity " + cap.getName() + "' with count " + cap.getCount());
      return om;
   }

   synchronized RequestClass findRequestClass(String name) {
      RequestClass rc = this.findPrimitiveRequestClass(name);
      if (rc != null) {
         return rc;
      } else {
         try {
            ContextRequestClassMBean ctxBean = this.stmb.lookupContextRequestClass(name);
            if (ctxBean == null) {
               if (this.parent != null) {
                  RequestClass parentRequestClass = this.parent.findRequestClass(name);
                  return this.getCopyForPartition(parentRequestClass, this.partitionFairShare);
               } else {
                  return null;
               }
            } else {
               return isDeployedToThisServer(ctxBean.getTargets()) ? this.create(ctxBean) : null;
            }
         } catch (ManagementException var5) {
            throw new IllegalArgumentException("RequestClassMBean  has invalid attributes", var5);
         }
      }
   }

   private RequestClass findPrimitiveRequestClass(String name) {
      RequestClass rc = (RequestClass)this.requestClassMap.get(name);
      if (rc != null) {
         return rc;
      } else {
         try {
            FairShareRequestClassMBean fsBean = this.stmb.lookupFairShareRequestClass(name);
            if (fsBean != null && isDeployedToThisServer(fsBean.getTargets())) {
               return this.create(fsBean);
            } else {
               ResponseTimeRequestClassMBean rtBean = this.stmb.lookupResponseTimeRequestClass(name);
               if (rtBean != null && isDeployedToThisServer(rtBean.getTargets())) {
                  return this.create(rtBean);
               } else if (fsBean == null && rtBean == null && this.parent != null) {
                  RequestClass parentRequestClass = this.parent.findPrimitiveRequestClass(name);
                  RequestClass copy = this.getCopyForPartition(parentRequestClass, this.partitionFairShare);
                  if (copy != null) {
                     this.requestClassMap.put(name, copy);
                     if (this.partitionRuntime != null && this.isPrimitiveRequestClass(copy)) {
                        try {
                           this.partitionRuntime.addRequestClassRuntime(new RequestClassRuntimeMBeanImpl(copy));
                        } catch (ManagementException var8) {
                           debug("Unable to register runtimeMBean for requestClass '" + copy + "' to partition " + this.partitionRuntime.getName());
                        }
                     }
                  }

                  return copy;
               } else {
                  return null;
               }
            }
         } catch (ManagementException var9) {
            throw new IllegalArgumentException("RequestClassMBean  has invalid attributes", var9);
         }
      }
   }

   private RequestClass findPrimitiveRequestClass(ContextCaseMBean casee) {
      String name = casee.getRequestClassName();
      if (name != null) {
         return this.findPrimitiveRequestClass(name);
      } else {
         RequestClass requestClass = null;
         FairShareRequestClassMBean share = casee.getFairShareRequestClass();
         if (share != null) {
            FairShareRequestClass fsrc = new FairShareRequestClass(share.getName(), share.getFairShare(), this.partitionFairShare);
            debug("created fair share RC '" + share.getName() + "'");
            share.addBeanUpdateListener(new FairShareRequestClassBeanUpdateListener(fsrc));
            requestClass = fsrc;
         } else {
            ResponseTimeRequestClassMBean response = casee.getResponseTimeRequestClass();
            ResponseTimeRequestClass rtrc = new ResponseTimeRequestClass(response.getName(), response.getGoalMs(), this.partitionFairShare);
            debug("created response time RC '" + response.getName() + "'");
            response.addBeanUpdateListener(new ResponseTimeRequestClassBeanUpdateListener(rtrc));
            requestClass = rtrc;
         }

         return (RequestClass)requestClass;
      }
   }

   synchronized MaxThreadsConstraint findMaxThreadsConstraint(String name) {
      MaxThreadsConstraint mtc = (MaxThreadsConstraint)this.maxMap.get(name);
      if (mtc != null) {
         return mtc;
      } else {
         MaxThreadsConstraintMBean bean = this.stmb.lookupMaxThreadsConstraint(name);
         if (bean == null) {
            return this.parent != null ? this.parent.findMaxThreadsConstraint(name) : null;
         } else if (!isDeployedToThisServer(bean.getTargets())) {
            return null;
         } else {
            try {
               return this.create(bean);
            } catch (ManagementException var5) {
               throw new IllegalArgumentException("MaxThreadsConstraintMBean '" + bean.getName() + "' has invalid attributes", var5);
            }
         }
      }
   }

   synchronized MinThreadsConstraint findMinThreadsConstraint(String name) {
      MinThreadsConstraint mtc = (MinThreadsConstraint)this.minMap.get(name);
      if (mtc != null) {
         return mtc;
      } else {
         MinThreadsConstraintMBean bean = this.stmb.lookupMinThreadsConstraint(name);
         if (bean == null) {
            return this.parent != null ? this.parent.findMinThreadsConstraint(name) : null;
         } else if (!isDeployedToThisServer(bean.getTargets())) {
            return null;
         } else {
            try {
               return this.create(bean);
            } catch (ManagementException var5) {
               throw new IllegalArgumentException("MinThreadsConstraintMBean '" + bean.getName() + "' has invalid attributes", var5);
            }
         }
      }
   }

   synchronized OverloadManager findOverloadManager(String name) {
      OverloadManager om = (OverloadManager)this.overloadMap.get(name);
      if (om != null) {
         return om;
      } else {
         CapacityMBean bean = this.stmb.lookupCapacity(name);
         if (bean == null) {
            return this.parent != null ? this.parent.findOverloadManager(name) : null;
         } else {
            return !isDeployedToThisServer(bean.getTargets()) ? null : this.create(bean);
         }
      }
   }

   synchronized Map getNonAppScopeWorkManagerTemplates() {
      if (this.parent == null) {
         return new HashMap(this.nonAppScopedWorkManagerTemplateMap);
      } else {
         HashMap result = new HashMap(this.parent.nonAppScopedWorkManagerTemplateMap.size());
         Iterator var2 = this.parent.nonAppScopedWorkManagerTemplateMap.entrySet().iterator();

         Map.Entry entry;
         while(var2.hasNext()) {
            entry = (Map.Entry)var2.next();
            result.put(entry.getKey(), new WorkManagerTemplate((WorkManagerTemplate)entry.getValue(), this.partitionFairShare));
         }

         var2 = this.nonAppScopedWorkManagerTemplateMap.entrySet().iterator();

         while(var2.hasNext()) {
            entry = (Map.Entry)var2.next();
            result.put(entry.getKey(), entry.getValue());
         }

         return result;
      }
   }

   synchronized WorkManagerTemplate removeWorkManagerTemplate(String workManagerName) {
      return (WorkManagerTemplate)this.workManagerTemplateMap.remove(workManagerName);
   }

   synchronized WorkManagerTemplate findWorkManagerTemplate(WorkManagerMBean mbean) {
      if (mbean != null && ("default".equals(mbean.getName()) || isDeployedToThisServer(mbean.getTargets()))) {
         WorkManagerTemplate template = (WorkManagerTemplate)this.workManagerTemplateMap.get(mbean.getName());
         if (template != null) {
            return template;
         } else {
            if (this.parent != null) {
               template = this.parent.findWorkManagerTemplate(mbean);
               if (template != null) {
                  return new WorkManagerTemplate(template, this.partitionFairShare);
               }
            }

            if (!this.stmb.equals(mbean.getParent())) {
               return null;
            } else {
               try {
                  template = new WorkManagerTemplate(mbean);
                  this.workManagerTemplateMap.put(mbean.getName(), template);
                  return template;
               } catch (ConfigurationException var4) {
                  throw new IllegalArgumentException("WorkManagerMBean '" + mbean.getName() + "' has invalid attributes", var4);
               }
            }
         }
      } else {
         return null;
      }
   }

   ServerFailureAction getServerFailedAction() {
      return this.serverFailureAction;
   }

   OverloadManager getParitionOverloadManager() {
      return this.partitionOverloadManager;
   }

   PartitionFairShare getPartitionFairShare() {
      return this.partitionFairShare;
   }

   PartitionMinThreadsConstraint getPartitionMinThreadsConstraint() {
      return this.partitionMinThreadsConstraint;
   }

   PartitionMaxThreadsConstraint getPartitionMaxThreadsConstraint() {
      return this.partitionMaxThreadsConstraint;
   }

   static boolean isDeployedToThisServer(TargetMBean[] targets) {
      if (targets != null && targets.length != 0) {
         TargetMBean[] var1 = targets;
         int var2 = targets.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            TargetMBean target = var1[var3];
            Set serverNames = target.getServerNames();
            if (serverNames != null && (serverNames.contains(thisServer) || serverNames.contains(thisCluster))) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   static boolean isPartitionDeployedToThisServer(PartitionMBean partitionMBean) {
      Set targetServers = PartitionUtils.getServers(partitionMBean);
      return targetServers.contains(thisServer) || targetServers.contains(thisCluster);
   }

   private PartitionRuntimeMBean lookupPartitionRuntime(String partitionName) {
      if (partitionName == null) {
         return null;
      } else {
         ServerRuntimeMBean serverRuntimeMBean = getRuntimeAccess().getServerRuntime();
         PartitionRuntimeMBean partitionRuntimeMBean = serverRuntimeMBean.lookupPartitionRuntime(partitionName);
         if (partitionRuntimeMBean == null) {
            serverRuntimeMBean.addPropertyChangeListener(new ServerRuntimeMBeanPropertyChangeListener(partitionName));
         }

         return serverRuntimeMBean.lookupPartitionRuntime(partitionName);
      }
   }

   private RequestClass getCopyForPartition(RequestClass source, PartitionFairShare partitionFairShare) {
      RequestClass requestClass = null;
      if (source instanceof ServiceClassSupport) {
         requestClass = ((ServiceClassSupport)source).createCopy(partitionFairShare);
      } else {
         requestClass = source;
      }

      return (RequestClass)requestClass;
   }

   private boolean isPrimitiveRequestClass(RequestClass requestClass) {
      return requestClass instanceof FairShareRequestClass || requestClass instanceof ResponseTimeRequestClass;
   }

   private static void debug(String str) {
      if (debugWM.isEnabled()) {
         WorkManagerLogger.logDebug("<GlobalWMComponents>" + str);
      }

   }

   // $FF: synthetic method
   GlobalWorkManagerComponentsFactory(Object x0) {
      this();
   }

   private class PartitionBeanUpdateListener implements BeanUpdateListener {
      private PartitionBeanUpdateListener() {
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      }

      public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
         BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();

         for(int i = 0; i < updated.length; ++i) {
            BeanUpdateEvent.PropertyUpdate propertyUpdate = updated[i];
            String name = propertyUpdate.getPropertyName();
            if ("PartitionWorkManager".equals(name) || "PartitionWorkManagerRef".equals(name)) {
               PartitionMBean partitionMBean = (PartitionMBean)event.getSourceBean();

               try {
                  GlobalWorkManagerComponentsFactory.this.updatePartition(partitionMBean, false);
               } catch (ManagementException var8) {
               }
            }
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }

      // $FF: synthetic method
      PartitionBeanUpdateListener(Object x1) {
         this();
      }
   }

   private class PartitionAddBeanUpdateListener implements BeanUpdateListener {
      private final String partitionName;

      PartitionAddBeanUpdateListener(String partitionName) {
         this.partitionName = partitionName;
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      }

      public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
         BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();
         int i = 0;

         while(i < updated.length) {
            BeanUpdateEvent.PropertyUpdate propertyUpdate = updated[i];
            switch (propertyUpdate.getUpdateType()) {
               case 2:
                  String name = propertyUpdate.getPropertyName();
                  if ("Partitions".equals(name)) {
                     PartitionMBean newPartition = (PartitionMBean)propertyUpdate.getAddedObject();
                     if (this.partitionName.equals(newPartition.getName())) {
                        WorkManagerLogger.logPartitionConfigAvailable(this.partitionName);

                        try {
                           GlobalWorkManagerComponentsFactory.this.updatePartition(newPartition, true);
                        } catch (ManagementException var8) {
                           WorkManagerLogger.logLoadComponentsFailed(this.partitionName, var8);
                        }

                        DomainMBean dom = GlobalWorkManagerComponentsFactory.getRuntimeAccess().getDomain();
                        dom.removeBeanUpdateListener(this);
                     }
                  }
               default:
                  ++i;
            }
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }
   }

   final class WorkManagerTemplate {
      private RequestClass requestClass;
      private MaxThreadsConstraint maxThreadsConstraint;
      private MinThreadsConstraint minThreadsConstraint;
      private OverloadManager capacity;

      WorkManagerTemplate(WorkManagerMBean mbean) throws ConfigurationException {
         this.initRequestClass(mbean);
         this.initMinThreadsConstraint(mbean);
         this.initMaxThreadsConstraint(mbean);
         this.initCapacity(mbean);
      }

      WorkManagerTemplate(WorkManagerTemplate source, PartitionFairShare partitionFairShare) {
         this.maxThreadsConstraint = source.getMaxThreadsConstraint();
         this.minThreadsConstraint = source.getMinThreadsConstraint();
         this.capacity = source.getCapacity();
         this.requestClass = GlobalWorkManagerComponentsFactory.this.getCopyForPartition(source.getRequestClass(), partitionFairShare);
      }

      RequestClass getRequestClass() {
         return this.requestClass;
      }

      MaxThreadsConstraint getMaxThreadsConstraint() {
         return this.maxThreadsConstraint;
      }

      MinThreadsConstraint getMinThreadsConstraint() {
         return this.minThreadsConstraint;
      }

      OverloadManager getCapacity() {
         return this.capacity;
      }

      OverloadManager getPartitionOverloadManager() {
         return GlobalWorkManagerComponentsFactory.this.partitionOverloadManager;
      }

      private void initRequestClass(WorkManagerMBean workManagerMBean) {
         ResponseTimeRequestClassMBean rtrcb = workManagerMBean.getResponseTimeRequestClass();
         if (rtrcb != null) {
            ResponseTimeRequestClass rtrc = new ResponseTimeRequestClass(rtrcb.getName(), rtrcb.getGoalMs(), GlobalWorkManagerComponentsFactory.this.partitionFairShare);
            rtrcb.addBeanUpdateListener(new ResponseTimeRequestClassBeanUpdateListener(rtrc));
            GlobalWorkManagerComponentsFactory.debug("created response time RC '" + rtrcb.getName() + "' for WorkManagerMBean '" + workManagerMBean.getName() + "'");
            this.requestClass = GlobalWorkManagerComponentsFactory.this.findRequestClass(rtrcb.getName());
         } else {
            ContextRequestClassMBean crcb = workManagerMBean.getContextRequestClass();
            if (crcb != null) {
               GlobalWorkManagerComponentsFactory.debug("created context RC  for WorkManagerMBean '" + workManagerMBean.getName() + "'");
               this.requestClass = GlobalWorkManagerComponentsFactory.this.findRequestClass(crcb.getName());
            } else {
               FairShareRequestClassMBean fsrcb = workManagerMBean.getFairShareRequestClass();
               if (fsrcb != null) {
                  FairShareRequestClass fsrc = new FairShareRequestClass(fsrcb.getName(), fsrcb.getFairShare(), GlobalWorkManagerComponentsFactory.this.partitionFairShare);
                  fsrcb.addBeanUpdateListener(new FairShareRequestClassBeanUpdateListener(fsrc));
                  GlobalWorkManagerComponentsFactory.debug("created fair share RC '" + fsrcb.getName() + "' for WorkManagerMBean '" + workManagerMBean.getName() + "'");
                  this.requestClass = GlobalWorkManagerComponentsFactory.this.findRequestClass(fsrcb.getName());
               }

            }
         }
      }

      private void initCapacity(WorkManagerMBean mbean) {
         CapacityMBean cap = mbean.getCapacity();
         if (cap != null) {
            GlobalWorkManagerComponentsFactory.debug("created capacity '" + cap.getName() + "' for WorkManagerMBean '" + mbean.getName() + "'");
            this.capacity = GlobalWorkManagerComponentsFactory.this.findOverloadManager(cap.getName());
            cap.addBeanUpdateListener(new OverloadManagerBeanUpdateListener(this.capacity));
         }

      }

      private void initMaxThreadsConstraint(WorkManagerMBean mbean) {
         MaxThreadsConstraintMBean max = mbean.getMaxThreadsConstraint();
         if (max != null) {
            GlobalWorkManagerComponentsFactory.debug("created max threads constraint '" + max.getName() + "' for WorkManagerMBean '" + mbean.getName() + "'");
            this.maxThreadsConstraint = GlobalWorkManagerComponentsFactory.this.findMaxThreadsConstraint(max.getName());
            max.addBeanUpdateListener(new MaxThreadsConstraintBeanUpdateListener(this.maxThreadsConstraint));
         }

      }

      private void initMinThreadsConstraint(WorkManagerMBean mbean) {
         MinThreadsConstraintMBean min = mbean.getMinThreadsConstraint();
         if (min != null) {
            GlobalWorkManagerComponentsFactory.debug("created min threads constraint '" + min.getName() + "' for WorkManagerMBean '" + mbean.getName() + "'");
            this.minThreadsConstraint = GlobalWorkManagerComponentsFactory.this.findMinThreadsConstraint(min.getName());
            min.addBeanUpdateListener(new MinThreadsConstraintBeanUpdateListener(this.minThreadsConstraint));
         }

      }
   }

   class ServerRuntimeMBeanPropertyChangeListener implements PropertyChangeListener {
      final String partitionName;

      ServerRuntimeMBeanPropertyChangeListener(String partitionName) {
         this.partitionName = partitionName;
      }

      public void propertyChange(PropertyChangeEvent event) {
         if (GlobalWorkManagerComponentsFactory.this.partitionRuntime == null && "PartitionRuntimes".equals(event.getPropertyName())) {
            ServerRuntimeMBean serverRuntimeMBean = GlobalWorkManagerComponentsFactory.getRuntimeAccess().getServerRuntime();
            GlobalWorkManagerComponentsFactory.this.partitionRuntime = serverRuntimeMBean.lookupPartitionRuntime(this.partitionName);
            if (GlobalWorkManagerComponentsFactory.this.partitionRuntime != null) {
               try {
                  GlobalWorkManagerComponentsFactory.this.createRuntimeMBeans(this.partitionName);
               } catch (ManagementException var9) {
               }

               Iterator var3 = GlobalWorkManagerComponentsFactory.this.maxMap.values().iterator();

               while(var3.hasNext()) {
                  MaxThreadsConstraint max = (MaxThreadsConstraint)var3.next();

                  try {
                     GlobalWorkManagerComponentsFactory.this.partitionRuntime.addMaxThreadsConstraintRuntime(new MaxThreadsConstraintRuntimeMBeanImpl(max, GlobalWorkManagerComponentsFactory.this.partitionRuntime));
                  } catch (ManagementException var8) {
                  }
               }

               var3 = GlobalWorkManagerComponentsFactory.this.minMap.values().iterator();

               while(var3.hasNext()) {
                  MinThreadsConstraint min = (MinThreadsConstraint)var3.next();

                  try {
                     GlobalWorkManagerComponentsFactory.this.partitionRuntime.addMinThreadsConstraintRuntime(new MinThreadsConstraintRuntimeMBeanImpl(min, GlobalWorkManagerComponentsFactory.this.partitionRuntime));
                  } catch (ManagementException var7) {
                  }
               }

               var3 = GlobalWorkManagerComponentsFactory.this.requestClassMap.values().iterator();

               while(var3.hasNext()) {
                  RequestClass requestClass = (RequestClass)var3.next();

                  try {
                     GlobalWorkManagerComponentsFactory.this.partitionRuntime.addRequestClassRuntime(new RequestClassRuntimeMBeanImpl(requestClass, GlobalWorkManagerComponentsFactory.this.partitionRuntime));
                  } catch (ManagementException var6) {
                  }
               }

               serverRuntimeMBean.removePropertyChangeListener(this);
            }
         }

      }
   }

   private static final class Factory {
      static final GlobalWorkManagerComponentsFactory THE_ONE = new GlobalWorkManagerComponentsFactory();
   }
}
