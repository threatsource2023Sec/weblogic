package weblogic.jdbc.extensions;

import oracle.ucp.ConnectionAffinityCallback;

public interface AffinityCallback extends ConnectionAffinityCallback {
   boolean isApplicationContextAvailable();
}
