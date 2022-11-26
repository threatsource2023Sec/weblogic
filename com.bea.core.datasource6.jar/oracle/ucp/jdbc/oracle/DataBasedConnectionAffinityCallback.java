package oracle.ucp.jdbc.oracle;

import oracle.ucp.ConnectionAffinityCallback;

public interface DataBasedConnectionAffinityCallback extends ConnectionAffinityCallback {
   boolean setDataKey(Object var1);

   int getPartitionId();
}
