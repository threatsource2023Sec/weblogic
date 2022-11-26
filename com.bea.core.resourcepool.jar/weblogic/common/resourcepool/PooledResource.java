package weblogic.common.resourcepool;

import java.util.Collection;
import weblogic.common.ResourceException;

public interface PooledResource {
   void initialize() throws ResourceException;

   void enable();

   void disable();

   void setup();

   void cleanup() throws ResourceException;

   void destroy();

   void forceDestroy();

   int test() throws ResourceException;

   long getCreationTime() throws ResourceException;

   PooledResourceInfo getPooledResourceInfo();

   void setPooledResourceInfo(PooledResourceInfo var1);

   void setResourceCleanupHandler(ResourceCleanupHandler var1);

   ResourceCleanupHandler getResourceCleanupHandler();

   void setUsed(boolean var1);

   boolean getUsed();

   void setDestroyAfterRelease();

   boolean needDestroyAfterRelease();

   ResourcePoolGroup getPrimaryGroup();

   Collection getGroups();

   ResourcePoolGroup getGroup(String var1);
}
