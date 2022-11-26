package weblogic.server;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.management.ManagementException;
import weblogic.management.configuration.CoherenceServerMBean;
import weblogic.management.configuration.CoherenceServerStartMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.runtime.CoherenceServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.CoherenceServerLifeCycleTaskRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBeanDelegate;
import weblogic.nodemanager.mbean.NodeManagerLifecycleService;
import weblogic.nodemanager.mbean.NodeManagerLifecycleServiceGenerator;
import weblogic.nodemanager.mbean.NodeManagerTask;
import weblogic.work.ContextWrap;
import weblogic.work.WorkManagerFactory;

/** @deprecated */
@Deprecated
public class CoherenceServerLifeCycleRuntime extends DomainRuntimeMBeanDelegate implements CoherenceServerLifeCycleRuntimeMBean {
   private static final int TASK_AFTERLIFE_TIME_MILLIS = 1800000;
   private final Set tasks;
   private final String serverName;
   private final CoherenceServerMBean serverMBean;
   private String oldState;
   private String currentState;
   private int startCount;

   CoherenceServerLifeCycleRuntime(CoherenceServerMBean serverMBean) throws ManagementException {
      super(serverMBean.getName());
      this.serverMBean = serverMBean;
      this.tasks = Collections.synchronizedSet(new HashSet());
      this.serverName = serverMBean.getName();
   }

   public void clearOldServerLifeCycleTaskRuntimes() {
      synchronized(this.tasks) {
         Iterator iter = this.tasks.iterator();

         while(iter.hasNext()) {
            CoherenceServerLifeCycleTaskRuntime task = (CoherenceServerLifeCycleTaskRuntime)iter.next();
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

   public CoherenceServerLifeCycleTaskRuntimeMBean forceShutdown() throws ServerLifecycleException {
      CoherenceServerLifeCycleTaskRuntime var3;
      try {
         if (this.getState() == "SHUTDOWN") {
            throw new ServerLifecycleException("Can not get to the relevant ServerRuntimeMBean for server " + this.serverName + ". Server is in SHUTDOWN state and cannot be reached.");
         }

         CoherenceServerLifeCycleTaskRuntime taskMBean = new CoherenceServerLifeCycleTaskRuntime(this, "Forcefully shutting down " + this.serverName + " server ...", "forceShutdown");
         this.tasks.add(taskMBean);
         ShutdownRequest request = new ShutdownRequest(taskMBean);
         WorkManagerFactory.getInstance().getSystem().schedule(new ContextWrap(request));
         var3 = taskMBean;
      } catch (ManagementException var7) {
         throw new ServerLifecycleException(var7);
      } finally {
         this.clearOldServerLifeCycleTaskRuntimes();
      }

      return var3;
   }

   public int getNodeManagerRestartCount() {
      return this.startCount > 0 ? this.startCount - 1 : 0;
   }

   public String getState() {
      String state = this.getStateNodeManager();
      if (state == null || "UNKNOWN".equalsIgnoreCase(state)) {
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
            return generator.getInstance(mmb).getState(this.serverMBean);
         } catch (IOException var3) {
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

   public CoherenceServerLifeCycleTaskRuntimeMBean[] getTasks() {
      return (CoherenceServerLifeCycleTaskRuntimeMBean[])this.tasks.toArray(new CoherenceServerLifeCycleTaskRuntimeMBean[this.tasks.size()]);
   }

   public void setState(String newState) {
      synchronized(this) {
         if (newState == null || newState.equalsIgnoreCase(this.currentState)) {
            return;
         }

         this.oldState = this.currentState;
         this.currentState = newState;
         if (!"SHUTTING_DOWN".equals(newState) && !"FORCE_SHUTTING_DOWN".equals(newState) && !"STARTING".equals(newState) && !"RESUMING".equals(newState) && "RUNNING".equals(newState)) {
         }
      }

      this._postSet("State", this.oldState, this.currentState);
   }

   public CoherenceServerLifeCycleTaskRuntimeMBean start() throws ServerLifecycleException {
      CoherenceServerLifeCycleTaskRuntime var2;
      try {
         CoherenceServerLifeCycleTaskRuntime taskMBean = new CoherenceServerLifeCycleTaskRuntime(this, "Starting " + this.serverName + " server ...", "start");
         this.tasks.add(taskMBean);
         this.startServer(taskMBean);
         updateTaskMBeanOnCompletion(taskMBean);
         var2 = taskMBean;
      } catch (ManagementException var6) {
         throw new ServerLifecycleException(var6);
      } finally {
         this.clearOldServerLifeCycleTaskRuntimes();
      }

      return var2;
   }

   private void startServer(CoherenceServerLifeCycleTaskRuntime taskMBean) {
      try {
         NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
         NodeManagerLifecycleService nm = null;
         nm = generator.getInstance(this.serverMBean);
         NodeManagerTask nmTask = nm.start(this.serverMBean);
         ++this.startCount;
         taskMBean.setNMTask(nmTask);
      } catch (SecurityException var5) {
         taskMBean.setError(var5);
      } catch (IOException var6) {
         taskMBean.setError(var6);
      }

   }

   void cleanup() {
   }

   private boolean useNodeManagerToShutdown() throws IOException {
      CoherenceServerStartMBean targetServerStartMBean = this.serverMBean.getCoherenceServerStart();
      if (targetServerStartMBean == null) {
         return false;
      } else {
         MachineMBean mc = this.serverMBean.getMachine();
         if (mc == null) {
            return false;
         } else {
            NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
            NodeManagerLifecycleService nm = generator.getInstance(mc);
            if (nm == null) {
               return false;
            } else {
               nm.kill(this.serverMBean);
               return true;
            }
         }
      }
   }

   private static void updateTaskMBeanOnCompletion(CoherenceServerLifeCycleTaskRuntime slcTaskRuntime) {
      if (slcTaskRuntime.getError() != null) {
         slcTaskRuntime.setStatus("FAILED");
      } else {
         slcTaskRuntime.setStatus("TASK COMPLETED");
      }

      slcTaskRuntime.setEndTime(System.currentTimeMillis());
      slcTaskRuntime.setIsRunning(false);
   }

   private final class ShutdownRequest implements Runnable {
      private final CoherenceServerLifeCycleTaskRuntime taskMBean;

      ShutdownRequest(CoherenceServerLifeCycleTaskRuntime taskMBean) {
         this.taskMBean = taskMBean;
      }

      public void run() {
         try {
            CoherenceServerLifeCycleRuntime.this.useNodeManagerToShutdown();
         } catch (IOException var5) {
            this.taskMBean.setError(var5);
         } finally {
            CoherenceServerLifeCycleRuntime.updateTaskMBeanOnCompletion(this.taskMBean);
         }

      }
   }
}
