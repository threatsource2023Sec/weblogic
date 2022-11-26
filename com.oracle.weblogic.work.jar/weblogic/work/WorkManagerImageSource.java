package weblogic.work;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.diagnostics.image.PartitionAwareImageSource;

public class WorkManagerImageSource implements PartitionAwareImageSource {
   private static final WorkManagerImageSource THE_ONE = new WorkManagerImageSource();
   final ArrayList workManagers = new ArrayList();

   private WorkManagerImageSource() {
   }

   static WorkManagerImageSource getInstance() {
      return THE_ONE;
   }

   synchronized void register(ServerWorkManagerImpl wm) {
      this.workManagers.add(wm);
   }

   synchronized void deregister(ServerWorkManagerImpl wm) {
      this.workManagers.remove(wm);
   }

   public synchronized void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      this.createDiagnosticImage((String)null, out);
   }

   public synchronized void createDiagnosticImage(String partitionName, OutputStream out) throws ImageSourceCreationException {
      RequestManager rm = RequestManager.getInstance();
      PrintWriter img = new PrintWriter(out);
      if (partitionName == null) {
         img.println("Total thread count  : " + rm.getExecuteThreadCount());
         img.println("Idle thread count   : " + rm.getIdleThreadCount());
         img.println("Standby thread count: " + rm.getStandbyCount());
         img.println("Queue depth         : " + rm.getQueueDepth());
         img.println("Queue departures    : " + rm.getQueueDepartures());
         img.println("Mean throughput     : " + rm.getThroughput());
         img.println("Total requests      : " + rm.getTotalRequestsCount());
      }

      Iterator iter = this.workManagers.iterator();

      while(iter.hasNext()) {
         ServerWorkManagerImpl swm = (ServerWorkManagerImpl)iter.next();
         if (this.workManagerInPartition(swm, partitionName)) {
            swm.dumpInformation(img);
         }
      }

      img.flush();
   }

   private boolean workManagerInPartition(ServerWorkManagerImpl swm, String partitionName) {
      String wmPartitionName = swm.getPartitionName();
      if (partitionName == null) {
         return wmPartitionName == null;
      } else {
         return partitionName.equals(wmPartitionName);
      }
   }

   synchronized void cleanupForPartition(String partitionName) {
      if (partitionName != null) {
         ArrayList wmToRemove = new ArrayList();
         Iterator var3 = this.workManagers.iterator();

         ServerWorkManagerImpl serverWorkManager;
         while(var3.hasNext()) {
            serverWorkManager = (ServerWorkManagerImpl)var3.next();
            if (this.workManagerInPartition(serverWorkManager, partitionName)) {
               wmToRemove.add(serverWorkManager);
            }
         }

         var3 = wmToRemove.iterator();

         while(var3.hasNext()) {
            serverWorkManager = (ServerWorkManagerImpl)var3.next();
            this.deregister(serverWorkManager);
         }

      }
   }

   public void timeoutImageCreation() {
   }
}
