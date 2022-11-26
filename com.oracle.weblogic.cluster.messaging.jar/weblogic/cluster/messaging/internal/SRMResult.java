package weblogic.cluster.messaging.internal;

public interface SRMResult {
   boolean hasReachabilityMajority();

   String getServerState(String var1);

   String getCurrentMachine(String var1);
}
