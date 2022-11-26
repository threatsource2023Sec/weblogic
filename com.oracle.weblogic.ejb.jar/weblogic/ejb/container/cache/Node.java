package weblogic.ejb.container.cache;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.interfaces.CachingManager;

final class Node {
   private static final DebugLogger DEBUG_LOGGER;
   private static final int FREE = 0;
   private static final int INACTIVE = 1;
   private static final int ACTIVE = 2;
   private Object bean;
   private CacheKey key;
   private CachingManager callback;
   private int size;
   private int state = 0;
   private long lastTouchedAt;
   private boolean isPinned = false;
   Node prev;
   Node next;

   Node() {
      this.touch();
   }

   void touch() {
      this.lastTouchedAt = System.currentTimeMillis();
   }

   long timeSinceLastTouch() {
      return Math.abs(System.currentTimeMillis() - this.lastTouchedAt);
   }

   boolean idleLongerThan(long milliSeconds) {
      return this.timeSinceLastTouch() > milliSeconds;
   }

   void pin() {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Pinning key: " + this.key);
      }

      this.isPinned = true;
   }

   void unpin() {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         debug("Unpinning key: " + this.key);
      }

      assert this.isPinned;

      this.isPinned = false;
   }

   boolean pinned() {
      return this.isPinned;
   }

   Object getBean() {
      return this.bean;
   }

   void setBean(Object b) {
      this.bean = b;
   }

   CacheKey getKey() {
      return this.key;
   }

   void setKey(CacheKey k) {
      this.key = k;
      if (this.key != null) {
         this.callback = k.getCallback();
         this.size = this.callback.getBeanSize();
      } else {
         this.callback = null;
      }

   }

   CachingManager getCallback() {
      return this.callback;
   }

   int getSize() {
      return this.size;
   }

   long timeLastTouched() {
      return this.lastTouchedAt;
   }

   boolean olderThan(long cutoff) {
      return this.lastTouchedAt < cutoff;
   }

   void setActive() {
      this.state = 2;
   }

   void setInActive() {
      this.state = 1;
   }

   void setFree() {
      this.state = 0;
   }

   boolean isActive() {
      return this.state == 2;
   }

   boolean isFree() {
      return this.state == 0;
   }

   boolean isInActive() {
      return this.state == 1;
   }

   private static void debug(String s) {
      DEBUG_LOGGER.debug("[CacheNode] " + s);
   }

   static {
      DEBUG_LOGGER = EJBDebugService.cachingLogger;
   }
}
