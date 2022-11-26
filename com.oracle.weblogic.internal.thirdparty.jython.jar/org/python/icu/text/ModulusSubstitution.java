package org.python.icu.text;

import java.text.ParsePosition;

class ModulusSubstitution extends NFSubstitution {
   long divisor;
   private final NFRule ruleToUse;

   ModulusSubstitution(int pos, NFRule rule, NFRule rulePredecessor, NFRuleSet ruleSet, String description) {
      super(pos, ruleSet, description);
      this.divisor = rule.getDivisor();
      if (this.divisor == 0L) {
         throw new IllegalStateException("Substitution with bad divisor (" + this.divisor + ") " + description.substring(0, pos) + " | " + description.substring(pos));
      } else {
         if (description.equals(">>>")) {
            this.ruleToUse = rulePredecessor;
         } else {
            this.ruleToUse = null;
         }

      }
   }

   public void setDivisor(int radix, short exponent) {
      this.divisor = NFRule.power((long)radix, exponent);
      if (this.divisor == 0L) {
         throw new IllegalStateException("Substitution with bad divisor");
      }
   }

   public boolean equals(Object that) {
      if (super.equals(that)) {
         ModulusSubstitution that2 = (ModulusSubstitution)that;
         return this.divisor == that2.divisor;
      } else {
         return false;
      }
   }

   public void doSubstitution(long number, StringBuilder toInsertInto, int position, int recursionCount) {
      if (this.ruleToUse == null) {
         super.doSubstitution(number, toInsertInto, position, recursionCount);
      } else {
         long numberToFormat = this.transformNumber(number);
         this.ruleToUse.doFormat(numberToFormat, toInsertInto, position + this.pos, recursionCount);
      }

   }

   public void doSubstitution(double number, StringBuilder toInsertInto, int position, int recursionCount) {
      if (this.ruleToUse == null) {
         super.doSubstitution(number, toInsertInto, position, recursionCount);
      } else {
         double numberToFormat = this.transformNumber(number);
         this.ruleToUse.doFormat(numberToFormat, toInsertInto, position + this.pos, recursionCount);
      }

   }

   public long transformNumber(long number) {
      return number % this.divisor;
   }

   public double transformNumber(double number) {
      return Math.floor(number % (double)this.divisor);
   }

   public Number doParse(String text, ParsePosition parsePosition, double baseValue, double upperBound, boolean lenientParse) {
      if (this.ruleToUse == null) {
         return super.doParse(text, parsePosition, baseValue, upperBound, lenientParse);
      } else {
         Number tempResult = this.ruleToUse.doParse(text, parsePosition, false, upperBound);
         if (parsePosition.getIndex() != 0) {
            double result = tempResult.doubleValue();
            result = this.composeRuleValue(result, baseValue);
            return (Number)(result == (double)((long)result) ? (long)result : new Double(result));
         } else {
            return tempResult;
         }
      }
   }

   public double composeRuleValue(double newRuleValue, double oldRuleValue) {
      return oldRuleValue - oldRuleValue % (double)this.divisor + newRuleValue;
   }

   public double calcUpperBound(double oldUpperBound) {
      return (double)this.divisor;
   }

   public boolean isModulusSubstitution() {
      return true;
   }

   char tokenChar() {
      return '>';
   }
}
