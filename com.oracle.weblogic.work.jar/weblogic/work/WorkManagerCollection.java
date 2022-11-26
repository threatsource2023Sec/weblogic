package weblogic.work;

import java.security.AccessController;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import weblogic.application.ApplicationWork;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.DescriptorBean;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.j2ee.descriptor.wl.ApplicationAdminModeTriggerBean;
import weblogic.j2ee.descriptor.wl.CapacityBean;
import weblogic.j2ee.descriptor.wl.ContextCaseBean;
import weblogic.j2ee.descriptor.wl.ContextRequestClassBean;
import weblogic.j2ee.descriptor.wl.FairShareRequestClassBean;
import weblogic.j2ee.descriptor.wl.MaxThreadsConstraintBean;
import weblogic.j2ee.descriptor.wl.MinThreadsConstraintBean;
import weblogic.j2ee.descriptor.wl.ResponseTimeRequestClassBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.j2ee.descriptor.wl.WorkManagerBean;
import weblogic.j2ee.descriptor.wl.WorkManagerShutdownTriggerBean;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.SelfTuningMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.WorkManagerMBean;
import weblogic.management.configuration.WorkManagerShutdownTriggerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.ClusterRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public final class WorkManagerCollection extends AbstractCollection implements BeanUpdateListener {
   public static final int SHUTDOWN = 0;
   public static final int STARTED = 1;
   private static final DebugCategory debugWMCollection = Debug.getCategory("weblogic.workmanagercollection");
   static final String DEFAULT_WM_NAME = "default";
   private static final String MODULE_DELIMITER = "@";
   private HashMap workManagers;
   private HashMap requestClassMap;
   private HashMap maxMap;
   private HashMap minMap;
   private HashMap inlineMinMap;
   private HashMap inlineMaxMap;
   private HashMap overloadMap;
   private Map runtimeMBeans;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final String applicationName;
   private WeblogicApplicationBean weblogicApplicationBean;
   private ApplicationWork applicationWork;
   private ApplicationRuntimeMBean applicationRuntimeMBean;
   private boolean initialized;
   private String partitionName;
   private static final boolean use81ExecuteQueues;
   private boolean internal;
   private ApplicationAdminModeAction adminModeAction;
   private int state;
   private static String thisCluster;
   private static String thisServer;

   public WorkManagerCollection(String applicationName) {
      this(applicationName, false);
   }

   public WorkManagerCollection(String applicationName, boolean internal) {
      this.workManagers = new HashMap();
      this.requestClassMap = new HashMap();
      this.maxMap = new HashMap();
      this.minMap = new HashMap();
      this.inlineMinMap = new HashMap();
      this.inlineMaxMap = new HashMap();
      this.overloadMap = new HashMap();
      this.runtimeMBeans = new HashMap();
      this.state = 0;
      this.applicationName = applicationName;
      this.internal = internal;
      this.debug("creating a new collection for app: " + applicationName + ", internal: " + internal);
   }

   public synchronized void initialize(WeblogicApplicationBean app) throws DeploymentException {
      if (!this.initialized) {
         PartitionMBean partitionMBean = this.getPartitionMBean();
         SelfTuningMBean partitionSelfTuningConfig = null;
         if (partitionMBean != null) {
            this.partitionName = partitionMBean.getName();
            partitionSelfTuningConfig = partitionMBean.getSelfTuning();
            if (partitionSelfTuningConfig != null) {
               try {
                  GlobalWorkManagerComponentsFactory.ensureInitialized(this.partitionName, partitionMBean);
               } catch (ManagementException var12) {
                  throw new DeploymentException(var12);
               }
            }
         }

         this.populateNonWorkManagerComponents(app);
         SelfTuningMBean selfTuningConfig = ManagementService.getRuntimeAccess(kernelId).getDomain().getSelfTuning();
         StuckThreadManager stm = null;
         WorkManagerService defaultService = null;
         if (selfTuningConfig == null && partitionSelfTuningConfig == null) {
            stm = this.getStuckThreadManager();
            defaultService = WorkManagerServiceImpl.createService("default", this.applicationName, (String)null, stm);
            if (this.internal) {
               defaultService.setInternal();
            }

            this.workManagers.put("default", defaultService);
            this.addWorkManagerRuntime(defaultService, this.applicationRuntimeMBean);
            this.debug("created default WorkManager for " + this.applicationName);
            this.initialized = true;
         } else {
            if (thisServer == null || thisCluster == null) {
               RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
               thisServer = runtimeAccess.getServerName();
               ClusterRuntimeMBean clusterRuntime = runtimeAccess.getServerRuntime().getClusterRuntime();
               if (clusterRuntime != null) {
                  thisCluster = clusterRuntime.getName();
               }
            }

            boolean existingDefault = false;
            WorkManagerMBean[] workManagerMBeans = this.getWorkManagerMBeans(selfTuningConfig, partitionSelfTuningConfig);
            if (workManagerMBeans != null) {
               for(int count = 0; count < workManagerMBeans.length; ++count) {
                  WorkManagerMBean workManagerMBean = workManagerMBeans[count];
                  if (workManagerMBean.isApplicationScope()) {
                     WorkManagerService wmService = this.configureWorkManagerService(workManagerMBean);
                     if (wmService != null && wmService.getName().equals("default")) {
                        existingDefault = true;
                     }
                  }
               }
            }

            if (selfTuningConfig != null) {
               selfTuningConfig.addBeanUpdateListener(this);
            }

            if (partitionSelfTuningConfig != null) {
               partitionSelfTuningConfig.addBeanUpdateListener(this);
            }

            if (!existingDefault) {
               stm = this.getStuckThreadManager();
               defaultService = WorkManagerServiceImpl.createService("default", this.applicationName, (String)null, stm);
               if (this.internal) {
                  defaultService.setInternal();
               }

               this.workManagers.put("default", defaultService);
               this.addWorkManagerRuntime(defaultService, this.applicationRuntimeMBean);
               this.debug("created default WorkManager for " + this.applicationName);
            }

            this.populateWorkManagers(app);
            this.initialized = true;
         }
      }
   }

   private WorkManagerService configureWorkManagerService(WorkManagerMBean workManagerMBean) throws DeploymentException {
      WorkManagerService wmService = null;
      if ("default".equals(workManagerMBean.getName())) {
         wmService = this.addWorkManager(workManagerMBean);
      } else {
         if (this.isWorkManagerConfiguredInPartition(workManagerMBean)) {
            return this.addWorkManager(workManagerMBean);
         }

         TargetMBean[] targets = workManagerMBean.getTargets();
         if (targets != null) {
            TargetMBean[] var4 = targets;
            int var5 = targets.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               TargetMBean target = var4[var6];
               String targetName = target.getName();
               if (targetName.equals(thisServer) || targetName.equals(thisCluster)) {
                  wmService = this.addWorkManager(workManagerMBean);
                  break;
               }
            }
         }
      }

      return wmService;
   }

   private boolean isWorkManagerConfiguredInPartition(WorkManagerMBean workManagerMBean) {
      boolean result = false;
      WebLogicMBean parent = workManagerMBean.getParent();
      if (parent instanceof SelfTuningMBean) {
         SelfTuningMBean selfTuningMBean = (SelfTuningMBean)parent;
         parent = selfTuningMBean.getParent();
         if (parent instanceof PartitionMBean) {
            result = true;
         }
      }

      return result;
   }

   public void setApplicationRuntime(ApplicationWork applicationWork, ApplicationRuntimeMBean applicationRuntime) {
      this.applicationRuntimeMBean = applicationRuntime;
      this.applicationWork = applicationWork;
   }

   public synchronized Iterator iterator() {
      return this.workManagers.values().iterator();
   }

   public int size() {
      return this.workManagers.size();
   }

   private synchronized void populateNonWorkManagerComponents(WeblogicApplicationBean app) throws DeploymentException {
      if (app != null) {
         this.weblogicApplicationBean = app;
         this.populate(app.getFairShareRequests());
         this.populate(app.getResponseTimeRequests());
         this.populate(app.getContextRequests());
         this.populate(app.getMaxThreadsConstraints());
         this.populate(app.getMinThreadsConstraints());
         this.populate(app.getCapacities());
         this.populate(app.getApplicationAdminModeTrigger());
      }
   }

   private synchronized void populateWorkManagers(WeblogicApplicationBean app) throws DeploymentException {
      if (app != null) {
         this.weblogicApplicationBean = app;
         this.populate((String)null, (WorkManagerBean[])app.getWorkManagers());
      }
   }

   public synchronized void populate(String moduleName, WeblogicEjbJarBean ejb) throws DeploymentException {
      this.populate(moduleName, ejb.getWorkManagers());
   }

   public synchronized void populate(String moduleName, WeblogicWebAppBean webapp) throws DeploymentException {
      this.populate(moduleName, webapp.getWorkManagers());
   }

   private void populate(FairShareRequestClassBean[] fairshares) throws DeploymentException {
      if (fairshares != null && !use81ExecuteQueues) {
         PartitionFairShare partitionFairShare = GlobalWorkManagerComponentsFactory.getInstance(this.partitionName).getPartitionFairShare();

         try {
            for(int count = 0; count < fairshares.length; ++count) {
               FairShareRequestClassBean share = fairshares[count];
               FairShareRequestClass rc = new FairShareRequestClass(share.getName(), share.getFairShare(), partitionFairShare);
               ((DescriptorBean)share).addBeanUpdateListener(new FairShareRequestClassBeanUpdateListener(rc));
               rc.setShared(true);
               this.requestClassMap.put(share.getName(), rc);
               this.applicationWork.addRequestClass(new RequestClassRuntimeMBeanImpl(rc, this.applicationRuntimeMBean));
            }

         } catch (ManagementException var6) {
            throw new DeploymentException(var6);
         }
      }
   }

   private void populate(ResponseTimeRequestClassBean[] responseTimes) throws DeploymentException {
      if (responseTimes != null && !use81ExecuteQueues) {
         PartitionFairShare partitionFairShare = GlobalWorkManagerComponentsFactory.getInstance(this.partitionName).getPartitionFairShare();

         try {
            for(int count = 0; count < responseTimes.length; ++count) {
               ResponseTimeRequestClassBean response = responseTimes[count];
               ResponseTimeRequestClass rc = new ResponseTimeRequestClass(response.getName(), response.getGoalMs(), partitionFairShare);
               ((DescriptorBean)response).addBeanUpdateListener(new ResponseTimeRequestClassBeanUpdateListener(rc));
               rc.setShared(true);
               this.requestClassMap.put(response.getName(), rc);
               this.applicationWork.addRequestClass(new RequestClassRuntimeMBeanImpl(rc, this.applicationRuntimeMBean));
            }

         } catch (ManagementException var6) {
            throw new DeploymentException(var6);
         }
      }
   }

   private void populate(ContextRequestClassBean[] contexts) throws DeploymentException {
      if (contexts != null && !use81ExecuteQueues) {
         PartitionFairShare partitionFairShare = GlobalWorkManagerComponentsFactory.getInstance(this.partitionName).getPartitionFairShare();

         for(int count = 0; count < contexts.length; ++count) {
            ContextRequestClassBean context = contexts[count];
            ContextCaseBean[] cases = context.getContextCases();
            ContextRequestClass cdp = new ContextRequestClass(context.getName(), partitionFairShare);
            if (cases != null) {
               for(int cnt = 0; cnt < cases.length; ++cnt) {
                  ContextCaseBean casee = cases[cnt];
                  RequestClass requestClass = this.getRequestClass(casee.getRequestClassName());
                  if (requestClass == null) {
                     throw new DeploymentException("request class " + casee.getRequestClassName() + " not found");
                  }

                  if (casee.getUserName() != null) {
                     cdp.addUser(casee.getUserName(), requestClass);
                  } else if (casee.getGroupName() != null) {
                     cdp.addGroup(casee.getGroupName(), requestClass);
                  }
               }
            }

            cdp.setShared(true);
            this.requestClassMap.put(context.getName(), cdp);
         }

      }
   }

   private void populate(MaxThreadsConstraintBean[] maxs) throws DeploymentException {
      if (maxs != null) {
         for(int count = 0; count < maxs.length; ++count) {
            MaxThreadsConstraintBean max = maxs[count];

            try {
               PartitionMaxThreadsConstraint partitionMaxThreadsConstraint = GlobalWorkManagerComponentsFactory.getInstance(this.partitionName).getPartitionMaxThreadsConstraint();
               Object mtc;
               if (max.getPoolName() != null) {
                  mtc = new PoolBasedMaxThreadsConstraint(max.getName(), max.getPoolName(), this.weblogicApplicationBean, partitionMaxThreadsConstraint);
               } else {
                  mtc = new MaxThreadsConstraint(max.getName(), max.getCount(), max.getQueueSize(), partitionMaxThreadsConstraint);
                  ((DescriptorBean)max).addBeanUpdateListener(new MaxThreadsConstraintBeanUpdateListener((MaxThreadsConstraint)mtc));
               }

               this.maxMap.put(max.getName(), mtc);
               if (!use81ExecuteQueues) {
                  this.applicationWork.addMaxThreadsConstraint(new MaxThreadsConstraintRuntimeMBeanImpl((MaxThreadsConstraint)mtc, this.applicationRuntimeMBean));
               }
            } catch (ManagementException var6) {
               throw new DeploymentException("unable to create runtime mbean for max constraint " + max.getName(), var6);
            }
         }

      }
   }

   private void populate(MinThreadsConstraintBean[] mins) throws DeploymentException {
      try {
         if (mins != null) {
            PartitionMinThreadsConstraint partitionMinThreadsConstraint = GlobalWorkManagerComponentsFactory.getInstance(this.partitionName).getPartitionMinThreadsConstraint();

            for(int count = 0; count < mins.length; ++count) {
               MinThreadsConstraintBean min = mins[count];
               MinThreadsConstraint mtc = new MinThreadsConstraint(min.getName(), min.getCount(), partitionMinThreadsConstraint);
               this.minMap.put(min.getName(), mtc);
               if (!use81ExecuteQueues) {
                  ((DescriptorBean)min).addBeanUpdateListener(new MinThreadsConstraintBeanUpdateListener(mtc));
                  this.applicationWork.addMinThreadsConstraint(new MinThreadsConstraintRuntimeMBeanImpl(mtc, this.applicationRuntimeMBean));
               }
            }

         }
      } catch (ManagementException var6) {
         throw new DeploymentException("unable to create runtime mbean for min constraint", var6);
      }
   }

   private void populate(CapacityBean[] caps) {
      if (caps != null && !use81ExecuteQueues) {
         for(int count = 0; count < caps.length; ++count) {
            CapacityBean cap = caps[count];
            OverloadManager om = new OverloadManager(cap.getName(), cap.getCount());
            ((DescriptorBean)cap).addBeanUpdateListener(new OverloadManagerBeanUpdateListener(om));
            this.overloadMap.put(cap.getName(), om);
         }

      }
   }

   private void populate(ApplicationAdminModeTriggerBean trigger) {
      if (trigger != null && !use81ExecuteQueues) {
         this.adminModeAction = new ApplicationAdminModeAction(trigger, this.applicationName);
      }
   }

   private void populate(String moduleName, WorkManagerBean[] wmBeans) throws DeploymentException {
      if (wmBeans != null) {
         for(int count = 0; count < wmBeans.length; ++count) {
            WorkManagerBean bean = wmBeans[count];
            this.populate(moduleName, bean);
         }

      }
   }

   public synchronized WorkManagerService populate(String moduleName, WorkManagerBean bean) throws DeploymentException {
      RequestClass requestClass = this.getPolicy(bean);
      MinThreadsConstraint min = this.getMinConstraint(bean);
      MaxThreadsConstraint max = this.getMaxConstraint(bean);
      OverloadManager overload = this.getOverload(bean);
      WorkManagerShutdownAction shutdownAction = this.getShutdownAction(bean);
      StuckThreadManager stm = this.getStuckThreadManager(bean, shutdownAction);
      WorkManagerLogger.logCreatingWorkManagerService(moduleName, this.applicationName, bean.getName(), min == null ? "null" : min.getName(), max == null ? "null" : max.getName());
      WorkManagerService wmService = WorkManagerServiceImpl.createService(bean.getName(), this.applicationName, moduleName, requestClass, max, min, overload, stm);
      if (shutdownAction != null) {
         shutdownAction.setWorkManagerService(wmService);
      }

      if (this.internal) {
         wmService.setInternal();
      }

      if (moduleName != null) {
         this.workManagers.put(moduleName + "@" + bean.getName(), wmService);
      } else {
         this.workManagers.put(bean.getName(), wmService);
      }

      if (!use81ExecuteQueues && moduleName == null) {
         this.addWorkManagerRuntime(wmService, this.applicationRuntimeMBean);
         return wmService;
      } else {
         return wmService;
      }
   }

   private WorkManagerShutdownAction getShutdownAction(WorkManagerMBean bean) {
      if (use81ExecuteQueues) {
         return null;
      } else if (bean == null) {
         return null;
      } else if (bean.getIgnoreStuckThreads()) {
         return null;
      } else {
         WorkManagerShutdownTriggerMBean trigger = bean.getWorkManagerShutdownTrigger();
         if (trigger != null && trigger.getStuckThreadCount() > 0) {
            this.debug("Found WorkManagerShutdownTriggerMBean with " + trigger.getMaxStuckThreadTime() + " seconds and " + trigger.getStuckThreadCount() + " count and resumeWhenUnstuck " + trigger.isResumeWhenUnstuck());
            return new WorkManagerShutdownAction(trigger);
         } else {
            return null;
         }
      }
   }

   private StuckThreadManager getStuckThreadManager(WorkManagerMBean bean, WorkManagerShutdownAction workManagerShutdown) {
      if (use81ExecuteQueues) {
         return null;
      } else if (bean != null && bean.getIgnoreStuckThreads()) {
         return new StuckThreadManager();
      } else if (workManagerShutdown == null && this.adminModeAction == null && GlobalWorkManagerComponentsFactory.getInstance().getServerFailedAction() == null) {
         this.debug("NO global ServerFailureAction found. No stuck thread action !");
         return null;
      } else {
         this.debug("Global ServerFailureAction FOUND. Creating StuckThreadManager !");
         return new StuckThreadManager(workManagerShutdown, this.adminModeAction, GlobalWorkManagerComponentsFactory.getInstance().getServerFailedAction());
      }
   }

   private StuckThreadManager getStuckThreadManager() {
      return this.getStuckThreadManager((WorkManagerBean)((WorkManagerBean)null), (WorkManagerShutdownAction)null);
   }

   private WorkManagerShutdownAction getShutdownAction(WorkManagerBean bean) {
      if (use81ExecuteQueues) {
         return null;
      } else if (bean == null) {
         return null;
      } else if (bean.getIgnoreStuckThreads()) {
         return null;
      } else {
         WorkManagerShutdownTriggerBean trigger = bean.getWorkManagerShutdownTrigger();
         if (trigger != null && trigger.getStuckThreadCount() > 0) {
            this.debug("Found WorkManagerShutdownTriggerMBean with " + trigger.getMaxStuckThreadTime() + " seconds and " + trigger.getStuckThreadCount() + " count");
            return new WorkManagerShutdownAction(trigger);
         } else {
            return null;
         }
      }
   }

   private StuckThreadManager getStuckThreadManager(WorkManagerBean bean, WorkManagerShutdownAction workManagerShutdown) {
      if (use81ExecuteQueues) {
         return null;
      } else if (bean != null && bean.getIgnoreStuckThreads()) {
         return new StuckThreadManager();
      } else if (workManagerShutdown == null && this.adminModeAction == null && GlobalWorkManagerComponentsFactory.getInstance().getServerFailedAction() == null) {
         this.debug("NO global ServerFailureAction found. No stuck thread action !");
         return null;
      } else {
         this.debug("Global ServerFailureAction FOUND. Creating StuckThreadManager !");
         return new StuckThreadManager(workManagerShutdown, this.adminModeAction, GlobalWorkManagerComponentsFactory.getInstance().getServerFailedAction());
      }
   }

   private OverloadManager getOverload(WorkManagerBean bean) {
      if (use81ExecuteQueues) {
         return null;
      } else {
         CapacityBean cap = bean.getCapacity();
         if (cap != null) {
            OverloadManager om = new OverloadManager(cap.getName(), cap.getCount());
            ((DescriptorBean)cap).addBeanUpdateListener(new OverloadManagerBeanUpdateListener(om));
            return om;
         } else {
            String name = bean.getCapacityName();
            return name != null ? this.getOverload(name) : null;
         }
      }
   }

   private MaxThreadsConstraint getMaxConstraint(WorkManagerBean bean) throws DeploymentException {
      MaxThreadsConstraintBean max = bean.getMaxThreadsConstraint();
      if (max != null) {
         PartitionMaxThreadsConstraint partitionMaxThreadsConstraint = GlobalWorkManagerComponentsFactory.getInstance(this.partitionName).getPartitionMaxThreadsConstraint();
         String poolName = max.getPoolName();
         MaxThreadsConstraint mtc = null;
         if (poolName != null) {
            mtc = new PoolBasedMaxThreadsConstraint(max.getName(), poolName, this.weblogicApplicationBean, partitionMaxThreadsConstraint);
         } else {
            mtc = new MaxThreadsConstraint(max.getName(), max.getCount(), max.getQueueSize(), partitionMaxThreadsConstraint);
            ((DescriptorBean)max).addBeanUpdateListener(new MaxThreadsConstraintBeanUpdateListener((MaxThreadsConstraint)mtc));
         }

         ((MaxThreadsConstraint)mtc).setShared(false);
         this.inlineMaxMap.put(max.getName(), mtc);
         return (MaxThreadsConstraint)mtc;
      } else {
         String name = bean.getMaxThreadsConstraintName();
         return name != null ? this.getMaxConstraint(name) : null;
      }
   }

   private MinThreadsConstraint getMinConstraint(WorkManagerBean bean) {
      MinThreadsConstraintBean min = bean.getMinThreadsConstraint();
      if (min != null) {
         PartitionMinThreadsConstraint partitionMinThreadsConstraint = GlobalWorkManagerComponentsFactory.getInstance(this.partitionName).getPartitionMinThreadsConstraint();
         MinThreadsConstraint mtc = new MinThreadsConstraint(min.getName(), min.getCount(), partitionMinThreadsConstraint);
         mtc.setShared(false);
         this.inlineMinMap.put(min.getName(), mtc);
         ((DescriptorBean)min).addBeanUpdateListener(new MinThreadsConstraintBeanUpdateListener(mtc));
         return mtc;
      } else {
         String name = bean.getMinThreadsConstraintName();
         return name != null ? this.getMinConstraint(name) : null;
      }
   }

   private RequestClass getPolicy(WorkManagerBean bean) {
      if (use81ExecuteQueues) {
         return null;
      } else {
         FairShareRequestClassBean fairShare = bean.getFairShareRequestClass();
         if (fairShare != null) {
            PartitionFairShare partitionFairShare = GlobalWorkManagerComponentsFactory.getInstance(this.partitionName).getPartitionFairShare();
            FairShareRequestClass rc = new FairShareRequestClass(fairShare.getName(), fairShare.getFairShare(), partitionFairShare);
            ((DescriptorBean)fairShare).addBeanUpdateListener(new FairShareRequestClassBeanUpdateListener(rc));
            return rc;
         } else {
            ResponseTimeRequestClassBean responseTime = bean.getResponseTimeRequestClass();
            if (responseTime != null) {
               PartitionFairShare partitionFairShare = GlobalWorkManagerComponentsFactory.getInstance(this.partitionName).getPartitionFairShare();
               ResponseTimeRequestClass rc = new ResponseTimeRequestClass(responseTime.getName(), responseTime.getGoalMs(), partitionFairShare);
               ((DescriptorBean)responseTime).addBeanUpdateListener(new ResponseTimeRequestClassBeanUpdateListener(rc));
               return rc;
            } else {
               ContextRequestClassBean context = bean.getContextRequestClass();
               if (context != null) {
                  ContextCaseBean[] cases = context.getContextCases();
                  if (cases != null) {
                     PartitionFairShare partitionFairShare = GlobalWorkManagerComponentsFactory.getInstance(this.partitionName).getPartitionFairShare();
                     ContextRequestClass cdp = new ContextRequestClass(context.getName(), partitionFairShare);

                     for(int count = 0; count < cases.length; ++count) {
                        ContextCaseBean casee = cases[count];
                        RequestClass requestClass;
                        if (casee.getUserName() != null) {
                           requestClass = this.getRequestClass(casee);
                           if (requestClass != null) {
                              cdp.addUser(casee.getUserName(), requestClass);
                           } else {
                              WorkManagerLogger.logMissingContextCaseRequestClass("user-name", casee.getUserName());
                           }
                        } else if (casee.getGroupName() != null) {
                           requestClass = this.getRequestClass(casee);
                           if (requestClass != null) {
                              cdp.addGroup(casee.getGroupName(), requestClass);
                           } else {
                              WorkManagerLogger.logMissingContextCaseRequestClass("group-name", casee.getGroupName());
                           }
                        }
                     }

                     return cdp;
                  }
               }

               String name = bean.getRequestClassName();
               return name != null ? this.getRequestClass(name) : null;
            }
         }
      }
   }

   private RequestClass getRequestClass(String name) {
      RequestClass requestClass = (RequestClass)this.requestClassMap.get(name);
      if (requestClass != null) {
         return requestClass;
      } else {
         return use81ExecuteQueues ? null : GlobalWorkManagerComponentsFactory.getInstance(this.partitionName).findRequestClass(name);
      }
   }

   private RequestClass getRequestClass(ContextCaseBean bean) {
      String name = bean.getRequestClassName();
      if (name != null) {
         return this.getRequestClass(name);
      } else {
         FairShareRequestClassBean fairShare = bean.getFairShareRequestClass();
         if (fairShare != null) {
            PartitionFairShare partitionFairShare = GlobalWorkManagerComponentsFactory.getInstance(this.partitionName).getPartitionFairShare();
            FairShareRequestClass rc = new FairShareRequestClass(fairShare.getName(), fairShare.getFairShare(), partitionFairShare);
            ((DescriptorBean)fairShare).addBeanUpdateListener(new FairShareRequestClassBeanUpdateListener(rc));
            return rc;
         } else {
            ResponseTimeRequestClassBean responseTime = bean.getResponseTimeRequestClass();
            if (responseTime != null) {
               PartitionFairShare partitionFairShare = GlobalWorkManagerComponentsFactory.getInstance(this.partitionName).getPartitionFairShare();
               ResponseTimeRequestClass rc = new ResponseTimeRequestClass(responseTime.getName(), responseTime.getGoalMs(), partitionFairShare);
               ((DescriptorBean)responseTime).addBeanUpdateListener(new ResponseTimeRequestClassBeanUpdateListener(rc));
               return rc;
            } else {
               return null;
            }
         }
      }
   }

   private MaxThreadsConstraint getMaxConstraint(String name) {
      MaxThreadsConstraint max = (MaxThreadsConstraint)this.maxMap.get(name);
      if (max != null) {
         return max;
      } else {
         return use81ExecuteQueues ? null : GlobalWorkManagerComponentsFactory.getInstance(this.partitionName).findMaxThreadsConstraint(name);
      }
   }

   private MinThreadsConstraint getMinConstraint(String name) {
      MinThreadsConstraint min = (MinThreadsConstraint)this.minMap.get(name);
      if (min != null) {
         return min;
      } else {
         return use81ExecuteQueues ? null : GlobalWorkManagerComponentsFactory.getInstance(this.partitionName).findMinThreadsConstraint(name);
      }
   }

   private OverloadManager getOverload(String name) {
      OverloadManager overload = (OverloadManager)this.overloadMap.get(name);
      if (overload != null) {
         return overload;
      } else {
         return use81ExecuteQueues ? null : GlobalWorkManagerComponentsFactory.getInstance(this.partitionName).findOverloadManager(name);
      }
   }

   public synchronized WorkManager get(String moduleName, String workManagerName) {
      return this.get(moduleName, workManagerName, true);
   }

   public synchronized WorkManager get(String moduleName, String workManagerName, boolean warnIfNotFound) {
      WorkManager wm = null;
      if (moduleName != null) {
         String name = moduleName + "@" + workManagerName;
         wm = (WorkManager)this.workManagers.get(name);
         if (wm != null) {
            this.debug("found WorkManager for module '" + moduleName + "' app '" + this.applicationName + "' wmName '" + workManagerName);
            return wm;
         }
      }

      wm = (WorkManager)this.workManagers.get(workManagerName);
      if (wm != null) {
         this.debug("found WorkManager for app '" + this.applicationName + "' wmName '" + workManagerName);
         return wm;
      } else {
         this.debug("No WorkManager for wmName '" + workManagerName + "' in app '" + this.applicationName + "', module '" + moduleName + "'. returning default");
         if (warnIfNotFound) {
            this.logWorkManagerNotFound(workManagerName);
         }

         return this.getDefault();
      }
   }

   private void logWorkManagerNotFound(String workManagerName) {
      if (workManagerName != null && workManagerName.toLowerCase(Locale.ENGLISH).indexOf("default") == -1) {
         WorkManagerLogger.logWorkManagerNotFound(workManagerName, this.applicationName);
      }

   }

   public synchronized void removeModuleEntries(String moduleName) {
      Iterator iter = this.workManagers.entrySet().iterator();

      while(iter.hasNext()) {
         Map.Entry entry = (Map.Entry)iter.next();
         if (((String)entry.getKey()).startsWith(moduleName + "@")) {
            ((WorkManagerService)entry.getValue()).cleanup();
            iter.remove();
         }
      }

   }

   public synchronized void releaseModuleTasks(String moduleName) {
      Iterator iter = this.workManagers.values().iterator();
      ModuleWorkFilter moduleWorkFilter = new ModuleWorkFilter(this.applicationName, moduleName);

      while(iter.hasNext()) {
         WorkManager wm = ((WorkManagerService)iter.next()).getDelegate();
         RequestManager.getInstance().releaseExecutingRequestFor(wm, moduleWorkFilter);
      }

   }

   public synchronized void activateModuleEntries(String moduleName) {
      if (this.state == 1) {
         Iterator iter = this.workManagers.entrySet().iterator();

         while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            if (((String)entry.getKey()).startsWith(moduleName + "@")) {
               WorkManagerService workManagerService = (WorkManagerService)entry.getValue();
               if (workManagerService.getState() != 1) {
                  workManagerService.start();
               }
            }
         }
      }

   }

   public synchronized List getWorkManagers(String moduleName) {
      if (moduleName == null) {
         return Collections.EMPTY_LIST;
      } else {
         ArrayList list = new ArrayList();
         Iterator iter = this.workManagers.keySet().iterator();

         while(iter.hasNext()) {
            String wmName = (String)iter.next();
            if (wmName.startsWith(moduleName + "@")) {
               list.add(this.workManagers.get(wmName));
            }
         }

         return list;
      }
   }

   public WorkManager getDefault() {
      return (WorkManager)this.workManagers.get("default");
   }

   private void addWorkManagerRuntime(WorkManagerService wmService, RuntimeMBean parent) throws DeploymentException {
      WorkManager wm = wmService.getDelegate();

      try {
         if (!use81ExecuteQueues) {
            WorkManagerRuntimeMBean wmRuntime = WorkManagerRuntimeMBeanImpl.getWorkManagerRuntime(wm, this.applicationRuntimeMBean, parent);
            this.runtimeMBeans.put(wm.getName(), wmRuntime);
            this.applicationWork.addWorkManager(wmRuntime);
         }
      } catch (ManagementException var5) {
         throw new DeploymentException("unable to create WorkManagerRuntimeMBean for " + wm.getName(), var5);
      }
   }

   private void debug(String str) {
      if (debugWMCollection.isEnabled()) {
         WorkManagerLogger.logDebug("<WMCollection>" + str);
      }

   }

   private synchronized WorkManagerService addWorkManager(WorkManagerMBean workManagerMBean) throws DeploymentException {
      WorkManagerLogger.logCreatingServiceFromMBean(this.applicationName, workManagerMBean.getName());
      WorkManagerShutdownAction shutdownAction = this.getShutdownAction(workManagerMBean);
      StuckThreadManager stmgr = this.getStuckThreadManager(workManagerMBean, shutdownAction);
      WorkManagerService wmService = WorkManagerServiceImpl.createService(this.partitionName, this.applicationName, (String)null, workManagerMBean, stmgr);
      if (shutdownAction != null) {
         shutdownAction.setWorkManagerService(wmService);
      }

      if (this.internal) {
         wmService.setInternal();
      }

      this.workManagers.put(workManagerMBean.getName(), wmService);
      this.addWorkManagerRuntime(wmService, this.applicationRuntimeMBean);
      return wmService;
   }

   private synchronized void removeWorkManager(WorkManagerMBean wm) {
      WorkManagerRuntimeMBeanImpl wmRuntime = (WorkManagerRuntimeMBeanImpl)this.runtimeMBeans.remove(wm.getName());
      if (wmRuntime != null) {
         try {
            if (debugWMCollection.isEnabled()) {
               this.debug("Removing work manager instance for " + wm.getName() + " from " + this.applicationName + " runtime");
            }

            this.workManagers.remove(wm.getName());
            wmRuntime.unregister();
         } catch (ManagementException var4) {
            if (debugWMCollection.isEnabled()) {
               this.debug("Unable to unregister RuntimeMBean for WorkManager " + wm.getName());
            }
         }
      }

   }

   public void prepareUpdate(BeanUpdateEvent event) {
   }

   public void activateUpdate(BeanUpdateEvent event) {
      if (!this.internal) {
         if (event.getSourceBean() instanceof SelfTuningMBean) {
            BeanUpdateEvent.PropertyUpdate[] changes = event.getUpdateList();
            WorkManagerMBean wm = null;
            if (changes == null) {
               return;
            }

            for(int i = 0; i < changes.length; ++i) {
               if (changes[i].getAddedObject() instanceof WorkManagerMBean) {
                  wm = (WorkManagerMBean)changes[i].getAddedObject();
                  if (wm.isApplicationScope()) {
                     try {
                        WorkManagerService wmService = this.configureWorkManagerService(wm);
                        if (wmService != null) {
                           this.startWorkManagerIfRequired(wmService);
                        }
                     } catch (DeploymentException var6) {
                        if (debugWMCollection.isEnabled()) {
                           this.debug("Unable to add WorkManagerMBean '" + wm.getName() + "' to application '" + this.applicationName + "'");
                        }
                     }
                  }
               } else if (changes[i].getRemovedObject() instanceof WorkManagerMBean) {
                  wm = (WorkManagerMBean)changes[i].getRemovedObject();
                  this.removeWorkManager(wm);
               }
            }
         }

      }
   }

   private synchronized void startWorkManagerIfRequired(WorkManagerService wmService) {
      if (this.state == 1) {
         wmService.start();
      }

   }

   public synchronized int getState() {
      return this.state;
   }

   public synchronized void setState(int state) {
      this.state = state;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   public synchronized void close() {
      SelfTuningMBean selfTuningConfig = ManagementService.getRuntimeAccess(kernelId).getDomain().getSelfTuning();
      if (selfTuningConfig != null) {
         selfTuningConfig.removeBeanUpdateListener(this);
      }

      PartitionMBean partitionMBean = this.getPartitionMBean();
      if (partitionMBean != null) {
         SelfTuningMBean partitionSelfTuningConfig = partitionMBean.getSelfTuning();
         if (partitionSelfTuningConfig != null) {
            partitionSelfTuningConfig.removeBeanUpdateListener(this);
         }
      }

      Iterator iter = this.workManagers.values().iterator();

      while(iter.hasNext()) {
         WorkManagerService workManager = (WorkManagerService)iter.next();
         workManager.cleanup();
      }

      iter = this.requestClassMap.values().iterator();

      while(iter.hasNext()) {
         RequestClass rc = (RequestClass)iter.next();
         rc.cleanup();
      }

      Iterator var8 = this.minMap.values().iterator();

      MinThreadsConstraint minThreadsConstraint;
      while(var8.hasNext()) {
         minThreadsConstraint = (MinThreadsConstraint)var8.next();
         minThreadsConstraint.cleanup();
      }

      var8 = this.inlineMinMap.values().iterator();

      while(var8.hasNext()) {
         minThreadsConstraint = (MinThreadsConstraint)var8.next();
         minThreadsConstraint.cleanup();
      }

      var8 = this.maxMap.values().iterator();

      MaxThreadsConstraint maxThreadsConstraint;
      while(var8.hasNext()) {
         maxThreadsConstraint = (MaxThreadsConstraint)var8.next();
         maxThreadsConstraint.cleanup();
      }

      var8 = this.inlineMaxMap.values().iterator();

      while(var8.hasNext()) {
         maxThreadsConstraint = (MaxThreadsConstraint)var8.next();
         maxThreadsConstraint.cleanup();
      }

      RequestManager.getInstance().cleanupThreadPoolTLs();
   }

   private PartitionMBean getPartitionMBean() {
      ComponentInvocationContext componentInvocationContext = PartitionUtility.getCurrentComponentInvocationContext();
      return componentInvocationContext != null && !componentInvocationContext.isGlobalRuntime() ? ManagementService.getRuntimeAccess(kernelId).getDomain().lookupPartition(componentInvocationContext.getPartitionName()) : null;
   }

   private WorkManagerMBean[] getWorkManagerMBeans(SelfTuningMBean domainSelfTuningConfig, SelfTuningMBean partitionSelfTuningConfig) {
      if (partitionSelfTuningConfig == null) {
         return domainSelfTuningConfig == null ? null : domainSelfTuningConfig.getWorkManagers();
      } else if (domainSelfTuningConfig == null) {
         return partitionSelfTuningConfig.getWorkManagers();
      } else {
         WorkManagerMBean[] domainWorkManagerMBeans = domainSelfTuningConfig.getWorkManagers();
         WorkManagerMBean[] partitionWorkManagerMBeans = partitionSelfTuningConfig.getWorkManagers();
         int size = (domainWorkManagerMBeans == null ? 0 : domainWorkManagerMBeans.length) + (partitionWorkManagerMBeans == null ? 0 : partitionWorkManagerMBeans.length);
         HashMap workManagerMBeanHashMap = new HashMap(size);
         WorkManagerMBean[] workManagerMBeans;
         int var8;
         int var9;
         WorkManagerMBean workManagerMBean;
         if (domainWorkManagerMBeans != null) {
            workManagerMBeans = domainWorkManagerMBeans;
            var8 = domainWorkManagerMBeans.length;

            for(var9 = 0; var9 < var8; ++var9) {
               workManagerMBean = workManagerMBeans[var9];
               workManagerMBeanHashMap.put(workManagerMBean.getName(), workManagerMBean);
            }
         }

         if (partitionWorkManagerMBeans != null) {
            workManagerMBeans = partitionWorkManagerMBeans;
            var8 = partitionWorkManagerMBeans.length;

            for(var9 = 0; var9 < var8; ++var9) {
               workManagerMBean = workManagerMBeans[var9];
               workManagerMBeanHashMap.put(workManagerMBean.getName(), workManagerMBean);
            }
         }

         workManagerMBeans = new WorkManagerMBean[workManagerMBeanHashMap.size()];
         workManagerMBeanHashMap.values().toArray(workManagerMBeans);
         return workManagerMBeans;
      }
   }

   public WorkManagerRuntimeMBean getRuntimeMBean(WorkManager wm) {
      return (WorkManagerRuntimeMBean)this.runtimeMBeans.get(wm.getName());
   }

   static {
      use81ExecuteQueues = ManagementService.getRuntimeAccess(kernelId).getServer().getUse81StyleExecuteQueues();
   }
}
