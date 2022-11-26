package weblogic.servlet.utils;

import weblogic.servlet.HTTPLogger;

public class URLMappingFactory {
   public static ServletMapping createDefaultURLMapping() {
      return new ServletMapping();
   }

   public static ServletMapping createDefaultURLMapping(boolean isCaseInsensitive, boolean enforceStrictPattern) {
      return new ServletMapping(isCaseInsensitive, enforceStrictPattern);
   }

   public static URLMapping createCustomURLMapping(String mapClassName, ClassLoader classloader, boolean isCaseInsensitive) {
      try {
         Class mapClass = classloader.loadClass(mapClassName);
         URLMapping map = (URLMapping)mapClass.newInstance();
         map.setCaseInsensitive(isCaseInsensitive);
         map.setExtensionCaseInsensitive(isCaseInsensitive);
         return map;
      } catch (ClassNotFoundException var5) {
         HTTPLogger.logCouldNotLoadUrlMatchMapClass(mapClassName, var5);
      } catch (InstantiationException var6) {
         HTTPLogger.logCouldNotLoadUrlMatchMapClass(mapClassName, var6);
      } catch (IllegalAccessException var7) {
         HTTPLogger.logCouldNotLoadUrlMatchMapClass(mapClassName, var7);
      } catch (ClassCastException var8) {
         HTTPLogger.logCouldNotLoadUrlMatchMapClass(mapClassName, var8);
      }

      return null;
   }

   public static StandardURLMapping createCompatibleURLMapping(String mapClassName, ClassLoader classloader, boolean isCaseInsensitive, boolean enforceStrictPattern) {
      return (StandardURLMapping)(mapClassName != null && mapClassName.equals(OC4JURLMapping.class.getName()) ? (StandardURLMapping)createCustomURLMapping(mapClassName, classloader, isCaseInsensitive) : createDefaultURLMapping(isCaseInsensitive, enforceStrictPattern));
   }

   public static boolean isInvalidUrlPattern(String mapClassName, String pattern) {
      if (pattern == null) {
         return true;
      } else {
         return (mapClassName == null || !mapClassName.equals(OC4JURLMapping.class.getName())) && pattern.length() > 1 && pattern.endsWith("*") && !pattern.endsWith("/*");
      }
   }
}
