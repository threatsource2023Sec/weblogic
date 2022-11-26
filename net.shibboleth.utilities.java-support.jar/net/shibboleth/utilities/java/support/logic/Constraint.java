package net.shibboleth.utilities.java.support.logic;

import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Constraint {
   private Constraint() {
   }

   @Nonnull
   public static Collection isEmpty(@Nonnull Collection collection, @Nonnull String message) {
      if (collection != null && !collection.isEmpty()) {
         throw new ConstraintViolationException(message);
      } else {
         return collection;
      }
   }

   public static boolean isFalse(boolean b, @Nonnull String message) {
      if (b) {
         throw new ConstraintViolationException(message);
      } else {
         return b;
      }
   }

   public static long isGreaterThan(long threshold, long number, @Nonnull String message) {
      if (number <= threshold) {
         throw new ConstraintViolationException(message);
      } else {
         return number;
      }
   }

   public static long isGreaterThanOrEqual(long threshold, long number, @Nonnull String message) {
      if (number < threshold) {
         throw new ConstraintViolationException(message);
      } else {
         return number;
      }
   }

   public static long isLessThan(long threshold, long number, @Nonnull String message) {
      if (number >= threshold) {
         throw new ConstraintViolationException(message);
      } else {
         return number;
      }
   }

   public static long isLessThanOrEqual(long threshold, long number, @Nonnull String message) {
      if (number > threshold) {
         throw new ConstraintViolationException(message);
      } else {
         return number;
      }
   }

   @Nonnull
   public static Collection isNotEmpty(@Nonnull Collection collection, @Nonnull String message) {
      if (collection != null && !collection.isEmpty()) {
         return collection;
      } else {
         throw new ConstraintViolationException(message);
      }
   }

   @Nonnull
   public static Object[] isNotEmpty(@Nonnull Object[] array, @Nonnull String message) {
      if (array != null && array.length != 0) {
         return array;
      } else {
         throw new ConstraintViolationException(message);
      }
   }

   @Nonnull
   public static byte[] isNotEmpty(@Nonnull byte[] array, @Nonnull String message) {
      if (array != null && array.length != 0) {
         return array;
      } else {
         throw new ConstraintViolationException(message);
      }
   }

   @Nonnull
   public static String isNotEmpty(@Nonnull String string, @Nonnull String message) {
      if (string != null && string.length() != 0) {
         return string;
      } else {
         throw new ConstraintViolationException(message);
      }
   }

   @Nonnull
   public static Object isNotNull(@Nullable Object obj, @Nonnull String message) {
      if (obj == null) {
         throw new ConstraintViolationException(message);
      } else {
         return obj;
      }
   }

   @Nullable
   public static Object isNull(@Nullable Object obj, @Nonnull String message) {
      if (obj != null) {
         throw new ConstraintViolationException(message);
      } else {
         return obj;
      }
   }

   public static boolean isTrue(boolean b, @Nonnull String message) {
      if (!b) {
         throw new ConstraintViolationException(message);
      } else {
         return b;
      }
   }

   @Nonnull
   public static Object[] noNullItems(@Nonnull Object[] array, @Nonnull String message) {
      if (array == null) {
         throw new ConstraintViolationException(message);
      } else {
         Object[] arr$ = array;
         int len$ = array.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Object element = arr$[i$];
            if (element == null) {
               throw new ConstraintViolationException(message);
            }
         }

         return array;
      }
   }

   @Nonnull
   public static Collection noNullItems(@Nonnull Collection collection, @Nonnull String message) {
      if (collection == null) {
         throw new ConstraintViolationException(message);
      } else {
         Iterator i$ = collection.iterator();

         Object element;
         do {
            if (!i$.hasNext()) {
               return collection;
            }

            element = i$.next();
         } while(element != null);

         throw new ConstraintViolationException(message);
      }
   }

   public static long numberInRangeExclusive(long lowerTheshold, long upperThreshold, long number, @Nonnull String message) {
      if (number > lowerTheshold && number < upperThreshold) {
         return number;
      } else {
         throw new ConstraintViolationException(message);
      }
   }

   public static long numberInRangeInclusive(long lowerTheshold, long upperThreshold, long number, @Nonnull String message) {
      if (number >= lowerTheshold && number <= upperThreshold) {
         return number;
      } else {
         throw new ConstraintViolationException(message);
      }
   }
}
