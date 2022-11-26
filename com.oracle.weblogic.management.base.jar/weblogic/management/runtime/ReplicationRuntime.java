package weblogic.management.runtime;

public class ReplicationRuntime extends RuntimeMBeanDelegate implements ReplicationRuntimeMBean {
   public long getPrimaryCount() {
      return 0L;
   }

   public ReplicationRuntime() throws Exception {
   }

   public long getSecondaryCount() {
      return 0L;
   }

   public String getSecondaryServerDetails() {
      return null;
   }

   public String[] getDetailedSecondariesDistribution() {
      return null;
   }
}
