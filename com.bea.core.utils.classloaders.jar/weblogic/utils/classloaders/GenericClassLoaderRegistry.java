package weblogic.utils.classloaders;

import java.util.Collection;
import java.util.Map;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.utils.collections.ConcurrentWeakHashMap;

@Service
@Singleton
public final class GenericClassLoaderRegistry {
   private static final Object PLACEHOLDER = new Object();
   private final Map allGenericClassLoaders = new ConcurrentWeakHashMap();

   public Collection listGenericClassLoaders() {
      return this.allGenericClassLoaders.keySet();
   }

   void registerGenericClassLoader(GenericClassLoader gcl) {
      this.allGenericClassLoaders.put(gcl, PLACEHOLDER);
   }

   void unregisterGenericClassLoader(GenericClassLoader gcl) {
      this.allGenericClassLoaders.remove(gcl);
   }
}
