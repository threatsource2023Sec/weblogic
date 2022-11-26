package org.python.icu.text;

import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.python.icu.impl.PatternProps;
import org.python.icu.impl.Utility;

final class NFRuleSet {
   private final String name;
   private NFRule[] rules;
   final NFRule[] nonNumericalRules = new NFRule[6];
   LinkedList fractionRules;
   static final int NEGATIVE_RULE_INDEX = 0;
   static final int IMPROPER_FRACTION_RULE_INDEX = 1;
   static final int PROPER_FRACTION_RULE_INDEX = 2;
   static final int MASTER_RULE_INDEX = 3;
   static final int INFINITY_RULE_INDEX = 4;
   static final int NAN_RULE_INDEX = 5;
   final RuleBasedNumberFormat owner;
   private boolean isFractionRuleSet = false;
   private final boolean isParseable;
   private static final int RECURSION_LIMIT = 64;

   public NFRuleSet(RuleBasedNumberFormat owner, String[] descriptions, int index) throws IllegalArgumentException {
      this.owner = owner;
      String description = descriptions[index];
      if (description.length() == 0) {
         throw new IllegalArgumentException("Empty rule set description");
      } else {
         if (description.charAt(0) == '%') {
            int pos = description.indexOf(58);
            if (pos == -1) {
               throw new IllegalArgumentException("Rule set name doesn't end in colon");
            }

            String name = description.substring(0, pos);
            this.isParseable = !name.endsWith("@noparse");
            if (!this.isParseable) {
               name = name.substring(0, name.length() - 8);
            }

            this.name = name;

            while(pos < description.length()) {
               ++pos;
               if (!PatternProps.isWhiteSpace(description.charAt(pos))) {
                  break;
               }
            }

            description = description.substring(pos);
            descriptions[index] = description;
         } else {
            this.name = "%default";
            this.isParseable = true;
         }

         if (description.length() == 0) {
            throw new IllegalArgumentException("Empty rule set description");
         }
      }
   }

   public void parseRules(String description) {
      List tempRules = new ArrayList();
      NFRule predecessor = null;
      int oldP = 0;
      int descriptionLen = description.length();

      do {
         int p = description.indexOf(59, oldP);
         if (p < 0) {
            p = descriptionLen;
         }

         NFRule.makeRules(description.substring(oldP, p), this, predecessor, this.owner, tempRules);
         if (!tempRules.isEmpty()) {
            predecessor = (NFRule)tempRules.get(tempRules.size() - 1);
         }

         oldP = p + 1;
      } while(oldP < descriptionLen);

      long defaultBaseValue = 0L;
      Iterator var9 = tempRules.iterator();

      while(var9.hasNext()) {
         NFRule rule = (NFRule)var9.next();
         long baseValue = rule.getBaseValue();
         if (baseValue == 0L) {
            rule.setBaseValue(defaultBaseValue);
         } else {
            if (baseValue < defaultBaseValue) {
               throw new IllegalArgumentException("Rules are not in order, base: " + baseValue + " < " + defaultBaseValue);
            }

            defaultBaseValue = baseValue;
         }

         if (!this.isFractionRuleSet) {
            ++defaultBaseValue;
         }
      }

      this.rules = new NFRule[tempRules.size()];
      tempRules.toArray(this.rules);
   }

   void setNonNumericalRule(NFRule rule) {
      long baseValue = rule.getBaseValue();
      if (baseValue == -1L) {
         this.nonNumericalRules[0] = rule;
      } else if (baseValue == -2L) {
         this.setBestFractionRule(1, rule, true);
      } else if (baseValue == -3L) {
         this.setBestFractionRule(2, rule, true);
      } else if (baseValue == -4L) {
         this.setBestFractionRule(3, rule, true);
      } else if (baseValue == -5L) {
         this.nonNumericalRules[4] = rule;
      } else if (baseValue == -6L) {
         this.nonNumericalRules[5] = rule;
      }

   }

   private void setBestFractionRule(int originalIndex, NFRule newRule, boolean rememberRule) {
      if (rememberRule) {
         if (this.fractionRules == null) {
            this.fractionRules = new LinkedList();
         }

         this.fractionRules.add(newRule);
      }

      NFRule bestResult = this.nonNumericalRules[originalIndex];
      if (bestResult == null) {
         this.nonNumericalRules[originalIndex] = newRule;
      } else {
         DecimalFormatSymbols decimalFormatSymbols = this.owner.getDecimalFormatSymbols();
         if (decimalFormatSymbols.getDecimalSeparator() == newRule.getDecimalPoint()) {
            this.nonNumericalRules[originalIndex] = newRule;
         }
      }

   }

   public void makeIntoFractionRuleSet() {
      this.isFractionRuleSet = true;
   }

   public boolean equals(Object that) {
      if (!(that instanceof NFRuleSet)) {
         return false;
      } else {
         NFRuleSet that2 = (NFRuleSet)that;
         if (this.name.equals(that2.name) && this.rules.length == that2.rules.length && this.isFractionRuleSet == that2.isFractionRuleSet) {
            int i;
            for(i = 0; i < this.nonNumericalRules.length; ++i) {
               if (!Utility.objectEquals(this.nonNumericalRules[i], that2.nonNumericalRules[i])) {
                  return false;
               }
            }

            for(i = 0; i < this.rules.length; ++i) {
               if (!this.rules[i].equals(that2.rules[i])) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public int hashCode() {
      assert false : "hashCode not designed";

      return 42;
   }

   public String toString() {
      StringBuilder result = new StringBuilder();
      result.append(this.name).append(":\n");
      NFRule[] var2 = this.rules;
      int var3 = var2.length;

      int var4;
      NFRule rule;
      for(var4 = 0; var4 < var3; ++var4) {
         rule = var2[var4];
         result.append(rule.toString()).append("\n");
      }

      var2 = this.nonNumericalRules;
      var3 = var2.length;

      for(var4 = 0; var4 < var3; ++var4) {
         rule = var2[var4];
         if (rule != null) {
            if (rule.getBaseValue() != -2L && rule.getBaseValue() != -3L && rule.getBaseValue() != -4L) {
               result.append(rule.toString()).append("\n");
            } else {
               Iterator var6 = this.fractionRules.iterator();

               while(var6.hasNext()) {
                  NFRule fractionRule = (NFRule)var6.next();
                  if (fractionRule.getBaseValue() == rule.getBaseValue()) {
                     result.append(fractionRule.toString()).append("\n");
                  }
               }
            }
         }
      }

      return result.toString();
   }

   public boolean isFractionSet() {
      return this.isFractionRuleSet;
   }

   public String getName() {
      return this.name;
   }

   public boolean isPublic() {
      return !this.name.startsWith("%%");
   }

   public boolean isParseable() {
      return this.isParseable;
   }

   public void format(long number, StringBuilder toInsertInto, int pos, int recursionCount) {
      if (recursionCount >= 64) {
         throw new IllegalStateException("Recursion limit exceeded when applying ruleSet " + this.name);
      } else {
         NFRule applicableRule = this.findNormalRule(number);
         ++recursionCount;
         applicableRule.doFormat(number, toInsertInto, pos, recursionCount);
      }
   }

   public void format(double number, StringBuilder toInsertInto, int pos, int recursionCount) {
      if (recursionCount >= 64) {
         throw new IllegalStateException("Recursion limit exceeded when applying ruleSet " + this.name);
      } else {
         NFRule applicableRule = this.findRule(number);
         ++recursionCount;
         applicableRule.doFormat(number, toInsertInto, pos, recursionCount);
      }
   }

   NFRule findRule(double number) {
      if (this.isFractionRuleSet) {
         return this.findFractionRuleSetRule(number);
      } else {
         NFRule rule;
         if (Double.isNaN(number)) {
            rule = this.nonNumericalRules[5];
            if (rule == null) {
               rule = this.owner.getDefaultNaNRule();
            }

            return rule;
         } else {
            if (number < 0.0) {
               if (this.nonNumericalRules[0] != null) {
                  return this.nonNumericalRules[0];
               }

               number = -number;
            }

            if (Double.isInfinite(number)) {
               rule = this.nonNumericalRules[4];
               if (rule == null) {
                  rule = this.owner.getDefaultInfinityRule();
               }

               return rule;
            } else {
               if (number != Math.floor(number)) {
                  if (number < 1.0 && this.nonNumericalRules[2] != null) {
                     return this.nonNumericalRules[2];
                  }

                  if (this.nonNumericalRules[1] != null) {
                     return this.nonNumericalRules[1];
                  }
               }

               return this.nonNumericalRules[3] != null ? this.nonNumericalRules[3] : this.findNormalRule(Math.round(number));
            }
         }
      }
   }

   private NFRule findNormalRule(long number) {
      if (this.isFractionRuleSet) {
         return this.findFractionRuleSetRule((double)number);
      } else {
         if (number < 0L) {
            if (this.nonNumericalRules[0] != null) {
               return this.nonNumericalRules[0];
            }

            number = -number;
         }

         int lo = 0;
         int hi = this.rules.length;
         if (hi > 0) {
            while(lo < hi) {
               int mid = lo + hi >>> 1;
               long ruleBaseValue = this.rules[mid].getBaseValue();
               if (ruleBaseValue == number) {
                  return this.rules[mid];
               }

               if (ruleBaseValue > number) {
                  hi = mid;
               } else {
                  lo = mid + 1;
               }
            }

            if (hi == 0) {
               throw new IllegalStateException("The rule set " + this.name + " cannot format the value " + number);
            } else {
               NFRule result = this.rules[hi - 1];
               if (result.shouldRollBack(number)) {
                  if (hi == 1) {
                     throw new IllegalStateException("The rule set " + this.name + " cannot roll back from the rule '" + result + "'");
                  }

                  result = this.rules[hi - 2];
               }

               return result;
            }
         } else {
            return this.nonNumericalRules[3];
         }
      }
   }

   private NFRule findFractionRuleSetRule(double number) {
      long leastCommonMultiple = this.rules[0].getBaseValue();

      for(int i = 1; i < this.rules.length; ++i) {
         leastCommonMultiple = lcm(leastCommonMultiple, this.rules[i].getBaseValue());
      }

      long numerator = Math.round(number * (double)leastCommonMultiple);
      long difference = Long.MAX_VALUE;
      int winner = 0;

      for(int i = 0; i < this.rules.length; ++i) {
         long tempDifference = numerator * this.rules[i].getBaseValue() % leastCommonMultiple;
         if (leastCommonMultiple - tempDifference < tempDifference) {
            tempDifference = leastCommonMultiple - tempDifference;
         }

         if (tempDifference < difference) {
            difference = tempDifference;
            winner = i;
            if (tempDifference == 0L) {
               break;
            }
         }
      }

      if (winner + 1 < this.rules.length && this.rules[winner + 1].getBaseValue() == this.rules[winner].getBaseValue() && (Math.round(number * (double)this.rules[winner].getBaseValue()) < 1L || Math.round(number * (double)this.rules[winner].getBaseValue()) >= 2L)) {
         ++winner;
      }

      return this.rules[winner];
   }

   private static long lcm(long x, long y) {
      long x1 = x;
      long y1 = y;

      int p2;
      for(p2 = 0; (x1 & 1L) == 0L && (y1 & 1L) == 0L; y1 >>= 1) {
         ++p2;
         x1 >>= 1;
      }

      long t;
      if ((x1 & 1L) == 1L) {
         t = -y1;
      } else {
         t = x1;
      }

      for(; t != 0L; t = x1 - y1) {
         while((t & 1L) == 0L) {
            t >>= 1;
         }

         if (t > 0L) {
            x1 = t;
         } else {
            y1 = -t;
         }
      }

      long gcd = x1 << p2;
      return x / gcd * y;
   }

   public Number parse(String text, ParsePosition parsePosition, double upperBound) {
      ParsePosition highWaterMark = new ParsePosition(0);
      Number result = NFRule.ZERO;
      if (text.length() == 0) {
         return (Number)result;
      } else {
         NFRule[] var7 = this.nonNumericalRules;
         int var8 = var7.length;

         Number tempResult;
         for(int var9 = 0; var9 < var8; ++var9) {
            NFRule fractionRule = var7[var9];
            if (fractionRule != null) {
               tempResult = fractionRule.doParse(text, parsePosition, false, upperBound);
               if (parsePosition.getIndex() > highWaterMark.getIndex()) {
                  result = tempResult;
                  highWaterMark.setIndex(parsePosition.getIndex());
               }

               parsePosition.setIndex(0);
            }
         }

         for(int i = this.rules.length - 1; i >= 0 && highWaterMark.getIndex() < text.length(); --i) {
            if (this.isFractionRuleSet || !((double)this.rules[i].getBaseValue() >= upperBound)) {
               tempResult = this.rules[i].doParse(text, parsePosition, this.isFractionRuleSet, upperBound);
               if (parsePosition.getIndex() > highWaterMark.getIndex()) {
                  result = tempResult;
                  highWaterMark.setIndex(parsePosition.getIndex());
               }

               parsePosition.setIndex(0);
            }
         }

         parsePosition.setIndex(highWaterMark.getIndex());
         return (Number)result;
      }
   }

   public void setDecimalFormatSymbols(DecimalFormatSymbols newSymbols) {
      NFRule[] var2 = this.rules;
      int var3 = var2.length;

      int var4;
      NFRule rule;
      for(var4 = 0; var4 < var3; ++var4) {
         rule = var2[var4];
         rule.setDecimalFormatSymbols(newSymbols);
      }

      if (this.fractionRules != null) {
         for(int nonNumericalIdx = 1; nonNumericalIdx <= 3; ++nonNumericalIdx) {
            if (this.nonNumericalRules[nonNumericalIdx] != null) {
               Iterator var7 = this.fractionRules.iterator();

               while(var7.hasNext()) {
                  NFRule rule = (NFRule)var7.next();
                  if (this.nonNumericalRules[nonNumericalIdx].getBaseValue() == rule.getBaseValue()) {
                     this.setBestFractionRule(nonNumericalIdx, rule, false);
                  }
               }
            }
         }
      }

      var2 = this.nonNumericalRules;
      var3 = var2.length;

      for(var4 = 0; var4 < var3; ++var4) {
         rule = var2[var4];
         if (rule != null) {
            rule.setDecimalFormatSymbols(newSymbols);
         }
      }

   }
}
