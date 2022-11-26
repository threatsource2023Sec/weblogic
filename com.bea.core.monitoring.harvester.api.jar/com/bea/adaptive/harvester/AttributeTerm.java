package com.bea.adaptive.harvester;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class AttributeTerm {
   String name;
   AttributeTerm next;
   AttributeTerm prev;

   AttributeTerm(String name, AttributeTerm prev) {
      this.name = name;
      this.prev = prev;
      if (prev != null) {
         prev.next = this;
      }

   }

   public boolean isArray() {
      return false;
   }

   public boolean isMap() {
      return false;
   }

   public boolean isSimple() {
      return false;
   }

   public AttributeTerm getPrev() {
      return this.prev;
   }

   public AttributeTerm getNext() {
      return this.next;
   }

   public String getName() {
      return this.name;
   }

   public AttributeTerm getRoot() {
      AttributeTerm first = null;

      AttributeTerm first2;
      for(first = this; (first2 = first.getPrev()) != null; first = first2) {
      }

      return first;
   }

   public boolean isComplex() {
      return this.prev != null || this.next != null;
   }

   public static class ArrayTerm extends AttributeTerm {
      int index;

      public ArrayTerm(AttributeTerm prev, String sub) {
         super((String)null, prev);
         if (sub.equals("*")) {
            this.index = -1;
         } else {
            this.index = Integer.parseInt(sub);
         }

      }

      public boolean isArray() {
         return true;
      }

      public int getIndex() {
         return this.index;
      }
   }

   public static class MapTerm extends AttributeTerm {
      String mapKey;

      public MapTerm(AttributeTerm prev, String key) {
         super((String)null, prev);
         if (key.equals("*")) {
            this.mapKey = null;
         }

         this.mapKey = key;
      }

      public boolean isMap() {
         return true;
      }

      public String getKey() {
         return this.mapKey;
      }
   }

   public static class SimpleTerm extends AttributeTerm {
      private Field cachedField;
      private Method cachedMethod;

      public SimpleTerm(String name, AttributeTerm prev) {
         super(name, prev);
      }

      public boolean isSimple() {
         return true;
      }

      public Field getCachedField() {
         return this.cachedField;
      }

      public Method getCachedMethod() {
         return this.cachedMethod;
      }

      public void setCachedField(Field f) {
         this.cachedField = f;
      }

      public void setCachedMethod(Method m) {
         this.cachedMethod = m;
      }
   }
}
