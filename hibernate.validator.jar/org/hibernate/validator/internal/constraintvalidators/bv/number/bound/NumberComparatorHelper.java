package org.hibernate.validator.internal.constraintvalidators.bv.number.bound;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.OptionalInt;
import org.hibernate.validator.internal.constraintvalidators.bv.number.InfinityNumberComparatorHelper;

final class NumberComparatorHelper {
   private NumberComparatorHelper() {
   }

   public static int compare(BigDecimal number, long value) {
      return number.compareTo(BigDecimal.valueOf(value));
   }

   public static int compare(BigInteger number, long value) {
      return number.compareTo(BigInteger.valueOf(value));
   }

   public static int compare(Long number, long value) {
      return number.compareTo(value);
   }

   public static int compare(Number number, long value, OptionalInt treatNanAs) {
      if (number instanceof Double) {
         return compare((Double)number, value, treatNanAs);
      } else if (number instanceof Float) {
         return compare((Float)number, value, treatNanAs);
      } else if (number instanceof BigDecimal) {
         return compare((BigDecimal)number, value);
      } else {
         return number instanceof BigInteger ? compare((BigInteger)number, value) : Long.compare(number.longValue(), value);
      }
   }

   public static int compare(Double number, long value, OptionalInt treatNanAs) {
      OptionalInt infinity = InfinityNumberComparatorHelper.infinityCheck(number, treatNanAs);
      return infinity.isPresent() ? infinity.getAsInt() : Double.compare(number, (double)value);
   }

   public static int compare(Float number, long value, OptionalInt treatNanAs) {
      OptionalInt infinity = InfinityNumberComparatorHelper.infinityCheck(number, treatNanAs);
      return infinity.isPresent() ? infinity.getAsInt() : Float.compare(number, (float)value);
   }
}
