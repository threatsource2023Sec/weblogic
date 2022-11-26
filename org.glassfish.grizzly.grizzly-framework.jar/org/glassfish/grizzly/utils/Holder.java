package org.glassfish.grizzly.utils;

public abstract class Holder {
   public static Holder staticHolder(final Object value) {
      return new Holder() {
         public Object get() {
            return value;
         }
      };
   }

   public static IntHolder staticIntHolder(final int value) {
      return new IntHolder() {
         public int getInt() {
            return value;
         }
      };
   }

   public static LazyHolder lazyHolder(final NullaryFunction factory) {
      return new LazyHolder() {
         protected Object evaluate() {
            return factory.evaluate();
         }
      };
   }

   public static LazyIntHolder lazyIntHolder(final NullaryFunction factory) {
      return new LazyIntHolder() {
         protected int evaluate() {
            return (Integer)factory.evaluate();
         }
      };
   }

   public abstract Object get();

   public String toString() {
      Object obj = this.get();
      return obj != null ? "{" + obj + "}" : null;
   }

   public abstract static class LazyIntHolder extends IntHolder {
      private volatile boolean isSet;
      private int value;

      public final int getInt() {
         if (this.isSet) {
            return this.value;
         } else {
            synchronized(this) {
               if (!this.isSet) {
                  this.value = this.evaluate();
                  this.isSet = true;
               }
            }

            return this.value;
         }
      }

      protected abstract int evaluate();
   }

   public abstract static class IntHolder extends Holder {
      public final Integer get() {
         return this.getInt();
      }

      public abstract int getInt();
   }

   public abstract static class LazyHolder extends Holder {
      private volatile boolean isSet;
      private Object value;

      public final Object get() {
         if (this.isSet) {
            return this.value;
         } else {
            synchronized(this) {
               if (!this.isSet) {
                  this.value = this.evaluate();
                  this.isSet = true;
               }
            }

            return this.value;
         }
      }

      protected abstract Object evaluate();
   }
}
