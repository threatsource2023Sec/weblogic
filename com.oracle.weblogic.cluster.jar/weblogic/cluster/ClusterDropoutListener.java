package weblogic.cluster;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ClusterDropoutListener implements ClusterMembersChangeListener {
   private static final boolean DEBUG = true;
   private static HashMap dropouts = new HashMap();
   private static ClusterDropoutListener theClusterDropoutListener;

   public static ClusterDropoutListener theOne() {
      return theClusterDropoutListener;
   }

   static void initialize() {
      if (theClusterDropoutListener == null) {
         theClusterDropoutListener = new ClusterDropoutListener();
      }
   }

   private ClusterDropoutListener() {
      MemberManager.theOne().addClusterMembersListener(this);
   }

   public HashMap getDropoutCounts() {
      HashMap copy = new HashMap(dropouts.size());
      Set s = dropouts.keySet();
      if (s == null) {
         return copy;
      } else {
         Iterator iter = s.iterator();

         while(iter.hasNext()) {
            String key = (String)iter.next();
            DropoutCounter dc = (DropoutCounter)dropouts.get(key);
            copy.put(key, new Integer(dc.getCount()));
         }

         return copy;
      }
   }

   public void clusterMembersChanged(ClusterMembersChangeEvent cece) {
      if (cece.getAction() == 1) {
         DropoutCounter dc = (DropoutCounter)dropouts.get(cece.getClusterMemberInfo().serverName());
         if (dc == null) {
            dc = new DropoutCounter();
         }

         dc.increment();
         dropouts.put(cece.getClusterMemberInfo().serverName(), dc);
      }

   }

   private void debug(Object o) {
      System.out.println("<ClusterDropoutListener> " + o.toString());
   }

   private static class DropoutCounter {
      private int dropouts;

      private DropoutCounter() {
         this.dropouts = 0;
      }

      public void increment() {
         ++this.dropouts;
      }

      public int getCount() {
         return this.dropouts;
      }

      public String toString() {
         return "Number of dropouts: " + this.dropouts;
      }

      // $FF: synthetic method
      DropoutCounter(Object x0) {
         this();
      }
   }
}
