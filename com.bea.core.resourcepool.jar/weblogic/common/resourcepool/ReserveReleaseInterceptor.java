package weblogic.common.resourcepool;

import weblogic.common.ResourceException;

public interface ReserveReleaseInterceptor {
   void onReserve(PooledResource var1) throws ResourceException;

   void onRelease(PooledResource var1) throws ResourceException;
}
