package org.python.icu.text;

import java.text.CharacterIterator;
import org.python.icu.impl.Assert;
import org.python.icu.util.BytesTrie;

class BytesDictionaryMatcher extends DictionaryMatcher {
   private final byte[] characters;
   private final int transform;

   public BytesDictionaryMatcher(byte[] chars, int transform) {
      this.characters = chars;
      Assert.assrt((transform & 2130706432) == 16777216);
      this.transform = transform;
   }

   private int transform(int c) {
      if (c == 8205) {
         return 255;
      } else if (c == 8204) {
         return 254;
      } else {
         int delta = c - (this.transform & 2097151);
         return delta >= 0 && 253 >= delta ? delta : -1;
      }
   }

   public int matches(CharacterIterator text_, int maxLength, int[] lengths, int[] count_, int limit, int[] values) {
      UCharacterIterator text = UCharacterIterator.getInstance(text_);
      BytesTrie bt = new BytesTrie(this.characters, 0);
      int c = text.nextCodePoint();
      if (c == -1) {
         return 0;
      } else {
         BytesTrie.Result result = bt.first(this.transform(c));
         int numChars = 1;
         int count = 0;

         while(true) {
            if (result.hasValue()) {
               if (count < limit) {
                  if (values != null) {
                     values[count] = bt.getValue();
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
            result = bt.next(this.transform(c));
         }

         count_[0] = count;
         return numChars;
      }
   }

   public int getType() {
      return 0;
   }
}
