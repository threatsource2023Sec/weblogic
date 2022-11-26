package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.lang.Nullable;

public abstract class PropertyAccessorUtils {
   public static String getPropertyName(String propertyPath) {
      int separatorIndex = propertyPath.endsWith("]") ? propertyPath.indexOf(91) : -1;
      return separatorIndex != -1 ? propertyPath.substring(0, separatorIndex) : propertyPath;
   }

   public static boolean isNestedOrIndexedProperty(@Nullable String propertyPath) {
      if (propertyPath == null) {
         return false;
      } else {
         for(int i = 0; i < propertyPath.length(); ++i) {
            char ch = propertyPath.charAt(i);
            if (ch == '.' || ch == '[') {
               return true;
            }
         }

         return false;
      }
   }

   public static int getFirstNestedPropertySeparatorIndex(String propertyPath) {
      return getNestedPropertySeparatorIndex(propertyPath, false);
   }

   public static int getLastNestedPropertySeparatorIndex(String propertyPath) {
      return getNestedPropertySeparatorIndex(propertyPath, true);
   }

   private static int getNestedPropertySeparatorIndex(String propertyPath, boolean last) {
      boolean inKey = false;
      int length = propertyPath.length();
      int i = last ? length - 1 : 0;

      while(true) {
         if (last) {
            if (i < 0) {
               break;
            }
         } else if (i >= length) {
            break;
         }

         switch (propertyPath.charAt(i)) {
            case '.':
               if (!inKey) {
                  return i;
               }
               break;
            case '[':
            case ']':
               inKey = !inKey;
         }

         if (last) {
            --i;
         } else {
            ++i;
         }
      }

      return -1;
   }

   public static boolean matchesProperty(String registeredPath, String propertyPath) {
      if (!registeredPath.startsWith(propertyPath)) {
         return false;
      } else if (registeredPath.length() == propertyPath.length()) {
         return true;
      } else if (registeredPath.charAt(propertyPath.length()) != '[') {
         return false;
      } else {
         return registeredPath.indexOf(93, propertyPath.length() + 1) == registeredPath.length() - 1;
      }
   }

   public static String canonicalPropertyName(@Nullable String propertyName) {
      if (propertyName == null) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder(propertyName);
         int searchIndex = 0;

         while(true) {
            int keyStart;
            int keyEnd;
            do {
               do {
                  if (searchIndex == -1) {
                     return sb.toString();
                  }

                  keyStart = sb.indexOf("[", searchIndex);
                  searchIndex = -1;
               } while(keyStart == -1);

               keyEnd = sb.indexOf("]", keyStart + "[".length());
            } while(keyEnd == -1);

            String key = sb.substring(keyStart + "[".length(), keyEnd);
            if (key.startsWith("'") && key.endsWith("'") || key.startsWith("\"") && key.endsWith("\"")) {
               sb.delete(keyStart + 1, keyStart + 2);
               sb.delete(keyEnd - 2, keyEnd - 1);
               keyEnd -= 2;
            }

            searchIndex = keyEnd + "]".length();
         }
      }
   }

   @Nullable
   public static String[] canonicalPropertyNames(@Nullable String[] propertyNames) {
      if (propertyNames == null) {
         return null;
      } else {
         String[] result = new String[propertyNames.length];

         for(int i = 0; i < propertyNames.length; ++i) {
            result[i] = canonicalPropertyName(propertyNames[i]);
         }

         return result;
      }
   }
}
