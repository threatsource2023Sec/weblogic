package weblogic.management.runtime;

public interface ReplicationRuntimeMBean extends RuntimeMBean {
   long getPrimaryCount();

   long getSecondaryCount();

   String getSecondaryServerDetails();

   String[] getDetailedSecondariesDistribution();
}
