package weblogic.descriptor.beangen;

import java.util.Arrays;

public class LegalChecks {
   private static final BeangenBeangenTextFormatter textFormatter = BeangenBeangenTextFormatter.getInstance();

   public static String checkInEnum(String name, String value, String[] set) throws IllegalArgumentException {
      if (value == null) {
         return null;
      } else {
         for(int i = 0; i < set.length; ++i) {
            if (value.equalsIgnoreCase(set[i])) {
               return set[i];
            }
         }

         throw new IllegalArgumentException(textFormatter.getCheckInEnumString(value, name, Arrays.asList((Object[])set).toString()));
      }
   }

   public static int checkInEnum(String name, int value, int[] set) throws IllegalArgumentException {
      for(int i = 0; i < set.length; ++i) {
         if (value == set[i]) {
            return set[i];
         }
      }

      throw new IllegalArgumentException(textFormatter.getCheckInEnumString(Integer.toString(value), name, asString(set)));
   }

   private static String asString(int[] array) {
      StringBuffer buf = new StringBuffer("[");

      for(int i = 0; i < array.length; ++i) {
         buf.append(array[i]);
         if (i + 1 < array.length) {
            buf.append(", ");
         }
      }

      buf.append("]");
      return buf.toString();
   }

   public static void checkInRange(String name, long value, long min, long max) throws IllegalArgumentException {
      if (value < min || value > max) {
         throw new IllegalArgumentException(textFormatter.getVoidCheckInRangeString(Long.toString(value), name, Long.toString(min), Long.toString(max)));
      }
   }

   public static void checkInRange(String name, double value, double min, double max) throws IllegalArgumentException {
      if (value < min || value > max) {
         throw new IllegalArgumentException(textFormatter.getVoidCheckInRangeString(Double.toString(value), name, Double.toString(min), Double.toString(max)));
      }
   }

   public static void checkMin(String name, int value, int min) throws IllegalArgumentException {
      checkInRange(name, (long)value, (long)min, 2147483647L);
   }

   public static void checkMin(String name, long value, long min) throws IllegalArgumentException {
      checkInRange(name, value, min, Long.MAX_VALUE);
   }

   public static void checkMin(String name, double value, double min) throws IllegalArgumentException {
      checkInRange(name, value, min, Double.MAX_VALUE);
   }

   public static void checkMax(String name, int value, int max) throws IllegalArgumentException {
      checkInRange(name, (long)value, -2147483648L, (long)max);
   }

   public static void checkMax(String name, long value, long max) throws IllegalArgumentException {
      checkInRange(name, value, Long.MIN_VALUE, max);
   }

   public static void checkNonNull(String name, Object value) throws IllegalArgumentException {
      if (value == null) {
         throw new IllegalArgumentException(textFormatter.getVoidCheckNonNullString(name));
      }
   }

   public static void checkNonEmptyString(String name, String value) throws IllegalArgumentException {
      if (value != null && value.length() == 0) {
         throw new IllegalArgumentException(textFormatter.getVoidCheckNonEmptyStringString(name));
      }
   }

   public static void checkIsSet(String name, boolean isSet) throws IllegalArgumentException {
      if (!isSet) {
         throw new IllegalArgumentException(textFormatter.getVoidCheckIsSetStringString(name));
      }
   }
}
