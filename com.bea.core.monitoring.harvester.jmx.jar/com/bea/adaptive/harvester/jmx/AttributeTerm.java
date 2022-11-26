package com.bea.adaptive.harvester.jmx;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public abstract class AttributeTerm {
   String name;
   AttributeTerm next;
   AttributeTerm prev;
   private static HarvesterJMXTextTextFormatter mtf_base = HarvesterJMXTextTextFormatter.getInstance();

   AttributeTerm(String name, AttributeTerm prev) {
      this.name = name;
      this.prev = prev;
      if (prev != null) {
         prev.next = this;
      }

   }

   public String dump() {
      String s = "" + this;
      if (this.next != null) {
         s = s + " => " + this.next.dump();
      }

      return s;
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

      public String toString() {
         return AttributeTerm.mtf_base.getMapArrayTermStr(this.index);
      }

      public boolean isArray() {
         return true;
      }

      public int getIndex() {
         return this.index;
      }

      public int hashCode() {
         return this.index;
      }

      public boolean equals(Object obj) {
         boolean result = super.equals(obj);
         if (!result && obj instanceof ArrayTerm) {
            ArrayTerm rhs = (ArrayTerm)obj;
            result = this.index == rhs.index;
         }

         return result;
      }
   }

   public static class MapTerm extends AttributeTerm {
      private ArrayList mapKeys;
      private Pattern mapPat;

      public MapTerm(AttributeTerm prev, String key, boolean keyIsPat) {
         super((String)null, prev);
         this.mapKeys = new ArrayList(1);
         if (keyIsPat) {
            this.mapPat = Pattern.compile(key);
         } else if (!key.equals("*")) {
            this.mapKeys.add(key);
         }

      }

      public MapTerm(AttributeTerm prev, String[] keys) {
         super((String)null, prev);
         if (keys != null) {
            this.mapKeys = new ArrayList(keys.length);
            String[] var3 = keys;
            int var4 = keys.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               String key = var3[var5];
               this.mapKeys.add(key);
            }
         }

      }

      public boolean isMap() {
         return true;
      }

      public String[] getKeys() {
         String[] keys = new String[0];
         if (this.mapKeys != null) {
            keys = (String[])this.mapKeys.toArray(new String[this.mapKeys.size()]);
         }

         return keys;
      }

      public int getNumKeys() {
         return this.mapKeys.size();
      }

      public boolean isDiscreteKeySet() {
         return this.mapKeys.size() > 1;
      }

      public String toString() {
         String keySet = Arrays.toString(this.getKeys());
         return this.mapPat != null ? AttributeTerm.mtf_base.getMapRegexExprTermStr(keySet) : AttributeTerm.mtf_base.getMapExprTermStr(keySet);
      }

      public int hashCode() {
         int retVal = this.mapKeys.hashCode();
         return this.mapPat == null ? retVal : retVal ^ this.mapPat.hashCode();
      }

      public boolean equals(Object obj) {
         boolean result = super.equals(obj);
         if (!result && obj instanceof MapTerm) {
            MapTerm rhs = (MapTerm)obj;
            if (this.mapKeys.equals(rhs.mapKeys) && (this.mapPat != null && rhs.mapPat != null && this.mapPat.toString().equals(rhs.mapPat.toString()) || this.mapPat == null && rhs.mapPat == null)) {
               result = true;
            }
         }

         return result;
      }

      Pattern getPattern() {
         return this.mapPat;
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

      public String toString() {
         return AttributeTerm.mtf_base.getSimpleExprTermStr(this.name);
      }

      public int hashCode() {
         return this.name == null ? 0 : this.name.hashCode();
      }

      public boolean equals(Object obj) {
         boolean result = super.equals(obj);
         if (!result && obj instanceof SimpleTerm) {
            SimpleTerm rhs = (SimpleTerm)obj;
            result = this.name.equals(rhs.getName());
         }

         return result;
      }
   }
}
