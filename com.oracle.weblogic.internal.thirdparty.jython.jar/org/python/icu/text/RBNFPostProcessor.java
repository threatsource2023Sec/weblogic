package org.python.icu.text;

interface RBNFPostProcessor {
   void init(RuleBasedNumberFormat var1, String var2);

   void process(StringBuilder var1, NFRuleSet var2);
}
