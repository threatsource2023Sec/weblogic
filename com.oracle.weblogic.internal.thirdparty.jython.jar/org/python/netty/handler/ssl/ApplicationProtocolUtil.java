package org.python.netty.handler.ssl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class ApplicationProtocolUtil {
   private static final int DEFAULT_LIST_SIZE = 2;

   private ApplicationProtocolUtil() {
   }

   static List toList(Iterable protocols) {
      return toList(2, (Iterable)protocols);
   }

   static List toList(int initialListSize, Iterable protocols) {
      if (protocols == null) {
         return null;
      } else {
         List result = new ArrayList(initialListSize);
         Iterator var3 = protocols.iterator();

         while(var3.hasNext()) {
            String p = (String)var3.next();
            if (p == null || p.isEmpty()) {
               throw new IllegalArgumentException("protocol cannot be null or empty");
            }

            result.add(p);
         }

         if (result.isEmpty()) {
            throw new IllegalArgumentException("protocols cannot empty");
         } else {
            return result;
         }
      }
   }

   static List toList(String... protocols) {
      return toList(2, (String[])protocols);
   }

   static List toList(int initialListSize, String... protocols) {
      if (protocols == null) {
         return null;
      } else {
         List result = new ArrayList(initialListSize);
         String[] var3 = protocols;
         int var4 = protocols.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String p = var3[var5];
            if (p == null || p.isEmpty()) {
               throw new IllegalArgumentException("protocol cannot be null or empty");
            }

            result.add(p);
         }

         if (result.isEmpty()) {
            throw new IllegalArgumentException("protocols cannot empty");
         } else {
            return result;
         }
      }
   }
}
