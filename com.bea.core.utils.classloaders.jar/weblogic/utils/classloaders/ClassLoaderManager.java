package weblogic.utils.classloaders;

public class ClassLoaderManager {
   private static final ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
   private static final ClassLoader weblogicExtensionLoader;
   private static final ClassLoader augmentableSystemLoader = AugmentableClassLoaderManager.getAugmentableSystemClassLoader();

   public static final ClassLoader getSystemLoader() {
      return systemLoader;
   }

   public static final ClassLoader getWebLogicExtensionLoader() {
      return weblogicExtensionLoader;
   }

   public static final ClassLoader getAugmentableSystemLoader() {
      return augmentableSystemLoader;
   }

   static {
      ClassLoader aslParent = augmentableSystemLoader.getParent();
      weblogicExtensionLoader = aslParent == systemLoader ? null : aslParent;
   }
}
