package com.bea.core.repackaged.springframework.expression.spel.support;

import com.bea.core.repackaged.springframework.expression.TypeComparator;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.NumberUtils;
import java.math.BigDecimal;
import java.math.BigInteger;

public class StandardTypeComparator implements TypeComparator {
   public boolean canCompare(@Nullable Object left, @Nullable Object right) {
      if (left != null && right != null) {
         if (left instanceof Number && right instanceof Number) {
            return true;
         } else {
            return left instanceof Comparable;
         }
      } else {
         return true;
      }
   }

   public int compare(@Nullable Object left, @Nullable Object right) throws SpelEvaluationException {
      if (left == null) {
         return right == null ? 0 : -1;
      } else if (right == null) {
         return 1;
      } else if (left instanceof Number && right instanceof Number) {
         Number leftNumber = (Number)left;
         Number rightNumber = (Number)right;
         if (!(leftNumber instanceof BigDecimal) && !(rightNumber instanceof BigDecimal)) {
            if (!(leftNumber instanceof Double) && !(rightNumber instanceof Double)) {
               if (!(leftNumber instanceof Float) && !(rightNumber instanceof Float)) {
                  if (!(leftNumber instanceof BigInteger) && !(rightNumber instanceof BigInteger)) {
                     if (!(leftNumber instanceof Long) && !(rightNumber instanceof Long)) {
                        if (!(leftNumber instanceof Integer) && !(rightNumber instanceof Integer)) {
                           if (!(leftNumber instanceof Short) && !(rightNumber instanceof Short)) {
                              return !(leftNumber instanceof Byte) && !(rightNumber instanceof Byte) ? Double.compare(leftNumber.doubleValue(), rightNumber.doubleValue()) : Byte.compare(leftNumber.byteValue(), rightNumber.byteValue());
                           } else {
                              return Short.compare(leftNumber.shortValue(), rightNumber.shortValue());
                           }
                        } else {
                           return Integer.compare(leftNumber.intValue(), rightNumber.intValue());
                        }
                     } else {
                        return Long.compare(leftNumber.longValue(), rightNumber.longValue());
                     }
                  } else {
                     BigInteger leftBigInteger = (BigInteger)NumberUtils.convertNumberToTargetClass(leftNumber, BigInteger.class);
                     BigInteger rightBigInteger = (BigInteger)NumberUtils.convertNumberToTargetClass(rightNumber, BigInteger.class);
                     return leftBigInteger.compareTo(rightBigInteger);
                  }
               } else {
                  return Float.compare(leftNumber.floatValue(), rightNumber.floatValue());
               }
            } else {
               return Double.compare(leftNumber.doubleValue(), rightNumber.doubleValue());
            }
         } else {
            BigDecimal leftBigDecimal = (BigDecimal)NumberUtils.convertNumberToTargetClass(leftNumber, BigDecimal.class);
            BigDecimal rightBigDecimal = (BigDecimal)NumberUtils.convertNumberToTargetClass(rightNumber, BigDecimal.class);
            return leftBigDecimal.compareTo(rightBigDecimal);
         }
      } else {
         try {
            if (left instanceof Comparable) {
               return ((Comparable)left).compareTo(right);
            }
         } catch (ClassCastException var7) {
            throw new SpelEvaluationException(var7, SpelMessage.NOT_COMPARABLE, new Object[]{left.getClass(), right.getClass()});
         }

         throw new SpelEvaluationException(SpelMessage.NOT_COMPARABLE, new Object[]{left.getClass(), right.getClass()});
      }
   }
}
