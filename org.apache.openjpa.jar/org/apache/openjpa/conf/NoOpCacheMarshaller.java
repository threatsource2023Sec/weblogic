package org.apache.openjpa.conf;

public class NoOpCacheMarshaller implements CacheMarshaller {
   private String id;

   public Object load() {
      return null;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getId() {
      return this.id;
   }

   public void setValidationPolicy(String policy) {
   }

   public void store(Object o) {
   }
}
