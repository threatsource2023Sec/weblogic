package com.bea.security.providers.xacml;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import weblogic.security.spi.Resource;

public class ResourceConverterRegistry {
   private Map registry = Collections.synchronizedMap(new HashMap());

   public void register(ResourceConverterFactory converterFactory, Class resourceClass) {
      this.registry.put(resourceClass, converterFactory);
   }

   public ResourceConverter getConverter(Resource r) {
      for(Class c = r.getClass(); c != null; c = c.getSuperclass()) {
         ResourceConverterFactory rcf = (ResourceConverterFactory)this.registry.get(c);
         if (rcf != null) {
            return rcf.getConverter(r);
         }
      }

      return null;
   }
}
