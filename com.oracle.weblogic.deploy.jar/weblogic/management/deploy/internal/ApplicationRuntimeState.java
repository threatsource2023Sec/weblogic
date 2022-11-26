package weblogic.management.deploy.internal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.DeploymentVersion;
import weblogic.deploy.internal.targetserver.state.DeploymentState;
import weblogic.deploy.internal.targetserver.state.TargetModuleState;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.utils.PlatformConstants;

public class ApplicationRuntimeState implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final String EOL;
   private String appId;
   private int retireTimeoutSecs = -1;
   private long retireTimeMillis = -1L;
   private HashMap appTargetState = new HashMap();
   private DeploymentVersion deploymentVersion = null;
   private Map modules = new HashMap();
   private transient HashMap moduleStateHistory = null;

   public ApplicationRuntimeState() {
   }

   ApplicationRuntimeState(String appId) {
      this.appId = appId;
   }

   ApplicationRuntimeState(ApplicationRuntimeState ars) {
      this.appId = ars.getAppId();
      this.retireTimeoutSecs = ars.getRetireTimeoutSeconds();
      this.retireTimeMillis = ars.getRetireTimeMillis();
   }

   public String getIntendedState(String target) {
      AppTargetState ats = this.getAppTargetState(target);
      return ats == null ? null : ats.getState();
   }

   public int getStagingState(String target) {
      AppTargetState ats = this.getAppTargetState(target);
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug(" AppTargetState for '" + target + "' is : " + ats);
      } else if (Debug.isDeploymentDebugConciseEnabled()) {
         Debug.deploymentDebugConcise(" AppTargetState for '" + target + "' is : " + ats);
      }

      return ats == null ? -1 : ats.getStagingState();
   }

   public String getAppId() {
      return this.appId;
   }

   public boolean isAdminMode(String target) {
      return "STATE_ADMIN".equals(this.getIntendedState(target));
   }

   public boolean isActiveVersion() {
      return this.retireTimeMillis == 0L;
   }

   void setActiveVersion(boolean force) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("ApplicationRuntimeState.setActiveVersion(" + force + ") invoked for appId " + this.appId + " when current retireTimeMillis is " + this.retireTimeMillis);
      }

      if (this.retireTimeMillis <= 0L || force) {
         this.retireTimeMillis = 0L;
      }
   }

   public int getRetireTimeoutSeconds() {
      return this.retireTimeoutSecs;
   }

   void setRetireTimeoutSeconds(int secs) {
      this.retireTimeoutSecs = secs;
   }

   public long getRetireTimeMillis() {
      return this.retireTimeMillis;
   }

   void setRetireTimeMillis(long millis) {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("ApplicationRuntimeState.setRetireTimeMillis(" + millis + ") invoked for appId " + this.appId + " when current retireTimeMillis is " + this.retireTimeMillis);
      }

      this.retireTimeMillis = millis;
   }

   public boolean markedForRetirement() {
      return this.retireTimeMillis > 0L;
   }

   public final DeploymentVersion getDeploymentVersion() {
      return this.deploymentVersion;
   }

   public final void setDeploymentVersion(DeploymentVersion version) {
      this.deploymentVersion = version;
   }

   public Map getModules() {
      return this.modules;
   }

   public void deleteModule(String moduleId) {
      this.modules.remove(moduleId);
   }

   public Map getAppTargetState() {
      return this.appTargetState;
   }

   public AppTargetState getAppTargetState(String target) {
      return (AppTargetState)this.appTargetState.get(target);
   }

   public void updateAppTargetState(AppTargetState ats, String target) {
      this.appTargetState.put(target, ats);
   }

   public void removeAppTargetState(String target) {
      this.appTargetState.remove(target);
   }

   public void removeTargetFromModuleState(String target) {
      Iterator it = this.modules.keySet().iterator();

      while(true) {
         while(true) {
            Map moduleMap;
            do {
               if (!it.hasNext()) {
                  return;
               }

               String moduleId = (String)it.next();
               moduleMap = (Map)this.modules.get(moduleId);
            } while(moduleMap == null);

            if (moduleMap.containsKey(target)) {
               moduleMap.remove(target);
            } else {
               Iterator states = moduleMap.values().iterator();

               while(states.hasNext()) {
                  Object targetValue = states.next();
                  if (targetValue instanceof Map && ((Map)targetValue).containsKey(target)) {
                     ((Map)targetValue).remove(target);
                  }
               }
            }
         }
      }
   }

   public boolean isEmptyModuleStates() {
      Iterator it = this.modules.keySet().iterator();

      while(true) {
         Map moduleMap;
         do {
            if (!it.hasNext()) {
               return true;
            }

            String moduleId = (String)it.next();
            moduleMap = (Map)this.modules.get(moduleId);
         } while(moduleMap == null);

         Iterator mv = moduleMap.values().iterator();

         while(mv.hasNext()) {
            Object mvo = mv.next();
            if (mvo != null) {
               if (!(mvo instanceof Map)) {
                  return false;
               }

               if (((Map)mvo).size() > 0) {
                  return false;
               }
            }
         }
      }
   }

   void updateState(DeploymentState update) {
      TargetModuleState[] tms = update.getTargetModules();
      this.updateState(update, tms);
      tms = new TargetModuleState[]{new TargetModuleState("ROOT_MODULE", "*", update.getTarget(), "*", update.getServerName())};
      tms[0].setCurrentState(update.getCurrentState());
      this.updateState(update, tms);
   }

   public void updateState(TargetModuleState[] tms) {
      this.updateState((DeploymentState)null, tms);
   }

   private void updateState(DeploymentState update, TargetModuleState[] tms) {
      for(int i = 0; tms != null && i < tms.length; ++i) {
         TargetModuleState thisTms = tms[i];
         if (!this.skip(thisTms)) {
            Map moduleMap = (Map)this.modules.get(thisTms.getModuleId());
            if (moduleMap == null) {
               moduleMap = new HashMap();
            }

            Map logicalTargetMap = (Map)((Map)moduleMap).get(thisTms.getTargetName());
            if (logicalTargetMap == null) {
               logicalTargetMap = new HashMap();
            }

            if (this.moduleStateHistory == null) {
               this.moduleStateHistory = new HashMap();
            }

            LimitedQueue history = (LimitedQueue)this.moduleStateHistory.get(thisTms.getModuleId());
            if (history == null) {
               history = new LimitedQueue(100);
               this.moduleStateHistory.put(thisTms.getModuleId(), history);
            }

            long updateTime = update != null ? update.getRelayTime() : -1L;
            State currentUpdate = new State(updateTime, thisTms);
            int previousOutOfOrder = "STATE_ACTIVE".equals(currentUpdate.state) ? -1 : findOutOfOrderState(history, currentUpdate);
            if (previousOutOfOrder != -1) {
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("ApplicationRuntimeState received current update OutOfOrder: " + currentUpdate);
                  Debug.deploymentDebug("ApplicationRuntimeState update history: " + history);
               } else if (Debug.isDeploymentDebugConciseEnabled()) {
                  Debug.deploymentDebugConcise("ApplicationRuntimeState received current update OutOfOrder for : " + currentUpdate);
               }

               history.add(previousOutOfOrder, currentUpdate);
               if (Debug.isDeploymentDebugEnabled()) {
                  Debug.deploymentDebug("ApplicationRuntimeState OutOfOrder fixed: " + history);
               }
            } else {
               history.add(currentUpdate);
               Object state;
               if (thisTms.isLogicalTarget()) {
                  state = ((Map)logicalTargetMap).get(thisTms.getServerName());
                  if (state == null) {
                     state = new HashMap();
                  }

                  ((Map)state).put(thisTms.getServerName(), thisTms);
               } else {
                  state = thisTms;
               }

               ((Map)logicalTargetMap).put(thisTms.getServerName(), state);
               ((Map)moduleMap).put(thisTms.getTargetName(), logicalTargetMap);
               this.modules.put(thisTms.getModuleId(), moduleMap);
            }
         }
      }

   }

   static int findOutOfOrderState(List history, State cur) {
      if (cur.relayTime == -1L) {
         return -1;
      } else {
         int pos = -1;

         for(int i = history.size(); i > 0; --i) {
            State pre = (State)history.get(i - 1);
            if (pre.server.equals(cur.server) && pre.relayTime >= cur.relayTime) {
               pos = i - 1;
            }
         }

         return pos;
      }
   }

   private boolean skip(TargetModuleState tms) {
      if (tms == null) {
         return true;
      } else {
         return tms.getCurrentState() == null || tms.getModuleId() == null || tms.getTargetName() == null || tms.getServerName() == null;
      }
   }

   void resetState(String server) {
      Iterator it = this.modules.keySet().iterator();

      while(it.hasNext()) {
         String moduleId = (String)it.next();
         Map moduleMap = (Map)this.modules.get(moduleId);
         if (moduleMap == null) {
            moduleMap = new HashMap();
         }

         Iterator modIt = ((Map)moduleMap).keySet().iterator();

         while(modIt.hasNext()) {
            String target = (String)modIt.next();
            Map logicalTargetMap = (Map)((Map)moduleMap).get(target);
            if (logicalTargetMap != null) {
               Object state = ((Map)moduleMap).get(target);
               if (state instanceof Map) {
                  Map serverMap = (Map)state;
                  Object serverState = serverMap.get(server);
                  if (serverState instanceof Map) {
                     serverState = ((Map)serverState).get(server);
                  }

                  if (serverState != null) {
                     this.resetState((TargetModuleState)serverState, server);
                  }
               } else {
                  this.resetState((TargetModuleState)state, server);
               }
            }
         }
      }

   }

   private void resetState(TargetModuleState tms, String server) {
      if (tms.getServerName().equals(server) && !"STATE_RETIRED".equals(tms.getCurrentState())) {
         tms.setCurrentState("STATE_NEW");
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("reset: " + tms);
         } else if (Debug.isDeploymentDebugConciseEnabled()) {
            Debug.deploymentDebugConcise("Resetting state for TargetModuleState: " + tms + ", on server: " + server);
         }
      }

   }

   private void prettyPrintModuleInfo(StringBuffer sb, Map cascadedMap, int tabLevel) {
      ApplicationUtils.printTabs(sb, tabLevel);
      Iterator var4 = cascadedMap.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry entry = (Map.Entry)var4.next();
         String moduleName = (String)entry.getKey();
         Object oValue = entry.getValue();
         if (oValue == null) {
            sb.append("--- Target \"" + moduleName + "\" has no associated target state ---");
         } else if (oValue instanceof TargetModuleState) {
            TargetModuleState targetModuleState = (TargetModuleState)oValue;
            sb.append("--- Target state for \"" + moduleName + "\" (" + System.identityHashCode(targetModuleState) + ") ---" + EOL);
            targetModuleState.pretty(sb, tabLevel);
            sb.append(EOL);
         } else {
            Map subModules = (Map)oValue;
            sb.append("--- Target \"" + moduleName + "\" has " + subModules.size() + " sub-modules ---" + EOL);
            this.prettyPrintModuleInfo(sb, subModules, tabLevel + 1);
         }
      }

   }

   public String pretty() {
      StringBuffer sb = new StringBuffer("ApplicationRuntimeState: " + this.appId + " (" + System.identityHashCode(this) + ")" + EOL);
      sb.append("Retire timeout seconds: " + this.retireTimeoutSecs + " sec" + EOL);
      sb.append("Retire time milliseconds: " + this.retireTimeMillis + " ms" + EOL);
      sb.append("=== Start Runtime State ===" + EOL);
      int lcv = 0;
      Iterator var3 = this.modules.entrySet().iterator();

      Map.Entry entry;
      String targetName;
      while(var3.hasNext()) {
         entry = (Map.Entry)var3.next();
         targetName = (String)entry.getKey();
         Map mInfo = (Map)entry.getValue();
         ++lcv;
         sb.append("--- Module \"" + targetName + "\", " + lcv + " of " + this.modules.size() + " ---" + EOL);
         this.prettyPrintModuleInfo(sb, mInfo, 1);
      }

      sb.append("=== End Runtime State, start Intended States ===" + EOL);
      lcv = 0;

      for(var3 = this.appTargetState.entrySet().iterator(); var3.hasNext(); sb.append(EOL)) {
         entry = (Map.Entry)var3.next();
         targetName = (String)entry.getKey();
         AppTargetState ats = (AppTargetState)entry.getValue();
         ++lcv;
         if (ats == null) {
            sb.append("--- Target \"" + targetName + "\", " + lcv + " of " + this.appTargetState.size() + " has no AppTargetState ---" + EOL);
         } else {
            sb.append("--- Target \"" + targetName + "\", " + lcv + " of " + this.appTargetState.size() + " ---" + EOL);
            ats.pretty(sb);
         }
      }

      sb.append("=== End Intended States, start Deployment Version ===" + EOL);
      if (this.deploymentVersion == null) {
         sb.append("There is no deployment version available" + EOL);
      } else {
         this.deploymentVersion.pretty(sb);
      }

      return sb.toString();
   }

   public String toString() {
      StringBuffer sb = (new StringBuffer()).append("AppRTState");
      sb.append("[").append("appId=").append(this.appId).append(",retireTimeoutSecs=").append(this.retireTimeoutSecs).append(",retireTimeMillis=").append(this.retireTimeMillis).append("," + EOL + "Module(s) State:").append(this.modules).append("," + EOL + "AppTarget(s) State:").append(this.appTargetState);
      if (this.deploymentVersion != null) {
         sb.append("," + EOL + "DeploymentVersion=").append(this.deploymentVersion);
      }

      sb.append("]");
      return sb.toString();
   }

   static {
      EOL = PlatformConstants.EOL;
   }

   private static class LimitedQueue extends LinkedList {
      private final int limit;

      public LimitedQueue(int limit) {
         this.limit = limit;
      }

      public boolean add(Object o) {
         boolean added = super.add(o);

         while(added && this.size() > this.limit) {
            super.remove();
         }

         return added;
      }
   }

   static class State {
      private long relayTime;
      private String state;
      private String server;

      public State(long relayTime, TargetModuleState state) {
         this.relayTime = relayTime;
         this.state = state.getCurrentState();
         this.server = state.getServerName();
      }

      public String toString() {
         if (!Debug.isDeploymentDebugConciseEnabled()) {
            return this.state + "(" + this.server + "," + this.relayTime + ")";
         } else {
            StringBuffer sb = new StringBuffer();
            sb.append("(");
            sb.append("TargetModuleState's current state=").append(this.state).append(", ");
            sb.append("on server=").append(this.server).append(", ");
            sb.append("with relayTime=").append(this.relayTime);
            sb.append(")");
            return sb.toString();
         }
      }
   }
}
