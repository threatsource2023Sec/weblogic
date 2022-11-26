package org.python.icu.text;

import java.text.ParsePosition;
import org.python.icu.impl.number.FormatQuantity4;

class FractionalPartSubstitution extends NFSubstitution {
   private final boolean byDigits;
   private final boolean useSpaces;

   FractionalPartSubstitution(int pos, NFRuleSet ruleSet, String description) {
      super(pos, ruleSet, description);
      if (!description.equals(">>") && !description.equals(">>>") && ruleSet != this.ruleSet) {
         this.byDigits = false;
         this.useSpaces = true;
         this.ruleSet.makeIntoFractionRuleSet();
      } else {
         this.byDigits = true;
         this.useSpaces = !description.equals(">>>");
      }

   }

   public void doSubstitution(double number, StringBuilder toInsertInto, int position, int recursionCount) {
      if (!this.byDigits) {
         super.doSubstitution(number, toInsertInto, position, recursionCount);
      } else {
         FormatQuantity4 fq = new FormatQuantity4(number);
         fq.roundToInfinity();
         boolean pad = false;

         for(int mag = fq.getLowerDisplayMagnitude(); mag < 0; this.ruleSet.format((long)fq.getDigit(mag++), toInsertInto, position + this.pos, recursionCount)) {
            if (pad && this.useSpaces) {
               toInsertInto.insert(position + this.pos, ' ');
            } else {
               pad = true;
            }
         }
      }

   }

   public long transformNumber(long number) {
      return 0L;
   }

   public double transformNumber(double number) {
      return number - Math.floor(number);
   }

   public Number doParse(String text, ParsePosition parsePosition, double baseValue, double upperBound, boolean lenientParse) {
      if (!this.byDigits) {
         return super.doParse(text, parsePosition, baseValue, 0.0, lenientParse);
      } else {
         String workText = text;
         ParsePosition workPos = new ParsePosition(1);
         FormatQuantity4 fq = new FormatQuantity4();
         int leadingZeros = 0;

         while(workText.length() > 0 && workPos.getIndex() != 0) {
            workPos.setIndex(0);
            int digit = this.ruleSet.parse(workText, workPos, 10.0).intValue();
            if (lenientParse && workPos.getIndex() == 0) {
               Number n = this.ruleSet.owner.getDecimalFormat().parse(workText, workPos);
               if (n != null) {
                  digit = n.intValue();
               }
            }

            if (workPos.getIndex() != 0) {
               if (digit == 0) {
                  ++leadingZeros;
               } else {
                  fq.appendDigit((byte)digit, leadingZeros, false);
                  leadingZeros = 0;
               }

               parsePosition.setIndex(parsePosition.getIndex() + workPos.getIndex());
               workText = workText.substring(workPos.getIndex());

               while(workText.length() > 0 && workText.charAt(0) == ' ') {
                  workText = workText.substring(1);
                  parsePosition.setIndex(parsePosition.getIndex() + 1);
               }
            }
         }

         double result = fq.toDouble();
         result = this.composeRuleValue(result, baseValue);
         return new Double(result);
      }
   }

   public double composeRuleValue(double newRuleValue, double oldRuleValue) {
      return newRuleValue + oldRuleValue;
   }

   public double calcUpperBound(double oldUpperBound) {
      return 0.0;
   }

   char tokenChar() {
      return '>';
   }
}
