package weblogic.deploy.internal.targetserver.state;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OptionalDataException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.application.ModuleListenerCtx;
import weblogic.application.SubModuleListenerCtx;
import weblogic.application.utils.TargetUtils;
import weblogic.deploy.common.Debug;
import weblogic.deploy.compatibility.NotificationBroadcaster;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.internal.AppRuntimeStateManager;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ClusterRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class DeploymentState implements Externalizable {
   private static final long serialVersionUID = 1L;
   private Map tms = new ConcurrentHashMap();
   private String appId;
   private String taskID;
   private int notifLevel;
   private String state;
   private int taskState;
   private Exception exception;
   private String target;
   private String intendedState;
   private int stagingState = -1;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private transient ArrayList xitions = new ArrayList();
   private String serverName;
   private long relayTime;

   public DeploymentState() {
      this.serverName = ManagementService.getRuntimeAccess(kernelId).getServerName();
      this.relayTime = -1L;
   }

   public DeploymentState(String appId, String taskID, int notifLevel) {
      this.serverName = ManagementService.getRuntimeAccess(kernelId).getServerName();
      this.relayTime = -1L;
      this.appId = appId;
      this.taskID = taskID;
      this.notifLevel = notifLevel;
   }

   public String toString() {
      String deploymentState = "appid=" + this.appId + ",taskid=" + this.taskID + ",state=" + this.state + ",serverName=" + this.serverName + ",intendedState=" + this.intendedState + ",relayTime=" + this.relayTime + ",tmsSize=" + this.tms.size();
      return Debug.isDeploymentDebugConciseEnabled() ? deploymentState : super.toString() + "[" + deploymentState + "]";
   }

   public String getServerName() {
      return this.serverName;
   }

   public long getRelayTime() {
      return this.relayTime;
   }

   public void setRelayTime(long time) {
      this.relayTime = time;
   }

   public String getId() {
      return this.appId;
   }

   public String getCurrentState() {
      return this.state;
   }

   public void setCurrentState(String s) {
      this.state = s;
   }

   public void setCurrentState(String state, boolean includeModules) {
      this.setCurrentState(state);
      if (includeModules) {
         TargetModuleState[] tmsArray = this.getTargetModules();

         for(int i = 0; i < tmsArray.length; ++i) {
            tmsArray[i].setCurrentState(state);
         }
      }

   }

   public void setCurrentState(String state, String[] moduleIds) {
      this.setCurrentState(state);
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      ServerRuntimeMBean serverRuntimeMBean = runtimeAccess.getServerRuntime();
      ClusterRuntimeMBean clusterRuntimeMBean = serverRuntimeMBean.getClusterRuntime();
      DomainMBean domainMBean = runtimeAccess.getDomain();
      Object target;
      if (clusterRuntimeMBean != null) {
         target = domainMBean.lookupCluster(clusterRuntimeMBean.getName());
      } else {
         target = domainMBean.lookupServer(this.serverName);
      }

      String targetName = ((ConfigurationMBean)target).getName();
      String targetType = ((ConfigurationMBean)target).getType();
      if (moduleIds != null) {
         String[] var10 = moduleIds;
         int var11 = moduleIds.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            String moduleId = var10[var12];
            String moduleType = AppRuntimeStateManager.getManager().getModuleType(this.appId, moduleId);
            this.getOrCreateTargetModuleState(moduleId, moduleType, targetName, targetType).setCurrentState(state);
         }
      }

   }

   public String getIntendedState() {
      return this.intendedState;
   }

   public void setIntendedState(String s) {
      this.intendedState = s;
   }

   public int getStagingState() {
      return this.stagingState;
   }

   public void setStagingState(int given) {
      this.stagingState = given;
   }

   public String getTarget() {
      return this.target;
   }

   public void setTarget(String target) {
      this.target = target;
   }

   private void setTarget(ModuleListenerCtx mod) {
      if (mod instanceof SubModuleListenerCtx) {
         TargetMBean t = TargetUtils.findLocalServerTarget(((SubModuleListenerCtx)mod).getSubModuleTargets());
         if (t != null) {
            this.setTarget(t.getName());
         }
      } else {
         this.setTarget(mod.getTarget().getName());
      }

   }

   public void addAppXition(String xition) {
      if (this.notifLevel >= 1) {
         this.xitions.add(new AppTransition(xition, (new Date()).getTime(), this.serverName));
      }

   }

   public Object[] getTransitions() {
      return this.xitions.toArray(new Object[this.xitions.size()]);
   }

   public TargetModuleState[] getTargetModules() {
      Collection tmsvalues = this.tms.values();
      TargetModuleState[] tmsarray = new TargetModuleState[tmsvalues.size()];
      return (TargetModuleState[])((TargetModuleState[])tmsvalues.toArray(tmsarray));
   }

   TargetModuleState addModuleTransition(ModuleListenerCtx mod, String curState, String newstate, String xition, long gentime) {
      TargetModuleState s = this.getOrCreateTargetModuleState(mod);
      if (s == null) {
         return null;
      } else {
         if (this.notifLevel >= 2 && NotificationBroadcaster.isRelevantToWLS81(curState, newstate)) {
            this.xitions.add(new ModuleTransition(curState, newstate, xition, gentime, s));
         }

         if (this.getTarget() == null) {
            this.setTarget(mod);
         }

         this.setIntendedState(newstate);
         return s;
      }
   }

   TargetModuleState getOrCreateTargetModuleState(ModuleListenerCtx mod) {
      if (mod instanceof SubModuleListenerCtx) {
         return this.getOrCreateSubmoduleTargetState(mod);
      } else {
         TargetModuleState s = (TargetModuleState)this.tms.get(mod.getModuleUri());
         if (null == s) {
            TargetMBean target = mod.getTarget();
            if (target == null) {
               return null;
            }

            s = new TargetModuleState(mod.getModuleUri(), mod.getType(), target.getName(), target.getType(), this.serverName);
            this.tms.put(mod.getModuleUri(), s);
         }

         return s;
      }
   }

   private TargetModuleState getOrCreateTargetModuleState(String moduleUri, String moduleType, String targetName, String targetType) {
      TargetModuleState s = (TargetModuleState)this.tms.get(moduleUri);
      if (null == s) {
         s = new TargetModuleState(moduleUri, moduleType, targetName, targetType, this.serverName);
         this.tms.put(moduleUri, s);
      }

      return s;
   }

   private TargetModuleState getOrCreateSubmoduleTargetState(ModuleListenerCtx mod) {
      SubModuleListenerCtx submod = (SubModuleListenerCtx)mod;
      TargetMBean target = TargetUtils.findLocalServerTarget(submod.getSubModuleTargets());
      if (target == null) {
         return null;
      } else {
         TargetModuleState s = new TargetModuleState(mod.getModuleUri(), submod.getSubModuleName(), mod.getType(), target.getName(), target.getType(), this.serverName);
         TargetModuleState s1 = (TargetModuleState)this.tms.get(s.getModuleId());
         if (s1 == null) {
            this.tms.put(s.getModuleId(), s);
            s1 = s;
         }

         return s1;
      }
   }

   public String getTaskID() {
      return this.taskID;
   }

   public int getTaskState() {
      return this.taskState;
   }

   public void setTaskState(int taskState) {
      this.taskState = taskState;
   }

   public Exception getException() {
      return this.exception;
   }

   public void setException(Exception exception) {
      this.exception = exception;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.tms);
      out.writeObject(this.appId);
      out.writeObject(this.taskID);
      out.writeInt(this.notifLevel);
      out.writeObject(this.state);
      out.writeInt(this.taskState);
      out.writeObject(this.exception);
      out.writeObject(this.target);
      out.writeObject(this.intendedState);
      out.writeInt(this.stagingState);
      out.writeObject(this.serverName);
      out.writeLong(this.relayTime);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      Map theTMS = (Map)in.readObject();
      if (theTMS != null) {
         this.tms.putAll(theTMS);
      }

      this.appId = (String)in.readObject();
      this.taskID = (String)in.readObject();
      this.notifLevel = in.readInt();
      this.state = (String)in.readObject();
      this.taskState = in.readInt();
      this.exception = (Exception)in.readObject();
      this.target = (String)in.readObject();
      this.intendedState = (String)in.readObject();
      this.stagingState = in.readInt();

      try {
         this.serverName = (String)in.readObject();
         this.relayTime = in.readLong();
      } catch (OptionalDataException var4) {
      }

   }
}
