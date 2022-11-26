package weblogic.utils.classloaders;

public final class ClassLoaderUtils {
   private ClassLoaderUtils() {
   }

   public static boolean visibleToClassLoader(Object o) {
      ClassLoader ocl = o.getClass().getClassLoader();
      if (ocl == null) {
         return true;
      } else {
         ClassLoader ccl;
         for(ccl = Thread.currentThread().getContextClassLoader(); ccl != null && !(ccl instanceof GenericClassLoader); ccl = ccl.getParent()) {
            if (ccl == ocl) {
               return true;
            }
         }

         if (ccl instanceof GenericClassLoader) {
            if (ccl == ocl) {
               return true;
            }

            if (((GenericClassLoader)ccl).isChildOf(ocl)) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean isChild(int clHashCode, ClassLoader currentCL) {
      if (currentCL instanceof GenericClassLoader) {
         return ((GenericClassLoader)currentCL).isChildOf(clHashCode);
      } else {
         ClassLoader parent;
         for(parent = currentCL.getParent(); parent != null && !(parent instanceof GenericClassLoader); parent = parent.getParent()) {
            if (parent.hashCode() == clHashCode) {
               return true;
            }
         }

         if (parent instanceof GenericClassLoader) {
            if (parent.hashCode() == clHashCode) {
               return true;
            }

            if (((GenericClassLoader)parent).isChildOf(clHashCode)) {
               return true;
            }
         }

         return false;
      }
   }

   public static GenericClassLoader createTemporaryAppClassLoader(GenericClassLoader refAppClassLoader) {
      return createAppClassLoader(refAppClassLoader, false, (ClassLoader)null);
   }

   public static GenericClassLoader createInterAppClassLoader(GenericClassLoader refAppClassLoader, ClassLoader parent) {
      return createAppClassLoader(refAppClassLoader, true, parent);
   }

   private static GenericClassLoader createAppClassLoader(GenericClassLoader refAppClassLoader, boolean useParent, ClassLoader parent) {
      ClassFinder finder = new NonClosingClassFinder(refAppClassLoader.getClassFinder());
      if (refAppClassLoader.getAltParent() != null) {
         finder = new MultiClassFinder((ClassFinder)finder);
         ((MultiClassFinder)finder).addFinderFirst(new NonClosingClassFinder(refAppClassLoader.getAltParent().getClassFinder()));
      }

      return new GenericClassLoader((ClassFinder)finder, useParent ? parent : refAppClassLoader.getParent());
   }

   private static final class NonClosingClassFinder extends DelegateFinder {
      public NonClosingClassFinder(ClassFinder finder) {
         super(finder);
      }

      public void close() {
      }
   }
}
