package weblogic.utils.classloaders;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class AugmentableClassLoaderManager {
   private static final Map classLoaderCache = Collections.synchronizedMap(new WeakHashMap(11));

   public static GenericClassLoader getAugmentableSystemClassLoader() {
      return AugmentableClassLoaderManager.AugmentableSingletonMaker.singleton;
   }

   public static final GenericClassLoader getAugmentableClassLoader(ClassLoader cl) {
      if (cl instanceof GenericClassLoader) {
         GenericClassLoader gcl = (GenericClassLoader)cl;
         return gcl;
      } else if (cl != null && cl != AugmentableClassLoaderManager.class.getClassLoader()) {
         WeakReference ref = (WeakReference)classLoaderCache.get(cl);
         GenericClassLoader gcl = null;
         if (ref != null && ref.get() != null) {
            gcl = (GenericClassLoader)ref.get();
         } else {
            gcl = new GenericClassLoader(new MultiClassFinder(), cl);
            ref = new WeakReference(gcl);
            classLoaderCache.put(cl, ref);
         }

         return gcl;
      } else {
         return AugmentableClassLoaderManager.AugmentableSingletonMaker.singleton;
      }
   }

   private static final class AugmentableSingletonMaker {
      static final GenericClassLoader singleton;

      static {
         ClassLoader contextCL = Thread.currentThread().getContextClassLoader();
         if (contextCL == null) {
            contextCL = AugmentableClassLoaderManager.class.getClassLoader();
         }

         singleton = new GenericClassLoader(new MultiClassFinder(), contextCL);
         singleton.setAnnotation(Annotation.createNonAppAnnotation("System:Augmentable"));
         AugmentableClassLoaderManager.classLoaderCache.put(contextCL, new WeakReference(singleton));
      }
   }
}
