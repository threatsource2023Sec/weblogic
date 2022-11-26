package weblogic.deploy.api.spi.status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.deploy.shared.ActionType;
import javax.enterprise.deploy.shared.CommandType;
import javax.enterprise.deploy.shared.StateType;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.exceptions.OperationUnsupportedException;
import javax.enterprise.deploy.spi.status.ClientConfiguration;
import javax.enterprise.deploy.spi.status.DeploymentStatus;
import javax.enterprise.deploy.spi.status.ProgressEvent;
import javax.enterprise.deploy.spi.status.ProgressListener;
import javax.enterprise.deploy.spi.status.ProgressObject;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.shared.WebLogicCommandType;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.spi.deploy.TargetImpl;
import weblogic.deploy.api.spi.deploy.TargetModuleIDImpl;
import weblogic.deploy.api.spi.exceptions.ServerConnectionException;
import weblogic.deploy.utils.TaskCompletionNotificationListener;
import weblogic.management.ManagementException;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.TargetStatus;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;

public class ProgressObjectImpl implements ProgressObject, Serializable {
   private static final long serialVersionUID = 1L;
   private static final boolean debug = Debug.isDebug("status");
   private transient StateType state;
   private transient CommandType cmd;
   private transient ActionType action;
   private String msg;
   private transient WebLogicDeploymentManager dm;
   private String task;
   private transient List tmids;
   private transient Set listeners;
   private transient Throwable error;
   private transient List pending;
   private transient DeploymentTaskRuntimeMBean dtrm;
   private transient boolean haveDtrm;
   private transient TaskCompletionNotificationListener taskCompletionListener;

   public ProgressObjectImpl(CommandType cmd, String task, TargetModuleID[] tmids, WebLogicDeploymentManager dm) {
      this.state = StateType.RUNNING;
      this.action = ActionType.EXECUTE;
      this.msg = null;
      this.tmids = Collections.synchronizedList(new ArrayList());
      this.listeners = Collections.synchronizedSet(new HashSet());
      this.error = null;
      this.pending = Collections.synchronizedList(new ArrayList());
      this.dtrm = null;
      this.haveDtrm = false;
      this.taskCompletionListener = null;
      this.cmd = cmd;
      this.task = task;
      this.dm = dm;
      this.copyInto(tmids, this.pending);
      if (debug) {
         this.dumpPO();
      }

   }

   private void copyInto(TargetModuleID[] tmids, List l) {
      if (tmids != null) {
         for(int i = 0; i < tmids.length; ++i) {
            l.add(tmids[i]);
         }

      }
   }

   public ProgressObjectImpl(CommandType cmd, String msg, WebLogicDeploymentManager dm) {
      this(cmd, (String)null, new TargetModuleID[0], dm);
      this.msg = msg;
      this.state = StateType.FAILED;
   }

   public ProgressObjectImpl(CommandType cmd, Throwable t, WebLogicDeploymentManager dm) {
      this(cmd, (String)null, new TargetModuleID[0], dm);
      Throwable rootCause = ManagementException.unWrapExceptions(t);
      this.msg = rootCause.toString();
      this.state = StateType.FAILED;
      this.error = rootCause;
   }

   public ProgressObjectImpl(CommandType cmd, WebLogicDeploymentManager dm) {
      this(cmd, (String)null, new TargetModuleID[0], dm);
      this.msg = SPIDeployerLogger.noop(cmd.toString());
      this.state = StateType.COMPLETED;
   }

   public DeploymentStatus getDeploymentStatus() {
      this.getResultTargetModuleIDs();
      return new DeploymentStatusImpl(this.state, this.cmd, this.action, this.msg, this.error);
   }

   public TargetModuleID[] getResultTargetModuleIDs() {
      this.updateState();
      if (this.state != StateType.RELEASED && this.pending.size() > 0) {
         this.updateTmids();
      }

      TargetModuleID[] targetModuleIDs = (TargetModuleID[])((TargetModuleID[])this.tmids.toArray(new TargetModuleID[0]));
      return targetModuleIDs;
   }

   private void updateTmids() {
      try {
         this.getDtrm();
         if (this.dtrm != null) {
            DeploymentData data = this.dtrm.getDeploymentData();
            DeploymentOptions options = null;
            if (data != null) {
               options = data.getDeploymentOptions();
            }

            TargetStatus[] tss = this.dtrm.getTargets();
            if (tss == null) {
               return;
            }

            for(int i = 0; i < tss.length; ++i) {
               TargetStatus ts = tss[i];
               if (ts.getState() != 1 && ts.getState() != 0) {
                  List mods = this.getPendingTmidsForTarget(ts.getTarget());
                  if (!mods.isEmpty()) {
                     for(int j = 0; j < mods.size(); ++j) {
                        TargetModuleID mod = (TargetModuleID)mods.get(j);
                        if (ts.getState() == 3 || ts.getState() == 4) {
                           if (debug) {
                              Debug.say("adding successful tmid: " + mod.toString());
                           }

                           TargetModuleID tmpTmid = this.dm.getServerConnection().getModuleCache().getResultTmids(mod.getModuleID(), mod.getTarget(), options);
                           if (tmpTmid == null) {
                              tmpTmid = mod;
                           }

                           this.tmids.add(tmpTmid);
                        }

                        this.pending.remove(mod);
                     }
                  }
               }
            }
         } else if (this.state == StateType.RUNNING) {
            this.setState(StateType.RELEASED);
         }
      } catch (Throwable var10) {
         SPIDeployerLogger.logConnectionError(var10.getMessage(), var10);
         if (this.state == StateType.RUNNING) {
            this.setState(StateType.RELEASED);
         }

         this.dtrm = null;
      }

   }

   private List getPendingTmidsForTarget(String target) {
      List pt = new ArrayList();

      for(int i = 0; i < this.pending.size(); ++i) {
         TargetModuleID id = (TargetModuleID)this.pending.get(i);
         if (id.getTarget().getName().equals(target)) {
            pt.add(id);
         }
      }

      return pt;
   }

   private void updateState() {
      if (this.state != StateType.RELEASED) {
         if (!this.dm.isConnected() && this.state == StateType.RUNNING) {
            this.setState(StateType.RELEASED);
         }

      }
   }

   public ClientConfiguration getClientConfiguration(TargetModuleID targetmoduleid) {
      return null;
   }

   public boolean isCancelSupported() {
      return true;
   }

   public void cancel() throws OperationUnsupportedException {
      DeploymentTaskRuntimeMBean t = this.getDtrm();
      if (t != null) {
         try {
            if (debug) {
               Debug.say("Cancelling task " + this.getTask());
            }

            t.cancel();
         } catch (Exception var3) {
            if (debug) {
               Debug.say("Cancel of task " + this.getTask() + " failed: " + var3.toString());
            }
         }
      }

   }

   public boolean isStopSupported() {
      return false;
   }

   public void stop() throws OperationUnsupportedException {
      throw new OperationUnsupportedException(SPIDeployerLogger.unsupported("stop"));
   }

   public void addProgressListener(ProgressListener progressListener) {
      if (!this.listeners.add(progressListener) && debug) {
         Debug.say("Listener already registered: " + progressListener.toString());
      }

      if (this.state != StateType.RUNNING) {
         this.reportEvent();
      }

   }

   public void removeProgressListener(ProgressListener progressListener) {
      if (!this.listeners.remove(progressListener) && debug) {
         Debug.say("Listener not registered: " + progressListener.toString());
      }

   }

   public final void setTaskCompletionListener(TaskCompletionNotificationListener listener) {
      this.taskCompletionListener = listener;
   }

   public final TaskCompletionNotificationListener getTaskCompletionListener() {
      return this.taskCompletionListener;
   }

   public String getTask() {
      return this.task;
   }

   public void reportEvent(String app, String module, String server, String msg) throws ServerConnectionException {
      this.getResultTargetModuleIDs();
      this.msg = msg;
      if (this.listeners.size() != 0) {
         Iterator serverTmids = this.getTmidsForServer(server).iterator();

         while(true) {
            TargetModuleIDImpl tmid;
            do {
               if (!serverTmids.hasNext()) {
                  return;
               }

               tmid = (TargetModuleIDImpl)serverTmids.next();
            } while(!tmid.getModuleID().equals(module) && !tmid.getModuleID().equals(app));

            this.broadcastReport(msg, tmid);
         }
      }
   }

   public void reportEvent(String app, String module, String server, String msg, Exception e) {
      this.msg = msg;
      if (this.listeners.size() != 0) {
         this.broadcastReport(SPIDeployerLogger.reportErrorEvent(app, module, server, msg, e.toString()), (TargetModuleID)null);
      }
   }

   public void reportEvent(String msg) {
      this.msg = msg;
      if (this.listeners.size() != 0) {
         this.broadcastReport(msg, (TargetModuleID)null);
      }
   }

   public void reportEvent() {
      if (this.state != null && StateType.COMPLETED.getValue() == this.state.getValue()) {
         this.msg = null;
      }

      this.getResultTargetModuleIDs();
      if (this.listeners.size() != 0) {
         if (this.tmids != null && this.tmids.size() > 0) {
            for(int i = 0; i < this.tmids.size(); ++i) {
               TargetModuleID tmid = (TargetModuleID)this.tmids.get(i);
               if (debug) {
                  Debug.say("reporting final event for " + tmid.getModuleID());
               }

               this.broadcastReport(this.msg, tmid);
            }
         } else {
            this.broadcastReport(this.msg, (TargetModuleID)null);
         }

      }
   }

   public void setState(StateType s) {
      if (debug) {
         Debug.say("Updating state to " + s.toString());
      }

      this.state = s;
      if (this.state == StateType.COMPLETED && (this.cmd == WebLogicCommandType.DEPLOY || this.cmd == WebLogicCommandType.UNDEPLOY || this.cmd == WebLogicCommandType.DISTRIBUTE || this.cmd == WebLogicCommandType.REMOVE)) {
         if (debug) {
            Debug.say("Resetting module cache on completion of " + this.cmd + " command");
         }

         this.dm.getServerConnection().getModuleCache().reset();
      }

   }

   public void setAction(ActionType a) {
      if (debug) {
         Debug.say("Updating action to " + a.toString());
      }

      this.action = a;
   }

   public void setMessage(String m) {
      if (debug) {
         Debug.say("Updating message to " + m);
      }

      this.msg = m;
   }

   public void setError(Throwable t) {
      if (t != null) {
         t = ManagementException.unWrapExceptions(t);
         this.setMessage(t.toString());
         this.error = t;
      }
   }

   private void broadcastReport(String msg, TargetModuleID tmid) {
      DeploymentStatusImpl dsi = (DeploymentStatusImpl)this.getDeploymentStatus();
      dsi.setMessage(msg);
      ProgressEvent pe = new ProgressEvent(this, tmid, dsi);
      ProgressListener[] currListeners = (ProgressListener[])((ProgressListener[])this.listeners.toArray(new ProgressListener[0]));
      ProgressListener[] var6 = currListeners;
      int var7 = currListeners.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         ProgressListener pl = var6[var8];
         pl.handleProgressEvent(pe);
      }

   }

   private Set getTmidsForServer(String server) throws ServerConnectionException {
      Set targets = this.getTargetsForServer(server);
      Set serverTmids = new HashSet();
      if (this.tmids != null) {
         for(int i = 0; i < this.tmids.size(); ++i) {
            TargetModuleID tmid = (TargetModuleID)this.tmids.get(i);
            if (targets.contains(tmid.getTarget())) {
               serverTmids.add(tmid);
            }
         }
      }

      return serverTmids;
   }

   private Set getTargetsForServer(String serverName) throws ServerConnectionException {
      Set targetSet = new HashSet();
      if (this.tmids != null) {
         for(int i = 0; i < this.tmids.size(); ++i) {
            TargetModuleIDImpl tm = (TargetModuleIDImpl)this.tmids.get(i);
            Iterator servers = tm.getServersForTarget().iterator();

            while(servers.hasNext()) {
               TargetImpl server = (TargetImpl)servers.next();
               if (server.getName().equals(serverName)) {
                  targetSet.add(tm.getTarget());
                  break;
               }
            }
         }
      }

      return targetSet;
   }

   private void dumpPO() {
      Debug.say("Constructed ProgressObject: ");
      if (this.cmd != null) {
         Debug.say("Command: " + this.cmd.toString());
      }

      if (this.task != null) {
         Debug.say("Task: " + this.task.toString());
      }

      Debug.say("TMIDs:");
      int i;
      if (this.tmids != null) {
         for(i = 0; i < this.tmids.size(); ++i) {
            Debug.say(this.tmids.get(i).toString());
         }
      }

      Debug.say("Pending TMIDs:");
      if (this.pending != null) {
         for(i = 0; i < this.pending.size(); ++i) {
            Debug.say(this.pending.get(i).toString());
         }
      }

   }

   public DeploymentTaskRuntimeMBean getDtrm() {
      if (!this.haveDtrm) {
         this.dtrm = this.dm.getHelper().getTaskByID(this.getTask());
         this.haveDtrm = true;
      }

      return this.dtrm;
   }
}
