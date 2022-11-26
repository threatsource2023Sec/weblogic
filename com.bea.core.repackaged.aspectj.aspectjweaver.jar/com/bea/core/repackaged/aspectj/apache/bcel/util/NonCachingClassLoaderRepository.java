package com.bea.core.repackaged.aspectj.apache.bcel.util;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ClassParser;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NonCachingClassLoaderRepository implements Repository {
   private static ClassLoader bootClassLoader = null;
   private final ClassLoaderReference loaderRef;
   private final Map loadedClasses = new SoftHashMap();

   public NonCachingClassLoaderRepository(ClassLoader loader) {
      this.loaderRef = new DefaultClassLoaderReference(loader != null ? loader : getBootClassLoader());
   }

   public NonCachingClassLoaderRepository(ClassLoaderReference loaderRef) {
      this.loaderRef = loaderRef;
   }

   private static synchronized ClassLoader getBootClassLoader() {
      if (bootClassLoader == null) {
         bootClassLoader = new URLClassLoader(new URL[0]);
      }

      return bootClassLoader;
   }

   public void storeClass(JavaClass clazz) {
      synchronized(this.loadedClasses) {
         this.loadedClasses.put(clazz.getClassName(), clazz);
      }

      clazz.setRepository(this);
   }

   public void removeClass(JavaClass clazz) {
      synchronized(this.loadedClasses) {
         this.loadedClasses.remove(clazz.getClassName());
      }
   }

   public JavaClass findClass(String className) {
      synchronized(this.loadedClasses) {
         return this.loadedClasses.containsKey(className) ? (JavaClass)this.loadedClasses.get(className) : null;
      }
   }

   public void clear() {
      synchronized(this.loadedClasses) {
         this.loadedClasses.clear();
      }
   }

   public JavaClass loadClass(String className) throws ClassNotFoundException {
      JavaClass javaClass = this.findClass(className);
      if (javaClass != null) {
         return javaClass;
      } else {
         javaClass = this.loadJavaClass(className);
         this.storeClass(javaClass);
         return javaClass;
      }
   }

   public JavaClass loadClass(Class clazz) throws ClassNotFoundException {
      return this.loadClass(clazz.getName());
   }

   private JavaClass loadJavaClass(String className) throws ClassNotFoundException {
      String classFile = className.replace('.', '/');

      try {
         InputStream is = this.loaderRef.getClassLoader().getResourceAsStream(classFile + ".class");
         if (is == null) {
            throw new ClassNotFoundException(className + " not found.");
         } else {
            ClassParser parser = new ClassParser(is, className);
            return parser.parse();
         }
      } catch (IOException var5) {
         throw new ClassNotFoundException(var5.toString());
      }
   }

   public static class SoftHashMap extends AbstractMap {
      private Map map;
      private ReferenceQueue rq;

      public SoftHashMap(Map map) {
         this.rq = new ReferenceQueue();
         this.map = map;
      }

      public SoftHashMap() {
         this(new HashMap());
      }

      public SoftHashMap(Map map, boolean b) {
         this(map);
      }

      private void processQueue() {
         SpecialValue sv = null;

         while((sv = (SpecialValue)this.rq.poll()) != null) {
            this.map.remove(sv.key);
         }

      }

      public Object get(Object key) {
         SpecialValue value = (SpecialValue)this.map.get(key);
         if (value == null) {
            return null;
         } else if (value.get() == null) {
            this.map.remove(value.key);
            return null;
         } else {
            return value.get();
         }
      }

      public Object put(Object k, Object v) {
         this.processQueue();
         return this.map.put(k, new SpecialValue(k, v));
      }

      public Set entrySet() {
         return this.map.entrySet();
      }

      public void clear() {
         this.processQueue();
         Set keys = this.map.keySet();
         Iterator iterator = keys.iterator();

         while(iterator.hasNext()) {
            Object name = iterator.next();
            this.map.remove(name);
         }

      }

      public int size() {
         this.processQueue();
         return this.map.size();
      }

      public Object remove(Object k) {
         this.processQueue();
         SpecialValue value = (SpecialValue)this.map.remove(k);
         if (value == null) {
            return null;
         } else {
            return value.get() != null ? value.get() : null;
         }
      }

      class SpecialValue extends SoftReference {
         private final Object key;

         SpecialValue(Object k, Object v) {
            super(v, SoftHashMap.this.rq);
            this.key = k;
         }
      }
   }
}
