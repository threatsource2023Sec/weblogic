package org.python.icu.text;

import java.text.ParsePosition;

abstract class NFSubstitution {
   final int pos;
   final NFRuleSet ruleSet;
   final DecimalFormat numberFormat;
   private static final long MAX_INT64_IN_DOUBLE = 9007199254740991L;

   public static NFSubstitution makeSubstitution(int pos, NFRule rule, NFRule rulePredecessor, NFRuleSet ruleSet, RuleBasedNumberFormat formatter, String description) {
      if (description.length() == 0) {
         return null;
      } else {
         switch (description.charAt(0)) {
            case '<':
               if (rule.getBaseValue() == -1L) {
                  throw new IllegalArgumentException("<< not allowed in negative-number rule");
               } else {
                  if (rule.getBaseValue() != -2L && rule.getBaseValue() != -3L && rule.getBaseValue() != -4L) {
                     if (ruleSet.isFractionSet()) {
                        return new NumeratorSubstitution(pos, (double)rule.getBaseValue(), formatter.getDefaultRuleSet(), description);
                     }

                     return new MultiplierSubstitution(pos, rule, ruleSet, description);
                  }

                  return new IntegralPartSubstitution(pos, ruleSet, description);
               }
            case '=':
               return new SameValueSubstitution(pos, ruleSet, description);
            case '>':
               if (rule.getBaseValue() == -1L) {
                  return new AbsoluteValueSubstitution(pos, ruleSet, description);
               } else {
                  if (rule.getBaseValue() != -2L && rule.getBaseValue() != -3L && rule.getBaseValue() != -4L) {
                     if (ruleSet.isFractionSet()) {
                        throw new IllegalArgumentException(">> not allowed in fraction rule set");
                     }

                     return new ModulusSubstitution(pos, rule, rulePredecessor, ruleSet, description);
                  }

                  return new FractionalPartSubstitution(pos, ruleSet, description);
               }
            default:
               throw new IllegalArgumentException("Illegal substitution character");
         }
      }
   }

   NFSubstitution(int pos, NFRuleSet ruleSet, String description) {
      this.pos = pos;
      int descriptionLen = description.length();
      if (descriptionLen >= 2 && description.charAt(0) == description.charAt(descriptionLen - 1)) {
         description = description.substring(1, descriptionLen - 1);
      } else if (descriptionLen != 0) {
         throw new IllegalArgumentException("Illegal substitution syntax");
      }

      if (description.length() == 0) {
         this.ruleSet = ruleSet;
         this.numberFormat = null;
      } else if (description.charAt(0) == '%') {
         this.ruleSet = ruleSet.owner.findRuleSet(description);
         this.numberFormat = null;
      } else if (description.charAt(0) != '#' && description.charAt(0) != '0') {
         if (description.charAt(0) != '>') {
            throw new IllegalArgumentException("Illegal substitution syntax");
         }

         this.ruleSet = ruleSet;
         this.numberFormat = null;
      } else {
         this.ruleSet = null;
         this.numberFormat = (DecimalFormat)ruleSet.owner.getDecimalFormat().clone();
         this.numberFormat.applyPattern(description);
      }

   }

   public void setDivisor(int radix, short exponent) {
   }

   public boolean equals(Object that) {
      if (that == null) {
         return false;
      } else if (this == that) {
         return true;
      } else if (this.getClass() != that.getClass()) {
         return false;
      } else {
         boolean var10000;
         label51: {
            NFSubstitution that2 = (NFSubstitution)that;
            if (this.pos == that2.pos && (this.ruleSet != null || that2.ruleSet == null)) {
               if (this.numberFormat == null) {
                  if (that2.numberFormat == null) {
                     break label51;
                  }
               } else if (this.numberFormat.equals(that2.numberFormat)) {
                  break label51;
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }

   public int hashCode() {
      assert false : "hashCode not designed";

      return 42;
   }

   public String toString() {
      return this.ruleSet != null ? this.tokenChar() + this.ruleSet.getName() + this.tokenChar() : this.tokenChar() + this.numberFormat.toPattern() + this.tokenChar();
   }

   public void doSubstitution(long number, StringBuilder toInsertInto, int position, int recursionCount) {
      long numberToFormat;
      if (this.ruleSet != null) {
         numberToFormat = this.transformNumber(number);
         this.ruleSet.format(numberToFormat, toInsertInto, position + this.pos, recursionCount);
      } else if (number <= 9007199254740991L) {
         double numberToFormat = this.transformNumber((double)number);
         if (this.numberFormat.getMaximumFractionDigits() == 0) {
            numberToFormat = Math.floor(numberToFormat);
         }

         toInsertInto.insert(position + this.pos, this.numberFormat.format(numberToFormat));
      } else {
         numberToFormat = this.transformNumber(number);
         toInsertInto.insert(position + this.pos, this.numberFormat.format(numberToFormat));
      }

   }

   public void doSubstitution(double number, StringBuilder toInsertInto, int position, int recursionCount) {
      double numberToFormat = this.transformNumber(number);
      if (Double.isInfinite(numberToFormat)) {
         NFRule infiniteRule = this.ruleSet.findRule(Double.POSITIVE_INFINITY);
         infiniteRule.doFormat(numberToFormat, toInsertInto, position + this.pos, recursionCount);
      } else {
         if (numberToFormat == Math.floor(numberToFormat) && this.ruleSet != null) {
            this.ruleSet.format((long)numberToFormat, toInsertInto, position + this.pos, recursionCount);
         } else if (this.ruleSet != null) {
            this.ruleSet.format(numberToFormat, toInsertInto, position + this.pos, recursionCount);
         } else {
            toInsertInto.insert(position + this.pos, this.numberFormat.format(numberToFormat));
         }

      }
   }

   public abstract long transformNumber(long var1);

   public abstract double transformNumber(double var1);

   public Number doParse(String text, ParsePosition parsePosition, double baseValue, double upperBound, boolean lenientParse) {
      upperBound = this.calcUpperBound(upperBound);
      Number tempResult;
      if (this.ruleSet != null) {
         tempResult = this.ruleSet.parse(text, parsePosition, upperBound);
         if (lenientParse && !this.ruleSet.isFractionSet() && parsePosition.getIndex() == 0) {
            tempResult = this.ruleSet.owner.getDecimalFormat().parse(text, parsePosition);
         }
      } else {
         tempResult = this.numberFormat.parse(text, parsePosition);
      }

      if (parsePosition.getIndex() != 0) {
         double result = tempResult.doubleValue();
         result = this.composeRuleValue(result, baseValue);
         return (Number)(result == (double)((long)result) ? (long)result : new Double(result));
      } else {
         return tempResult;
      }
   }

   public abstract double composeRuleValue(double var1, double var3);

   public abstract double calcUpperBound(double var1);

   public final int getPos() {
      return this.pos;
   }

   abstract char tokenChar();

   public boolean isModulusSubstitution() {
      return false;
   }

   public void setDecimalFormatSymbols(DecimalFormatSymbols newSymbols) {
      if (this.numberFormat != null) {
         this.numberFormat.setDecimalFormatSymbols(newSymbols);
      }

   }
}
