package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public abstract class Assert {
   public static void state(boolean expression, String message) {
      if (!expression) {
         throw new IllegalStateException(message);
      }
   }

   public static void state(boolean expression, Supplier messageSupplier) {
      if (!expression) {
         throw new IllegalStateException(nullSafeGet(messageSupplier));
      }
   }

   /** @deprecated */
   @Deprecated
   public static void state(boolean expression) {
      state(expression, "[Assertion failed] - this state invariant must be true");
   }

   public static void isTrue(boolean expression, String message) {
      if (!expression) {
         throw new IllegalArgumentException(message);
      }
   }

   public static void isTrue(boolean expression, Supplier messageSupplier) {
      if (!expression) {
         throw new IllegalArgumentException(nullSafeGet(messageSupplier));
      }
   }

   /** @deprecated */
   @Deprecated
   public static void isTrue(boolean expression) {
      isTrue(expression, "[Assertion failed] - this expression must be true");
   }

   public static void isNull(@Nullable Object object, String message) {
      if (object != null) {
         throw new IllegalArgumentException(message);
      }
   }

   public static void isNull(@Nullable Object object, Supplier messageSupplier) {
      if (object != null) {
         throw new IllegalArgumentException(nullSafeGet(messageSupplier));
      }
   }

   /** @deprecated */
   @Deprecated
   public static void isNull(@Nullable Object object) {
      isNull(object, "[Assertion failed] - the object argument must be null");
   }

   public static void notNull(@Nullable Object object, String message) {
      if (object == null) {
         throw new IllegalArgumentException(message);
      }
   }

   public static void notNull(@Nullable Object object, Supplier messageSupplier) {
      if (object == null) {
         throw new IllegalArgumentException(nullSafeGet(messageSupplier));
      }
   }

   /** @deprecated */
   @Deprecated
   public static void notNull(@Nullable Object object) {
      notNull(object, "[Assertion failed] - this argument is required; it must not be null");
   }

   public static void hasLength(@Nullable String text, String message) {
      if (!StringUtils.hasLength(text)) {
         throw new IllegalArgumentException(message);
      }
   }

   public static void hasLength(@Nullable String text, Supplier messageSupplier) {
      if (!StringUtils.hasLength(text)) {
         throw new IllegalArgumentException(nullSafeGet(messageSupplier));
      }
   }

   /** @deprecated */
   @Deprecated
   public static void hasLength(@Nullable String text) {
      hasLength(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
   }

   public static void hasText(@Nullable String text, String message) {
      if (!StringUtils.hasText(text)) {
         throw new IllegalArgumentException(message);
      }
   }

   public static void hasText(@Nullable String text, Supplier messageSupplier) {
      if (!StringUtils.hasText(text)) {
         throw new IllegalArgumentException(nullSafeGet(messageSupplier));
      }
   }

   /** @deprecated */
   @Deprecated
   public static void hasText(@Nullable String text) {
      hasText(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
   }

   public static void doesNotContain(@Nullable String textToSearch, String substring, String message) {
      if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
         throw new IllegalArgumentException(message);
      }
   }

   public static void doesNotContain(@Nullable String textToSearch, String substring, Supplier messageSupplier) {
      if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
         throw new IllegalArgumentException(nullSafeGet(messageSupplier));
      }
   }

   /** @deprecated */
   @Deprecated
   public static void doesNotContain(@Nullable String textToSearch, String substring) {
      doesNotContain(textToSearch, substring, () -> {
         return "[Assertion failed] - this String argument must not contain the substring [" + substring + "]";
      });
   }

   public static void notEmpty(@Nullable Object[] array, String message) {
      if (ObjectUtils.isEmpty(array)) {
         throw new IllegalArgumentException(message);
      }
   }

   public static void notEmpty(@Nullable Object[] array, Supplier messageSupplier) {
      if (ObjectUtils.isEmpty(array)) {
         throw new IllegalArgumentException(nullSafeGet(messageSupplier));
      }
   }

   /** @deprecated */
   @Deprecated
   public static void notEmpty(@Nullable Object[] array) {
      notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
   }

   public static void noNullElements(@Nullable Object[] array, String message) {
      if (array != null) {
         Object[] var2 = array;
         int var3 = array.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object element = var2[var4];
            if (element == null) {
               throw new IllegalArgumentException(message);
            }
         }
      }

   }

   public static void noNullElements(@Nullable Object[] array, Supplier messageSupplier) {
      if (array != null) {
         Object[] var2 = array;
         int var3 = array.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object element = var2[var4];
            if (element == null) {
               throw new IllegalArgumentException(nullSafeGet(messageSupplier));
            }
         }
      }

   }

   /** @deprecated */
   @Deprecated
   public static void noNullElements(@Nullable Object[] array) {
      noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
   }

   public static void notEmpty(@Nullable Collection collection, String message) {
      if (CollectionUtils.isEmpty(collection)) {
         throw new IllegalArgumentException(message);
      }
   }

   public static void notEmpty(@Nullable Collection collection, Supplier messageSupplier) {
      if (CollectionUtils.isEmpty(collection)) {
         throw new IllegalArgumentException(nullSafeGet(messageSupplier));
      }
   }

   /** @deprecated */
   @Deprecated
   public static void notEmpty(@Nullable Collection collection) {
      notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
   }

   public static void notEmpty(@Nullable Map map, String message) {
      if (CollectionUtils.isEmpty(map)) {
         throw new IllegalArgumentException(message);
      }
   }

   public static void notEmpty(@Nullable Map map, Supplier messageSupplier) {
      if (CollectionUtils.isEmpty(map)) {
         throw new IllegalArgumentException(nullSafeGet(messageSupplier));
      }
   }

   /** @deprecated */
   @Deprecated
   public static void notEmpty(@Nullable Map map) {
      notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
   }

   public static void isInstanceOf(Class type, @Nullable Object obj, String message) {
      notNull(type, (String)"Type to check against must not be null");
      if (!type.isInstance(obj)) {
         instanceCheckFailed(type, obj, message);
      }

   }

   public static void isInstanceOf(Class type, @Nullable Object obj, Supplier messageSupplier) {
      notNull(type, (String)"Type to check against must not be null");
      if (!type.isInstance(obj)) {
         instanceCheckFailed(type, obj, nullSafeGet(messageSupplier));
      }

   }

   public static void isInstanceOf(Class type, @Nullable Object obj) {
      isInstanceOf(type, obj, "");
   }

   public static void isAssignable(Class superType, @Nullable Class subType, String message) {
      notNull(superType, (String)"Super type to check against must not be null");
      if (subType == null || !superType.isAssignableFrom(subType)) {
         assignableCheckFailed(superType, subType, message);
      }

   }

   public static void isAssignable(Class superType, @Nullable Class subType, Supplier messageSupplier) {
      notNull(superType, (String)"Super type to check against must not be null");
      if (subType == null || !superType.isAssignableFrom(subType)) {
         assignableCheckFailed(superType, subType, nullSafeGet(messageSupplier));
      }

   }

   public static void isAssignable(Class superType, Class subType) {
      isAssignable(superType, subType, "");
   }

   private static void instanceCheckFailed(Class type, @Nullable Object obj, @Nullable String msg) {
      String className = obj != null ? obj.getClass().getName() : "null";
      String result = "";
      boolean defaultMessage = true;
      if (StringUtils.hasLength(msg)) {
         if (endsWithSeparator(msg)) {
            result = msg + " ";
         } else {
            result = messageWithTypeName(msg, className);
            defaultMessage = false;
         }
      }

      if (defaultMessage) {
         result = result + "Object of class [" + className + "] must be an instance of " + type;
      }

      throw new IllegalArgumentException(result);
   }

   private static void assignableCheckFailed(Class superType, @Nullable Class subType, @Nullable String msg) {
      String result = "";
      boolean defaultMessage = true;
      if (StringUtils.hasLength(msg)) {
         if (endsWithSeparator(msg)) {
            result = msg + " ";
         } else {
            result = messageWithTypeName(msg, subType);
            defaultMessage = false;
         }
      }

      if (defaultMessage) {
         result = result + subType + " is not assignable to " + superType;
      }

      throw new IllegalArgumentException(result);
   }

   private static boolean endsWithSeparator(String msg) {
      return msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith(".");
   }

   private static String messageWithTypeName(String msg, @Nullable Object typeName) {
      return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
   }

   @Nullable
   private static String nullSafeGet(@Nullable Supplier messageSupplier) {
      return messageSupplier != null ? (String)messageSupplier.get() : null;
   }
}
