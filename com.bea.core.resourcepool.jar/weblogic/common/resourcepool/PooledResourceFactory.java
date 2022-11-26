package weblogic.common.resourcepool;

import weblogic.common.ResourceException;

public interface PooledResourceFactory {
   PooledResource createResource(PooledResourceInfo var1) throws ResourceException;

   void refreshResource(PooledResource var1) throws ResourceException;
}
