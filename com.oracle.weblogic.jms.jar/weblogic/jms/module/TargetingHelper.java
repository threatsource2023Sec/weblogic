package weblogic.jms.module;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.DeploymentManager;
import weblogic.application.ModuleException;
import weblogic.application.ModuleListener;
import weblogic.application.ModuleListenerCtx;
import weblogic.application.SubModuleListenerCtx;
import weblogic.application.SubModuleListenerCtxImpl;
import weblogic.cluster.migration.DynamicLoadbalancer;
import weblogic.cluster.migration.MigratableGroupConfig;
import weblogic.cluster.migration.MigrationManager;
import weblogic.cluster.migration.DynamicLoadbalancer.State;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.j2ee.descriptor.wl.TargetableBean;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.common.JMSDebug;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.utils.GenericDeploymentManager;
import weblogic.management.utils.jmsdlb.DLClusterRegistration;
import weblogic.management.utils.jmsdlb.DLDynamicPlacement;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public class TargetingHelper {
   static final int UNCHANGED = 0;
   static final int ADDED = 1;
   static final int REMOVED = 2;
   static final int CHANGED = 3;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private String serverName;
   private String clusterName;
   private boolean hasUpdate = false;
   private TargetMBean updatedTargetMBean = null;
   private int updateAction = -1;
   private HashMap acceptedLocalGroups;
   private HashMap acceptedGroups;
   String EARModuleName;
   private String appId;
   private String moduleUri;
   private ModuleListener.State state;
   public HashMap subModuleListenerContextMap;
   private final DeploymentManager deploymentManager;

   TargetingHelper(JMSModule module, DomainMBean domain, String paramEARModuleName, String applicationId) throws ModuleException {
      this.state = ModuleListener.STATE_NEW;
      this.deploymentManager = DeploymentManager.getDeploymentManager();
      this.acceptedLocalGroups = new HashMap();
      this.acceptedGroups = new HashMap();
      this.subModuleListenerContextMap = new HashMap();
      this.moduleUri = module.getId();
      this.appId = applicationId;
      ServerMBean myServer = ManagementService.getRuntimeAccess(kernelId).getServer();
      this.serverName = myServer.getName();
      ClusterMBean myCluster = myServer.getCluster();
      if (myCluster != null) {
         this.clusterName = myCluster.getName();
      }

      this.EARModuleName = paramEARModuleName;
      BasicDeploymentMBean bdm = module.getBasicDeployment(domain);
      this.analyzeTargets(bdm, (UpdateInformation)null, module.getAppCtx());
   }

   public String getServerName() {
      return this.serverName;
   }

   public void setHasUpdate(boolean hasUpdate) {
      this.hasUpdate = hasUpdate;
   }

   public boolean isHasUpdate() {
      return this.hasUpdate;
   }

   public void setUpdatedTargetMBean(TargetMBean targetMBean) {
      this.updatedTargetMBean = targetMBean;
   }

   public TargetMBean getUpdatedTargetMBean() {
      return this.updatedTargetMBean;
   }

   public void setUpdateAction(int action) {
      this.updateAction = action;
   }

   public int getUpdateAction() {
      return this.updateAction;
   }

   private void fixupList(Map mapOfGroupLists, DomainMBean domain) {
      Iterator listIterator = mapOfGroupLists.keySet().iterator();

      while(listIterator.hasNext()) {
         String groupName = (String)listIterator.next();
         List currentList = (List)mapOfGroupLists.get(groupName);
         LinkedList fixedList = new LinkedList();
         SubModuleListenerCtx smlctx = (SubModuleListenerCtxImpl)this.subModuleListenerContextMap.get(groupName);
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("TargetingHelper:activate(): Got " + smlctx + " for " + groupName);
            this.dumpSubModuleListenerContext((SubModuleListenerCtxImpl)smlctx);
         }

         this.begin(smlctx, ModuleListener.STATE_ADMIN);
         Iterator groupIterator = currentList.iterator();

         while(groupIterator.hasNext()) {
            TargetMBean replaceMe = (TargetMBean)groupIterator.next();
            String targetName = replaceMe.getName();
            TargetMBean with = null;
            if (replaceMe instanceof ClusterMBean) {
               with = domain.lookupCluster(targetName);
            } else if (replaceMe instanceof ServerMBean) {
               with = domain.lookupServer(targetName);
            } else if (replaceMe instanceof MigratableTargetMBean) {
               with = domain.lookupMigratableTarget(targetName);
            } else if (replaceMe instanceof JMSServerMBean) {
               with = domain.lookupJMSServer(targetName);
            } else if (replaceMe instanceof SAFAgentMBean) {
               with = domain.lookupSAFAgent(targetName);
            } else if (replaceMe instanceof VirtualTargetMBean) {
               with = domain.lookupVirtualTarget(targetName);
            }

            if (with == null) {
               this.failed(smlctx, ModuleListener.STATE_ADMIN);
               throw new AssertionError("Could not find a target of name " + targetName + " in module=" + this.appId + " URI=" + this.moduleUri);
            }

            fixedList.add(with);
         }

         this.end(smlctx, ModuleListener.STATE_ADMIN);
         mapOfGroupLists.put(groupName, fixedList);
      }

   }

   void activate() {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      Iterator smlctxIterator = this.subModuleListenerContextMap.keySet().iterator();

      while(smlctxIterator.hasNext()) {
         String smlctxName = (String)smlctxIterator.next();
         SubModuleListenerCtx smlctx = (SubModuleListenerCtxImpl)this.subModuleListenerContextMap.get(smlctxName);
         TargetMBean replaceMe = ((ModuleListenerCtx)smlctx).getTarget();
         TargetMBean with = null;
         if (replaceMe != null) {
            String targetName = replaceMe.getName();
            if (replaceMe instanceof ClusterMBean) {
               with = domain.lookupCluster(targetName);
            } else if (replaceMe instanceof ServerMBean) {
               with = domain.lookupServer(targetName);
            } else if (replaceMe instanceof MigratableTargetMBean) {
               with = domain.lookupMigratableTarget(targetName);
            } else if (replaceMe instanceof JMSServerMBean) {
               with = domain.lookupJMSServer(targetName);
            } else if (replaceMe instanceof SAFAgentMBean) {
               with = domain.lookupSAFAgent(targetName);
            } else if (replaceMe instanceof VirtualTargetMBean) {
               with = domain.lookupVirtualTarget(targetName);
            }

            SubModuleListenerCtx fixedSmlctx = new SubModuleListenerCtxImpl(((ModuleListenerCtx)smlctx).getApplicationId(), ((ModuleListenerCtx)smlctx).getModuleUri(), ((ModuleListenerCtx)smlctx).getType(), (TargetMBean)with, smlctx.getSubModuleName(), smlctx.getSubModuleTargets());
            this.subModuleListenerContextMap.put(smlctxName, fixedSmlctx);
         }
      }

      this.fixupList(this.acceptedLocalGroups, domain);
      this.fixupList(this.acceptedGroups, domain);
      this.state = ModuleListener.STATE_ADMIN;
   }

   void adminToProduction() {
      if (this.acceptedGroups != null) {
         Iterator subModuleNames = this.acceptedGroups.keySet().iterator();

         while(subModuleNames.hasNext()) {
            String subModuleName = (String)subModuleNames.next();
            SubModuleListenerCtx smlctx = (SubModuleListenerCtx)this.subModuleListenerContextMap.get(subModuleName);
            this.begin(smlctx, ModuleListener.STATE_ACTIVE);
            this.end(smlctx, ModuleListener.STATE_ACTIVE);
         }
      }

      this.state = ModuleListener.STATE_PREPARED;
   }

   void productionToAdmin() {
      if (this.acceptedGroups != null) {
         Iterator subModuleNames = this.acceptedGroups.keySet().iterator();

         while(subModuleNames.hasNext()) {
            String subModuleName = (String)subModuleNames.next();
            SubModuleListenerCtx smlctx = (SubModuleListenerCtx)this.subModuleListenerContextMap.get(subModuleName);
            this.begin(smlctx, ModuleListener.STATE_ADMIN);
            this.end(smlctx, ModuleListener.STATE_ADMIN);
         }
      }

      this.state = ModuleListener.STATE_PREPARED;
   }

   void unprepare() {
      if (this.acceptedGroups != null) {
         Iterator subModuleNames = this.acceptedGroups.keySet().iterator();

         while(subModuleNames.hasNext()) {
            String subModuleName = (String)subModuleNames.next();
            SubModuleListenerCtx smlctx = (SubModuleListenerCtx)this.subModuleListenerContextMap.get(subModuleName);
            this.begin(smlctx, ModuleListener.STATE_NEW);
            this.end(smlctx, ModuleListener.STATE_NEW);
         }
      }

      this.state = ModuleListener.STATE_NEW;
   }

   void deactivate() {
      if (this.acceptedGroups != null) {
         Iterator subModuleNames = this.acceptedGroups.keySet().iterator();

         while(subModuleNames.hasNext()) {
            String subModuleName = (String)subModuleNames.next();
            SubModuleListenerCtx smlctx = (SubModuleListenerCtx)this.subModuleListenerContextMap.get(subModuleName);
            this.begin(smlctx, ModuleListener.STATE_PREPARED);
            this.end(smlctx, ModuleListener.STATE_PREPARED);
         }
      }

      this.state = ModuleListener.STATE_PREPARED;
   }

   void prepareUpdate(BasicDeploymentMBean bdm, UpdateInformation info, ApplicationContextInternal appCtx) throws ModuleException {
      if (info != null) {
         HashMap addedGroups = new HashMap();
         info.setAddedGroups(addedGroups);
         HashMap addedLocalGroups = new HashMap();
         info.setAddedLocalGroups(addedLocalGroups);
         HashMap changedGroups = new HashMap();
         info.setChangedGroups(changedGroups);
         HashMap changedLocalGroups = new HashMap();
         info.setChangedLocalGroups(changedLocalGroups);
         if (bdm == null) {
            info.setRemovedLocalGroups(new HashMap());
            info.setRemovedGroups(new HashMap());
         } else {
            this.analyzeTargets(bdm, info, appCtx);
            removeAll(info.getChangedGroups(), info.getRemovedGroups());
            removeAll(info.getChangedLocalGroups(), info.getRemovedLocalGroups());
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               Iterator it = changedLocalGroups.keySet().iterator();

               String groupName;
               while(it.hasNext()) {
                  groupName = (String)it.next();
                  JMSDebug.JMSModule.debug("Group " + groupName + " is changed locally in an update");
               }

               it = changedGroups.keySet().iterator();

               while(it.hasNext()) {
                  groupName = (String)it.next();
                  JMSDebug.JMSModule.debug("Group " + groupName + " is changed in an update");
               }
            }

         }
      }
   }

   private static void removeAll(HashMap removeFrom, HashMap removeThese) {
      Set keySet = removeThese.keySet();
      Iterator it = keySet.iterator();

      while(it.hasNext()) {
         Object key = it.next();
         removeFrom.remove(key);
      }

   }

   void activateUpdate(UpdateInformation info) {
      if (info != null) {
         this.acceptedLocalGroups.putAll(info.getAddedLocalGroups());
         this.acceptedGroups.putAll(info.getAddedGroups());
         this.acceptedLocalGroups.putAll(info.getChangedLocalGroups());
         this.acceptedGroups.putAll(info.getChangedGroups());
         removeAll(this.acceptedLocalGroups, info.getRemovedLocalGroups());
         removeAll(this.acceptedGroups, info.getRemovedGroups());
         info.clearTargetUpdates();
         this.activate();
      }
   }

   void rollbackUpdate(UpdateInformation info) {
      if (info != null) {
         info.clearTargetUpdates();
      }

   }

   private boolean isLocallyTargeted(TargetMBean targetBean) {
      return isLocallyTargeted(targetBean, this.clusterName, this.serverName, this.hasUpdate, this.updatedTargetMBean, this.updateAction);
   }

   public static boolean isLocallyTargeted(TargetMBean targetBean, String clusterName, String serverName) {
      return isLocallyTargeted(targetBean, clusterName, serverName, false, (TargetMBean)null, -1);
   }

   public static boolean isLocallyTargeted(TargetMBean targetBean, String clusterName, String serverName, boolean lvHasUpdate, TargetMBean lvUpdatedTargetMBean, int lvUpdateAction) {
      if (targetBean == null) {
         return false;
      } else {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("TargetingHelper.isLocallyTargeted(): lvhasUpdate=" + lvHasUpdate + ", lvUpdatedTargetMBean=" + (lvUpdatedTargetMBean != null ? lvUpdatedTargetMBean.getName() : "") + ", lvUpdateAction=" + lvUpdateAction + ", clusterName=" + clusterName + ", serverName=" + serverName + ", targetBean=" + targetBean + ", targetBean Name=" + targetBean.getName() + ", targetBean Type=" + targetBean.getType());
         }

         if (targetBean instanceof JMSServerMBean || targetBean instanceof SAFAgentMBean) {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("TargetingHelper.isLocallyTargeted(): current targetBean=" + targetBean + ", current targetBean Name=" + targetBean.getName() + ", current targetBean Type=" + targetBean.getType());
            }

            TargetMBean[] targets = ((DeploymentMBean)targetBean).getTargets();
            if (targets.length <= 0) {
               return false;
            }

            if (targets.length != 1 || !(targets[0] instanceof MigratableTargetMBean)) {
               for(int i = 0; i < targets.length; ++i) {
                  String targetName = targets[i].getName();
                  if (targetName.equals(serverName) || targetName.equals(clusterName)) {
                     return true;
                  }
               }

               return false;
            }

            targetBean = targets[0];
         }

         if (targetBean instanceof MigratableTargetMBean) {
            MigratableTargetMBean mig = (MigratableTargetMBean)targetBean;
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("TargetingHelper.isLocallyTargeted(): current MigratableTargetBean=" + targetBean + ", current MigratableTargetBean Name=" + targetBean.getName() + ", mig.getHostingServer()=" + (mig.getHostingServer() != null ? mig.getHostingServer().getName() : "null") + ", ups=" + (mig.getUserPreferredServer() != null ? mig.getUserPreferredServer().getName() : "null"));
            }

            if (lvHasUpdate && lvUpdateAction != -1 && lvUpdatedTargetMBean != null) {
               TargetMBean[] lvUpdatedTargets = ((DeploymentMBean)lvUpdatedTargetMBean).getTargets();
               if (lvUpdatedTargets.length == 1 && lvUpdatedTargets[0].equals(mig)) {
                  if (lvUpdateAction != 1 && lvUpdateAction != 2) {
                     return false;
                  }

                  return true;
               }
            }

            if (mig.getMigrationPolicy().equals("manual")) {
               return isMigratableTargetActive(mig.getName());
            }

            ServerMBean hostingSrvr = mig.getHostingServer();
            if (hostingSrvr == null) {
               return false;
            }

            if (hostingSrvr.getName().equals(serverName)) {
               return true;
            }
         }

         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("TargetingHelper.isLocallyTargeted(): current targetBean=" + targetBean + ", current targetBean Name=" + targetBean.getName() + ", current targetBean Type=" + targetBean.getType());
         }

         String targetName = targetBean.getName();
         boolean isLocallyTargeted = targetName.equals(serverName) || targetName.equals(clusterName);
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("TargetingHelper.isLocallyTargeted():Returning: " + isLocallyTargeted);
         }

         return isLocallyTargeted;
      }
   }

   private static void compareToBaseline(String groupName, UpdateInformation info, HashMap baselineGroups, HashMap addedGroups, HashMap changedGroups, HashMap removedGroups, TargetMBean realTarget, boolean isCurrent) {
      List baselineList = (List)baselineGroups.get(groupName);
      Object changedList;
      if (baselineList == null) {
         if (isCurrent) {
            if (addedGroups.containsKey(groupName)) {
               changedList = (List)addedGroups.get(groupName);
            } else {
               changedList = new LinkedList();
               addedGroups.put(groupName, changedList);
            }

            ((List)changedList).add(realTarget);
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("Group " + groupName + " added in an update");
            }

         }
      } else if (isCurrent) {
         removedGroups.remove(groupName);
         if (!changedGroups.containsKey(groupName)) {
            changedList = new LinkedList();
            changedGroups.put(groupName, changedList);
         } else {
            changedList = (List)changedGroups.get(groupName);
         }

         ((List)changedList).add(realTarget);
      }
   }

   private static void addToGroupMap(Map groupMap, String groupName, Object putThis) {
      Object list;
      if (groupMap.containsKey(groupName)) {
         list = (List)groupMap.get(groupName);
      } else {
         list = new LinkedList();
         groupMap.put(groupName, list);
      }

      ((List)list).add(putThis);
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Group " + groupName + " added");
      }

   }

   private void updateMaps(String groupName, TargetMBean targetBean, UpdateInformation info, TargetMBean realTarget) throws ModuleException {
      boolean isLocal;
      if (this.isClusterTargetedSingletonJMSServer(targetBean, realTarget)) {
         isLocal = this.isSingletonJMSServerLocallyActivated((JMSServerMBean)realTarget);
      } else {
         isLocal = this.isLocallyTargeted(targetBean);
      }

      if (info != null) {
         compareToBaseline(groupName, info, this.acceptedLocalGroups, info.getAddedLocalGroups(), info.getChangedLocalGroups(), info.getRemovedLocalGroups(), realTarget, isLocal);
         compareToBaseline(groupName, info, this.acceptedGroups, info.getAddedGroups(), info.getChangedGroups(), info.getRemovedGroups(), realTarget, true);
      } else {
         if (isLocal) {
            addToGroupMap(this.acceptedLocalGroups, groupName, realTarget);
         }

         addToGroupMap(this.acceptedGroups, groupName, realTarget);
      }

   }

   private SubDeploymentMBean[] getSubModules(BasicDeploymentMBean moduleBean, SubDeploymentMBean[] librarySubDeployments) {
      SubDeploymentMBean[] parentBeans = moduleBean.getSubDeployments();
      if (parentBeans == null) {
         return null;
      } else if (this.EARModuleName == null) {
         return parentBeans;
      } else {
         int i;
         for(i = 0; i < parentBeans.length; ++i) {
            SubDeploymentMBean parentBean = parentBeans[i];
            if (this.EARModuleName.equals(parentBean.getName())) {
               return parentBean.getSubDeployments();
            }
         }

         if (librarySubDeployments != null && librarySubDeployments.length > 0) {
            for(i = 0; i < librarySubDeployments.length; ++i) {
               if (this.EARModuleName.equals(librarySubDeployments[i].getName())) {
                  return librarySubDeployments[i].getSubDeployments();
               }
            }
         }

         JMSLogger.logNoEARSubDeployment(this.EARModuleName, moduleBean.getName());
         return null;
      }
   }

   private void analyzeTargets(BasicDeploymentMBean moduleBean, UpdateInformation info, ApplicationContextInternal appCtx) throws ModuleException {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Analyzing targets in " + moduleBean.getName());
      }

      if (info != null) {
         HashMap removedGroups = (HashMap)this.acceptedGroups.clone();
         HashMap removedLocalGroups = (HashMap)this.acceptedLocalGroups.clone();
         info.setRemovedGroups(removedGroups);
         info.setRemovedLocalGroups(removedLocalGroups);

         assert info.getChangedGroups().size() == 0;

         assert info.getChangedLocalGroups().size() == 0;
      }

      SubDeploymentMBean[] subModuleBeans = this.getSubModules(moduleBean, appCtx.getLibrarySubDeployments());
      if (subModuleBeans != null) {
         for(int outer = 0; outer < subModuleBeans.length; ++outer) {
            SubDeploymentMBean subModuleBean = subModuleBeans[outer];
            String groupName = subModuleBean.getName();
            TargetMBean[] targetBeans = subModuleBean.getTargets();
            SubModuleListenerCtx smlctx = (SubModuleListenerCtx)this.subModuleListenerContextMap.get(groupName);
            TargetMBean targetBean;
            if (smlctx == null) {
               TargetMBean[] targets = moduleBean.getTargets();
               targetBean = targets.length > 0 ? targets[0] : null;
               smlctx = new SubModuleListenerCtxImpl(this.appId, this.moduleUri, WebLogicModuleType.MODULETYPE_SUBMODULE, targetBean, groupName, targetBeans);
               if (JMSDebug.JMSModule.isDebugEnabled()) {
                  JMSDebug.JMSModule.debug("TargetingHelper:analyzeTargets(): Created a new " + smlctx + " for " + groupName);
               }

               this.subModuleListenerContextMap.put(groupName, smlctx);
            }

            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("TargetingHelper:analyzeTargets(): Got " + smlctx + " for " + groupName);
               this.dumpSubModuleListenerContext((SubModuleListenerCtxImpl)smlctx);
            }

            this.begin((SubModuleListenerCtx)smlctx, ModuleListener.STATE_PREPARED);

            for(int inner = 0; inner < targetBeans.length; ++inner) {
               targetBean = targetBeans[inner];
               if (!(targetBean instanceof VirtualTargetMBean)) {
                  TargetMBean[] safAgentTargets;
                  int lcv;
                  TargetMBean jmsServerTarget;
                  if (targetBean instanceof JMSServerMBean) {
                     JMSServerMBean jmsServerBean = (JMSServerMBean)targetBean;
                     safAgentTargets = jmsServerBean.getTargets();

                     for(lcv = 0; lcv < safAgentTargets.length; ++lcv) {
                        jmsServerTarget = safAgentTargets[lcv];
                        if (!(jmsServerTarget instanceof ClusterMBean) || isLocallyTargetedClusteredSingletonJMSServer(jmsServerBean, this.clusterName, this.serverName)) {
                           this.updateMaps(groupName, jmsServerTarget, info, targetBean);
                        }
                     }
                  } else if (targetBean instanceof SAFAgentMBean) {
                     SAFAgentMBean safAgentBean = (SAFAgentMBean)targetBean;
                     safAgentTargets = safAgentBean.getTargets();

                     for(lcv = 0; lcv < safAgentTargets.length; ++lcv) {
                        jmsServerTarget = safAgentTargets[lcv];
                        this.updateMaps(groupName, jmsServerTarget, info, targetBean);
                     }
                  } else {
                     this.updateMaps(groupName, targetBean, info, targetBean);
                  }

                  this.end((SubModuleListenerCtx)smlctx, ModuleListener.STATE_PREPARED);
               }
            }

            this.updateChangedGroup(groupName, info);
         }

         this.state = ModuleListener.STATE_PREPARED;
      }
   }

   private void updateChangedGroup(String groupName, UpdateInformation info) {
      if (info != null) {
         List acceptedTargets = null;
         List newTargets = null;
         if (this.acceptedLocalGroups != null && info.getChangedLocalGroups() != null) {
            acceptedTargets = (List)this.acceptedLocalGroups.get(groupName);
            newTargets = (List)info.getChangedLocalGroups().get(groupName);
            if (isSameTarget(acceptedTargets, newTargets)) {
               info.getChangedLocalGroups().remove(groupName);
            }
         }

         if (this.acceptedGroups != null && info.getChangedGroups() != null) {
            acceptedTargets = (List)this.acceptedGroups.get(groupName);
            newTargets = (List)info.getChangedGroups().get(groupName);
            if (isSameTarget(acceptedTargets, newTargets)) {
               info.getChangedGroups().remove(groupName);
            }
         }

      }
   }

   private static boolean isSameTarget(List s1, List s2) {
      if (s1 != null && s2 != null) {
         Set h1 = new HashSet();
         Set h2 = new HashSet();
         Iterator it = s1.iterator();
         TargetMBean tmb = null;

         while(it.hasNext()) {
            tmb = (TargetMBean)it.next();
            h1.add(tmb.getType() + tmb.getName());
         }

         it = s2.iterator();

         while(it.hasNext()) {
            tmb = (TargetMBean)it.next();
            h2.add(tmb.getType() + tmb.getName());
         }

         return h1.equals(h2);
      } else {
         return false;
      }
   }

   private static boolean hasTargetingChanged(Map addedGroups, Map removedGroups, Map changedGroups) {
      return removedGroups != null && removedGroups.size() > 0 || addedGroups != null && addedGroups.size() > 0 || changedGroups != null && changedGroups.size() > 0;
   }

   static boolean hasTargetingChanged(UpdateInformation info) {
      return hasTargetingChanged(info.getAddedGroups(), info.getRemovedGroups(), info.getChangedGroups()) || hasTargetingChanged(info.getAddedLocalGroups(), info.getRemovedLocalGroups(), info.getChangedLocalGroups());
   }

   int getGroupTargetChangeStatus(TargetableBean targetable, String groupName, UpdateInformation info, boolean useGlobal, boolean isStandaloneDefaultTargeted, boolean isConnectionFactoryDefinitionToJMSServer) {
      HashMap useAcceptedGroups;
      if (useGlobal) {
         useAcceptedGroups = this.acceptedGroups;
      } else {
         useAcceptedGroups = this.acceptedLocalGroups;
      }

      if (info == null) {
         if (!useAcceptedGroups.containsKey(groupName) && !targetable.isDefaultTargetingEnabled()) {
            return useAcceptedGroups.isEmpty() && isConnectionFactoryDefinitionToJMSServer ? 1 : 0;
         } else {
            return 1;
         }
      } else if (targetable.isDefaultTargetingEnabled() && !isStandaloneDefaultTargeted) {
         return info.hasDefaultTargetsChanged() ? 3 : 0;
      } else {
         HashMap useAddedGroups;
         HashMap useChangedGroups;
         HashMap useRemovedGroups;
         if (useGlobal) {
            useAddedGroups = info.getAddedGroups();
            useChangedGroups = info.getChangedGroups();
            useRemovedGroups = info.getRemovedGroups();
         } else {
            useAddedGroups = info.getAddedLocalGroups();
            useChangedGroups = info.getChangedLocalGroups();
            useRemovedGroups = info.getRemovedLocalGroups();
         }

         if (useAddedGroups.containsKey(groupName)) {
            return 1;
         } else if (useRemovedGroups.containsKey(groupName)) {
            return 2;
         } else {
            return useChangedGroups.containsKey(groupName) ? 3 : 0;
         }
      }
   }

   List getTarget(TargetInfoMBean targetInfo, TargetableBean targetable, String groupName, UpdateInformation info, boolean useGlobal) {
      List retVal = null;
      if (targetable != null && targetInfo != null && targetable.isDefaultTargetingEnabled()) {
         TargetMBean[] targets = targetInfo.getTargets();
         if (targets == null || targets.length == 0 && targetInfo instanceof SubDeploymentMBean) {
            TargetInfoMBean parentTargetInfo = (TargetInfoMBean)targetInfo.getParent();
            targets = parentTargetInfo.getTargets();
         }

         if (targets != null) {
            retVal = new LinkedList();

            for(int i = 0; i < targets.length; ++i) {
               if (useGlobal || this.isLocallyTargeted(targets[i])) {
                  retVal.add(targets[i]);
               }
            }
         }

         return retVal;
      } else if (groupName == null) {
         return null;
      } else {
         List acceptedList;
         if (useGlobal) {
            acceptedList = (List)this.acceptedGroups.get(groupName);
         } else {
            acceptedList = (List)this.acceptedLocalGroups.get(groupName);
         }

         if (info == null) {
            return acceptedList;
         } else {
            HashMap useAddedGroups;
            HashMap useRemovedGroups;
            HashMap useChangedGroups;
            if (useGlobal) {
               useAddedGroups = info.getAddedGroups();
               useRemovedGroups = info.getRemovedGroups();
               useChangedGroups = info.getChangedGroups();
            } else {
               useAddedGroups = info.getAddedLocalGroups();
               useRemovedGroups = info.getRemovedLocalGroups();
               useChangedGroups = info.getChangedLocalGroups();
            }

            if (acceptedList != null) {
               if (useRemovedGroups == null) {
                  return acceptedList;
               } else if (useRemovedGroups.containsKey(groupName)) {
                  return null;
               } else {
                  return useChangedGroups != null && useChangedGroups.containsKey(groupName) ? (List)useChangedGroups.get(groupName) : acceptedList;
               }
            } else {
               return useAddedGroups == null ? null : (List)useAddedGroups.get(groupName);
            }
         }
      }
   }

   private void dumpSubModuleListenerContext(SubModuleListenerCtxImpl smlctx) {
      StringBuffer sb = new StringBuffer();
      sb.append("SubModuleListenerContext " + smlctx);
      sb.append("\n  Application Id     : " + smlctx.getApplicationId());
      sb.append("\n  Module URI         : " + smlctx.getModuleUri());
      sb.append("\n  Module Target      : " + (smlctx.getTarget() != null ? smlctx.getTarget().getName() : ""));
      sb.append("\n  Sub Module Name    : " + smlctx.getSubModuleName());
      StringBuffer sb1 = new StringBuffer();
      TargetMBean[] targets = smlctx.getSubModuleTargets();

      for(int i = 0; i < targets.length; ++i) {
         sb1.append(targets[i].getName());
         if (i < targets.length - 1) {
            sb1.append(",");
         }
      }

      sb.append("\n  Sub Module Targets : " + sb1.toString());
      System.out.println(sb.toString());
   }

   private void begin(SubModuleListenerCtx ctx, ModuleListener.State newState) {
      Iterator it = this.deploymentManager.getModuleListeners();

      while(it.hasNext()) {
         ((ModuleListener)it.next()).beginTransition((ModuleListenerCtx)ctx, this.state, newState);
      }

   }

   private void end(SubModuleListenerCtx ctx, ModuleListener.State newState) {
      Iterator it = this.deploymentManager.getModuleListeners();

      while(it.hasNext()) {
         ((ModuleListener)it.next()).endTransition((ModuleListenerCtx)ctx, this.state, newState);
      }

   }

   private void failed(SubModuleListenerCtx ctx, ModuleListener.State newState) {
      Iterator it = this.deploymentManager.getModuleListeners();

      while(it.hasNext()) {
         ((ModuleListener)it.next()).failedTransition((ModuleListenerCtx)ctx, this.state, newState);
      }

   }

   private static boolean isMigratableTargetActive(String targetName) {
      try {
         ServiceLocator sl = GlobalServiceLocator.getServiceLocator();
         MigrationManager mm = (MigrationManager)sl.getService(MigrationManager.class, new Annotation[0]);
         int state = mm.getMigratableTargetState(targetName);
         if (state == 2 || state == 1) {
            return true;
         }
      } catch (MultiException | IllegalStateException var4) {
      }

      return false;
   }

   private boolean isClusterTargetedSingletonJMSServer(TargetMBean targetBean, TargetMBean realTarget) {
      if (targetBean instanceof ClusterMBean && realTarget instanceof JMSServerMBean) {
         PersistentStoreMBean storeMBean = ((JMSServerMBean)realTarget).getPersistentStore();
         if (storeMBean != null && storeMBean.getDistributionPolicy().equals("Singleton")) {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("TargetingHelper.isClusterTargetedSingletonJMSServer(): is true, name" + realTarget.getName());
            }

            return true;
         }
      }

      return false;
   }

   private boolean isSingletonJMSServerLocallyActivated(JMSServerMBean jmsServer) throws ModuleException {
      String name = GenericDeploymentManager.getDecoratedSingletonInstanceName(jmsServer, "01");
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("TargetingHelper.isSingletonJMSServerLocallyActivated(): jmsserverInstanceName=" + name);
      }

      if (JMSService.getJMSServiceWithModuleException().getBEDeployer().findBackEnd(name) != null) {
         JMSDebug.JMSModule.debug("TargetingHelper.isSingletonJMSServerLocallyActivated(): return true");
         return true;
      } else {
         return false;
      }
   }

   private static boolean isLocallyTargetedClusteredSingletonJMSServer(JMSServerMBean jmsServer, String clusterName, String serverName) {
      PersistentStoreMBean storeMBean = jmsServer.getPersistentStore();
      if (storeMBean != null && !GenericDeploymentManager.isDistributed(storeMBean)) {
         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("TargetingHelper.isLocallyTargetedClusteredSingleton(): , clusterName=" + clusterName + ", serverName=" + serverName + ", mbean=" + jmsServer.getName() + ", storeMBean=" + storeMBean.getName());
         }

         ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getServer();
         ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
         String partitionName = GenericDeploymentManager.getMyPartitionName();
         MigratableGroupConfig mgc = null;
         DynamicLoadbalancer dlb = DLDynamicPlacement.getSingleton(partitionName, clusterName);
         mgc = ((DLClusterRegistration)dlb).getGroupConfigMBean(storeMBean.getName(), server, storeMBean.getDistributionPolicy().equalsIgnoreCase("Distributed") ? (server == null ? null : server.getName()) : String.valueOf(1));
         if (mgc == null) {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("TargetingHelper.isLocallyTargetedClusteredSingleton(): JMSServer or SAFAgent is currently NOT deployed on my server(local):" + serverName);
            }

            return false;
         } else {
            DynamicLoadbalancer.ServiceStatus sts = null;
            MigrationManager migrationManagerService = (MigrationManager)GlobalServiceLocator.getServiceLocator().getService(MigrationManager.class, new Annotation[0]);

            try {
               sts = migrationManagerService.getServiceStatus(partitionName, mgc.getName());
               if (JMSDebug.JMSModule.isDebugEnabled()) {
                  JMSDebug.JMSModule.debug("TargetingHelper.isLocallyTargetedClusteredSingleton(): , clusterName=" + clusterName + ", serverName=" + serverName + ", serverState=" + serverRuntime.getState() + ", mbean=" + jmsServer.getName() + ", storeMBean=" + storeMBean.getName() + ", mgcName=" + mgc.getName() + ", dlb.sts=" + (sts == null ? "null" : sts.getState()) + ", dlb.sts.getHostingServer()=" + (sts == null ? "null" : sts.getHostingServer()) + ", ups=" + (mgc.getUserPreferredServer() != null ? mgc.getUserPreferredServer().getName() : "null"));
               }

               if (sts != null && sts.getState() == State.ACTIVE && sts.getHostingServer().equals(serverName) && serverRuntime.getState().equals("RUNNING")) {
                  if (JMSDebug.JMSModule.isDebugEnabled()) {
                     JMSDebug.JMSModule.debug("TargetingHelper.isLocallyTargetedClusteredSingleton(): JMSServer or SAFAgent is currently deployed on my server(local):" + serverName);
                  }

                  return true;
               }
            } catch (Exception var12) {
               if (JMSDebug.JMSModule.isDebugEnabled()) {
                  JMSDebug.JMSModule.debug("TargetingHelper.isLocallyTargetedClusteredSingleton(): Error while getting the MigrationManager.getServiceStatus() mgc name=" + mgc.getName() + ", exception.getMessage()=" + var12.getMessage() + ". This might be due to cluster still booting.");
               }

               return false;
            }

            boolean isLocallyTargeted = ((DLClusterRegistration)dlb).isMigratedLocally(mgc.getName());
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("TargetingHelper.isLocallyTargetedClusteredSingleton(): isLocallyTargeted=" + isLocallyTargeted);
            }

            return isLocallyTargeted;
         }
      } else {
         return true;
      }
   }

   public boolean isLocallyTargetedClusteredSingletonJMSServer(JMSServerMBean jmsServer) {
      return this.clusterName == null ? true : isLocallyTargetedClusteredSingletonJMSServer(jmsServer, this.clusterName, this.serverName);
   }
}
