package weblogic.cache.webapp;

import java.io.Serializable;
import weblogic.cache.CacheValue;

public interface CacheListener {
   String ATTRIBUTE = "weblogic.cache.CacheListener";

   void cacheUpdateOccurred(CacheEvent var1);

   void cacheAccessOccurred(CacheEvent var1);

   void cacheFlushOccurred(CacheEvent var1);

   public static class CacheEvent implements Serializable {
      private int time;
      private CacheValue value;
      private int size = -1;
      private int timeout = -1;
      private String scope;
      private String scopeType;
      private String name;
      private KeySet keySet;

      public void setTime(int time) {
         this.time = time;
      }

      public int getTime() {
         return this.time;
      }

      public void setValue(CacheValue value) {
         this.value = value;
      }

      public CacheValue getValue() {
         return this.value;
      }

      public void setSize(int size) {
         this.size = size;
      }

      public int getSize() {
         return this.size;
      }

      public void setScope(String scope) {
         this.scope = scope;
      }

      public String getScope() {
         return this.scope;
      }

      public void setScopeType(String scopeType) {
         this.scopeType = scopeType;
      }

      public String getScopeType() {
         return this.scopeType;
      }

      public void setName(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      public void setKeySet(KeySet keySet) {
         this.keySet = keySet;
      }

      public KeySet getKeySet() {
         return this.keySet;
      }
   }
}
