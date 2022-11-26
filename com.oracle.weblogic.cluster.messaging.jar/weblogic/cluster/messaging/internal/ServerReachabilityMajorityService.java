package weblogic.cluster.messaging.internal;

public interface ServerReachabilityMajorityService {
   boolean USE_NM_CONNECTION_TIMEOUT = true;
   boolean IGNORE_NM_CONNECTION_TIMEOUT = false;

   SRMResult performSRMCheck(ServerInformation var1, String var2);

   SRMResult performSRMCheck(ServerInformation var1, String var2, String var3);

   SRMResult performSRMCheck(ServerInformation var1, String var2, String var3, boolean var4);

   SRMResult getLastSRMResult();
}
