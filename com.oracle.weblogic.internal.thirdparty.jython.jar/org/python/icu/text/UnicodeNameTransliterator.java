package org.python.icu.text;

import org.python.icu.lang.UCharacter;

class UnicodeNameTransliterator extends Transliterator {
   static final String _ID = "Any-Name";
   static final String OPEN_DELIM = "\\N{";
   static final char CLOSE_DELIM = '}';
   static final int OPEN_DELIM_LEN = 3;

   static void register() {
      Transliterator.registerFactory("Any-Name", new Transliterator.Factory() {
         public Transliterator getInstance(String ID) {
            return new UnicodeNameTransliterator((UnicodeFilter)null);
         }
      });
   }

   public UnicodeNameTransliterator(UnicodeFilter filter) {
      super("Any-Name", filter);
   }

   protected void handleTransliterate(Replaceable text, Transliterator.Position offsets, boolean isIncremental) {
      int cursor = offsets.start;
      int limit = offsets.limit;
      StringBuilder str = new StringBuilder();
      str.append("\\N{");

      while(cursor < limit) {
         int c = text.char32At(cursor);
         String name;
         if ((name = UCharacter.getExtendedName(c)) != null) {
            str.setLength(3);
            str.append(name).append('}');
            int clen = UTF16.getCharCount(c);
            text.replace(cursor, cursor + clen, str.toString());
            int len = str.length();
            cursor += len;
            limit += len - clen;
         } else {
            ++cursor;
         }
      }

      offsets.contextLimit += limit - offsets.limit;
      offsets.limit = limit;
      offsets.start = cursor;
   }

   public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
      UnicodeSet myFilter = this.getFilterAsUnicodeSet(inputFilter);
      if (myFilter.size() > 0) {
         sourceSet.addAll(myFilter);
         targetSet.addAll(48, 57).addAll(65, 90).add(45).add(32).addAll((CharSequence)"\\N{").add(125).addAll(97, 122).add(60).add(62).add(40).add(41);
      }

   }
}
