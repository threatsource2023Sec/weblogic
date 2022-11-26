package com.bea.common.security.jdkutils;

import java.io.IOException;
import java.util.Collection;

public class WeaverUtil {
   private WeaverUtil() {
   }

   public static class Collections {
      private Collections() {
      }

      public static boolean addAll(Collection c, Object[] a) {
         boolean modified = false;

         for(int i = 0; i < a.length; ++i) {
            modified |= c.add(a[i]);
         }

         return modified;
      }
   }

   public static class Arrays {
      private Arrays() {
      }

      public static int hashCode(byte[] a) {
         if (a == null) {
            return 0;
         } else {
            int result = 1;

            for(int i = 0; i < a.length; ++i) {
               result = 31 * result + a[i];
            }

            return result;
         }
      }
   }

   public static class Writer {
      private Writer() {
      }

      public static void append(java.io.Writer writer, CharSequence csq) throws IOException {
         if (csq == null) {
            writer.write("null");
         } else {
            writer.write(csq.toString());
         }

      }
   }

   public static class Boolean {
      private Boolean() {
      }

      public static boolean parseBoolean(String s) {
         return s != null && s.equalsIgnoreCase("true");
      }
   }
}
