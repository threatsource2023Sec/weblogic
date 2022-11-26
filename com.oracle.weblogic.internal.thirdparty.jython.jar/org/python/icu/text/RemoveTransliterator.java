package org.python.icu.text;

class RemoveTransliterator extends Transliterator {
   private static final String _ID = "Any-Remove";

   static void register() {
      Transliterator.registerFactory("Any-Remove", new Transliterator.Factory() {
         public Transliterator getInstance(String ID) {
            return new RemoveTransliterator();
         }
      });
      Transliterator.registerSpecialInverse("Remove", "Null", false);
   }

   public RemoveTransliterator() {
      super("Any-Remove", (UnicodeFilter)null);
   }

   protected void handleTransliterate(Replaceable text, Transliterator.Position index, boolean incremental) {
      text.replace(index.start, index.limit, "");
      int len = index.limit - index.start;
      index.contextLimit -= len;
      index.limit -= len;
   }

   public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
      UnicodeSet myFilter = this.getFilterAsUnicodeSet(inputFilter);
      sourceSet.addAll(myFilter);
   }
}
