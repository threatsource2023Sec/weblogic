package org.python.icu.text;

import java.util.HashMap;
import java.util.Map;

/** @deprecated */
@Deprecated
public class RuleBasedTransliterator extends Transliterator {
   private final Data data;

   RuleBasedTransliterator(String ID, Data data, UnicodeFilter filter) {
      super(ID, filter);
      this.data = data;
      this.setMaximumContextLength(data.ruleSet.getMaximumContextLength());
   }

   /** @deprecated */
   @Deprecated
   protected void handleTransliterate(Replaceable text, Transliterator.Position index, boolean incremental) {
      synchronized(this.data) {
         int loopCount = 0;
         int loopLimit = index.limit - index.start << 4;
         if (loopLimit < 0) {
            loopLimit = Integer.MAX_VALUE;
         }

         while(index.start < index.limit && loopCount <= loopLimit && this.data.ruleSet.transliterate(text, index, incremental)) {
            ++loopCount;
         }

      }
   }

   /** @deprecated */
   @Deprecated
   public String toRules(boolean escapeUnprintable) {
      return this.data.ruleSet.toRules(escapeUnprintable);
   }

   /** @deprecated */
   @Deprecated
   public void addSourceTargetSet(UnicodeSet filter, UnicodeSet sourceSet, UnicodeSet targetSet) {
      this.data.ruleSet.addSourceTargetSet(filter, sourceSet, targetSet);
   }

   /** @deprecated */
   @Deprecated
   public Transliterator safeClone() {
      UnicodeFilter filter = this.getFilter();
      if (filter != null && filter instanceof UnicodeSet) {
         filter = new UnicodeSet((UnicodeSet)filter);
      }

      return new RuleBasedTransliterator(this.getID(), this.data, (UnicodeFilter)filter);
   }

   static class Data {
      public TransliterationRuleSet ruleSet = new TransliterationRuleSet();
      Map variableNames = new HashMap();
      Object[] variables;
      char variablesBase;

      public Data() {
      }

      public UnicodeMatcher lookupMatcher(int standIn) {
         int i = standIn - this.variablesBase;
         return i >= 0 && i < this.variables.length ? (UnicodeMatcher)this.variables[i] : null;
      }

      public UnicodeReplacer lookupReplacer(int standIn) {
         int i = standIn - this.variablesBase;
         return i >= 0 && i < this.variables.length ? (UnicodeReplacer)this.variables[i] : null;
      }
   }
}
