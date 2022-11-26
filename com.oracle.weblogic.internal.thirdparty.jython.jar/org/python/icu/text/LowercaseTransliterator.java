package org.python.icu.text;

import org.python.icu.impl.UCaseProps;
import org.python.icu.lang.UCharacter;
import org.python.icu.util.ULocale;

class LowercaseTransliterator extends Transliterator {
   static final String _ID = "Any-Lower";
   private final ULocale locale;
   private final UCaseProps csp;
   private ReplaceableContextIterator iter;
   private StringBuilder result;
   private int caseLocale;
   SourceTargetUtility sourceTargetUtility = null;

   static void register() {
      Transliterator.registerFactory("Any-Lower", new Transliterator.Factory() {
         public Transliterator getInstance(String ID) {
            return new LowercaseTransliterator(ULocale.US);
         }
      });
      Transliterator.registerSpecialInverse("Lower", "Upper", true);
   }

   public LowercaseTransliterator(ULocale loc) {
      super("Any-Lower", (UnicodeFilter)null);
      this.locale = loc;
      this.csp = UCaseProps.INSTANCE;
      this.iter = new ReplaceableContextIterator();
      this.result = new StringBuilder();
      this.caseLocale = UCaseProps.getCaseLocale(this.locale);
   }

   protected synchronized void handleTransliterate(Replaceable text, Transliterator.Position offsets, boolean isIncremental) {
      if (this.csp != null) {
         if (offsets.start < offsets.limit) {
            this.iter.setText(text);
            this.result.setLength(0);
            this.iter.setIndex(offsets.start);
            this.iter.setLimit(offsets.limit);
            this.iter.setContextLimits(offsets.contextStart, offsets.contextLimit);

            int c;
            while((c = this.iter.nextCaseMapCP()) >= 0) {
               c = this.csp.toFullLower(c, this.iter, this.result, this.caseLocale);
               if (this.iter.didReachLimit() && isIncremental) {
                  offsets.start = this.iter.getCaseMapCPStart();
                  return;
               }

               if (c >= 0) {
                  int delta;
                  if (c <= 31) {
                     delta = this.iter.replace(this.result.toString());
                     this.result.setLength(0);
                  } else {
                     delta = this.iter.replace(UTF16.valueOf(c));
                  }

                  if (delta != 0) {
                     offsets.limit += delta;
                     offsets.contextLimit += delta;
                  }
               }
            }

            offsets.start = offsets.limit;
         }
      }
   }

   public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
      synchronized(this) {
         if (this.sourceTargetUtility == null) {
            this.sourceTargetUtility = new SourceTargetUtility(new Transform() {
               public String transform(String source) {
                  return UCharacter.toLowerCase(LowercaseTransliterator.this.locale, source);
               }
            });
         }
      }

      this.sourceTargetUtility.addSourceTargetSet(this, inputFilter, sourceSet, targetSet);
   }
}
