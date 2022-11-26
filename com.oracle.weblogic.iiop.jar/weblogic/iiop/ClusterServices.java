package weblogic.iiop;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterMembersChangeEvent;
import weblogic.cluster.ClusterMembersChangeListener;
import weblogic.cluster.ClusterServicesActivator.Locator;
import weblogic.rjvm.JVMID;

public final class ClusterServices implements ClusterMembersChangeListener {
   private static ClusterServices singleton;
   private ClusterMemberInfo[] members;
   private final Object memberslock = new Object();
   private int current = 0;
   private JVMID secondary;
   private final Object secondarylock = new Object();
   private int lcpolicy = 1;
   private final Set serverSet = Collections.synchronizedSet(new TreeSet());
   public static final int LOCATION_FORWARD_OFF = 0;
   public static final int LOCATION_FORWARD_FAILOVER = 1;
   public static final int LOCATION_FORWARD_ROUND_ROBIN = 2;
   public static final int LOCATION_FORWARD_RANDOM = 3;

   public static ClusterServices getServices() {
      return singleton;
   }

   static void initialize() {
      if (singleton == null) {
         createSingleton();
      }

   }

   private static synchronized ClusterServices createSingleton() {
      if (singleton == null) {
         singleton = new ClusterServices(IIOPClientService.locationForwardPolicy);
         weblogic.cluster.ClusterServices cs = Locator.locateClusterServices();
         if (cs != null) {
            cs.addClusterMembersListener(singleton);
         }
      }

      return singleton;
   }

   private ClusterServices(String policy) {
      if (policy != null && !policy.equals("failover")) {
         if (policy.equals("off")) {
            this.lcpolicy = 0;
         } else if (policy.equals("round-robin")) {
            this.lcpolicy = 2;
         } else if (policy.equals("random")) {
            this.lcpolicy = 3;
         }
      } else {
         this.lcpolicy = 1;
      }

      this.getMembers();
   }

   public void clusterMembersChanged(ClusterMembersChangeEvent cece) {
      this.serverSet.add(cece.getClusterMemberInfo().identity());
      this.getMembers();
   }

   public final int getLocationForwardPolicy() {
      return this.lcpolicy;
   }

   public JVMID getNextMember() {
      if (this.members != null && this.lcpolicy != 0) {
         switch (this.lcpolicy) {
            case 1:
               return this.getNextMemberPrimarySecondary();
            case 2:
            default:
               ClusterMemberInfo cminf = null;
               synchronized(this.memberslock) {
                  cminf = this.members[this.current];
                  this.current = (this.current + 1) % this.members.length;
               }

               return (JVMID)cminf.identity();
            case 3:
               return this.getNextMemberRandom();
         }
      } else {
         return JVMID.localID();
      }
   }

   private void getMembers() {
      weblogic.cluster.ClusterServices cs = Locator.locateClusterServices();
      if (cs != null) {
         synchronized(this.memberslock) {
            Collection cmem = cs.getRemoteMembers();
            if (cmem != null) {
               this.members = (ClusterMemberInfo[])((ClusterMemberInfo[])cmem.toArray(new ClusterMemberInfo[0]));
               this.current = 0;
            }

            if (this.serverSet.size() > 0) {
               this.secondary = (JVMID)this.serverSet.toArray()[0];
            }
         }
      }

   }

   protected JVMID getNextMemberRandom() {
      JVMID jvmid = null;
      synchronized(this.memberslock) {
         int count = this.members.length;
         double dIdx = Math.random() * (double)count + 0.5;
         int idx = (int)Math.round(dIdx) - 1;
         jvmid = (JVMID)this.members[idx].identity();
         return jvmid;
      }
   }

   protected JVMID getNextMemberPrimarySecondary() {
      synchronized(this.secondarylock) {
         return this.secondary != null ? this.secondary : JVMID.localID();
      }
   }
}
