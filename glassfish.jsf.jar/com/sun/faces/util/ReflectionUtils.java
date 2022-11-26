package com.sun.faces.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ReflectionUtils {
   private static final Logger LOGGER;
   private static final Map REFLECTION_CACHE;

   private ReflectionUtils() {
   }

   public static void setProperties(Object object, Map propertiesToSet) {
      try {
         Map availableProperties = new HashMap();
         PropertyDescriptor[] var3 = Introspector.getBeanInfo(object.getClass()).getPropertyDescriptors();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PropertyDescriptor propertyDescriptor = var3[var5];
            availableProperties.put(propertyDescriptor.getName(), propertyDescriptor);
         }

         Iterator var8 = propertiesToSet.entrySet().iterator();

         while(var8.hasNext()) {
            Map.Entry propertyToSet = (Map.Entry)var8.next();
            ((PropertyDescriptor)availableProperties.get(propertyToSet.getKey())).getWriteMethod().invoke(object, propertyToSet.getValue());
         }

      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException var7) {
         throw new IllegalStateException(var7);
      }
   }

   public static void setPropertiesWithCoercion(Object object, Map propertiesToSet) {
      try {
         PropertyDescriptor[] var2 = Introspector.getBeanInfo(object.getClass()).getPropertyDescriptors();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PropertyDescriptor property = var2[var4];
            Method setter = property.getWriteMethod();
            if (setter != null && propertiesToSet.containsKey(property.getName())) {
               Object value = propertiesToSet.get(property.getName());
               if (value instanceof String && !property.getPropertyType().equals(String.class)) {
                  PropertyEditor editor = PropertyEditorManager.findEditor(property.getPropertyType());
                  editor.setAsText((String)value);
                  value = editor.getValue();
               }

               property.getWriteMethod().invoke(object, value);
            }
         }

      } catch (Exception var9) {
         throw new IllegalStateException(var9);
      }
   }

   public static Method findMethod(Object base, String methodName, Object[] params) {
      List methods = new ArrayList();
      Method[] var4 = base.getClass().getMethods();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Method method = var4[var6];
         if (method.getName().equals(methodName) && method.getParameterTypes().length == params.length) {
            methods.add(method);
         }
      }

      if (methods.size() == 1) {
         return (Method)methods.get(0);
      } else {
         if (methods.size() > 1) {
            Iterator var9 = methods.iterator();

            while(var9.hasNext()) {
               Method method = (Method)var9.next();
               boolean match = true;
               Class[] candidateParams = method.getParameterTypes();

               for(int i = 0; i < params.length; ++i) {
                  if (!candidateParams[i].isInstance(params[i])) {
                     match = false;
                     break;
                  }
               }

               if (match) {
                  return method;
               }
            }
         }

         return null;
      }
   }

   public static Class toClass(String className) {
      try {
         return Class.forName(className, true, Thread.currentThread().getContextClassLoader());
      } catch (ClassNotFoundException var4) {
         try {
            return Class.forName(className);
         } catch (Exception var3) {
            Object var2 = null;
            throw new IllegalStateException(var4);
         }
      }
   }

   public static Object instance(String className) {
      return instance(toClass(className));
   }

   public static Object instance(Class clazz) {
      try {
         return clazz.newInstance();
      } catch (IllegalAccessException | InstantiationException var2) {
         throw new IllegalStateException(var2);
      }
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

   public static Method lookupMethod(Object object, String methodName, Class... params) {
      Class clazz = object.getClass();
      ClassLoader loader = Util.getCurrentLoader(clazz);
      return loader == null ? null : getMetaData(loader, clazz).lookupMethod(methodName, params);
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

   public static Method lookupWriteMethod(String className, String propertyName) {
      ClassLoader loader = Util.getCurrentLoader((Object)null);
      return loader == null ? null : getMetaData(loader, className).lookupWriteMethod(propertyName);
   }

   public static Method lookupReadMethod(String className, String propertyName) {
      ClassLoader loader = Util.getCurrentLoader((Object)null);
      return loader == null ? null : getMetaData(loader, className).lookupReadMethod(propertyName);
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

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      REFLECTION_CACHE = new WeakHashMap();
   }

   private static final class MetaData {
      Map constructors;
      Map methods;
      Map declaredMethods;
      Map propertyDescriptors;
      Class clazz;

      public MetaData(Class clazz) {
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

         String name;
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

         try {
            BeanInfo info = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] pds = info.getPropertyDescriptors();
            if (pds != null) {
               if (this.propertyDescriptors == null) {
                  this.propertyDescriptors = new HashMap(pds.length, 1.0F);
               }

               PropertyDescriptor[] var15 = pds;
               int var8 = pds.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  PropertyDescriptor pd = var15[var9];
                  this.propertyDescriptors.put(pd.getName(), pd);
               }
            }
         } catch (IntrospectionException var11) {
            if (ReflectionUtils.LOGGER.isLoggable(Level.SEVERE)) {
               ReflectionUtils.LOGGER.log(Level.SEVERE, var11.toString(), var11);
            }
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

      public Method lookupWriteMethod(String propName) {
         if (this.propertyDescriptors == null) {
            return null;
         } else {
            PropertyDescriptor pd = (PropertyDescriptor)this.propertyDescriptors.get(propName);
            return pd != null ? pd.getWriteMethod() : null;
         }
      }

      public Method lookupReadMethod(String propName) {
         if (this.propertyDescriptors == null) {
            return null;
         } else {
            PropertyDescriptor pd = (PropertyDescriptor)this.propertyDescriptors.get(propName);
            return pd != null ? pd.getReadMethod() : null;
         }
      }

      private static Integer getKey(Class... params) {
         return Arrays.deepHashCode(params);
      }
   }
}
