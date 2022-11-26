package weblogic.scheduler.ejb;

import weblogic.invocation.ComponentInvocationContext;
import weblogic.scheduler.ejb.internal.ClusteredTimerManagerImpl;
import weblogic.scheduler.ejb.internal.ClusteredTimerManagerUtility;

public final class ClusteredTimerManagerFactory {
   public static ClusteredTimerManagerFactory getInstance() {
      return ClusteredTimerManagerFactory.Factory.THE_ONE;
   }

   private ClusteredTimerManagerFactory() {
   }

   public ClusteredTimerManager create(String name, String annotation) {
      return new ClusteredTimerManagerImpl(name, annotation);
   }

   public ClusteredTimerManager create(String name, String annotation, String dispatchPolicy) {
      return new ClusteredTimerManagerImpl(name, annotation, dispatchPolicy);
   }

   public void ensureIsOperational() throws ConfigurationException {
      ClusteredTimerManagerUtility.verifyJobSchedulerConfig();
   }

   public String getClusterOrPartitionName(ComponentInvocationContext cic) {
      return ClusteredTimerManagerUtility.getClusterOrPartitionName(cic);
   }

   // $FF: synthetic method
   ClusteredTimerManagerFactory(Object x0) {
      this();
   }

   private static final class Factory {
      static final ClusteredTimerManagerFactory THE_ONE = new ClusteredTimerManagerFactory();
   }
}
