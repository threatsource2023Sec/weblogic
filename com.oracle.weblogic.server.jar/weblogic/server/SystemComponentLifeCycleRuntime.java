package weblogic.server;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import weblogic.management.ManagementException;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.SystemComponentMBean;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.management.runtime.SystemComponentLifeCycleRuntimeMBean;
import weblogic.management.runtime.SystemComponentLifeCycleTaskRuntimeMBean;
import weblogic.nodemanager.mbean.NodeManagerLifecycleService;
import weblogic.nodemanager.mbean.NodeManagerLifecycleServiceGenerator;
import weblogic.nodemanager.mbean.NodeManagerTask;
import weblogic.work.ContextWrap;
import weblogic.work.WorkManagerFactory;

public class SystemComponentLifeCycleRuntime extends DomainRuntimeMBeanDelegate implements SystemComponentLifeCycleRuntimeMBean {
   private static final int TASK_AFTERLIFE_TIME_MILLIS = 1800000;
   private final Set tasks;
   private final String serverName;
   private final SystemComponentMBean serverMBean;
   private String oldState;
   private String currentState;
   private int startCount;

   SystemComponentLifeCycleRuntime(SystemComponentMBean serverMBean) throws ManagementException {
      super(serverMBean.getName());
      this.serverMBean = serverMBean;
      this.tasks = Collections.synchronizedSet(new HashSet());
      this.serverName = serverMBean.getName();
      MachineMBean mmb = serverMBean.getMachine();
      if (mmb != null) {
         try {
            NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
            generator.getInstance(mmb).initState(serverMBean);
         } catch (IOException var4) {
         }

      }
   }

   public void clearOldServerLifeCycleTaskRuntimes() {
      synchronized(this.tasks) {
         Iterator iter = this.tasks.iterator();

         while(iter.hasNext()) {
            SystemComponentLifeCycleTaskRuntime task = (SystemComponentLifeCycleTaskRuntime)iter.next();
            if (task.getEndTime() > 0L && System.currentTimeMillis() - task.getEndTime() > 1800000L) {
               try {
                  task.unregister();
               } catch (ManagementException var6) {
               }

               iter.remove();
            }
         }

      }
   }

   public SystemComponentLifeCycleTaskRuntimeMBean shutdown(Properties props) throws ServerLifecycleException {
      SystemComponentLifeCycleTaskRuntime var4;
      try {
         if (this.getState() == "SHUTDOWN") {
            throw new ServerLifecycleException(this.serverName + " is in SHUTDOWN state.");
         }

         SystemComponentLifeCycleTaskRuntime taskMBean = new SystemComponentLifeCycleTaskRuntime(this, "Forcefully shutting down " + this.serverName + " server ...", "forceShutdown");
         this.tasks.add(taskMBean);
         ShutdownRequest request = new ShutdownRequest(taskMBean, props);
         WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(request));
         var4 = taskMBean;
      } catch (ManagementException var8) {
         throw new ServerLifecycleException(var8);
      } finally {
         this.clearOldServerLifeCycleTaskRuntimes();
      }

      return var4;
   }

   public int getNodeManagerRestartCount() {
      return this.startCount > 0 ? this.startCount - 1 : 0;
   }

   public String getState() {
      String state = this.getStateNodeManager();
      if (state == null) {
         state = "UNKNOWN";
      }

      this.clearOldServerLifeCycleTaskRuntimes();
      return state;
   }

   private String getStateNodeManager() {
      MachineMBean mmb = this.serverMBean.getMachine();
      if (mmb == null) {
         return null;
      } else {
         try {
            NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
            NodeManagerLifecycleService nmr = generator.getInstance(mmb);
            String tmpState = nmr.getState(this.serverMBean);
            if (tmpState.equals("UNKNOWN")) {
               try {
                  nmr.initState(this.serverMBean);
                  tmpState = nmr.getState(this.serverMBean);
               } catch (IOException var6) {
               }
            }

            return tmpState;
         } catch (IOException var7) {
            return null;
         }
      }
   }

   public int getStateVal() {
      String state = this.getState().intern();
      if (state != "STARTING" && state != "FAILED_RESTARTING") {
         if (state == "SHUTTING_DOWN") {
            return 7;
         } else if (state == "FORCE_SHUTTING_DOWN") {
            return 18;
         } else if (state == "STANDBY") {
            return 3;
         } else if (state == "ADMIN") {
            return 17;
         } else if (state == "SUSPENDING") {
            return 4;
         } else if (state == "RESUMING") {
            return 6;
         } else if (state == "RUNNING") {
            return 2;
         } else if (state == "SHUTDOWN") {
            return 0;
         } else if (state == "FAILED") {
            return 8;
         } else if (state == "ACTIVATE_LATER") {
            return 13;
         } else if (state == "FAILED_NOT_RESTARTABLE") {
            return 14;
         } else {
            return state == "FAILED_MIGRATABLE" ? 15 : 9;
         }
      } else {
         return 1;
      }
   }

   public SystemComponentLifeCycleTaskRuntimeMBean[] getTasks() {
      return (SystemComponentLifeCycleTaskRuntimeMBean[])this.tasks.toArray(new SystemComponentLifeCycleTaskRuntimeMBean[this.tasks.size()]);
   }

   public SystemComponentLifeCycleTaskRuntimeMBean lookupTask(String taskName) {
      if (taskName != null) {
         Iterator var2 = this.tasks.iterator();

         while(var2.hasNext()) {
            SystemComponentLifeCycleTaskRuntimeMBean task = (SystemComponentLifeCycleTaskRuntimeMBean)var2.next();
            if (taskName.equals(task.getName())) {
               return task;
            }
         }
      }

      return null;
   }

   public void setState(String newState) {
      synchronized(this) {
         if (newState == null || newState.equalsIgnoreCase(this.currentState)) {
            return;
         }

         this.oldState = this.currentState;
         this.currentState = newState;
      }

      this._postSet("State", this.oldState, this.currentState);
   }

   public SystemComponentLifeCycleTaskRuntimeMBean start(Properties props) throws ServerLifecycleException {
      SystemComponentLifeCycleTaskRuntime var3;
      try {
         SystemComponentLifeCycleTaskRuntime taskMBean = new SystemComponentLifeCycleTaskRuntime(this, "Starting " + this.serverName + " server ...", "start");
         this.tasks.add(taskMBean);
         this.startServer(taskMBean, props);
         updateTaskMBeanOnCompletion(taskMBean);
         var3 = taskMBean;
      } catch (ManagementException var7) {
         throw new ServerLifecycleException(var7);
      } finally {
         this.clearOldServerLifeCycleTaskRuntimes();
      }

      return var3;
   }

   public SystemComponentLifeCycleTaskRuntimeMBean softRestart(Properties props) throws ServerLifecycleException {
      SystemComponentLifeCycleTaskRuntime var4;
      try {
         if (this.getState() == "SHUTDOWN") {
            throw new ServerLifecycleException(this.serverName + " is in SHUTDOWN state.");
         }

         SystemComponentLifeCycleTaskRuntime taskMBean = new SystemComponentLifeCycleTaskRuntime(this, "Signalling softRestart on" + this.serverName + " server ...", "softRestart");
         this.tasks.add(taskMBean);
         SoftRestartRequest request = new SoftRestartRequest(taskMBean, props);
         WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(request));
         var4 = taskMBean;
      } catch (ManagementException var8) {
         throw new ServerLifecycleException(var8);
      } finally {
         this.clearOldServerLifeCycleTaskRuntimes();
      }

      return var4;
   }

   private void startServer(SystemComponentLifeCycleTaskRuntime taskMBean, Properties props) {
      try {
         NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
         NodeManagerLifecycleService nm = generator.getInstance(this.serverMBean);
         nm.syncMachineIfNecessary(this.serverMBean.getMachine());
         NodeManagerTask nmTask = nm.start(this.serverMBean, props);
         ++this.startCount;
         taskMBean.setNMTask(nmTask);
      } catch (SecurityException var6) {
         taskMBean.setError(var6);
      } catch (IOException var7) {
         taskMBean.setError(var7);
      }

   }

   void cleanup() {
      MachineMBean mmb = this.serverMBean.getMachine();
      if (mmb != null) {
         try {
            NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
            generator.getInstance(mmb).remove(this.serverMBean);
         } catch (IOException var3) {
         }

      }
   }

   private static void updateTaskMBeanOnCompletion(SystemComponentLifeCycleTaskRuntime slcTaskRuntime) {
      if (slcTaskRuntime.getError() != null) {
         slcTaskRuntime.setStatus("FAILED");
      } else {
         slcTaskRuntime.setStatus("TASK COMPLETED");
      }

      slcTaskRuntime.setEndTime(System.currentTimeMillis());
      slcTaskRuntime.setIsRunning(false);
   }

   private final class SoftRestartRequest implements Runnable {
      private final SystemComponentLifeCycleTaskRuntime taskMBean;
      private final Properties props;

      SoftRestartRequest(SystemComponentLifeCycleTaskRuntime taskMBean, Properties props) {
         this.taskMBean = taskMBean;
         this.props = props;
      }

      public void run() {
         try {
            MachineMBean mc = SystemComponentLifeCycleRuntime.this.serverMBean.getMachine();
            if (mc == null) {
               this.taskMBean.setError(new Exception("MachineMBean: " + mc));
               return;
            }

            NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
            NodeManagerLifecycleService nm = generator.getInstance(mc);
            if (nm == null) {
               this.taskMBean.setError(new Exception("NodeManagerRuntime: " + nm + " for MachineMBean: " + mc));
               return;
            }

            nm.syncMachineIfNecessary(SystemComponentLifeCycleRuntime.this.serverMBean.getMachine());
            nm.softRestart(SystemComponentLifeCycleRuntime.this.serverMBean, this.props);
         } catch (IOException var7) {
            this.taskMBean.setError(var7);
         } finally {
            SystemComponentLifeCycleRuntime.updateTaskMBeanOnCompletion(this.taskMBean);
         }

      }
   }

   private final class ShutdownRequest implements Runnable {
      private final SystemComponentLifeCycleTaskRuntime taskMBean;
      private final Properties props;

      ShutdownRequest(SystemComponentLifeCycleTaskRuntime taskMBean, Properties props) {
         this.taskMBean = taskMBean;
         this.props = props;
      }

      public void run() {
         try {
            MachineMBean mc = SystemComponentLifeCycleRuntime.this.serverMBean.getMachine();
            if (mc == null) {
               this.taskMBean.setError(new Exception("MachineMBean: " + mc));
               return;
            }

            NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
            NodeManagerLifecycleService nm = generator.getInstance(mc);
            if (nm == null) {
               this.taskMBean.setError(new Exception("NodeManagerRuntime: " + nm + " for MachineMBean: " + mc));
               return;
            }

            nm.kill(SystemComponentLifeCycleRuntime.this.serverMBean, this.props);
         } catch (IOException var7) {
            this.taskMBean.setError(var7);
         } finally {
            SystemComponentLifeCycleRuntime.updateTaskMBeanOnCompletion(this.taskMBean);
         }

      }
   }
}
