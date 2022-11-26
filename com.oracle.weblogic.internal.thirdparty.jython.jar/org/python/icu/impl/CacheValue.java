package org.python.icu.impl;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import org.python.icu.util.ICUException;

public abstract class CacheValue {
   private static volatile Strength strength;
   private static final CacheValue NULL_VALUE;

   public static void setStrength(Strength strength) {
      CacheValue.strength = strength;
   }

   public static boolean futureInstancesWillBeStrong() {
      return strength == CacheValue.Strength.STRONG;
   }

   public static CacheValue getInstance(Object value) {
      if (value == null) {
         return NULL_VALUE;
      } else {
         return (CacheValue)(strength == CacheValue.Strength.STRONG ? new StrongValue(value) : new SoftValue(value));
      }
   }

   public boolean isNull() {
      return false;
   }

   public abstract Object get();

   public abstract Object resetIfCleared(Object var1);

   static {
      strength = CacheValue.Strength.SOFT;
      NULL_VALUE = new NullValue();
   }

   private static final class SoftValue extends CacheValue {
      private Reference ref;

      SoftValue(Object value) {
         this.ref = new SoftReference(value);
      }

      public Object get() {
         return this.ref.get();
      }

      public synchronized Object resetIfCleared(Object value) {
         Object oldValue = this.ref.get();
         if (oldValue == null) {
            this.ref = new SoftReference(value);
            return value;
         } else {
            return oldValue;
         }
      }
   }

   private static final class StrongValue extends CacheValue {
      private Object value;

      StrongValue(Object value) {
         this.value = value;
      }

      public Object get() {
         return this.value;
      }

      public Object resetIfCleared(Object value) {
         return this.value;
      }
   }

   private static final class NullValue extends CacheValue {
      private NullValue() {
      }

      public boolean isNull() {
         return true;
      }

      public Object get() {
         return null;
      }

      public Object resetIfCleared(Object value) {
         if (value != null) {
            throw new ICUException("resetting a null value to a non-null value");
         } else {
            return null;
         }
      }

      // $FF: synthetic method
      NullValue(Object x0) {
         this();
      }
   }

   public static enum Strength {
      STRONG,
      SOFT;
   }
}
