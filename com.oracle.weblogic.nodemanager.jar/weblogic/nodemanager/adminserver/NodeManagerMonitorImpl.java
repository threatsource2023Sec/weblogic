package weblogic.nodemanager.adminserver;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.SystemComponentMBean;
import weblogic.nodemanager.NodeManagerLogger;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManager;

public class NodeManagerMonitorImpl implements NodeManagerMonitor {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private final MachineChangeListProvider machineChangeListProvider;
   private static int WAIT_SEC = 1000;
   private static int WAIT_COUNT = 15;
   private final ConcurrentHashMap machines = new ConcurrentHashMap();
   private static final WorkAdapter DUMMY_WORK = new WorkAdapter() {
      public void run() {
      }
   };
   private Timer timer = null;
   private static final String MONITOR = "NodeManagerMonitorService ";

   public NodeManagerMonitorImpl(WorkManager workManager, DomainMBean domainMBean, MachineChangeListProvider machineChangeListProvider, TimerManager timerManager, long timerPeriod) {
      this.machineChangeListProvider = machineChangeListProvider;
      SystemComponentMBean[] var7 = domainMBean.getSystemComponents();
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         SystemComponentMBean systemComponent = var7[var9];
         MachineMBean machineMBean = systemComponent.getMachine();
         if (machineMBean != null) {
            this.add(machineMBean);
         }
      }

      this.timer = timerManager.schedule(new CheckerTimerListener(workManager), 0L, timerPeriod);
   }

   void shutdown() {
      if (this.timer != null) {
         this.timer.cancel();
      }

      Iterator var1 = this.machines.keySet().iterator();

      while(var1.hasNext()) {
         MachineMBean machineMBean = (MachineMBean)var1.next();
         WorkAdapter work = (WorkAdapter)this.machines.remove(machineMBean);
         if (!DUMMY_WORK.equals(work)) {
            work.cancel("NodeManagerMonitorService  shutdown");
         }
      }

   }

   public void add(MachineMBean machineMBean) {
      if (machineMBean == null) {
         throw new IllegalArgumentException(NodeManagerLogger.illegalNullMachineMBean());
      } else {
         WorkAdapter value = (WorkAdapter)this.machines.putIfAbsent(machineMBean, DUMMY_WORK);
         if (value == null && debugLogger.isDebugEnabled()) {
            this.debug("adding machine: " + machineMBean);
         }

      }
   }

   private void debug(String msg) {
      debugLogger.debug("NodeManagerMonitorService " + msg);
   }

   private void debug(String msg, Exception e) {
      debugLogger.debug("NodeManagerMonitorService " + msg, e);
   }

   public void syncMachineIfNecessary(MachineMBean machineMBean) throws IOException {
      if (this.machines.containsKey(machineMBean)) {
         int counter = WAIT_COUNT;
         WorkAdapter work;
         synchronized(this.machines) {
            for(; counter > 0; --counter) {
               work = (WorkAdapter)this.machines.get(machineMBean);
               if (work == null || DUMMY_WORK.equals(work)) {
                  break;
               }

               try {
                  Thread.sleep((long)WAIT_SEC);
               } catch (InterruptedException var8) {
               }
            }

            work = (WorkAdapter)this.machines.remove(machineMBean);
         }

         if (work != null) {
            if (debugLogger.isDebugEnabled()) {
               this.debug("Pushing over changes to machine: " + machineMBean);
            }

            try {
               this.machineChangeListProvider.syncChangeList(machineMBean);
            } catch (IOException var7) {
               this.machines.put(machineMBean, DUMMY_WORK);
               throw var7;
            }
         }
      }
   }

   private class CheckerTimerListener implements TimerListener {
      private final WorkManager workManager;

      public CheckerTimerListener(WorkManager workManager) {
         this.workManager = workManager;
      }

      public void timerExpired(Timer timer) {
         synchronized(NodeManagerMonitorImpl.this.machines) {
            Iterator var3 = NodeManagerMonitorImpl.this.machines.keySet().iterator();

            while(var3.hasNext()) {
               MachineMBean machineMBean = (MachineMBean)var3.next();
               WorkAdapter workx = (WorkAdapter)NodeManagerMonitorImpl.this.machines.get(machineMBean);
               if (NodeManagerMonitorImpl.DUMMY_WORK.equals(workx)) {
                  WorkAdapter work = NodeManagerMonitorImpl.this.new ChangeListWork(machineMBean);
                  this.workManager.schedule(work);
                  NodeManagerMonitorImpl.this.machines.put(machineMBean, work);
                  if (NodeManagerMonitorImpl.debugLogger.isDebugEnabled()) {
                     NodeManagerMonitorImpl.this.debug("Created and scheduled work for machine: " + machineMBean);
                  }
               }
            }

         }
      }
   }

   private class ChangeListWork extends WorkAdapter {
      private final MachineMBean machineMBean;

      public ChangeListWork(MachineMBean machineMBean) {
         this.machineMBean = machineMBean;
      }

      public void run() {
         try {
            if (NodeManagerMonitorImpl.debugLogger.isDebugEnabled()) {
               NodeManagerMonitorImpl.this.debug("sending over changes to machine: " + this.machineMBean);
            }

            NodeManagerMonitorImpl.this.machineChangeListProvider.syncChangeList(this.machineMBean);
            NodeManagerMonitorImpl.this.machines.remove(this.machineMBean);
            if (NodeManagerMonitorImpl.debugLogger.isDebugEnabled()) {
               NodeManagerMonitorImpl.this.debug("work succeeded for machine:" + this.machineMBean);
            }
         } catch (IOException var2) {
            if (NodeManagerMonitorImpl.debugLogger.isDebugEnabled()) {
               NodeManagerMonitorImpl.this.debug("work failed for machine:" + this.machineMBean, var2);
            }

            if (NodeManagerMonitorImpl.this.machines.containsKey(this.machineMBean)) {
               NodeManagerMonitorImpl.this.machines.put(this.machineMBean, NodeManagerMonitorImpl.DUMMY_WORK);
            }
         }

      }
   }
}
