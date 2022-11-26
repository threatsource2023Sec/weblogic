package com.bea.core.repackaged.springframework.cglib.beans;

import com.bea.core.repackaged.springframework.asm.ClassVisitor;
import com.bea.core.repackaged.springframework.cglib.core.AbstractClassGenerator;
import com.bea.core.repackaged.springframework.cglib.core.KeyFactory;
import com.bea.core.repackaged.springframework.cglib.core.ReflectUtils;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BeanMap implements Map {
   public static final int REQUIRE_GETTER = 1;
   public static final int REQUIRE_SETTER = 2;
   protected Object bean;

   public static BeanMap create(Object bean) {
      Generator gen = new Generator();
      gen.setBean(bean);
      return gen.create();
   }

   public abstract BeanMap newInstance(Object var1);

   public abstract Class getPropertyType(String var1);

   protected BeanMap() {
   }

   protected BeanMap(Object bean) {
      this.setBean(bean);
   }

   public Object get(Object key) {
      return this.get(this.bean, key);
   }

   public Object put(Object key, Object value) {
      return this.put(this.bean, key, value);
   }

   public abstract Object get(Object var1, Object var2);

   public abstract Object put(Object var1, Object var2, Object var3);

   public void setBean(Object bean) {
      this.bean = bean;
   }

   public Object getBean() {
      return this.bean;
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }

   public boolean containsKey(Object key) {
      return this.keySet().contains(key);
   }

   public boolean containsValue(Object value) {
      Iterator it = this.keySet().iterator();

      Object v;
      do {
         if (!it.hasNext()) {
            return false;
         }

         v = this.get(it.next());
      } while((value != null || v != null) && (value == null || !value.equals(v)));

      return true;
   }

   public int size() {
      return this.keySet().size();
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public Object remove(Object key) {
      throw new UnsupportedOperationException();
   }

   public void putAll(Map t) {
      Iterator it = t.keySet().iterator();

      while(it.hasNext()) {
         Object key = it.next();
         this.put(key, t.get(key));
      }

   }

   public boolean equals(Object o) {
      if (o != null && o instanceof Map) {
         Map other = (Map)o;
         if (this.size() != other.size()) {
            return false;
         } else {
            Iterator it = this.keySet().iterator();

            while(true) {
               if (!it.hasNext()) {
                  return true;
               }

               Object key = it.next();
               if (!other.containsKey(key)) {
                  return false;
               }

               Object v1 = this.get(key);
               Object v2 = other.get(key);
               if (v1 == null) {
                  if (v2 == null) {
                     continue;
                  }
                  break;
               } else if (!v1.equals(v2)) {
                  break;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int code = 0;

      Object key;
      Object value;
      for(Iterator it = this.keySet().iterator(); it.hasNext(); code += (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode())) {
         key = it.next();
         value = this.get(key);
      }

      return code;
   }

   public Set entrySet() {
      HashMap copy = new HashMap();
      Iterator it = this.keySet().iterator();

      while(it.hasNext()) {
         Object key = it.next();
         copy.put(key, this.get(key));
      }

      return Collections.unmodifiableMap(copy).entrySet();
   }

   public Collection values() {
      Set keys = this.keySet();
      List values = new ArrayList(keys.size());
      Iterator it = keys.iterator();

      while(it.hasNext()) {
         values.add(this.get(it.next()));
      }

      return Collections.unmodifiableCollection(values);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append('{');
      Iterator it = this.keySet().iterator();

      while(it.hasNext()) {
         Object key = it.next();
         sb.append(key);
         sb.append('=');
         sb.append(this.get(key));
         if (it.hasNext()) {
            sb.append(", ");
         }
      }

      sb.append('}');
      return sb.toString();
   }

   public static class Generator extends AbstractClassGenerator {
      private static final AbstractClassGenerator.Source SOURCE = new AbstractClassGenerator.Source(BeanMap.class.getName());
      private static final BeanMapKey KEY_FACTORY;
      private Object bean;
      private Class beanClass;
      private int require;

      public Generator() {
         super(SOURCE);
      }

      public void setBean(Object bean) {
         this.bean = bean;
         if (bean != null) {
            this.beanClass = bean.getClass();
         }

      }

      public void setBeanClass(Class beanClass) {
         this.beanClass = beanClass;
      }

      public void setRequire(int require) {
         this.require = require;
      }

      protected ClassLoader getDefaultClassLoader() {
         return this.beanClass.getClassLoader();
      }

      protected ProtectionDomain getProtectionDomain() {
         return ReflectUtils.getProtectionDomain(this.beanClass);
      }

      public BeanMap create() {
         if (this.beanClass == null) {
            throw new IllegalArgumentException("Class of bean unknown");
         } else {
            this.setNamePrefix(this.beanClass.getName());
            return (BeanMap)super.create(KEY_FACTORY.newInstance(this.beanClass, this.require));
         }
      }

      public void generateClass(ClassVisitor v) throws Exception {
         new BeanMapEmitter(v, this.getClassName(), this.beanClass, this.require);
      }

      protected Object firstInstance(Class type) {
         return ((BeanMap)ReflectUtils.newInstance(type)).newInstance(this.bean);
      }

      protected Object nextInstance(Object instance) {
         return ((BeanMap)instance).newInstance(this.bean);
      }

      static {
         KEY_FACTORY = (BeanMapKey)KeyFactory.create(BeanMapKey.class, KeyFactory.CLASS_BY_NAME);
      }

      interface BeanMapKey {
         Object newInstance(Class var1, int var2);
      }
   }
}
