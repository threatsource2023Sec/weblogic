package org.python.icu.text;

import java.io.IOException;
import java.text.CharacterIterator;
import org.python.icu.impl.Assert;
import org.python.icu.impl.CharacterIteration;

class CjkBreakEngine extends DictionaryBreakEngine {
   private static final UnicodeSet fHangulWordSet = new UnicodeSet();
   private static final UnicodeSet fHanWordSet = new UnicodeSet();
   private static final UnicodeSet fKatakanaWordSet = new UnicodeSet();
   private static final UnicodeSet fHiraganaWordSet = new UnicodeSet();
   private DictionaryMatcher fDictionary = null;
   private static final int kMaxKatakanaLength = 8;
   private static final int kMaxKatakanaGroupLength = 20;
   private static final int maxSnlp = 255;
   private static final int kint32max = Integer.MAX_VALUE;

   public CjkBreakEngine(boolean korean) throws IOException {
      super(1);
      this.fDictionary = DictionaryData.loadDictionaryFor("Hira");
      if (korean) {
         this.setCharacters(fHangulWordSet);
      } else {
         UnicodeSet cjSet = new UnicodeSet();
         cjSet.addAll(fHanWordSet);
         cjSet.addAll(fKatakanaWordSet);
         cjSet.addAll(fHiraganaWordSet);
         cjSet.add(65392);
         cjSet.add(12540);
         this.setCharacters(cjSet);
      }

   }

   public boolean equals(Object obj) {
      if (obj instanceof CjkBreakEngine) {
         CjkBreakEngine other = (CjkBreakEngine)obj;
         return this.fSet.equals(other.fSet);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.getClass().hashCode();
   }

   private static int getKatakanaCost(int wordlength) {
      int[] katakanaCost = new int[]{8192, 984, 408, 240, 204, 252, 300, 372, 480};
      return wordlength > 8 ? 8192 : katakanaCost[wordlength];
   }

   private static boolean isKatakana(int value) {
      return value >= 12449 && value <= 12542 && value != 12539 || value >= 65382 && value <= 65439;
   }

   public int divideUpDictionaryRange(CharacterIterator inText, int startPos, int endPos, DictionaryBreakEngine.DequeI foundBreaks) {
      if (startPos >= endPos) {
         return 0;
      } else {
         inText.setIndex(startPos);
         int inputLength = endPos - startPos;
         int[] charPositions = new int[inputLength + 1];
         StringBuffer s = new StringBuffer("");
         inText.setIndex(startPos);

         while(inText.getIndex() < endPos) {
            s.append(inText.current());
            inText.next();
         }

         String prenormstr = s.toString();
         boolean isNormalized = Normalizer.quickCheck(prenormstr, Normalizer.NFKC) == Normalizer.YES || Normalizer.isNormalized(prenormstr, Normalizer.NFKC, 0);
         int numChars = 0;
         java.text.StringCharacterIterator text;
         int i;
         int i;
         if (isNormalized) {
            text = new java.text.StringCharacterIterator(prenormstr);
            int index = 0;

            for(charPositions[0] = 0; index < prenormstr.length(); charPositions[numChars] = index) {
               i = prenormstr.codePointAt(index);
               index += Character.charCount(i);
               ++numChars;
            }
         } else {
            String normStr = Normalizer.normalize(prenormstr, Normalizer.NFKC);
            text = new java.text.StringCharacterIterator(normStr);
            charPositions = new int[normStr.length() + 1];
            Normalizer normalizer = new Normalizer(prenormstr, Normalizer.NFKC, 0);
            i = 0;

            for(charPositions[0] = 0; i < normalizer.endIndex(); charPositions[numChars] = i) {
               normalizer.next();
               ++numChars;
               i = normalizer.getIndex();
            }
         }

         int[] bestSnlp = new int[numChars + 1];
         bestSnlp[0] = 0;

         for(i = 1; i <= numChars; ++i) {
            bestSnlp[i] = Integer.MAX_VALUE;
         }

         int[] prev = new int[numChars + 1];

         for(i = 0; i <= numChars; ++i) {
            prev[i] = -1;
         }

         int maxWordSize = true;
         int[] values = new int[numChars];
         int[] lengths = new int[numChars];
         boolean is_prev_katakana = false;

         int numBreaks;
         int i;
         int pos;
         for(int i = 0; i < numChars; ++i) {
            text.setIndex(i);
            if (bestSnlp[i] != Integer.MAX_VALUE) {
               numBreaks = i + 20 < numChars ? 20 : numChars - i;
               int[] count_ = new int[1];
               this.fDictionary.matches(text, numBreaks, lengths, count_, numBreaks, values);
               i = count_[0];
               text.setIndex(i);
               if ((i == 0 || lengths[0] != 1) && CharacterIteration.current32(text) != Integer.MAX_VALUE && !fHangulWordSet.contains(CharacterIteration.current32(text))) {
                  values[i] = 255;
                  lengths[i] = 1;
                  ++i;
               }

               int j;
               for(pos = 0; pos < i; ++pos) {
                  j = bestSnlp[i] + values[pos];
                  if (j < bestSnlp[lengths[pos] + i]) {
                     bestSnlp[lengths[pos] + i] = j;
                     prev[lengths[pos] + i] = i;
                  }
               }

               boolean is_katakana = isKatakana(CharacterIteration.current32(text));
               if (!is_prev_katakana && is_katakana) {
                  j = i + 1;
                  CharacterIteration.next32(text);

                  while(j < numChars && j - i < 20 && isKatakana(CharacterIteration.current32(text))) {
                     CharacterIteration.next32(text);
                     ++j;
                  }

                  if (j - i < 20) {
                     int newSnlp = bestSnlp[i] + getKatakanaCost(j - i);
                     if (newSnlp < bestSnlp[j]) {
                        bestSnlp[j] = newSnlp;
                        prev[j] = i;
                     }
                  }
               }

               is_prev_katakana = is_katakana;
            }
         }

         int[] t_boundary = new int[numChars + 1];
         numBreaks = 0;
         int correctedNumBreaks;
         if (bestSnlp[numChars] == Integer.MAX_VALUE) {
            t_boundary[numBreaks] = numChars;
            ++numBreaks;
         } else {
            for(correctedNumBreaks = numChars; correctedNumBreaks > 0; correctedNumBreaks = prev[correctedNumBreaks]) {
               t_boundary[numBreaks] = correctedNumBreaks;
               ++numBreaks;
            }

            Assert.assrt(prev[t_boundary[numBreaks - 1]] == 0);
         }

         if (foundBreaks.size() == 0 || foundBreaks.peek() < startPos) {
            t_boundary[numBreaks++] = 0;
         }

         correctedNumBreaks = 0;

         for(i = numBreaks - 1; i >= 0; --i) {
            pos = charPositions[t_boundary[i]] + startPos;
            if (!foundBreaks.contains(pos) && pos != startPos) {
               foundBreaks.push(charPositions[t_boundary[i]] + startPos);
               ++correctedNumBreaks;
            }
         }

         if (!foundBreaks.isEmpty() && foundBreaks.peek() == endPos) {
            foundBreaks.pop();
            --correctedNumBreaks;
         }

         if (!foundBreaks.isEmpty()) {
            inText.setIndex(foundBreaks.peek());
         }

         return correctedNumBreaks;
      }
   }

   static {
      fHangulWordSet.applyPattern("[\\uac00-\\ud7a3]");
      fHanWordSet.applyPattern("[:Han:]");
      fKatakanaWordSet.applyPattern("[[:Katakana:]\\uff9e\\uff9f]");
      fHiraganaWordSet.applyPattern("[:Hiragana:]");
      fHangulWordSet.freeze();
      fHanWordSet.freeze();
      fKatakanaWordSet.freeze();
      fHiraganaWordSet.freeze();
   }
}
