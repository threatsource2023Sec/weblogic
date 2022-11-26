package org.python.icu.text;

class MultiplierSubstitution extends NFSubstitution {
   long divisor;

   MultiplierSubstitution(int pos, NFRule rule, NFRuleSet ruleSet, String description) {
      super(pos, ruleSet, description);
      this.divisor = rule.getDivisor();
      if (this.divisor == 0L) {
         throw new IllegalStateException("Substitution with divisor 0 " + description.substring(0, pos) + " | " + description.substring(pos));
      }
   }

   public void setDivisor(int radix, short exponent) {
      this.divisor = NFRule.power((long)radix, exponent);
      if (this.divisor == 0L) {
         throw new IllegalStateException("Substitution with divisor 0");
      }
   }

   public boolean equals(Object that) {
      return super.equals(that) && this.divisor == ((MultiplierSubstitution)that).divisor;
   }

   public long transformNumber(long number) {
      return (long)Math.floor((double)(number / this.divisor));
   }

   public double transformNumber(double number) {
      return this.ruleSet == null ? number / (double)this.divisor : Math.floor(number / (double)this.divisor);
   }

   public double composeRuleValue(double newRuleValue, double oldRuleValue) {
      return newRuleValue * (double)this.divisor;
   }

   public double calcUpperBound(double oldUpperBound) {
      return (double)this.divisor;
   }

   char tokenChar() {
      return '<';
   }
}
