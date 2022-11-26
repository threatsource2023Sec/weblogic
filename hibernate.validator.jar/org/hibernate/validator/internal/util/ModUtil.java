package org.hibernate.validator.internal.util;

import java.util.List;

public final class ModUtil {
   private ModUtil() {
   }

   public static int calculateLuhnMod10Check(List digits) {
      int sum = 0;
      boolean even = true;

      for(int index = digits.size() - 1; index >= 0; --index) {
         int digit = (Integer)digits.get(index);
         if (even) {
            digit <<= 1;
         }

         if (digit > 9) {
            digit -= 9;
         }

         sum += digit;
         even = !even;
      }

      return (10 - sum % 10) % 10;
   }

   public static int calculateMod10Check(List digits, int multiplier, int weight) {
      int sum = 0;
      boolean even = true;

      for(int index = digits.size() - 1; index >= 0; --index) {
         int digit = (Integer)digits.get(index);
         if (even) {
            digit *= multiplier;
         } else {
            digit *= weight;
         }

         sum += digit;
         even = !even;
      }

      return (10 - sum % 10) % 10;
   }

   public static int calculateMod11Check(List digits, int threshold) {
      int sum = 0;
      int multiplier = 2;

      for(int index = digits.size() - 1; index >= 0; --index) {
         sum += (Integer)digits.get(index) * multiplier++;
         if (multiplier > threshold) {
            multiplier = 2;
         }
      }

      return 11 - sum % 11;
   }

   public static int calculateMod11Check(List digits) {
      return calculateMod11Check(digits, Integer.MAX_VALUE);
   }

   public static int calculateModXCheckWithWeights(List digits, int moduloParam, int threshold, int... weights) {
      int sum = 0;
      int multiplier = 1;

      for(int index = digits.size() - 1; index >= 0; --index) {
         if (weights.length != 0) {
            multiplier = weights[weights.length - index % weights.length - 1];
         } else {
            ++multiplier;
            if (multiplier > threshold) {
               multiplier = 2;
            }
         }

         sum += (Integer)digits.get(index) * multiplier;
      }

      return moduloParam - sum % moduloParam;
   }
}
