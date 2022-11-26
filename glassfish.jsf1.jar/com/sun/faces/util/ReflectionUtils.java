package com.sun.faces.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class ReflectionUtils {
   private static final Map REFLECTION_CACHE = new WeakHashMap();

   private ReflectionUtils() {
   }

   public static synchronized void clearCache(ClassLoader loader) {
      REFLECTION_CACHE.remove(loader);
   }

   public static synchronized void initCache(ClassLoader loader) {
      if (REFLECTION_CACHE.get(loader) == null) {
         REFLECTION_CACHE.put(loader, new ConcurrentHashMap());
      }

   }

   public static Constructor lookupConstructor(Class clazz, Class... params) {
      ClassLoader loader = Util.getCurrentLoader(clazz);
      return loader == null ? null : getMetaData(loader, clazz).lookupConstructor(params);
   }

   public static Method lookupMethod(Class clazz, String methodName, Class... params) {
      ClassLoader loader = Util.getCurrentLoader(clazz);
      return loader == null ? null : getMetaData(loader, clazz).lookupMethod(methodName, params);
   }

   public static Object newInstance(String className) throws InstantiationException, IllegalAccessException {
      ClassLoader loader = Util.getCurrentLoader((Object)null);
      return loader == null ? null : getMetaData(loader, className).lookupClass().newInstance();
   }

   public static Class lookupClass(String className) {
      ClassLoader loader = Util.getCurrentLoader((Object)null);
      return loader == null ? null : getMetaData(loader, className).lookupClass();
   }

   private static MetaData getMetaData(ClassLoader loader, Class clazz) {
      ConcurrentMap cache = (ConcurrentMap)REFLECTION_CACHE.get(loader);
      if (cache == null) {
         initCache(loader);
         cache = (ConcurrentMap)REFLECTION_CACHE.get(loader);
      }

      MetaData meta = (MetaData)cache.get(clazz.getName());
      if (meta == null) {
         meta = new MetaData(clazz);
         cache.put(clazz.getName(), meta);
      }

      return meta;
   }

   private static MetaData getMetaData(ClassLoader loader, String className) {
      ConcurrentMap cache = (ConcurrentMap)REFLECTION_CACHE.get(loader);
      if (cache == null) {
         initCache(loader);
         cache = (ConcurrentMap)REFLECTION_CACHE.get(loader);
      }

      MetaData meta = (MetaData)cache.get(className);
      if (meta == null) {
         try {
            Class clazz = Util.loadClass(className, cache);
            meta = new MetaData(clazz);
            cache.put(className, meta);
         } catch (ClassNotFoundException var5) {
            return null;
         }
      }

      return meta;
   }

   private static final class MetaData {
      Map constructors;
      Map methods;
      Map declaredMethods;
      Class clazz;

      public MetaData(Class clazz) {
         String name = null;
         this.clazz = clazz;
         Constructor[] ctors = clazz.getConstructors();
         this.constructors = new HashMap(ctors.length, 1.0F);
         int i = 0;

         int i;
         for(i = ctors.length; i < i; ++i) {
            this.constructors.put(getKey(ctors[i].getParameterTypes()), ctors[i]);
         }

         Method[] meths = clazz.getMethods();
         this.methods = new HashMap(meths.length, 1.0F);
         i = 0;

         int len;
         HashMap declaredMethodsMap;
         for(len = meths.length; i < len; ++i) {
            name = meths[i].getName();
            declaredMethodsMap = (HashMap)this.methods.get(name);
            if (declaredMethodsMap == null) {
               declaredMethodsMap = new HashMap(4, 1.0F);
               this.methods.put(name, declaredMethodsMap);
            }

            declaredMethodsMap.put(getKey(meths[i].getParameterTypes()), meths[i]);
         }

         meths = clazz.getDeclaredMethods();
         this.declaredMethods = new HashMap(meths.length, 1.0F);
         i = 0;

         for(len = meths.length; i < len; ++i) {
            name = meths[i].getName();
            declaredMethodsMap = (HashMap)this.declaredMethods.get(name);
            if (declaredMethodsMap == null) {
               declaredMethodsMap = new HashMap(4, 1.0F);
               this.declaredMethods.put(name, declaredMethodsMap);
            }

            declaredMethodsMap.put(getKey(meths[i].getParameterTypes()), meths[i]);
         }

      }

      public Constructor lookupConstructor(Class... params) {
         return (Constructor)this.constructors.get(getKey(params));
      }

      public Method lookupMethod(String name, Class... params) {
         Map map = (Map)this.methods.get(name);
         Integer key = getKey(params);
         Method result = null;
         if (null == map || null == (result = (Method)map.get(key))) {
            map = (Map)this.declaredMethods.get(name);
            if (null != map) {
               result = (Method)map.get(key);
            }
         }

         return result;
      }

      public Class lookupClass() {
         return this.clazz;
      }

      private static Integer getKey(Class... params) {
         return Arrays.deepHashCode(params);
      }
   }
}
