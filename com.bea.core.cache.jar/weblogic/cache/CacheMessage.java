package weblogic.cache;

import java.io.Serializable;

public class CacheMessage implements Serializable {
   private String scope;
   private String cacheName;
   private String key;
   private Object cacheValue;
   private int size;
   private int timeout;

   public void setScope(String scope) {
      this.scope = scope;
   }

   public String getScope() {
      return this.scope;
   }

   public void setCacheName(String cacheName) {
      this.cacheName = cacheName;
   }

   public String getCacheName() {
      return this.cacheName;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public String getKey() {
      return this.key;
   }

   public void setCacheValue(Object cacheValue) {
      this.cacheValue = cacheValue;
   }

   public Object getCacheValue() {
      return this.cacheValue;
   }

   public void setSize(int size) {
      this.size = size;
   }

   public int getSize() {
      return this.size;
   }

   public void setTimeout(int timeout) {
      this.timeout = timeout;
   }

   public int getTimeout() {
      return this.timeout;
   }
}
