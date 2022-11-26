package org.python.icu.text;

import org.python.icu.impl.UCaseProps;
import org.python.icu.lang.UCharacter;

class CaseFoldTransliterator extends Transliterator {
   static final String _ID = "Any-CaseFold";
   private final UCaseProps csp;
   private ReplaceableContextIterator iter;
   private StringBuilder result;
   static SourceTargetUtility sourceTargetUtility = null;

   static void register() {
      Transliterator.registerFactory("Any-CaseFold", new Transliterator.Factory() {
         public Transliterator getInstance(String ID) {
            return new CaseFoldTransliterator();
         }
      });
      Transliterator.registerSpecialInverse("CaseFold", "Upper", false);
   }

   public CaseFoldTransliterator() {
      super("Any-CaseFold", (UnicodeFilter)null);
      this.csp = UCaseProps.INSTANCE;
      this.iter = new ReplaceableContextIterator();
      this.result = new StringBuilder();
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
               c = this.csp.toFullFolding(c, this.result, 0);
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
      Class var4 = UppercaseTransliterator.class;
      synchronized(UppercaseTransliterator.class) {
         if (sourceTargetUtility == null) {
            sourceTargetUtility = new SourceTargetUtility(new Transform() {
               public String transform(String source) {
                  return UCharacter.foldCase(source, true);
               }
            });
         }
      }

      sourceTargetUtility.addSourceTargetSet(this, inputFilter, sourceSet, targetSet);
   }
}
