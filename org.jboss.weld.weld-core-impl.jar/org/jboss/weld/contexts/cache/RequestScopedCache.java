package org.jboss.weld.contexts.cache;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RequestScopedCache {
   private static final ThreadLocal CACHE = new ThreadLocal();

   private RequestScopedCache() {
   }

   public static boolean isActive() {
      return CACHE.get() != null;
   }

   private static void checkCacheForAdding(List cache) {
      if (cache == null) {
         throw new IllegalStateException("Unable to add request scoped cache item when request cache is not active");
      }
   }

   public static void addItem(RequestScopedItem item) {
      List cache = (List)CACHE.get();
      checkCacheForAdding(cache);
      cache.add(item);
   }

   public static boolean addItemIfActive(RequestScopedItem item) {
      List cache = (List)CACHE.get();
      if (cache != null) {
         cache.add(item);
         return true;
      } else {
         return false;
      }
   }

   public static boolean addItemIfActive(final ThreadLocal item) {
      List cache = (List)CACHE.get();
      if (cache != null) {
         cache.add(new RequestScopedItem() {
            public void invalidate() {
               item.remove();
            }
         });
         return true;
      } else {
         return false;
      }
   }

   public static void beginRequest() {
      endRequest();
      CACHE.set(new LinkedList());
   }

   public static void endRequest() {
      List result = (List)CACHE.get();
      if (result != null) {
         CACHE.remove();
         Iterator var1 = result.iterator();

         while(var1.hasNext()) {
            RequestScopedItem item = (RequestScopedItem)var1.next();
            item.invalidate();
         }
      }

   }

   public static void invalidate() {
      if (isActive()) {
         endRequest();
         beginRequest();
      }

   }
}
