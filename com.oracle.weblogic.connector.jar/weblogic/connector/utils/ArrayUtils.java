package weblogic.connector.utils;

import java.util.Map;

public class ArrayUtils {
   private ArrayUtils() {
   }

   public static boolean contains(String[] array, String target) {
      assert target != null;

      return contains(array, target, new KeyLocator() {
         public String getKey(String entry) {
            return entry;
         }
      });
   }

   public static boolean contains(Object[] array, String target, KeyLocator locator) {
      return search(array, target, locator) != null;
   }

   public static Object search(Object[] array, String target, KeyLocator locator) {
      assert target != null;

      if (array == null) {
         return null;
      } else {
         for(int i = 0; i < array.length; ++i) {
            if (target.equals(locator.getKey(array[i]))) {
               return array[i];
            }
         }

         return null;
      }
   }

   public static void putInMap(Object[] array, KeyLocator locator, Map map) {
      if (array != null) {
         Object[] var3 = array;
         int var4 = array.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Object t = var3[var5];
            map.put(locator.getKey(t), t);
         }
      }

   }

   public interface KeyLocator {
      String getKey(Object var1);
   }
}
