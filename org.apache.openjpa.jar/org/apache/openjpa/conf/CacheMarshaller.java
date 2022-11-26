package org.apache.openjpa.conf;

public interface CacheMarshaller {
   Object load();

   void store(Object var1);

   void setId(String var1);

   String getId();

   void setValidationPolicy(String var1) throws InstantiationException, IllegalAccessException;

   public interface ValidationPolicy {
      Object getValidData(Object var1);

      Object getCacheableData(Object var1);
   }
}
