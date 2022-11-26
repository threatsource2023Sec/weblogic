package org.python.icu.text;

import org.python.icu.impl.UCaseProps;
import org.python.icu.lang.UCharacter;
import org.python.icu.util.ULocale;

class TitlecaseTransliterator extends Transliterator {
   static final String _ID = "Any-Title";
   private final ULocale locale;
   private final UCaseProps csp;
   private ReplaceableContextIterator iter;
   private StringBuilder result;
   private int caseLocale;
   SourceTargetUtility sourceTargetUtility = null;

   static void register() {
      Transliterator.registerFactory("Any-Title", new Transliterator.Factory() {
         public Transliterator getInstance(String ID) {
            return new TitlecaseTransliterator(ULocale.US);
         }
      });
      registerSpecialInverse("Title", "Lower", false);
   }

   public TitlecaseTransliterator(ULocale loc) {
      super("Any-Title", (UnicodeFilter)null);
      this.locale = loc;
      this.setMaximumContextLength(2);
      this.csp = UCaseProps.INSTANCE;
      this.iter = new ReplaceableContextIterator();
      this.result = new StringBuilder();
      this.caseLocale = UCaseProps.getCaseLocale(this.locale);
   }

   protected synchronized void handleTransliterate(Replaceable text, Transliterator.Position offsets, boolean isIncremental) {
      if (offsets.start < offsets.limit) {
         boolean doTitle = true;

         int c;
         int type;
         for(int start = offsets.start - 1; start >= offsets.contextStart; start -= UTF16.getCharCount(c)) {
            c = text.char32At(start);
            type = this.csp.getTypeOrIgnorable(c);
            if (type > 0) {
               doTitle = false;
               break;
            }

            if (type == 0) {
               break;
            }
         }

         this.iter.setText(text);
         this.iter.setIndex(offsets.start);
         this.iter.setLimit(offsets.limit);
         this.iter.setContextLimits(offsets.contextStart, offsets.contextLimit);
         this.result.setLength(0);

         while((c = this.iter.nextCaseMapCP()) >= 0) {
            type = this.csp.getTypeOrIgnorable(c);
            if (type >= 0) {
               if (doTitle) {
                  c = this.csp.toFullTitle(c, this.iter, this.result, this.caseLocale);
               } else {
                  c = this.csp.toFullLower(c, this.iter, this.result, this.caseLocale);
               }

               doTitle = type == 0;
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
         }

         offsets.start = offsets.limit;
      }
   }

   public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
      synchronized(this) {
         if (this.sourceTargetUtility == null) {
            this.sourceTargetUtility = new SourceTargetUtility(new Transform() {
               public String transform(String source) {
                  return UCharacter.toTitleCase((ULocale)TitlecaseTransliterator.this.locale, source, (BreakIterator)null);
               }
            });
         }
      }

      this.sourceTargetUtility.addSourceTargetSet(this, inputFilter, sourceSet, targetSet);
   }
}
