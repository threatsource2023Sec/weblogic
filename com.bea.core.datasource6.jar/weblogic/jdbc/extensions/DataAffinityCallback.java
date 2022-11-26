package weblogic.jdbc.extensions;

import oracle.ucp.jdbc.oracle.DataBasedConnectionAffinityCallback;

public interface DataAffinityCallback extends AffinityCallback, DataBasedConnectionAffinityCallback {
}
