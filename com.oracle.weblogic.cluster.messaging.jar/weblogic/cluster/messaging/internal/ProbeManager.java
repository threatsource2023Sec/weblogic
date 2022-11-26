package weblogic.cluster.messaging.internal;

import java.util.Iterator;
import java.util.LinkedList;

public final class ProbeManager {
   private LinkedList list = new LinkedList();
   private static ProbeManager clusterMasterProbeManager;
   private static ProbeManager clusterMemberProbeManager;

   public static ProbeManager getClusterMasterProbeManager() {
      if (clusterMasterProbeManager == null) {
         ProbeManager initializeMe = new ProbeManager();
         initializeMe.add(new HttpPingProbe());
         initializeMe.add(new NodeManagerQueryProbe());
         clusterMasterProbeManager = initializeMe;
      }

      return clusterMasterProbeManager;
   }

   public static ProbeManager getClusterMemberProbeManager() {
      if (clusterMemberProbeManager == null) {
         ProbeManager initializeMe = new ProbeManager();
         initializeMe.add(new LeaseTableReachabilityProbe());
         clusterMemberProbeManager = initializeMe;
      }

      return clusterMemberProbeManager;
   }

   protected ProbeManager() {
   }

   public synchronized void add(Probe probe) {
      this.list.add(probe);
   }

   public synchronized Probe remove(Probe probe) {
      int index = this.list.indexOf(probe);
      return (Probe)this.list.remove(index);
   }

   synchronized LinkedList getProbes() {
      return new LinkedList(this.list);
   }

   public synchronized void invoke(ProbeContext context) {
      Iterator itr = this.list.iterator();

      while(itr.hasNext()) {
         Probe probe = (Probe)itr.next();
         probe.invoke(context);
         if (context.getNextAction() != 1) {
            break;
         }
      }

   }
}
