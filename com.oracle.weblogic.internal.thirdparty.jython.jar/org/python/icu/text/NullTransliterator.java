package org.python.icu.text;

class NullTransliterator extends Transliterator {
   static final String SHORT_ID = "Null";
   static final String _ID = "Any-Null";

   public NullTransliterator() {
      super("Any-Null", (UnicodeFilter)null);
   }

   protected void handleTransliterate(Replaceable text, Transliterator.Position offsets, boolean incremental) {
      offsets.start = offsets.limit;
   }

   public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
   }
}
