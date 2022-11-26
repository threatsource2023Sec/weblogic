package org.python.icu.text;

import java.io.IOException;
import java.text.CharacterIterator;
import org.python.icu.lang.UCharacter;

class LaoBreakEngine extends DictionaryBreakEngine {
   private static final byte LAO_LOOKAHEAD = 3;
   private static final byte LAO_ROOT_COMBINE_THRESHOLD = 3;
   private static final byte LAO_PREFIX_COMBINE_THRESHOLD = 3;
   private static final byte LAO_MIN_WORD = 2;
   private DictionaryMatcher fDictionary;
   private static UnicodeSet fLaoWordSet = new UnicodeSet();
   private static UnicodeSet fEndWordSet;
   private static UnicodeSet fBeginWordSet = new UnicodeSet();
   private static UnicodeSet fMarkSet = new UnicodeSet();

   public LaoBreakEngine() throws IOException {
      super(1, 2);
      this.setCharacters(fLaoWordSet);
      this.fDictionary = DictionaryData.loadDictionaryFor("Laoo");
   }

   public boolean equals(Object obj) {
      return obj instanceof LaoBreakEngine;
   }

   public int hashCode() {
      return this.getClass().hashCode();
   }

   public boolean handles(int c, int breakType) {
      if (breakType != 1 && breakType != 2) {
         return false;
      } else {
         int script = UCharacter.getIntPropertyValue(c, 4106);
         return script == 24;
      }
   }

   public int divideUpDictionaryRange(CharacterIterator fIter, int rangeStart, int rangeEnd, DictionaryBreakEngine.DequeI foundBreaks) {
      if (rangeEnd - rangeStart < 2) {
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
            char pc;
            if (candidates == 1) {
               wordLength = words[wordsFound % 3].acceptMarked(fIter);
               ++wordsFound;
            } else if (candidates > 1) {
               boolean foundBest = false;
               if (fIter.getIndex() < rangeEnd) {
                  do {
                     pc = 1;
                     if (words[(wordsFound + 1) % 3].candidates(fIter, this.fDictionary, rangeEnd) > 0) {
                        if (pc < 2) {
                           words[wordsFound % 3].markCurrent();
                           int wordsMatched = true;
                        }

                        if (fIter.getIndex() >= rangeEnd) {
                           break;
                        }

                        do {
                           if (words[(wordsFound + 2) % 3].candidates(fIter, this.fDictionary, rangeEnd) > 0) {
                              words[wordsFound % 3].markCurrent();
                              foundBest = true;
                              break;
                           }
                        } while(words[(wordsFound + 1) % 3].backUp(fIter));
                     }
                  } while(words[wordsFound % 3].backUp(fIter) && !foundBest);
               }

               wordLength = words[wordsFound % 3].acceptMarked(fIter);
               ++wordsFound;
            }

            int remaining;
            if (fIter.getIndex() < rangeEnd && wordLength < 3) {
               if (words[wordsFound % 3].candidates(fIter, this.fDictionary, rangeEnd) > 0 || wordLength != 0 && words[wordsFound % 3].longestPrefix() >= 3) {
                  fIter.setIndex(current + wordLength);
               } else {
                  remaining = rangeEnd - (current + wordLength);
                  pc = fIter.current();
                  int chars = 0;

                  while(true) {
                     fIter.next();
                     int uc = fIter.current();
                     ++chars;
                     --remaining;
                     if (remaining <= 0) {
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

            while((remaining = fIter.getIndex()) < rangeEnd && fMarkSet.contains(fIter.current())) {
               fIter.next();
               wordLength += fIter.getIndex() - remaining;
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
      fLaoWordSet.applyPattern("[[:Laoo:]&[:LineBreak=SA:]]");
      fLaoWordSet.compact();
      fMarkSet.applyPattern("[[:Laoo:]&[:LineBreak=SA:]&[:M:]]");
      fMarkSet.add(32);
      fEndWordSet = new UnicodeSet(fLaoWordSet);
      fEndWordSet.remove(3776, 3780);
      fBeginWordSet.add(3713, 3758);
      fBeginWordSet.add(3804, 3805);
      fBeginWordSet.add(3776, 3780);
      fMarkSet.compact();
      fEndWordSet.compact();
      fBeginWordSet.compact();
      fLaoWordSet.freeze();
      fMarkSet.freeze();
      fEndWordSet.freeze();
      fBeginWordSet.freeze();
   }
}
