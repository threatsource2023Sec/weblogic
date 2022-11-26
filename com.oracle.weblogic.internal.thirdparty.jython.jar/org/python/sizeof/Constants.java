package org.python.sizeof;

import java.lang.reflect.Field;

final class Constants {
   public static final boolean JRE_IS_64BIT;
   public static final String JAVA_VERSION = System.getProperty("java.version");
   public static final String JAVA_VENDOR = System.getProperty("java.vendor");
   public static final String JVM_VENDOR = System.getProperty("java.vm.vendor");
   public static final String JVM_VERSION = System.getProperty("java.vm.version");
   public static final String JVM_NAME = System.getProperty("java.vm.name");
   public static final String OS_ARCH = System.getProperty("os.arch");
   public static final String OS_VERSION = System.getProperty("os.version");

   private Constants() {
   }

   static {
      String OS_ARCH = System.getProperty("os.arch");
      boolean is64Bit = false;

      try {
         Class unsafeClass = Class.forName("sun.misc.Unsafe");
         Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
         unsafeField.setAccessible(true);
         Object unsafe = unsafeField.get((Object)null);
         int addressSize = ((Number)unsafeClass.getMethod("addressSize").invoke(unsafe)).intValue();
         is64Bit = addressSize >= 8;
      } catch (Exception var6) {
         String x = System.getProperty("sun.arch.data.model");
         if (x != null) {
            is64Bit = x.indexOf("64") != -1;
         } else if (OS_ARCH != null && OS_ARCH.indexOf("64") != -1) {
            is64Bit = true;
         } else {
            is64Bit = false;
         }
      }

      JRE_IS_64BIT = is64Bit;
   }
}
