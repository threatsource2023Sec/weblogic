package org.python.icu.text;

import org.python.icu.impl.Utility;

class EscapeTransliterator extends Transliterator {
   private String prefix;
   private String suffix;
   private int radix;
   private int minDigits;
   private boolean grokSupplementals;
   private EscapeTransliterator supplementalHandler;

   static void register() {
      Transliterator.registerFactory("Any-Hex/Unicode", new Transliterator.Factory() {
         public Transliterator getInstance(String ID) {
            return new EscapeTransliterator("Any-Hex/Unicode", "U+", "", 16, 4, true, (EscapeTransliterator)null);
         }
      });
      Transliterator.registerFactory("Any-Hex/Java", new Transliterator.Factory() {
         public Transliterator getInstance(String ID) {
            return new EscapeTransliterator("Any-Hex/Java", "\\u", "", 16, 4, false, (EscapeTransliterator)null);
         }
      });
      Transliterator.registerFactory("Any-Hex/C", new Transliterator.Factory() {
         public Transliterator getInstance(String ID) {
            return new EscapeTransliterator("Any-Hex/C", "\\u", "", 16, 4, true, new EscapeTransliterator("", "\\U", "", 16, 8, true, (EscapeTransliterator)null));
         }
      });
      Transliterator.registerFactory("Any-Hex/XML", new Transliterator.Factory() {
         public Transliterator getInstance(String ID) {
            return new EscapeTransliterator("Any-Hex/XML", "&#x", ";", 16, 1, true, (EscapeTransliterator)null);
         }
      });
      Transliterator.registerFactory("Any-Hex/XML10", new Transliterator.Factory() {
         public Transliterator getInstance(String ID) {
            return new EscapeTransliterator("Any-Hex/XML10", "&#", ";", 10, 1, true, (EscapeTransliterator)null);
         }
      });
      Transliterator.registerFactory("Any-Hex/Perl", new Transliterator.Factory() {
         public Transliterator getInstance(String ID) {
            return new EscapeTransliterator("Any-Hex/Perl", "\\x{", "}", 16, 1, true, (EscapeTransliterator)null);
         }
      });
      Transliterator.registerFactory("Any-Hex/Plain", new Transliterator.Factory() {
         public Transliterator getInstance(String ID) {
            return new EscapeTransliterator("Any-Hex/Plain", "", "", 16, 4, true, (EscapeTransliterator)null);
         }
      });
      Transliterator.registerFactory("Any-Hex", new Transliterator.Factory() {
         public Transliterator getInstance(String ID) {
            return new EscapeTransliterator("Any-Hex", "\\u", "", 16, 4, false, (EscapeTransliterator)null);
         }
      });
   }

   EscapeTransliterator(String ID, String prefix, String suffix, int radix, int minDigits, boolean grokSupplementals, EscapeTransliterator supplementalHandler) {
      super(ID, (UnicodeFilter)null);
      this.prefix = prefix;
      this.suffix = suffix;
      this.radix = radix;
      this.minDigits = minDigits;
      this.grokSupplementals = grokSupplementals;
      this.supplementalHandler = supplementalHandler;
   }

   protected void handleTransliterate(Replaceable text, Transliterator.Position pos, boolean incremental) {
      int start = pos.start;
      int limit = pos.limit;
      StringBuilder buf = new StringBuilder(this.prefix);
      int prefixLen = this.prefix.length();

      int charLen;
      for(boolean redoPrefix = false; start < limit; limit += buf.length() - charLen) {
         int c = this.grokSupplementals ? text.char32At(start) : text.charAt(start);
         charLen = this.grokSupplementals ? UTF16.getCharCount(c) : 1;
         if ((c & -65536) != 0 && this.supplementalHandler != null) {
            buf.setLength(0);
            buf.append(this.supplementalHandler.prefix);
            Utility.appendNumber(buf, c, this.supplementalHandler.radix, this.supplementalHandler.minDigits);
            buf.append(this.supplementalHandler.suffix);
            redoPrefix = true;
         } else {
            if (redoPrefix) {
               buf.setLength(0);
               buf.append(this.prefix);
               redoPrefix = false;
            } else {
               buf.setLength(prefixLen);
            }

            Utility.appendNumber(buf, c, this.radix, this.minDigits);
            buf.append(this.suffix);
         }

         text.replace(start, start + charLen, buf.toString());
         start += buf.length();
      }

      pos.contextLimit += limit - pos.limit;
      pos.limit = limit;
      pos.start = start;
   }

   public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
      sourceSet.addAll(this.getFilterAsUnicodeSet(inputFilter));

      for(EscapeTransliterator it = this; it != null; it = it.supplementalHandler) {
         if (inputFilter.size() != 0) {
            targetSet.addAll((CharSequence)it.prefix);
            targetSet.addAll((CharSequence)it.suffix);
            StringBuilder buffer = new StringBuilder();

            for(int i = 0; i < it.radix; ++i) {
               Utility.appendNumber(buffer, i, it.radix, it.minDigits);
            }

            targetSet.addAll((CharSequence)buffer.toString());
         }
      }

   }
}
