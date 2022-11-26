package weblogic.jdbc.common.internal;

import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.management.ManagementException;

public interface HADataSourceRuntime {
   HADataSourceInstanceRuntime createInstanceRuntime(ResourcePoolGroup var1, String var2) throws ManagementException;

   boolean instanceExists(ResourcePoolGroup var1);
}
