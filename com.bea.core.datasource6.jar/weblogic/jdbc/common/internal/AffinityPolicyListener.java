package weblogic.jdbc.common.internal;

import oracle.ucp.ConnectionAffinityCallback;

public interface AffinityPolicyListener {
   void affinityPolicyNotification(ConnectionAffinityCallback.AffinityPolicy var1, boolean var2);
}
