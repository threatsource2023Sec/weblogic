package weblogic.management.mbeanservers.edit.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.management.mbeanservers.edit.ActivationTaskMBean;
import weblogic.management.mbeanservers.edit.Change;
import weblogic.management.mbeanservers.edit.ServerStatus;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.ActivateTask;

public class ActivationTaskMBeanImpl extends ServiceImpl implements ActivationTaskMBean {
   protected ActivateTask activateTask;
   private ConfigurationManagerMBeanImpl manager;
   protected boolean completed;
   private int state;
   private ServerStatus[] serverStatus;
   private Exception taskError;
   private long beginTime;
   private long endTime;
   private String user;
   private Change[] changes;
   private String[] scToRestart;
   private String taskId;

   public ActivationTaskMBeanImpl(ConfigurationManagerMBeanImpl manager, ActivateTask activateTask) {
      super(Long.toString(activateTask.getTaskId()), ActivationTaskMBean.class.getName(), manager);
      this.activateTask = activateTask;
      this.manager = manager;
   }

   public int getState() {
      return this.completed ? this.state : this.activateTask.getState();
   }

   public ServerStatus[] getStatusByServer() {
      if (this.completed) {
         return this.serverStatus;
      } else {
         Map map = this.activateTask.getStateOnServers();
         Map failedMap = this.activateTask.getFailedServers();
         ServerStatus[] serverStates = new ServerStatus[map.size()];
         Set values = map.entrySet();
         int i = 0;

         for(Iterator var6 = values.iterator(); var6.hasNext(); ++i) {
            Object value = var6.next();
            Map.Entry entry = (Map.Entry)value;
            Exception failure = (Exception)failedMap.get(entry.getKey());
            serverStates[i] = new ServerStatusImpl((String)entry.getKey(), (Integer)entry.getValue(), failure);
         }

         return serverStates;
      }
   }

   public String[] getSystemComponentsToRestart() {
      if (this.completed) {
         return this.scToRestart;
      } else {
         Map systemComponentRestartRequired = this.activateTask.getSystemComponentRestartRequired();
         Set keySet = systemComponentRestartRequired.keySet();
         return (String[])keySet.toArray(new String[keySet.size()]);
      }
   }

   public String getDetails() {
      StringBuffer buf = new StringBuffer();
      buf.append("Activation Task started at " + this.getStartTime() + "\n");
      buf.append("User that initiated this task " + this.getUser() + "\n");
      buf.append("Changes that are being activated are: \n");
      Change[] changes = this.getChanges();
      Change[] var3 = changes;
      int var4 = changes.length;

      int var5;
      for(var5 = 0; var5 < var4; ++var5) {
         Change change = var3[var5];
         buf.append("    " + change.toString());
      }

      buf.append("Status of this activation per server: \n");
      ServerStatus[] status = this.getStatusByServer();
      ServerStatus[] var10 = status;
      var5 = status.length;

      int var13;
      for(var13 = 0; var13 < var5; ++var13) {
         ServerStatus statu = var10[var13];
         buf.append("  ServerName : " + statu.getServerName() + "\n");
         buf.append("    Status : " + this.statusString(statu.getServerState()) + "\n");
         if (statu.getServerState() == 5) {
            buf.append("    Exception : " + statu.getServerException() + "\n");
         }
      }

      String[] systemComponentsToRestart = this.getSystemComponentsToRestart();
      if (systemComponentsToRestart.length > 0) {
         buf.append("System components to be restarted: \n");
         String[] var12 = systemComponentsToRestart;
         var13 = systemComponentsToRestart.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            String scName = var12[var14];
            buf.append("  System component: \"" + scName + "\" needs to be restarted.\n");
         }
      }

      return buf.toString();
   }

   private String statusString(int status) {
      if (status == 0) {
         return "NEW";
      } else if (status == 4) {
         return "COMMITTED";
      } else if (status == 2) {
         return "DISTRIBUTED";
      } else if (status == 1) {
         return "DISTRIBUTING";
      } else if (status == 5) {
         return "FAILED";
      } else if (status == 3) {
         return "PENDING";
      } else if (status == 6) {
         return "CANCELING";
      } else {
         return status == 7 ? "COMMIT_FAILING" : "NEW";
      }
   }

   public Exception getError() {
      return this.completed ? this.taskError : this.activateTask.getError();
   }

   public long getStartTime() {
      return this.completed ? this.beginTime : this.activateTask.getBeginTime();
   }

   public long getCompletionTime() {
      return this.completed ? this.endTime : this.activateTask.getEndTime();
   }

   public String getUser() {
      return this.completed ? this.user : this.activateTask.getUser();
   }

   public Change[] getChanges() {
      return this.completed ? this.changes : this.manager.convertBeanUpdatesToChanges(this.activateTask.getChanges());
   }

   public void waitForTaskCompletion() {
      if (!this.completed) {
         this.activateTask.waitForTaskCompletion();
      }
   }

   public void waitForTaskCompletion(long timeout) {
      if (!this.completed) {
         this.activateTask.waitForTaskCompletion(timeout);
      }
   }

   public ActivateTask getActivateTask() {
      return this.activateTask;
   }

   public String getEditSessionName() {
      return this.getActivateTask().getEditSessionName();
   }

   public String getPartitionName() {
      return this.getActivateTask().getPartitionName();
   }

   void movingToCompleted() {
      this.state = this.getState();
      this.serverStatus = this.getStatusByServer();
      this.taskError = this.getError();
      this.beginTime = this.getStartTime();
      this.endTime = this.getCompletionTime();
      this.user = this.getUser();
      this.changes = this.getChanges();
      this.scToRestart = this.getSystemComponentsToRestart();
      this.taskId = this.getName();
      this.completed = true;
      this.activateTask = null;
      this.manager = null;
   }
}
