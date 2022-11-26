package weblogic.utils.classloaders.debug;

import java.util.HashMap;
import java.util.Map;
import weblogic.utils.classloaders.ChangeAwareClassLoader;
import weblogic.utils.classloaders.FilteringClassLoader;
import weblogic.utils.classloaders.GenericClassLoader;

public enum SupportedClassLoader {
   GCL(GenericClassLoader.class.getName()),
   FCL(FilteringClassLoader.class.getName()),
   CACL(ChangeAwareClassLoader.class.getName());

   private final String className;
   private static Map acronyms;

   private SupportedClassLoader(String className) {
      this.className = className;
   }

   public static SupportedClassLoader fromClass(Class c) {
      return valueOf(getAcronym(c.getSimpleName()));
   }

   public static boolean isSupported(Class clz) {
      return isSupported(clz.getName());
   }

   public static boolean isSupported(String className) {
      SupportedClassLoader[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         SupportedClassLoader type = var1[var3];
         if (type.className.equals(className)) {
            return true;
         }
      }

      return false;
   }

   private static String getAcronym(String simpleName) {
      if (acronyms == null) {
         acronyms = new HashMap();
      }

      if (acronyms.containsKey(simpleName)) {
         return (String)acronyms.get(simpleName);
      } else {
         StringBuilder builder = new StringBuilder();
         char[] var2 = simpleName.toCharArray();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            char c = var2[var4];
            if (c >= 'A' && c <= 'Z') {
               builder.append(c);
            }
         }

         String acronym = builder.toString();
         acronyms.put(simpleName, acronym);
         return acronym;
      }
   }
}
