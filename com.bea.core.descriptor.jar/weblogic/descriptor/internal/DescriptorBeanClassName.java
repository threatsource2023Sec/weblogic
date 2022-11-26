package weblogic.descriptor.internal;

public class DescriptorBeanClassName {
   public static final String RUNTIME_SUFFIX = "Impl";
   public static final String RUNTIME_PACKAGE = "beanimpl";

   public static String toImpl(String interfaceName, boolean editable) {
      return toImpl(interfaceName, editable, Thread.currentThread().getContextClassLoader());
   }

   public static String toImpl(String interfaceName, boolean editable, ClassLoader loader) {
      String implName = interfaceName + "Impl";
      int lastPeriodPosition = implName.lastIndexOf(46);
      if (lastPeriodPosition > -1 && lastPeriodPosition < implName.length()) {
         String packageName = implName.substring(0, lastPeriodPosition + 1) + "beanimpl";
         String className = implName.substring(lastPeriodPosition + 1);
         String otherImplName = packageName + '.' + className;

         try {
            Class.forName(otherImplName, false, loader);
            implName = otherImplName;
         } catch (ClassNotFoundException var9) {
         }
      }

      return implName;
   }

   public static String toImpl(String interfaceName) {
      return toImpl(interfaceName, false);
   }

   public static String toEditableImpl(String interfaceName) {
      return toImpl(interfaceName, true);
   }

   public static String toInterface(String implName) {
      String suffix = "Impl";
      return implName.substring(0, implName.length() - suffix.length());
   }
}
