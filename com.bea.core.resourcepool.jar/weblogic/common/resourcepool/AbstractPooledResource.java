package weblogic.common.resourcepool;

import weblogic.common.ResourceException;

public abstract class AbstractPooledResource implements PooledResource {
   private long creationTime;
   private boolean used;
   private boolean enabled = true;

   public void initialize() {
      this.creationTime = System.currentTimeMillis();
   }

   public void enable() {
      this.enabled = true;
   }

   public void disable() {
      this.enabled = false;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void cleanup() throws ResourceException {
   }

   public void destroy() {
   }

   public void forceDestroy() {
   }

   public int test() throws ResourceException {
      return 0;
   }

   public long getCreationTime() throws ResourceException {
      return this.creationTime;
   }

   public void setResourceCleanupHandler(ResourceCleanupHandler hdlr) {
   }

   public ResourceCleanupHandler getResourceCleanupHandler() {
      return null;
   }

   public void setUsed(boolean newVal) {
      this.used = newVal;
   }

   public boolean getUsed() {
      return this.used;
   }
}
