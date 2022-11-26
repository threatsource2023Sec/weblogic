package org.python.google.common.base;

import java.util.Arrays;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public final class MoreObjects {
   public static Object firstNonNull(@Nullable Object first, @Nullable Object second) {
      return first != null ? first : Preconditions.checkNotNull(second);
   }

   public static ToStringHelper toStringHelper(Object self) {
      return new ToStringHelper(self.getClass().getSimpleName());
   }

   public static ToStringHelper toStringHelper(Class clazz) {
      return new ToStringHelper(clazz.getSimpleName());
   }

   public static ToStringHelper toStringHelper(String className) {
      return new ToStringHelper(className);
   }

   private MoreObjects() {
   }

   public static final class ToStringHelper {
      private final String className;
      private final ValueHolder holderHead;
      private ValueHolder holderTail;
      private boolean omitNullValues;

      private ToStringHelper(String className) {
         this.holderHead = new ValueHolder();
         this.holderTail = this.holderHead;
         this.omitNullValues = false;
         this.className = (String)Preconditions.checkNotNull(className);
      }

      @CanIgnoreReturnValue
      public ToStringHelper omitNullValues() {
         this.omitNullValues = true;
         return this;
      }

      @CanIgnoreReturnValue
      public ToStringHelper add(String name, @Nullable Object value) {
         return this.addHolder(name, value);
      }

      @CanIgnoreReturnValue
      public ToStringHelper add(String name, boolean value) {
         return this.addHolder(name, String.valueOf(value));
      }

      @CanIgnoreReturnValue
      public ToStringHelper add(String name, char value) {
         return this.addHolder(name, String.valueOf(value));
      }

      @CanIgnoreReturnValue
      public ToStringHelper add(String name, double value) {
         return this.addHolder(name, String.valueOf(value));
      }

      @CanIgnoreReturnValue
      public ToStringHelper add(String name, float value) {
         return this.addHolder(name, String.valueOf(value));
      }

      @CanIgnoreReturnValue
      public ToStringHelper add(String name, int value) {
         return this.addHolder(name, String.valueOf(value));
      }

      @CanIgnoreReturnValue
      public ToStringHelper add(String name, long value) {
         return this.addHolder(name, String.valueOf(value));
      }

      @CanIgnoreReturnValue
      public ToStringHelper addValue(@Nullable Object value) {
         return this.addHolder(value);
      }

      @CanIgnoreReturnValue
      public ToStringHelper addValue(boolean value) {
         return this.addHolder(String.valueOf(value));
      }

      @CanIgnoreReturnValue
      public ToStringHelper addValue(char value) {
         return this.addHolder(String.valueOf(value));
      }

      @CanIgnoreReturnValue
      public ToStringHelper addValue(double value) {
         return this.addHolder(String.valueOf(value));
      }

      @CanIgnoreReturnValue
      public ToStringHelper addValue(float value) {
         return this.addHolder(String.valueOf(value));
      }

      @CanIgnoreReturnValue
      public ToStringHelper addValue(int value) {
         return this.addHolder(String.valueOf(value));
      }

      @CanIgnoreReturnValue
      public ToStringHelper addValue(long value) {
         return this.addHolder(String.valueOf(value));
      }

      public String toString() {
         boolean omitNullValuesSnapshot = this.omitNullValues;
         String nextSeparator = "";
         StringBuilder builder = (new StringBuilder(32)).append(this.className).append('{');

         for(ValueHolder valueHolder = this.holderHead.next; valueHolder != null; valueHolder = valueHolder.next) {
            Object value = valueHolder.value;
            if (!omitNullValuesSnapshot || value != null) {
               builder.append(nextSeparator);
               nextSeparator = ", ";
               if (valueHolder.name != null) {
                  builder.append(valueHolder.name).append('=');
               }

               if (value != null && value.getClass().isArray()) {
                  Object[] objectArray = new Object[]{value};
                  String arrayString = Arrays.deepToString(objectArray);
                  builder.append(arrayString, 1, arrayString.length() - 1);
               } else {
                  builder.append(value);
               }
            }
         }

         return builder.append('}').toString();
      }

      private ValueHolder addHolder() {
         ValueHolder valueHolder = new ValueHolder();
         this.holderTail = this.holderTail.next = valueHolder;
         return valueHolder;
      }

      private ToStringHelper addHolder(@Nullable Object value) {
         ValueHolder valueHolder = this.addHolder();
         valueHolder.value = value;
         return this;
      }

      private ToStringHelper addHolder(String name, @Nullable Object value) {
         ValueHolder valueHolder = this.addHolder();
         valueHolder.value = value;
         valueHolder.name = (String)Preconditions.checkNotNull(name);
         return this;
      }

      // $FF: synthetic method
      ToStringHelper(String x0, Object x1) {
         this(x0);
      }

      private static final class ValueHolder {
         String name;
         Object value;
         ValueHolder next;

         private ValueHolder() {
         }

         // $FF: synthetic method
         ValueHolder(Object x0) {
            this();
         }
      }
   }
}
