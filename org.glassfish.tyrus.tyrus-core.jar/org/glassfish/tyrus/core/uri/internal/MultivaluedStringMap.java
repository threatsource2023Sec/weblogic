package org.glassfish.tyrus.core.uri.internal;

import java.lang.reflect.Constructor;
import java.util.List;

public class MultivaluedStringMap extends MultivaluedHashMap {
   static final long serialVersionUID = -6052320403766368902L;

   public MultivaluedStringMap(MultivaluedMap map) {
      super(map);
   }

   public MultivaluedStringMap(int initialCapacity, float loadFactor) {
      super(initialCapacity, loadFactor);
   }

   public MultivaluedStringMap(int initialCapacity) {
      super(initialCapacity);
   }

   public MultivaluedStringMap() {
   }

   protected void addFirstNull(List values) {
      values.add("");
   }

   protected void addNull(List values) {
      values.add(0, "");
   }

   public final Object getFirst(String key, Class type) {
      String value = (String)this.getFirst(key);
      if (value == null) {
         return null;
      } else {
         Constructor c = null;

         try {
            c = type.getConstructor(String.class);
         } catch (Exception var8) {
            throw new IllegalArgumentException(type.getName() + " has no String constructor", var8);
         }

         Object retVal = null;

         try {
            retVal = c.newInstance(value);
         } catch (Exception var7) {
         }

         return retVal;
      }
   }

   public final Object getFirst(String key, Object defaultValue) {
      String value = (String)this.getFirst(key);
      if (value == null) {
         return defaultValue;
      } else {
         Class type = defaultValue.getClass();
         Constructor c = null;

         try {
            c = type.getConstructor(String.class);
         } catch (Exception var9) {
            throw new IllegalArgumentException(type.getName() + " has no String constructor", var9);
         }

         Object retVal = defaultValue;

         try {
            retVal = c.newInstance(value);
         } catch (Exception var8) {
         }

         return retVal;
      }
   }
}
