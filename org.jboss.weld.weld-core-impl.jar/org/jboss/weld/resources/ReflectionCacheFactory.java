package org.jboss.weld.resources;

import org.jboss.weld.metadata.TypeStore;
import org.jboss.weld.util.reflection.Reflections;

public class ReflectionCacheFactory {
   private static final String HOTSPOT_MARKER = "sun.reflect.annotation.AnnotationType";

   private ReflectionCacheFactory() {
   }

   public static ReflectionCache newInstance(TypeStore store) {
      return (ReflectionCache)(Reflections.isClassLoadable("sun.reflect.annotation.AnnotationType", WeldClassLoaderResourceLoader.INSTANCE) ? new HotspotReflectionCache(store) : new DefaultReflectionCache(store));
   }
}
