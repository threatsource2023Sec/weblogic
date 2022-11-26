package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class NumberUtils {
   private static final BigInteger LONG_MIN = BigInteger.valueOf(Long.MIN_VALUE);
   private static final BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);
   public static final Set STANDARD_NUMBER_TYPES;

   public static Number convertNumberToTargetClass(Number number, Class targetClass) throws IllegalArgumentException {
      Assert.notNull(number, (String)"Number must not be null");
      Assert.notNull(targetClass, (String)"Target class must not be null");
      if (targetClass.isInstance(number)) {
         return number;
      } else {
         long value;
         if (Byte.class == targetClass) {
            value = checkedLongValue(number, targetClass);
            if (value < -128L || value > 127L) {
               raiseOverflowException(number, targetClass);
            }

            return number.byteValue();
         } else if (Short.class == targetClass) {
            value = checkedLongValue(number, targetClass);
            if (value < -32768L || value > 32767L) {
               raiseOverflowException(number, targetClass);
            }

            return number.shortValue();
         } else if (Integer.class != targetClass) {
            if (Long.class == targetClass) {
               value = checkedLongValue(number, targetClass);
               return value;
            } else if (BigInteger.class == targetClass) {
               return number instanceof BigDecimal ? ((BigDecimal)number).toBigInteger() : BigInteger.valueOf(number.longValue());
            } else if (Float.class == targetClass) {
               return number.floatValue();
            } else if (Double.class == targetClass) {
               return number.doubleValue();
            } else if (BigDecimal.class == targetClass) {
               return new BigDecimal(number.toString());
            } else {
               throw new IllegalArgumentException("Could not convert number [" + number + "] of type [" + number.getClass().getName() + "] to unsupported target class [" + targetClass.getName() + "]");
            }
         } else {
            value = checkedLongValue(number, targetClass);
            if (value < -2147483648L || value > 2147483647L) {
               raiseOverflowException(number, targetClass);
            }

            return number.intValue();
         }
      }
   }

   private static long checkedLongValue(Number number, Class targetClass) {
      BigInteger bigInt = null;
      if (number instanceof BigInteger) {
         bigInt = (BigInteger)number;
      } else if (number instanceof BigDecimal) {
         bigInt = ((BigDecimal)number).toBigInteger();
      }

      if (bigInt != null && (bigInt.compareTo(LONG_MIN) < 0 || bigInt.compareTo(LONG_MAX) > 0)) {
         raiseOverflowException(number, targetClass);
      }

      return number.longValue();
   }

   private static void raiseOverflowException(Number number, Class targetClass) {
      throw new IllegalArgumentException("Could not convert number [" + number + "] of type [" + number.getClass().getName() + "] to target class [" + targetClass.getName() + "]: overflow");
   }

   public static Number parseNumber(String text, Class targetClass) {
      Assert.notNull(text, (String)"Text must not be null");
      Assert.notNull(targetClass, (String)"Target class must not be null");
      String trimmed = StringUtils.trimAllWhitespace(text);
      if (Byte.class == targetClass) {
         return isHexNumber(trimmed) ? Byte.decode(trimmed) : Byte.valueOf(trimmed);
      } else if (Short.class == targetClass) {
         return isHexNumber(trimmed) ? Short.decode(trimmed) : Short.valueOf(trimmed);
      } else if (Integer.class == targetClass) {
         return isHexNumber(trimmed) ? Integer.decode(trimmed) : Integer.valueOf(trimmed);
      } else if (Long.class == targetClass) {
         return isHexNumber(trimmed) ? Long.decode(trimmed) : Long.valueOf(trimmed);
      } else if (BigInteger.class == targetClass) {
         return isHexNumber(trimmed) ? decodeBigInteger(trimmed) : new BigInteger(trimmed);
      } else if (Float.class == targetClass) {
         return Float.valueOf(trimmed);
      } else if (Double.class == targetClass) {
         return Double.valueOf(trimmed);
      } else if (BigDecimal.class != targetClass && Number.class != targetClass) {
         throw new IllegalArgumentException("Cannot convert String [" + text + "] to target class [" + targetClass.getName() + "]");
      } else {
         return new BigDecimal(trimmed);
      }
   }

   public static Number parseNumber(String text, Class targetClass, @Nullable NumberFormat numberFormat) {
      if (numberFormat == null) {
         return parseNumber(text, targetClass);
      } else {
         Assert.notNull(text, (String)"Text must not be null");
         Assert.notNull(targetClass, (String)"Target class must not be null");
         DecimalFormat decimalFormat = null;
         boolean resetBigDecimal = false;
         if (numberFormat instanceof DecimalFormat) {
            decimalFormat = (DecimalFormat)numberFormat;
            if (BigDecimal.class == targetClass && !decimalFormat.isParseBigDecimal()) {
               decimalFormat.setParseBigDecimal(true);
               resetBigDecimal = true;
            }
         }

         Number var6;
         try {
            Number number = numberFormat.parse(StringUtils.trimAllWhitespace(text));
            var6 = convertNumberToTargetClass(number, targetClass);
         } catch (ParseException var10) {
            throw new IllegalArgumentException("Could not parse number: " + var10.getMessage());
         } finally {
            if (resetBigDecimal) {
               decimalFormat.setParseBigDecimal(false);
            }

         }

         return var6;
      }
   }

   private static boolean isHexNumber(String value) {
      int index = value.startsWith("-") ? 1 : 0;
      return value.startsWith("0x", index) || value.startsWith("0X", index) || value.startsWith("#", index);
   }

   private static BigInteger decodeBigInteger(String value) {
      int radix = 10;
      int index = 0;
      boolean negative = false;
      if (value.startsWith("-")) {
         negative = true;
         ++index;
      }

      if (!value.startsWith("0x", index) && !value.startsWith("0X", index)) {
         if (value.startsWith("#", index)) {
            ++index;
            radix = 16;
         } else if (value.startsWith("0", index) && value.length() > 1 + index) {
            ++index;
            radix = 8;
         }
      } else {
         index += 2;
         radix = 16;
      }

      BigInteger result = new BigInteger(value.substring(index), radix);
      return negative ? result.negate() : result;
   }

   static {
      Set numberTypes = new HashSet(8);
      numberTypes.add(Byte.class);
      numberTypes.add(Short.class);
      numberTypes.add(Integer.class);
      numberTypes.add(Long.class);
      numberTypes.add(BigInteger.class);
      numberTypes.add(Float.class);
      numberTypes.add(Double.class);
      numberTypes.add(BigDecimal.class);
      STANDARD_NUMBER_TYPES = Collections.unmodifiableSet(numberTypes);
   }
}
