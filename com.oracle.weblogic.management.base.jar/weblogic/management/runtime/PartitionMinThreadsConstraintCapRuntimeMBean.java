package weblogic.management.runtime;

public interface PartitionMinThreadsConstraintCapRuntimeMBean extends RuntimeMBean {
   int getExecutingRequests();

   int getSumMinThreadsConstraints();
}
