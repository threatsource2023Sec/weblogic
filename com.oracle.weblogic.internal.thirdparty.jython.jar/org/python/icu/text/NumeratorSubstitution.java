package org.python.icu.text;

import java.text.ParsePosition;

class NumeratorSubstitution extends NFSubstitution {
   private final double denominator;
   private final boolean withZeros;

   NumeratorSubstitution(int pos, double denominator, NFRuleSet ruleSet, String description) {
      super(pos, ruleSet, fixdesc(description));
      this.denominator = denominator;
      this.withZeros = description.endsWith("<<");
   }

   static String fixdesc(String description) {
      return description.endsWith("<<") ? description.substring(0, description.length() - 1) : description;
   }

   public boolean equals(Object that) {
      if (!super.equals(that)) {
         return false;
      } else {
         NumeratorSubstitution that2 = (NumeratorSubstitution)that;
         return this.denominator == that2.denominator && this.withZeros == that2.withZeros;
      }
   }

   public void doSubstitution(double number, StringBuilder toInsertInto, int position, int recursionCount) {
      double numberToFormat = this.transformNumber(number);
      if (this.withZeros && this.ruleSet != null) {
         long nf = (long)numberToFormat;
         int len = toInsertInto.length();

         while((double)(nf *= 10L) < this.denominator) {
            toInsertInto.insert(position + this.pos, ' ');
            this.ruleSet.format(0L, toInsertInto, position + this.pos, recursionCount);
         }

         position += toInsertInto.length() - len;
      }

      if (numberToFormat == Math.floor(numberToFormat) && this.ruleSet != null) {
         this.ruleSet.format((long)numberToFormat, toInsertInto, position + this.pos, recursionCount);
      } else if (this.ruleSet != null) {
         this.ruleSet.format(numberToFormat, toInsertInto, position + this.pos, recursionCount);
      } else {
         toInsertInto.insert(position + this.pos, this.numberFormat.format(numberToFormat));
      }

   }

   public long transformNumber(long number) {
      return Math.round((double)number * this.denominator);
   }

   public double transformNumber(double number) {
      return (double)Math.round(number * this.denominator);
   }

   public Number doParse(String text, ParsePosition parsePosition, double baseValue, double upperBound, boolean lenientParse) {
      int zeroCount = 0;
      if (this.withZeros) {
         String workText = text;
         ParsePosition workPos = new ParsePosition(1);

         while(workText.length() > 0 && workPos.getIndex() != 0) {
            workPos.setIndex(0);
            this.ruleSet.parse(workText, workPos, 1.0).intValue();
            if (workPos.getIndex() == 0) {
               break;
            }

            ++zeroCount;
            parsePosition.setIndex(parsePosition.getIndex() + workPos.getIndex());
            workText = workText.substring(workPos.getIndex());

            while(workText.length() > 0 && workText.charAt(0) == ' ') {
               workText = workText.substring(1);
               parsePosition.setIndex(parsePosition.getIndex() + 1);
            }
         }

         text = text.substring(parsePosition.getIndex());
         parsePosition.setIndex(0);
      }

      Number result = super.doParse(text, parsePosition, this.withZeros ? 1.0 : baseValue, upperBound, false);
      if (this.withZeros) {
         long n = ((Number)result).longValue();

         long d;
         for(d = 1L; d <= n; d *= 10L) {
         }

         while(zeroCount > 0) {
            d *= 10L;
            --zeroCount;
         }

         result = new Double((double)n / (double)d);
      }

      return (Number)result;
   }

   public double composeRuleValue(double newRuleValue, double oldRuleValue) {
      return newRuleValue / oldRuleValue;
   }

   public double calcUpperBound(double oldUpperBound) {
      return this.denominator;
   }

   char tokenChar() {
      return '<';
   }
}
