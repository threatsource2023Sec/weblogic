package com.sun.faces.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

public class Cache {
   private static final Logger LOGGER;
   private final ConcurrentMap cache = new ConcurrentHashMap();
   private final Factory factory;

   public Cache(Factory factory) {
      this.factory = factory;
   }

   public Object get(Object key) {
      Object result = this.cache.get(key);
      if (result == null) {
         try {
            result = this.factory.newInstance(key);
         } catch (InterruptedException var4) {
            throw new RuntimeException(var4);
         }

         Object oldResult = this.cache.putIfAbsent(key, result);
         if (oldResult != null) {
            result = oldResult;
         }
      }

      return result;
   }

   public Object remove(Object key) {
      return this.cache.remove(key);
   }

   static {
      LOGGER = FacesLogger.UTIL.getLogger();
   }

   public interface Factory {
      Object newInstance(Object var1) throws InterruptedException;
   }
}
