package org.python.icu.text;

import java.text.CharacterIterator;
import org.python.icu.util.BytesTrie;
import org.python.icu.util.CharsTrie;

class CharsDictionaryMatcher extends DictionaryMatcher {
   private CharSequence characters;

   public CharsDictionaryMatcher(CharSequence chars) {
      this.characters = chars;
   }

   public int matches(CharacterIterator text_, int maxLength, int[] lengths, int[] count_, int limit, int[] values) {
      UCharacterIterator text = UCharacterIterator.getInstance(text_);
      CharsTrie uct = new CharsTrie(this.characters, 0);
      int c = text.nextCodePoint();
      if (c == -1) {
         return 0;
      } else {
         BytesTrie.Result result = uct.firstForCodePoint(c);
         int numChars = 1;
         int count = 0;

         while(true) {
            if (result.hasValue()) {
               if (count < limit) {
                  if (values != null) {
                     values[count] = uct.getValue();
                  }

                  lengths[count] = numChars;
                  ++count;
               }

               if (result == BytesTrie.Result.FINAL_VALUE) {
                  break;
               }
            } else if (result == BytesTrie.Result.NO_MATCH) {
               break;
            }

            if (numChars >= maxLength) {
               break;
            }

            c = text.nextCodePoint();
            if (c == -1) {
               break;
            }

            ++numChars;
            result = uct.nextForCodePoint(c);
         }

         count_[0] = count;
         return numChars;
      }
   }

   public int getType() {
      return 1;
   }
}
