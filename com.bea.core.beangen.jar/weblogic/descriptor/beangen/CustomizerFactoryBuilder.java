package weblogic.descriptor.beangen;

import java.util.HashMap;
import java.util.Map;

public final class CustomizerFactoryBuilder {
   private static final Map factoryCache = new HashMap();

   private CustomizerFactoryBuilder() {
   }

   public static CustomizerFactory buildFactory(String factoryClassName) {
      synchronized(factoryCache) {
         if (factoryCache.containsKey(factoryClassName)) {
            return (CustomizerFactory)factoryCache.get(factoryClassName);
         } else {
            CustomizerFactory factory = doBuildFactory(factoryClassName);
            factoryCache.put(factoryClassName, factory);
            return factory;
         }
      }
   }

   private static CustomizerFactory doBuildFactory(String factoryClassName) {
      try {
         Class cls = Class.forName(factoryClassName);
         return (CustomizerFactory)cls.newInstance();
      } catch (Exception var2) {
         throw new RuntimeException("Cannot build CustomizerFactory [" + factoryClassName + "].", var2);
      }
   }
}
