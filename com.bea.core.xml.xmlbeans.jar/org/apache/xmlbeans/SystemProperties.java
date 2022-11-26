package org.apache.xmlbeans;

import java.util.Hashtable;

public class SystemProperties {
   protected static Hashtable propertyH;

   public static String getProperty(String key) {
      if (propertyH == null) {
         try {
            propertyH = System.getProperties();
         } catch (SecurityException var2) {
            propertyH = new Hashtable();
            return null;
         }
      }

      return (String)propertyH.get(key);
   }

   public static String getProperty(String key, String defaultValue) {
      String result = getProperty(key);
      return result == null ? defaultValue : result;
   }

   public static void setPropertyH(Hashtable aPropertyH) {
      propertyH = aPropertyH;
   }
}
