package org.python.icu.text;

import java.io.IOException;
import java.text.CharacterIterator;
import org.python.icu.lang.UCharacter;

class ThaiBreakEngine extends DictionaryBreakEngine {
   private static final byte THAI_LOOKAHEAD = 3;
   private static final byte THAI_ROOT_COMBINE_THRESHOLD = 3;
   private static final byte THAI_PREFIX_COMBINE_THRESHOLD = 3;
   private static final char THAI_PAIYANNOI = 'ฯ';
   private static final char THAI_MAIYAMOK = 'ๆ';
   private static final byte THAI_MIN_WORD = 2;
   private static final byte THAI_MIN_WORD_SPAN = 4;
   private DictionaryMatcher fDictionary;
   private static UnicodeSet fThaiWordSet = new UnicodeSet();
   private static UnicodeSet fEndWordSet;
   private static UnicodeSet fBeginWordSet = new UnicodeSet();
   private static UnicodeSet fSuffixSet = new UnicodeSet();
   private static UnicodeSet fMarkSet = new UnicodeSet();

   public ThaiBreakEngine() throws IOException {
      super(1, 2);
      this.setCharacters(fThaiWordSet);
      this.fDictionary = DictionaryData.loadDictionaryFor("Thai");
   }

   public boolean equals(Object obj) {
      return obj instanceof ThaiBreakEngine;
   }

   public int hashCode() {
      return this.getClass().hashCode();
   }

   public boolean handles(int c, int breakType) {
      if (breakType != 1 && breakType != 2) {
         return false;
      } else {
         int script = UCharacter.getIntPropertyValue(c, 4106);
         return script == 38;
      }
   }

   public int divideUpDictionaryRange(CharacterIterator fIter, int rangeStart, int rangeEnd, DictionaryBreakEngine.DequeI foundBreaks) {
      if (rangeEnd - rangeStart < 4) {
         return 0;
      } else {
         int wordsFound = 0;
         DictionaryBreakEngine.PossibleWord[] words = new DictionaryBreakEngine.PossibleWord[3];

         for(int i = 0; i < 3; ++i) {
            words[i] = new DictionaryBreakEngine.PossibleWord();
         }

         fIter.setIndex(rangeStart);

         int current;
         while((current = fIter.getIndex()) < rangeEnd) {
            int wordLength = 0;
            int candidates = words[wordsFound % 3].candidates(fIter, this.fDictionary, rangeEnd);
            int wordsMatched;
            if (candidates == 1) {
               wordLength = words[wordsFound % 3].acceptMarked(fIter);
               ++wordsFound;
            } else if (candidates > 1) {
               if (fIter.getIndex() < rangeEnd) {
                  label94:
                  do {
                     wordsMatched = 1;
                     if (words[(wordsFound + 1) % 3].candidates(fIter, this.fDictionary, rangeEnd) > 0) {
                        if (wordsMatched < 2) {
                           words[wordsFound % 3].markCurrent();
                           int wordsMatched = true;
                        }

                        if (fIter.getIndex() >= rangeEnd) {
                           break;
                        }

                        do {
                           if (words[(wordsFound + 2) % 3].candidates(fIter, this.fDictionary, rangeEnd) > 0) {
                              words[wordsFound % 3].markCurrent();
                              break label94;
                           }
                        } while(words[(wordsFound + 1) % 3].backUp(fIter));
                     }
                  } while(words[wordsFound % 3].backUp(fIter));
               }

               wordLength = words[wordsFound % 3].acceptMarked(fIter);
               ++wordsFound;
            }

            char uc;
            if (fIter.getIndex() < rangeEnd && wordLength < 3) {
               if (words[wordsFound % 3].candidates(fIter, this.fDictionary, rangeEnd) > 0 || wordLength != 0 && words[wordsFound % 3].longestPrefix() >= 3) {
                  fIter.setIndex(current + wordLength);
               } else {
                  wordsMatched = rangeEnd - (current + wordLength);
                  int pc = fIter.current();
                  int chars = 0;

                  while(true) {
                     fIter.next();
                     uc = fIter.current();
                     ++chars;
                     --wordsMatched;
                     if (wordsMatched <= 0) {
                        break;
                     }

                     if (fEndWordSet.contains(pc) && fBeginWordSet.contains(uc)) {
                        int candidate = words[(wordsFound + 1) % 3].candidates(fIter, this.fDictionary, rangeEnd);
                        fIter.setIndex(current + wordLength + chars);
                        if (candidate > 0) {
                           break;
                        }
                     }

                     pc = uc;
                  }

                  if (wordLength <= 0) {
                     ++wordsFound;
                  }

                  wordLength += chars;
               }
            }

            while((wordsMatched = fIter.getIndex()) < rangeEnd && fMarkSet.contains(fIter.current())) {
               fIter.next();
               wordLength += fIter.getIndex() - wordsMatched;
            }

            if (fIter.getIndex() < rangeEnd && wordLength > 0) {
               if (words[wordsFound % 3].candidates(fIter, this.fDictionary, rangeEnd) <= 0 && fSuffixSet.contains(uc = fIter.current())) {
                  if (uc == 3631) {
                     if (!fSuffixSet.contains(fIter.previous())) {
                        fIter.next();
                        fIter.next();
                        ++wordLength;
                        uc = fIter.current();
                     } else {
                        fIter.next();
                     }
                  }

                  if (uc == 3654) {
                     if (fIter.previous() != 3654) {
                        fIter.next();
                        fIter.next();
                        ++wordLength;
                     } else {
                        fIter.next();
                     }
                  }
               } else {
                  fIter.setIndex(current + wordLength);
               }
            }

            if (wordLength > 0) {
               foundBreaks.push(Integer.valueOf(current + wordLength));
            }
         }

         if (foundBreaks.peek() >= rangeEnd) {
            foundBreaks.pop();
            --wordsFound;
         }

         return wordsFound;
      }
   }

   static {
      fThaiWordSet.applyPattern("[[:Thai:]&[:LineBreak=SA:]]");
      fThaiWordSet.compact();
      fMarkSet.applyPattern("[[:Thai:]&[:LineBreak=SA:]&[:M:]]");
      fMarkSet.add(32);
      fEndWordSet = new UnicodeSet(fThaiWordSet);
      fEndWordSet.remove(3633);
      fEndWordSet.remove(3648, 3652);
      fBeginWordSet.add(3585, 3630);
      fBeginWordSet.add(3648, 3652);
      fSuffixSet.add(3631);
      fSuffixSet.add(3654);
      fMarkSet.compact();
      fEndWordSet.compact();
      fBeginWordSet.compact();
      fSuffixSet.compact();
      fThaiWordSet.freeze();
      fMarkSet.freeze();
      fEndWordSet.freeze();
      fBeginWordSet.freeze();
      fSuffixSet.freeze();
   }
}
